package com.bytrees.chat.client.channelhandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bytrees.chat.message.ConsoleMessageIdl;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

@ChannelHandler.Sharable
public class ProtobufChannelHandler extends SimpleChannelInboundHandler<ConsoleMessageIdl.ConsoleMessage> {
	private static final Logger logger = LoggerFactory.getLogger(ProtobufChannelHandler.class);

	@Override
	public void channelActive(ChannelHandlerContext ctx) {
		//当链接激活时，向服务器发送通知
		ConsoleMessageIdl.ConsoleMessage.Builder builder = ConsoleMessageIdl.ConsoleMessage.newBuilder();
		builder.setUserId(0L);
		builder.setMessage("hello world!");
		ConsoleMessageIdl.ConsoleMessage message = builder.build();
		ctx.writeAndFlush(message);
	}

	@Override
	public void channelRead0(ChannelHandlerContext ctx, ConsoleMessageIdl.ConsoleMessage msg) throws Exception {
		logger.info("[server]{}", msg.getMessage());
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
