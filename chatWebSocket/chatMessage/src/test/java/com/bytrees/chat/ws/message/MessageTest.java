package com.bytrees.chat.ws.message;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MessageTest {
	private static final Logger logger = LoggerFactory.getLogger(MessageTest.class);

	@Test
	public void messageTest() {
		String str = "你好吗";
		WebSocketMessageIdl.WebSocketMessage message = WebSocketMessageIdl.WebSocketMessage.newBuilder()
				.setMessageTypeValue(1)
				.setMessageContent(str)
				.build();
		logger.info("message: {}", message);
	}
}
