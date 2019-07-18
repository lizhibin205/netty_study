package com.bytrees.chat;

import java.net.InetSocketAddress;
import java.util.Date;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bytrees.chat.message.ConsoleMessageIdl;
import com.google.protobuf.InvalidProtocolBufferException;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class ChatClient {
	private static final String SERVER_HOST = "127.0.0.1";
	private static final int SERVER_PORT = 9100;
	private static final Logger logger = LoggerFactory.getLogger(ChatClient.class);
	private static final long USER_ID = new Date().getTime();//把当前时间作为userId

	public static void main(String[] args) throws Exception {
		logger.info("chat client start...");
		new ChatClient().start();
		logger.info("bye.");
	}

	public void start() throws InterruptedException {
		EventLoopGroup group = new NioEventLoopGroup();
		try (Scanner sc = new Scanner(System.in)) {
			Bootstrap boot = new Bootstrap();
			boot.group(group).channel(NioSocketChannel.class)
			.remoteAddress(new InetSocketAddress(SERVER_HOST, SERVER_PORT))
			.handler(new ChannelInitializer<SocketChannel>() {
				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					ch.pipeline().addLast(new EchoClientChannel());
				}
			});
			ChannelFuture future = boot.connect().sync();
			//消息发送和接收
			while (sc.hasNext()) {
				String readLine = sc.nextLine();

				ConsoleMessageIdl.ConsoleMessage.Builder builder = ConsoleMessageIdl.ConsoleMessage.newBuilder();
				builder.setUserId(USER_ID);
				builder.setMessage(readLine);
				ConsoleMessageIdl.ConsoleMessage message = builder.build();

				//模拟粘包场景
				//for (int i = 1; i<100; i++) {
				//	future.channel().write(Unpooled.copiedBuffer(message.toByteArray()));
				//}
				//future.channel().flush();

				//这里消息发送是阻塞的-永远不会粘包
				future.channel().writeAndFlush(Unpooled.copiedBuffer(message.toByteArray())).sync();
			}
			future.channel().closeFuture().sync();
		} finally {
			group.shutdownGracefully().sync();
		}
	}

	@ChannelHandler.Sharable
	class EchoClientChannel extends SimpleChannelInboundHandler<ByteBuf> {
		@Override
		public void channelActive(ChannelHandlerContext ctx) {
			//当链接激活时，向服务器发送通知
			ConsoleMessageIdl.ConsoleMessage.Builder builder = ConsoleMessageIdl.ConsoleMessage.newBuilder();
			builder.setUserId(USER_ID);
			builder.setMessage("hello world!");
			ConsoleMessageIdl.ConsoleMessage message = builder.build();
			ctx.writeAndFlush(Unpooled.copiedBuffer(message.toByteArray()));
		}

		@Override
		public void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
			byte[] data = new byte[msg.readableBytes()];
			msg.readBytes(data);
			try {
				ConsoleMessageIdl.ConsoleMessage readMessage = ConsoleMessageIdl.ConsoleMessage.parseFrom(data);
				logger.info("[server]{}", readMessage.getMessage());
			} catch (InvalidProtocolBufferException ex) {
				logger.warn("Server message broken.", ex);
			}
		}

		/**
		 * 引发异常调用
		 */
		@Override
		public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
			logger.error(cause.getMessage(), cause);
			ctx.close();
		}
	}
}
