package com.bytrees.chat.client;

public enum ChatProtocolEnum {
	STRINGLINE(1),
	PROTOBUF(2);

	private int typeId;
	private ChatProtocolEnum(int typeId) {
		this.typeId = typeId;
	}
	public int getTypeId() {
		return typeId;
	}
}
