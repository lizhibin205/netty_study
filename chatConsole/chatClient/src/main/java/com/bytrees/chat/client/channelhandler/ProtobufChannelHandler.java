package com.bytrees.chat.client.channelhandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bytrees.chat.message.ConsoleMessageIdl;
import com.google.protobuf.InvalidProtocolBufferException;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

@ChannelHandler.Sharable
public class ProtobufChannelHandler extends SimpleChannelInboundHandler<ByteBuf> {
	private static final Logger logger = LoggerFactory.getLogger(ProtobufChannelHandler.class);

	@Override
	public void channelActive(ChannelHandlerContext ctx) {
		//当链接激活时，向服务器发送通知
		ConsoleMessageIdl.ConsoleMessage.Builder builder = ConsoleMessageIdl.ConsoleMessage.newBuilder();
		builder.setUserId(0L);
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
