package com.bytrees.chat.ws.channelhandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.bytrees.chat.ws.room.SimpleMessage;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.ChannelMatcher;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;

public class TextWebSocketFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
	private static final Logger logger = LoggerFactory.getLogger(TextWebSocketFrameHandler.class);
	private final ChannelGroup group;

	public TextWebSocketFrameHandler(ChannelGroup group) {
		this.group = group;
	}

	/**
	 * 当连接成功时触发
	 */
	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object event) throws Exception {
		Channel channel = ctx.channel();
		logger.info("Client on userEventTriggered. Client:{}, Event:{}", channel, event);
		if (event instanceof WebSocketServerProtocolHandler.HandshakeComplete) {
			//如果握手成功，移除pipeline中的HttpRequestHandler，因为之后不会再使用HTTP
			ctx.pipeline().remove(HttpRequestHandler.class);
			//广播加入聊天室
			//group.writeAndFlush(new TextWebSocketFrame(SimpleMessage.serverMessage(channel.toString(), 
			//		"加入聊天室")));
			//加入到组中，所有人都可以收到信息
			//group.add(ctx.channel());
			ctx.channel().writeAndFlush(stringToFrame(SimpleMessage.welcome()));
		}
		super.userEventTriggered(ctx, event);
	}

	/**
	 * 当接收到信息时响应
	 */
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
		String text = msg.text();
		logger.info("Client on channelRead0. Client:{}, Message:{}", ctx.channel(), text);

		//向组里面的其他Channel广播信息
		//TextWebSocketChannelMatcher matcher = new TextWebSocketChannelMatcher(ctx.channel());
		//group.writeAndFlush(new TextWebSocketFrame(SimpleMessage.serverMessage(ctx.channel().toString(), 
		//		text)), matcher);
		ctx.channel().writeAndFlush(stringToFrame(text));
	}

	/**
	 * 把返回内容包装成Text帧
	 */
	private TextWebSocketFrame stringToFrame(String str) {
		return new TextWebSocketFrame(str);
	}

	class TextWebSocketChannelMatcher implements ChannelMatcher {
		private final Channel myChannel;

		public TextWebSocketChannelMatcher(Channel myChannel) {
			this.myChannel = myChannel;
		}

		@Override
		public boolean matches(Channel channel) {
			//不等于当前channel时返回true
			return !channel.equals(myChannel);
		}
	}
}
