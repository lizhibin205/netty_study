package com.bytrees.chat.ws.channelhandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bytrees.chat.ws.message.WebSocketMessageIdl;
import com.bytrees.chat.ws.task.TaskExecutors;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;

public class BinaryWebSocketFrameHandler extends SimpleChannelInboundHandler<BinaryWebSocketFrame> {
	private static final Logger logger = LoggerFactory.getLogger(BinaryWebSocketFrameHandler.class);
	private final ChannelGroup group;
	private final TaskExecutors taskExecutors;

	public BinaryWebSocketFrameHandler(ChannelGroup group, TaskExecutors taskExecutors) {
		this.group = group;
		this.taskExecutors = taskExecutors;
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, BinaryWebSocketFrame msg) throws Exception {
		ByteBuf byteBuf = msg.content();
		WebSocketMessageIdl.WebSocketMessage message = WebSocketMessageIdl.WebSocketMessage.parseFrom(byteBuf.nioBuffer());
		logger.info("client on channelRead0.Thread:{},  Client:{}, Message:{}", Thread.currentThread().getName(), 
			ctx.channel(), message.getMessageContent());

		ctx.channel().writeAndFlush(stringToFrame(message.getMessageContent()));
	}

	/**
	 * 把返回内容包装成Binary帧
	 */
	private BinaryWebSocketFrame stringToFrame(String str) {
		WebSocketMessageIdl.WebSocketMessage.Builder builder =  WebSocketMessageIdl.WebSocketMessage.newBuilder();
		builder.setClientId(0L);
		builder.setMessageType(WebSocketMessageIdl.MessageType.STRING);
		builder.setMessageContent(str);
		return new BinaryWebSocketFrame(Unpooled.copiedBuffer(builder.build().toByteArray()));
	}
}
