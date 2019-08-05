package com.bytrees.chat.ws.channelhandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.bytrees.chat.ws.task.TaskExecutors;
import com.bytrees.chat.ws.task.TextFrameTask;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;

public class TextWebSocketFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
	private static final Logger logger = LoggerFactory.getLogger(TextWebSocketFrameHandler.class);
	private final TaskExecutors taskExecutors;

	public TextWebSocketFrameHandler(TaskExecutors taskExecutors) {
		this.taskExecutors = taskExecutors;
	}

	/**
	 * 当连接成功时触发
	 */
	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object event) throws Exception {
		Channel channel = ctx.channel();
		logger.info("Client on userEventTriggered.Thread:{}, Client:{}, Event:{}", Thread.currentThread().getName(), channel, event);
		if (event instanceof WebSocketServerProtocolHandler.HandshakeComplete) {
			//如果握手成功，移除pipeline中的HttpRequestHandler，因为之后不会再使用HTTP
			ctx.pipeline().remove(HttpRequestHandler.class);
		}
		super.userEventTriggered(ctx, event);
	}

	/**
	 * 当接收到信息时响应
	 */
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
		String text = msg.text();
		logger.info("client on channelRead0.Thread:{},  Client:{}, Message:{}", Thread.currentThread().getName(), 
			ctx.channel(), text);

		//使用业务线程池执行任务
		taskExecutors.execute(new TextFrameTask(ctx, text));
	}
}
