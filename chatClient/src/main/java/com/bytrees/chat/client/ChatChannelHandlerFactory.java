package com.bytrees.chat.client;

import com.bytrees.chat.client.channelhandler.DelimiterChannelHandler;
import com.bytrees.chat.client.channelhandler.JavaChannelHandler;
import com.bytrees.chat.client.channelhandler.ProtobufChannelHandler;
import com.bytrees.chat.client.channelhandler.StringChannelHandler;
import com.bytrees.chat.message.ConsoleMessageIdl;

import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.util.CharsetUtil;

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
					//进行半包处理
					ch.pipeline().addLast(new ProtobufVarint32FrameDecoder());
					//添加解码器，用于指定解码的目标是什么，否则单凭字节码是无法判断目标类型
					ch.pipeline().addLast(new ProtobufDecoder(ConsoleMessageIdl.ConsoleMessage.getDefaultInstance()));
					//添加编码器
					ch.pipeline().addLast(new ProtobufVarint32LengthFieldPrepender());
					ch.pipeline().addLast(new ProtobufEncoder());
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
		} else if(chatProtocol.equals(ChatProtocolEnum.JAVA)) {
			return new ChannelInitializer<SocketChannel>() {
				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					ch.pipeline().addLast(new ObjectDecoder(1024, ClassResolvers.weakCachingConcurrentResolver(
							ChatClient.class.getClassLoader())));
					ch.pipeline().addLast(new ObjectEncoder());
					ch.pipeline().addLast(new JavaChannelHandler());
				}
			};
		}
		return null;
	}
}
