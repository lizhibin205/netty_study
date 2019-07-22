package com.bytrees.chat.ws.room;

public class SimpleMessage {
	private static final String WELCOME = "连接服务器成功";
	private SimpleMessage() {}

	/**
	 * 服务器欢迎语
	 * @return
	 */
	public static String welcome() {
		return WELCOME;
	}

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
