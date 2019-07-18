package com.bytrees.chat;

import java.net.InetSocketAddress;
import java.util.Date;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bytrees.chat.message.ConsoleMessage;

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

				ConsoleMessage.ConsoleMessageIdl.Builder builder = ConsoleMessage.ConsoleMessageIdl.newBuilder();
				builder.setUserId(USER_ID);
				builder.setMessage(readLine);
				ConsoleMessage.ConsoleMessageIdl message = builder.build();

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
			ConsoleMessage.ConsoleMessageIdl.Builder builder = ConsoleMessage.ConsoleMessageIdl.newBuilder();
			builder.setUserId(USER_ID);
			builder.setMessage("hello world!");
			ConsoleMessage.ConsoleMessageIdl message = builder.build();
			ctx.writeAndFlush(Unpooled.copiedBuffer(message.toByteArray()));
		}

		@Override
		public void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
			byte[] data = new byte[msg.readableBytes()];
			msg.readBytes(data);
			logger.info("[server]{}", data);
		}

		/**
		 * 引发异常调用
		 */
		@Override
		public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
			logger.error("exception caught!", cause);
			ctx.close();
		}
	}
}
