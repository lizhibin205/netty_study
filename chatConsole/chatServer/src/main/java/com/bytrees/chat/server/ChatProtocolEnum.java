package com.bytrees.chat.server;

public enum ChatProtocolEnum {
	STRINGLINE(1, "String line protocol."),
	PROTOBUF(2, "Google protobuf protocol."),
	DELIMITER(3, "Delimiter protocol.");

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
		return new StringBuilder(String.valueOf(typeId)).append("-").append(typeName).toString();
	}
}
