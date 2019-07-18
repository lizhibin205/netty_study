package com.bytrees.chat.client;

public enum ChatProtocolEnum {
	STRINGLINE(1, "String line protocol."),
	PROTOBUF(2, "Google protobuf protocol.");

	private final int typeId;
	private final String typeName;
	private ChatProtocolEnum(int typeId, String typeName) {
		this.typeId = typeId;
		this.typeName = typeName;
	}

	public int getTypeId() {
		return typeId;
	}

	@Override
	public String toString() {
		return new StringBuilder(typeId).append("-").append(typeName).toString();
	}
}
