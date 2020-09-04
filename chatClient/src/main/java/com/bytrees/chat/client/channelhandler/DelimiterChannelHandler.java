package com.bytrees.chat.client.channelhandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

public class DelimiterChannelHandler extends SimpleChannelInboundHandler<String> {
	private static final Logger logger = LoggerFactory.getLogger(DelimiterChannelHandler.class);

	@Override
	public void channelActive(ChannelHandlerContext ctx) {
		//当链接激活时，向服务器发送通知
		ctx.writeAndFlush(Unpooled.copiedBuffer("hello world!;", CharsetUtil.UTF_8));
	}

	@Override
	public void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
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
