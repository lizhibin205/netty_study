package com.bytrees.chat.client;

import com.bytrees.chat.client.channelhandler.ProtobufChannelHandler;
import com.bytrees.chat.client.channelhandler.StringChannelHandler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

public class ChatChannelHandlerFactory {
	private ChatChannelHandlerFactory() {}

	public static ChannelHandler getChannelInitializer(ChatProtocolEnum chatProtocol) {
		if (chatProtocol.equals(ChatProtocolEnum.STRINGLINE)) {
			return new ChannelInitializer<Channel>() {
				@Override
				protected void initChannel(Channel ch) throws Exception {
					ch.pipeline().addLast(new LineBasedFrameDecoder(1024));
					ch.pipeline().addLast(new StringDecoder());
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
