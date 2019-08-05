package com.bytrees.chat.ws.task;

import com.bytrees.chat.ws.message.WebSocketMessageIdl.WebSocketMessage;
import com.bytrees.chat.ws.qa.QuestionAnsweringSystem;

import io.netty.channel.ChannelHandlerContext;

public class BinaryFrameTask extends AbstractBinaryFrameTask {
	public BinaryFrameTask(ChannelHandlerContext ctx, WebSocketMessage message) {
		super(ctx, message);
	}

	@Override
	public void run() {
		String answer = QuestionAnsweringSystem.answer(message.getMessageContent());
		ctx.writeAndFlush(stringToBinaryFrame(answer));
	}
}
