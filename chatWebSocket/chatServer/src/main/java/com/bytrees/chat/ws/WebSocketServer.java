package com.bytrees.chat.ws;

import java.net.InetSocketAddress;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.ImmediateEventExecutor;

public class WebSocketServer {
	private static final int SERVER_PORT = 9100;
	private static final Logger logger = LoggerFactory.getLogger(WebSocketServer.class);

	public static void main(String[] args) {
		logger.info("Starting web socket server...");
		new WebSocketServer().run();
		logger.info("bye");
	}

	/**
	 * WEBSOCKET SERVER启动
	 */
	public void run() {
		final EventLoopGroup group = new NioEventLoopGroup();
		final ChannelGroup channelGroup = new DefaultChannelGroup(ImmediateEventExecutor.INSTANCE);

		ServerBootstrap boot = new ServerBootstrap();
		boot.group(group)
		.channel(NioServerSocketChannel.class)
		.childHandler(new ChatServerInitializer(channelGroup));

		ChannelFuture future = boot.bind(new InetSocketAddress(SERVER_PORT));
		future.syncUninterruptibly();
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				if (future.channel() != null) {
					future.channel().close();
				}
				channelGroup.close();
				group.shutdownGracefully();
			}
		});
		future.channel().closeFuture().syncUninterruptibly();
	}
}
