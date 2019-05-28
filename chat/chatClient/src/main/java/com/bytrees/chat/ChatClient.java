package com.bytrees.chat;

import java.net.InetSocketAddress;
import java.util.Scanner;

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
import io.netty.util.CharsetUtil;

public class ChatClient {
	private final static String SERVER_HOST = "127.0.0.1";
    private final static int SERVER_PORT = 9100;
	
    public static void main(String[] args) throws Exception {
    	System.out.println("Starting chat client.");
    	new ChatClient().start();
    	System.out.println("bye.");
    }
    public void start() throws Exception {
    	EventLoopGroup group = new NioEventLoopGroup();
    	try {
    		Bootstrap boot = new Bootstrap();
    		boot.group(group).channel(NioSocketChannel.class)
    		.remoteAddress(new InetSocketAddress(SERVER_HOST, SERVER_PORT))
    		.handler(new ChannelInitializer<SocketChannel>() {
				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					// TODO Auto-generated method stub
						ch.pipeline().addLast(new EchoClientChannel());
				}
    		});
    		ChannelFuture future = boot.connect().sync();
            //消息发送和接收
    		Scanner sc = new Scanner(System.in);
    		while (sc.hasNext()) {
    			String message = sc.nextLine();
    			future.channel().writeAndFlush(Unpooled.copiedBuffer(message, CharsetUtil.UTF_8)).sync();
    		}
    		future.channel().closeFuture().sync();
    		sc.close();
    	} finally {
    		group.shutdownGracefully().sync();
    	}
    }

    @ChannelHandler.Sharable
    class EchoClientChannel extends SimpleChannelInboundHandler<ByteBuf> {
    	@Override
        public void channelActive(ChannelHandlerContext ctx) {
        	ctx.writeAndFlush(Unpooled.copiedBuffer("Netty rocks!", CharsetUtil.UTF_8));
        }

		@Override
		public void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
			// TODO Auto-generated method stub
			System.out.println("[server]" + msg.toString(CharsetUtil.UTF_8));
		}

		/**
		 * 引发异常调用
		 */
		@Override
    	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
    		cause.printStackTrace();
    		ctx.close();
    	}
    }
}
