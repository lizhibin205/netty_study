package com.bytrees.chat;

import java.net.InetSocketAddress;

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

    public static void main(String[] args)  throws Exception  {
    	System.out.println("Starting web socket server...");
    	new WebSocketServer().run();
    	System.out.println("bye");
    }

    public void run() throws Exception {
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
