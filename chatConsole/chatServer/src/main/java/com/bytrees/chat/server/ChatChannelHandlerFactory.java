package com.bytrees.chat.server;

import com.bytrees.chat.server.channelhandler.ProtobufChannelHandler;
import com.bytrees.chat.server.channelhandler.StringChannelHandler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

public class ChatChannelHandlerFactory {
	private ChatChannelHandlerFactory() {}

	public static ChannelHandler getChannelInitializer(ChatProtocolEnum chatProtocol) {
		if (chatProtocol.equals(ChatProtocolEnum.STRINGLINE)) {
			return new ChannelInitializer<SocketChannel>() {
				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					ch.pipeline().addLast(new StringChannelHandler());
				}
			};
		} else if (chatProtocol.equals(ChatProtocolEnum.PROTOBUF)) {
			return new ChannelInitializer<SocketChannel>() {
				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					ch.pipeline().addLast(new ProtobufChannelHandler());
				}
			};
		}
		return null;
	}
}
