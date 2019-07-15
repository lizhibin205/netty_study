package com.bytrees.chat.ws.channelhandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.DefaultHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpUtil;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.LastHttpContent;
import io.netty.util.CharsetUtil;

public class HttpRequestHandler extends SimpleChannelInboundHandler<FullHttpRequest> {
	private static final Logger logger = LoggerFactory.getLogger(HttpRequestHandler.class);
	private String wsUri;

	public HttpRequestHandler(String wsUri) {
		this.wsUri = wsUri;
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {
		if (wsUri.equalsIgnoreCase(request.uri())) {
			//如果请求了WebSocket协议升级，则增加引用计算，并将它传�?�给下一个ChannelInboundHandler
			ctx.fireChannelRead(request.retain());
		} else {
			if (HttpUtil.is100ContinueExpected(request)) {
				//处理100 Continue请求，以符合HTTP 1.1 规范
				FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.CONTINUE);
				ctx.writeAndFlush(response);
			}
			String returnStr = "ok";
			HttpResponse response = new DefaultHttpResponse(request.protocolVersion(), HttpResponseStatus.OK);
			response.headers().set("Content-Type", "text/plain; charset=UTF-8");

			//判断是否keepalive
			boolean isKeepalive = HttpUtil.isKeepAlive(request);
			if (isKeepalive) {
				response.headers().set("Content-Length", returnStr.length());
				response.headers().set("Connection", "keep-alive");
			}
			ctx.write(response);

			//判断是否https
			//if (ctx.pipeline().get(SslHandler.class) == null) {
			//	ctx.write(Unpooled.copiedBuffer(returnStr, CharsetUtil.UTF_8));
			//} else {
			//	ctx.write(Unpooled.copiedBuffer(returnStr, CharsetUtil.UTF_8));
			//}
			ctx.write(Unpooled.copiedBuffer(returnStr, CharsetUtil.UTF_8));
			ChannelFuture future = ctx.writeAndFlush(LastHttpContent.EMPTY_LAST_CONTENT);
			if (! isKeepalive) {
				future.addListener(ChannelFutureListener.CLOSE);
			}
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		logger.error(cause.getMessage(), cause);
		ctx.close();
	}
}
