package com.bytrees.chat.ChannelHandler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;

public class TextWebSocketFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
	private final ChannelGroup group;

	public TextWebSocketFrameHandler(ChannelGroup group) {
		this.group = group;
	}

	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object event) throws Exception {
		if (event instanceof WebSocketServerProtocolHandler.HandshakeComplete) {
			//如果握手成功，移除pipline中的http消息，因为之后不会再使用http了
			ctx.pipeline().remove(HttpRequestHandler.class);
			//通知所有客户端有新人加入
			group.writeAndFlush(new TextWebSocketFrame("Client " + ctx.channel().toString() + "joined."));
			//加入到组中，可以收到所有信息
			group.add(ctx.channel());
		} else {
			super.userEventTriggered(ctx, event);
		}
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
		// TODO Auto-generated method stub
		//增加消息的引用计数，并将它写到ChannelGroup中所有已连接的客户端
		group.writeAndFlush(msg.retain());
	}
}
