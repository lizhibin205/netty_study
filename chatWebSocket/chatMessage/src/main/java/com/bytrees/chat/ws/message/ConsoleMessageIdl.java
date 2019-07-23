// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: ConsoleMessageIdl.proto

package com.bytrees.chat.ws.message;

public final class ConsoleMessageIdl {
  private ConsoleMessageIdl() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  public interface ConsoleMessageOrBuilder extends
      // @@protoc_insertion_point(interface_extends:com.bytrees.chat.ws.message.ConsoleMessage)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <code>int64 userId = 1;</code>
     */
    long getUserId();

    /**
     * <code>string message = 2;</code>
     */
    java.lang.String getMessage();
    /**
     * <code>string message = 2;</code>
     */
    com.google.protobuf.ByteString
        getMessageBytes();
  }
  /**
   * Protobuf type {@code com.bytrees.chat.ws.message.ConsoleMessage}
   */
  public  static final class ConsoleMessage extends
      com.google.protobuf.GeneratedMessageV3 implements
      // @@protoc_insertion_point(message_implements:com.bytrees.chat.ws.message.ConsoleMessage)
      ConsoleMessageOrBuilder {
  private static final long serialVersionUID = 0L;
    // Use ConsoleMessage.newBuilder() to construct.
    private ConsoleMessage(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }
    private ConsoleMessage() {
      message_ = "";
    }

    @java.lang.Override
    @SuppressWarnings({"unused"})
    protected java.lang.Object newInstance(
        UnusedPrivateParameter unused) {
      return new ConsoleMessage();
    }

    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet
    getUnknownFields() {
      return this.unknownFields;
    }
    private ConsoleMessage(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      this();
      if (extensionRegistry == null) {
        throw new java.lang.NullPointerException();
      }
      com.google.protobuf.UnknownFieldSet.Builder unknownFields =
          com.google.protobuf.UnknownFieldSet.newBuilder();
      try {
        boolean done = false;
        while (!done) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
              done = true;
              break;
            case 8: {

              userId_ = input.readInt64();
              break;
            }
            case 18: {
              java.lang.String s = input.readStringRequireUtf8();

              message_ = s;
              break;
            }
            default: {
              if (!parseUnknownField(
                  input, unknownFields, extensionRegistry, tag)) {
                done = true;
              }
              break;
            }
          }
        }
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        throw e.setUnfinishedMessage(this);
      } catch (java.io.IOException e) {
        throw new com.google.protobuf.InvalidProtocolBufferException(
            e).setUnfinishedMessage(this);
      } finally {
        this.unknownFields = unknownFields.build();
        makeExtensionsImmutable();
      }
    }
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return com.bytrees.chat.ws.message.ConsoleMessageIdl.internal_static_com_bytrees_chat_ws_message_ConsoleMessage_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.bytrees.chat.ws.message.ConsoleMessageIdl.internal_static_com_bytrees_chat_ws_message_ConsoleMessage_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.bytrees.chat.ws.message.ConsoleMessageIdl.ConsoleMessage.class, com.bytrees.chat.ws.message.ConsoleMessageIdl.ConsoleMessage.Builder.class);
    }

    public static final int USERID_FIELD_NUMBER = 1;
    private long userId_;
    /**
     * <code>int64 userId = 1;</code>
     */
    public long getUserId() {
      return userId_;
    }

    public static final int MESSAGE_FIELD_NUMBER = 2;
    private volatile java.lang.Object message_;
    /**
     * <code>string message = 2;</code>
     */
    public java.lang.String getMessage() {
      java.lang.Object ref = message_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = 
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        message_ = s;
        return s;
      }
    }
    /**
     * <code>string message = 2;</code>
     */
    public com.google.protobuf.ByteString
        getMessageBytes() {
      java.lang.Object ref = message_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        message_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    private byte memoizedIsInitialized = -1;
    @java.lang.Override
    public final boolean isInitialized() {
      byte isInitialized = memoizedIsInitialized;
      if (isInitialized == 1) return true;
      if (isInitialized == 0) return false;

      memoizedIsInitialized = 1;
      return true;
    }

    @java.lang.Override
    public void writeTo(com.google.protobuf.CodedOutputStream output)
                        throws java.io.IOException {
      if (userId_ != 0L) {
        output.writeInt64(1, userId_);
      }
      if (!getMessageBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 2, message_);
      }
      unknownFields.writeTo(output);
    }

    @java.lang.Override
    public int getSerializedSize() {
      int size = memoizedSize;
      if (size != -1) return size;

      size = 0;
      if (userId_ != 0L) {
        size += com.google.protobuf.CodedOutputStream
          .computeInt64Size(1, userId_);
      }
      if (!getMessageBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(2, message_);
      }
      size += unknownFields.getSerializedSize();
      memoizedSize = size;
      return size;
    }

    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this) {
       return true;
      }
      if (!(obj instanceof com.bytrees.chat.ws.message.ConsoleMessageIdl.ConsoleMessage)) {
        return super.equals(obj);
      }
      com.bytrees.chat.ws.message.ConsoleMessageIdl.ConsoleMessage other = (com.bytrees.chat.ws.message.ConsoleMessageIdl.ConsoleMessage) obj;

      if (getUserId()
          != other.getUserId()) return false;
      if (!getMessage()
          .equals(other.getMessage())) return false;
      if (!unknownFields.equals(other.unknownFields)) return false;
      return true;
    }

    @java.lang.Override
    public int hashCode() {
      if (memoizedHashCode != 0) {
        return memoizedHashCode;
      }
      int hash = 41;
      hash = (19 * hash) + getDescriptor().hashCode();
      hash = (37 * hash) + USERID_FIELD_NUMBER;
      hash = (53 * hash) + com.google.protobuf.Internal.hashLong(
          getUserId());
      hash = (37 * hash) + MESSAGE_FIELD_NUMBER;
      hash = (53 * hash) + getMessage().hashCode();
      hash = (29 * hash) + unknownFields.hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static com.bytrees.chat.ws.message.ConsoleMessageIdl.ConsoleMessage parseFrom(
        java.nio.ByteBuffer data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static com.bytrees.chat.ws.message.ConsoleMessageIdl.ConsoleMessage parseFrom(
        java.nio.ByteBuffer data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static com.bytrees.chat.ws.message.ConsoleMessageIdl.ConsoleMessage parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static com.bytrees.chat.ws.message.ConsoleMessageIdl.ConsoleMessage parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static com.bytrees.chat.ws.message.ConsoleMessageIdl.ConsoleMessage parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static com.bytrees.chat.ws.message.ConsoleMessageIdl.ConsoleMessage parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static com.bytrees.chat.ws.message.ConsoleMessageIdl.ConsoleMessage parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static com.bytrees.chat.ws.message.ConsoleMessageIdl.ConsoleMessage parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }
    public static com.bytrees.chat.ws.message.ConsoleMessageIdl.ConsoleMessage parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input);
    }
    public static com.bytrees.chat.ws.message.ConsoleMessageIdl.ConsoleMessage parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }
    public static com.bytrees.chat.ws.message.ConsoleMessageIdl.ConsoleMessage parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static com.bytrees.chat.ws.message.ConsoleMessageIdl.ConsoleMessage parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }

    @java.lang.Override
    public Builder newBuilderForType() { return newBuilder(); }
    public static Builder newBuilder() {
      return DEFAULT_INSTANCE.toBuilder();
    }
    public static Builder newBuilder(com.bytrees.chat.ws.message.ConsoleMessageIdl.ConsoleMessage prototype) {
      return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
    }
    @java.lang.Override
    public Builder toBuilder() {
      return this == DEFAULT_INSTANCE
          ? new Builder() : new Builder().mergeFrom(this);
    }

    @java.lang.Override
    protected Builder newBuilderForType(
        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      Builder builder = new Builder(parent);
      return builder;
    }
    /**
     * Protobuf type {@code com.bytrees.chat.ws.message.ConsoleMessage}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
        // @@protoc_insertion_point(builder_implements:com.bytrees.chat.ws.message.ConsoleMessage)
        com.bytrees.chat.ws.message.ConsoleMessageIdl.ConsoleMessageOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return com.bytrees.chat.ws.message.ConsoleMessageIdl.internal_static_com_bytrees_chat_ws_message_ConsoleMessage_descriptor;
      }

      @java.lang.Override
      protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return com.bytrees.chat.ws.message.ConsoleMessageIdl.internal_static_com_bytrees_chat_ws_message_ConsoleMessage_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                com.bytrees.chat.ws.message.ConsoleMessageIdl.ConsoleMessage.class, com.bytrees.chat.ws.message.ConsoleMessageIdl.ConsoleMessage.Builder.class);
      }

      // Construct using com.bytrees.chat.ws.message.ConsoleMessageIdl.ConsoleMessage.newBuilder()
      private Builder() {
        maybeForceBuilderInitialization();
      }

      private Builder(
          com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
        super(parent);
        maybeForceBuilderInitialization();
      }
      private void maybeForceBuilderInitialization() {
        if (com.google.protobuf.GeneratedMessageV3
                .alwaysUseFieldBuilders) {
        }
      }
      @java.lang.Override
      public Builder clear() {
        super.clear();
        userId_ = 0L;

        message_ = "";

        return this;
      }

      @java.lang.Override
      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return com.bytrees.chat.ws.message.ConsoleMessageIdl.internal_static_com_bytrees_chat_ws_message_ConsoleMessage_descriptor;
      }

      @java.lang.Override
      public com.bytrees.chat.ws.message.ConsoleMessageIdl.ConsoleMessage getDefaultInstanceForType() {
        return com.bytrees.chat.ws.message.ConsoleMessageIdl.ConsoleMessage.getDefaultInstance();
      }

      @java.lang.Override
      public com.bytrees.chat.ws.message.ConsoleMessageIdl.ConsoleMessage build() {
        com.bytrees.chat.ws.message.ConsoleMessageIdl.ConsoleMessage result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      @java.lang.Override
      public com.bytrees.chat.ws.message.ConsoleMessageIdl.ConsoleMessage buildPartial() {
        com.bytrees.chat.ws.message.ConsoleMessageIdl.ConsoleMessage result = new com.bytrees.chat.ws.message.ConsoleMessageIdl.ConsoleMessage(this);
        result.userId_ = userId_;
        result.message_ = message_;
        onBuilt();
        return result;
      }

      @java.lang.Override
      public Builder clone() {
        return super.clone();
      }
      @java.lang.Override
      public Builder setField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          java.lang.Object value) {
        return super.setField(field, value);
      }
      @java.lang.Override
      public Builder clearField(
          com.google.protobuf.Descriptors.FieldDescriptor field) {
        return super.clearField(field);
      }
      @java.lang.Override
      public Builder clearOneof(
          com.google.protobuf.Descriptors.OneofDescriptor oneof) {
        return super.clearOneof(oneof);
      }
      @java.lang.Override
      public Builder setRepeatedField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          int index, java.lang.Object value) {
        return super.setRepeatedField(field, index, value);
      }
      @java.lang.Override
      public Builder addRepeatedField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          java.lang.Object value) {
        return super.addRepeatedField(field, value);
      }
      @java.lang.Override
      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof com.bytrees.chat.ws.message.ConsoleMessageIdl.ConsoleMessage) {
          return mergeFrom((com.bytrees.chat.ws.message.ConsoleMessageIdl.ConsoleMessage)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(com.bytrees.chat.ws.message.ConsoleMessageIdl.ConsoleMessage other) {
        if (other == com.bytrees.chat.ws.message.ConsoleMessageIdl.ConsoleMessage.getDefaultInstance()) return this;
        if (other.getUserId() != 0L) {
          setUserId(other.getUserId());
        }
        if (!other.getMessage().isEmpty()) {
          message_ = other.message_;
          onChanged();
        }
        this.mergeUnknownFields(other.unknownFields);
        onChanged();
        return this;
      }

      @java.lang.Override
      public final boolean isInitialized() {
        return true;
      }

      @java.lang.Override
      public Builder mergeFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
        com.bytrees.chat.ws.message.ConsoleMessageIdl.ConsoleMessage parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (com.bytrees.chat.ws.message.ConsoleMessageIdl.ConsoleMessage) e.getUnfinishedMessage();
          throw e.unwrapIOException();
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }

      private long userId_ ;
      /**
       * <code>int64 userId = 1;</code>
       */
      public long getUserId() {
        return userId_;
      }
      /**
       * <code>int64 userId = 1;</code>
       */
      public Builder setUserId(long value) {
        
        userId_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>int64 userId = 1;</code>
       */
      public Builder clearUserId() {
        
        userId_ = 0L;
        onChanged();
        return this;
      }

      private java.lang.Object message_ = "";
      /**
       * <code>string message = 2;</code>
       */
      public java.lang.String getMessage() {
        java.lang.Object ref = message_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs =
              (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          message_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string message = 2;</code>
       */
      public com.google.protobuf.ByteString
          getMessageBytes() {
        java.lang.Object ref = message_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b = 
              com.google.protobuf.ByteString.copyFromUtf8(
                  (java.lang.String) ref);
          message_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string message = 2;</code>
       */
      public Builder setMessage(
          java.lang.String value) {
        if (value == null) {
    throw new NullPointerException();
  }
  
        message_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string message = 2;</code>
       */
      public Builder clearMessage() {
        
        message_ = getDefaultInstance().getMessage();
        onChanged();
        return this;
      }
      /**
       * <code>string message = 2;</code>
       */
      public Builder setMessageBytes(
          com.google.protobuf.ByteString value) {
        if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
        
        message_ = value;
        onChanged();
        return this;
      }
      @java.lang.Override
      public final Builder setUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        return super.setUnknownFields(unknownFields);
      }

      @java.lang.Override
      public final Builder mergeUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        return super.mergeUnknownFields(unknownFields);
      }


      // @@protoc_insertion_point(builder_scope:com.bytrees.chat.ws.message.ConsoleMessage)
    }

    // @@protoc_insertion_point(class_scope:com.bytrees.chat.ws.message.ConsoleMessage)
    private static final com.bytrees.chat.ws.message.ConsoleMessageIdl.ConsoleMessage DEFAULT_INSTANCE;
    static {
      DEFAULT_INSTANCE = new com.bytrees.chat.ws.message.ConsoleMessageIdl.ConsoleMessage();
    }

    public static com.bytrees.chat.ws.message.ConsoleMessageIdl.ConsoleMessage getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<ConsoleMessage>
        PARSER = new com.google.protobuf.AbstractParser<ConsoleMessage>() {
      @java.lang.Override
      public ConsoleMessage parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
        return new ConsoleMessage(input, extensionRegistry);
      }
    };

    public static com.google.protobuf.Parser<ConsoleMessage> parser() {
      return PARSER;
    }

    @java.lang.Override
    public com.google.protobuf.Parser<ConsoleMessage> getParserForType() {
      return PARSER;
    }

    @java.lang.Override
    public com.bytrees.chat.ws.message.ConsoleMessageIdl.ConsoleMessage getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }

  }

  private static final com.google.protobuf.Descriptors.Descriptor
    internal_static_com_bytrees_chat_ws_message_ConsoleMessage_descriptor;
  private static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_com_bytrees_chat_ws_message_ConsoleMessage_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\027ConsoleMessageIdl.proto\022\033com.bytrees.c" +
      "hat.ws.message\"1\n\016ConsoleMessage\022\016\n\006user" +
      "Id\030\001 \001(\003\022\017\n\007message\030\002 \001(\tB\023B\021ConsoleMess" +
      "ageIdlb\006proto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        });
    internal_static_com_bytrees_chat_ws_message_ConsoleMessage_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_com_bytrees_chat_ws_message_ConsoleMessage_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_com_bytrees_chat_ws_message_ConsoleMessage_descriptor,
        new java.lang.String[] { "UserId", "Message", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}
