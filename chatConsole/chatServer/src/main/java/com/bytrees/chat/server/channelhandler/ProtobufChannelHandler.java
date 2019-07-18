package com.bytrees.chat.server.channelhandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bytrees.chat.message.ConsoleMessageIdl;
import com.google.protobuf.InvalidProtocolBufferException;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

@ChannelHandler.Sharable
public class ProtobufChannelHandler extends ChannelInboundHandlerAdapter {
	private static final Logger logger = LoggerFactory.getLogger(ProtobufChannelHandler.class);

	/**
	 * 对于每一个传入的消息都要调用
	 */
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) {
		ByteBuf in = (ByteBuf) msg;
		String remoteAddress = ctx.channel().remoteAddress().toString();
		try {
			//不要使用in.array()
			//Netty默认的I/O Buffer使用直接内存DirectByteBuf，可以减少Socket读写的内存拷贝，即著名的 ”零拷贝”。
			//由于是直接内存，因此无法直接转换成堆内存，因此它并不支持array()方法。用户需要自己做内存拷贝。
			byte[] readIn = new byte[in.readableBytes()];
			in.getBytes(in.readerIndex(), readIn);
			//构造ProtoBuf信息
			ConsoleMessageIdl.ConsoleMessage readMessage = ConsoleMessageIdl.ConsoleMessage.parseFrom(readIn);
			ConsoleMessageIdl.ConsoleMessage.Builder builder = ConsoleMessageIdl.ConsoleMessage.newBuilder();
			builder.setUserId(0);
			builder.setMessage("Received: " + readMessage.getMessage());
			ConsoleMessageIdl.ConsoleMessage message = builder.build();
			//向客户端发送信息
			ctx.write(Unpooled.copiedBuffer(message.toByteArray()));
			logger.info("[{}] {}", remoteAddress, readMessage.getMessage());
		} catch (InvalidProtocolBufferException ex) {
			logger.warn("[{}] {}", remoteAddress, "Client message broken.", ex);
		}

		//需要显式释放资源
		ReferenceCountUtil.release(msg);
	}

	/**
	 * 读取完成后事件
	 * 通知ChannelInboundHandler最后一次channelRead的调用是当前批量读取中的最后一条消息
	 */
	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) {
		ctx.flush();
		//这个实现了收到消息之后就关闭连接
		//ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
	}

	/**
	 * 在读取操作期间，有异常抛出的时候会调用
	 */
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		logger.error(cause.getMessage(), cause);
		ctx.close();
	}
}
