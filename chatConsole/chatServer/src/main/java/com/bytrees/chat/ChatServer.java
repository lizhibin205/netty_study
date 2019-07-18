package com.bytrees.chat;

import java.net.InetSocketAddress;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bytrees.chat.message.ConsoleMessage;
import com.google.protobuf.InvalidProtocolBufferException;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.ReferenceCountUtil;

public class ChatServer {
	private static final int PORT = 9100;
	private static final Logger logger = LoggerFactory.getLogger(ChatServer.class);

	public static void main(String[] args) throws Exception {
		logger.info("chat server start...");
		new ChatServer().start();
		logger.info("bye.");
	}

	/**
	 * 在main线程中启动服务
	 */
	public void start() throws InterruptedException {
		//创建NIO的服务端线程组
		final EventLoopGroup group = new NioEventLoopGroup();
		final EchoServerHandler server = new EchoServerHandler();
		try {
			ServerBootstrap boot = new ServerBootstrap();
			boot.group(group)
			.channel(NioServerSocketChannel.class)
			.option(ChannelOption.SO_BACKLOG, 128)
			.localAddress(new InetSocketAddress(PORT))
			.childHandler(new ChannelInitializer<SocketChannel>() {

				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					ch.pipeline().addLast(server);
				}

			});
			//绑定端口，同步等待成功-这里会阻塞主线程main
			ChannelFuture future = boot.bind().sync();
			//等等服务端监听端口关闭
			future.channel().closeFuture().sync();
		} finally {
			//优雅退出，释放线程池资源
			group.shutdownGracefully().sync();
		}
	}

	@ChannelHandler.Sharable
	class EchoServerHandler extends ChannelInboundHandlerAdapter {
		/**
		 * 对于每一个传入的消息都要调用
		 */
		@Override
		public void channelRead(ChannelHandlerContext ctx, Object msg) {
			ByteBuf in = (ByteBuf) msg;
			String remoteAddress = ctx.channel().remoteAddress().toString();
			try {
				//不要使用in.array()
				//Netty默认的I/O Buffer使用直接内存DirectByteBuf，可以减少Socket读写的内存拷贝，即著名的 ”零拷贝”。
				//由于是直接内存，因此无法直接转换成堆内存，因此它并不支持array()方法。用户需要自己做内存拷贝。
				byte[] readIn = new byte[in.readableBytes()];
				in.getBytes(in.readerIndex(), readIn);
				//构造Protobuf信息
				ConsoleMessage.ConsoleMessageIdl readMessage = ConsoleMessage.ConsoleMessageIdl.parseFrom(readIn);
				ConsoleMessage.ConsoleMessageIdl.Builder builder = ConsoleMessage.ConsoleMessageIdl.newBuilder();
				builder.setUserId(0);
				builder.setMessage("Received: " + readMessage.getMessage());
				ConsoleMessage.ConsoleMessageIdl message = builder.build();
				//向客户端发送信息
				ctx.write(Unpooled.copiedBuffer(message.toByteArray()));
				logger.info("[{}] {}", remoteAddress, readMessage.getMessage());
			} catch (InvalidProtocolBufferException ex) {
				logger.warn("[{}] {}", remoteAddress, "Client message broken.", ex);
			}

			//需要显式释放资源
			ReferenceCountUtil.release(msg);
		}

		/**
		 * 读取完成后事件
		 * 通知ChannelInboundHandler最后一次channelRead的调用是当前批量读取中的最后一条消息
		 */
		@Override
		public void channelReadComplete(ChannelHandlerContext ctx) {
			ctx.flush();
			//这个实现了收到消息之后就关闭连接
			//ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
		}

		/**
		 * 在读取操作期间，有异常抛出的时候会调用
		 */
		@Override
		public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
			logger.error("exception caught!", cause);
			ctx.close();
		}
	}
}
