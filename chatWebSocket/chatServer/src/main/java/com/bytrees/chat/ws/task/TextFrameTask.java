package com.bytrees.chat.ws.task;

import com.bytrees.chat.ws.qa.QuestionAnsweringSystem;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

public class TextFrameTask implements Runnable {
	private ChannelHandlerContext ctx;
	private String message;

	public TextFrameTask(ChannelHandlerContext ctx, String message) {
		this.ctx = ctx;
		this.message = message;
	}

	@Override
	public void run() {
		String answer = QuestionAnsweringSystem.answer(message);
		ctx.channel().writeAndFlush(new TextWebSocketFrame(answer));
	}
}
