package com.bytrees.chat.client;

import java.net.InetSocketAddress;
import java.util.Date;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bytrees.chat.message.ConsoleMessageIdl;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.CharsetUtil;

public class ChatClient {
	private static final String SERVER_HOST = "127.0.0.1";
	private static final int SERVER_PORT = 9100;
	private static final Logger logger = LoggerFactory.getLogger(ChatClient.class);
	private static final long USER_ID = new Date().getTime();//把当前时间作为userId

	public static void main(String[] args) throws Exception {
		logger.info("chat client start...");
		new ChatClient().start(ChatProtocolEnum.STRINGLINE);
		logger.info("bye.");
	}

	public void start(ChatProtocolEnum chatProtocol) throws InterruptedException {
		EventLoopGroup group = new NioEventLoopGroup();
		ChannelHandler channelInitializer = ChatChannelHandlerFactory.getChannelInitializer(chatProtocol);
		if (channelInitializer == null) {
			logger.error("channelInitializer is null.");
			return;
		}
		try (Scanner sc = new Scanner(System.in)) {
			Bootstrap boot = new Bootstrap();
			boot.group(group).channel(NioSocketChannel.class)
			.remoteAddress(new InetSocketAddress(SERVER_HOST, SERVER_PORT))
			.handler(channelInitializer);
			ChannelFuture future = boot.connect().sync();
			//消息发送和接收
			while (sc.hasNext()) {
				String readLine = sc.nextLine();
				if (readLine.equals("quit")) {
					break;
				}

				if (chatProtocol.equals(ChatProtocolEnum.STRINGLINE)) {
					sendStringLineProtocol(future, readLine);
				} else if (chatProtocol.equals(ChatProtocolEnum.PROTOBUF)) {
					sendProtobufProtocol(future, readLine);
				}
			}
			future.channel().closeFuture().sync();
		} finally {
			group.shutdownGracefully().sync();
		}
	}

	/**
	 * 字符串协议内容发送
	 */
	private void sendStringLineProtocol(final ChannelFuture future, final String readLine) throws InterruptedException {
		//一个可以产生粘包的例子
		String sendLine = new StringBuilder(readLine).append("\n").toString();
		for (int i = 1; i<=5; i++) {
			future.channel().write(Unpooled.copiedBuffer(sendLine, CharsetUtil.UTF_8));
		}
		future.channel().flush();
	}

	private void sendProtobufProtocol(final ChannelFuture future, final String readLine) throws InterruptedException  {
		ConsoleMessageIdl.ConsoleMessage.Builder builder = ConsoleMessageIdl.ConsoleMessage.newBuilder();
		builder.setUserId(USER_ID);
		builder.setMessage(readLine);
		ConsoleMessageIdl.ConsoleMessage message = builder.build();
		//这里消息发送是阻塞的-永远不会粘包
		future.channel().writeAndFlush(Unpooled.copiedBuffer(message.toByteArray())).sync();
	}
}
