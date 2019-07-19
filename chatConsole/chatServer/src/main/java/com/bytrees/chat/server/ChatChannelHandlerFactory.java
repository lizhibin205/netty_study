package com.bytrees.chat.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bytrees.chat.server.channelhandler.DelimiterChannelHandler;
import com.bytrees.chat.server.channelhandler.JavaChannelHandler;
import com.bytrees.chat.server.channelhandler.ProtobufChannelHandler;
import com.bytrees.chat.server.channelhandler.StringChannelHandler;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.util.CharsetUtil;

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
		} else if (chatProtocol.equals(ChatProtocolEnum.DELIMITER)) {
			return new ChannelInitializer<SocketChannel>() {
				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					ch.pipeline().addLast(new DelimiterBasedFrameDecoder(1024, Unpooled.copiedBuffer(";", CharsetUtil.UTF_8)));
					ch.pipeline().addLast(new StringDecoder());
					ch.pipeline().addLast(new DelimiterChannelHandler());
				}
			};
		} else if (chatProtocol.equals(ChatProtocolEnum.JAVA)) {
			return new ChannelInitializer<SocketChannel>() {
				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					ch.pipeline().addLast(new ObjectDecoder(1024, ClassResolvers.weakCachingConcurrentResolver(
							ChatServer.class.getClassLoader())));
					ch.pipeline().addLast(new ObjectEncoder());
					ch.pipeline().addLast(new JavaChannelHandler());
				}
			};
		}
		return null;
	}
}
