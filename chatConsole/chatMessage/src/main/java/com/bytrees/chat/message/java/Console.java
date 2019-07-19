package com.bytrees.chat.message.java;

import java.io.Serializable;

public class Console implements Serializable {
	private static final long serialVersionUID = 1L;

	private String message;

	public Console() {}
	public Console(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return message;
	}
}
