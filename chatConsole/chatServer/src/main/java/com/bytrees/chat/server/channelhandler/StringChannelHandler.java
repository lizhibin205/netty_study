package com.bytrees.chat.server.channelhandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;

@ChannelHandler.Sharable
public class StringChannelHandler extends ChannelInboundHandlerAdapter {
	private static final Logger logger = LoggerFactory.getLogger(StringChannelHandler.class);

	/**
	 * 对于每一个传入的消息都要调用
	 */
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) {
		String in = (String) msg;
		String remoteAddress = ctx.channel().remoteAddress().toString();

		//向客户端发送信息
		ctx.write(Unpooled.copiedBuffer(new StringBuilder(in).append("\n").toString(), CharsetUtil.UTF_8));
		logger.info("[{}] {}", remoteAddress, in);

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
