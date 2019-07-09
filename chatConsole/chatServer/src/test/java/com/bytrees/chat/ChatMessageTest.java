package com.bytrees.chat;
import org.junit.Assert;
import org.junit.Test;
import com.bytrees.chat.message.ConsoleMessage;
import com.google.protobuf.InvalidProtocolBufferException;

public class ChatMessageTest {
	@Test
	public void messageTest() {
		ConsoleMessage.ConsoleMessageIdl.Builder builder = ConsoleMessage.ConsoleMessageIdl.newBuilder();
		builder.setUserId(0);
		builder.setMessage("hello world!");

		ConsoleMessage.ConsoleMessageIdl message = builder.build();
		//反序列化
		try {
			ConsoleMessage.ConsoleMessageIdl readMessage = ConsoleMessage.ConsoleMessageIdl.parseFrom(message.toByteArray());
			Assert.assertTrue(readMessage.getUserId() == 0);
			Assert.assertTrue(readMessage.getMessage().equals("hello world!"));
		} catch (InvalidProtocolBufferException ex) {
			Assert.fail(ex.getMessage());
		}
	}
}
