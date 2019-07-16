package com.bytrees.chat.ws.room;

public class SimpleMessage {
	private SimpleMessage() {}

	/**
	 * 包装服务器回复
	 * @return
	 */
	public static String serverMessage(String client, String message) {
		return new StringBuilder("[").append(client).append("]")
				.append(message).toString();
	}

	/**
	 * 包装服务器回复
	 * @return
	 */
	public static String serverMessage(String client, byte[] message) {
		return new StringBuilder("[").append(client).append("]")
				.append(message).toString();
	}
}
