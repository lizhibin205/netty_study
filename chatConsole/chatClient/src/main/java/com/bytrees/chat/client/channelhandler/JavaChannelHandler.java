package com.bytrees.chat.client.channelhandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bytrees.chat.message.java.Console;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

@ChannelHandler.Sharable
public class JavaChannelHandler  extends SimpleChannelInboundHandler<Console> {
	private static final Logger logger = LoggerFactory.getLogger(JavaChannelHandler.class);

	@Override
	public void channelActive(ChannelHandlerContext ctx) {
		//当链接激活时，向服务器发送通知
		ctx.writeAndFlush(new Console("hello world!"));
	}

	@Override
	public void channelRead0(ChannelHandlerContext ctx, Console msg) throws Exception {
		logger.info("[server]{}", msg);
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
