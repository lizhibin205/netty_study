package com.bytrees.chat.server;

import java.net.InetSocketAddress;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;


public class ChatServer {
	private static final int PORT = 9100;
	private static final Logger logger = LoggerFactory.getLogger(ChatServer.class);

	public static void main(String[] args) throws Exception {
		logger.info("chat server start...");
		new ChatServer().start(ChatProtocolEnum.PROTOBUF);
		logger.info("bye.");
	}

	/**
	 * 在main线程中启动服务
	 */
	public void start(ChatProtocolEnum chatProtocol) throws InterruptedException {
		//创建NIO的服务端线程组
		final EventLoopGroup group = new NioEventLoopGroup();
		try {
			ServerBootstrap boot = new ServerBootstrap();
			boot.group(group)
			.channel(NioServerSocketChannel.class)
			.option(ChannelOption.SO_BACKLOG, 128)
			.localAddress(new InetSocketAddress(PORT))
			.childHandler(ChatChannelHandlerFactory.getChannelInitializer(chatProtocol));
			//绑定端口，同步等待成功-这里会阻塞主线程main
			ChannelFuture future = boot.bind().sync();
			//等等服务端监听端口关闭
			future.channel().closeFuture().sync();
		} finally {
			//优雅退出，释放线程池资源
			group.shutdownGracefully().sync();
		}
	}
}
