package com.bytrees.chat.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bytrees.chat.server.channelhandler.ProtobufChannelHandler;
import com.bytrees.chat.server.channelhandler.StringChannelHandler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

public class ChatChannelHandlerFactory {
	private static final Logger logger = LoggerFactory.getLogger(ChatChannelHandlerFactory.class);
	private ChatChannelHandlerFactory() {}

	public static ChannelHandler getChannelInitializer(ChatProtocolEnum chatProtocol) {
		logger.info("channel initializer: {}", chatProtocol);
		if (chatProtocol.equals(ChatProtocolEnum.STRINGLINE)) {
			return new ChannelInitializer<SocketChannel>() {
				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
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
