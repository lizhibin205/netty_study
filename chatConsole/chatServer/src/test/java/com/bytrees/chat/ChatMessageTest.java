package com.bytrees.chat;
import org.junit.Assert;
import org.junit.Test;
import com.bytrees.chat.message.ConsoleMessageIdl;
import com.google.protobuf.InvalidProtocolBufferException;

public class ChatMessageTest {
	@Test
	public void messageTest() {
		ConsoleMessageIdl.ConsoleMessage.Builder builder = ConsoleMessageIdl.ConsoleMessage.newBuilder();
		builder.setUserId(0);
		builder.setMessage("hello world!");

		ConsoleMessageIdl.ConsoleMessage message = builder.build();
		//反序列化
		try {
			ConsoleMessageIdl.ConsoleMessage readMessage = ConsoleMessageIdl.ConsoleMessage.parseFrom(message.toByteArray());
			Assert.assertTrue(readMessage.getUserId() == 0);
			Assert.assertTrue(readMessage.getMessage().equals("hello world!"));
		} catch (InvalidProtocolBufferException ex) {
			Assert.fail(ex.getMessage());
		}
	}
}
