// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: DataEntry.proto

package com.yoti.api.client.spi.remote.proto;

public final class DataEntryProto {
  private DataEntryProto() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  public interface DataEntryOrBuilder extends
      // @@protoc_insertion_point(interface_extends:sharepubapi_v1.DataEntry)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <code>.sharepubapi_v1.DataEntry.Type type = 1;</code>
     * @return The enum numeric value on the wire for type.
     */
    int getTypeValue();
    /**
     * <code>.sharepubapi_v1.DataEntry.Type type = 1;</code>
     * @return The type.
     */
    com.yoti.api.client.spi.remote.proto.DataEntryProto.DataEntry.Type getType();

    /**
     * <code>bytes value = 2;</code>
     * @return The value.
     */
    com.google.protobuf.ByteString getValue();
  }
  /**
   * Protobuf type {@code sharepubapi_v1.DataEntry}
   */
  public static final class DataEntry extends
      com.google.protobuf.GeneratedMessageV3 implements
      // @@protoc_insertion_point(message_implements:sharepubapi_v1.DataEntry)
      DataEntryOrBuilder {
  private static final long serialVersionUID = 0L;
    // Use DataEntry.newBuilder() to construct.
    private DataEntry(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }
    private DataEntry() {
      type_ = 0;
      value_ = com.google.protobuf.ByteString.EMPTY;
    }

    @java.lang.Override
    @SuppressWarnings({"unused"})
    protected java.lang.Object newInstance(
        UnusedPrivateParameter unused) {
      return new DataEntry();
    }

    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet
    getUnknownFields() {
      return this.unknownFields;
    }
    private DataEntry(
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
              int rawValue = input.readEnum();

              type_ = rawValue;
              break;
            }
            case 18: {

              value_ = input.readBytes();
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
      return com.yoti.api.client.spi.remote.proto.DataEntryProto.internal_static_sharepubapi_v1_DataEntry_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.yoti.api.client.spi.remote.proto.DataEntryProto.internal_static_sharepubapi_v1_DataEntry_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.yoti.api.client.spi.remote.proto.DataEntryProto.DataEntry.class, com.yoti.api.client.spi.remote.proto.DataEntryProto.DataEntry.Builder.class);
    }

    /**
     * Protobuf enum {@code sharepubapi_v1.DataEntry.Type}
     */
    public enum Type
        implements com.google.protobuf.ProtocolMessageEnum {
      /**
       * <code>UNDEFINED = 0;</code>
       */
      UNDEFINED(0),
      /**
       * <code>INVOICE = 1;</code>
       */
      INVOICE(1),
      /**
       * <code>PAYMENT_TRANSACTION = 2;</code>
       */
      PAYMENT_TRANSACTION(2),
      /**
       * <code>LOCATION = 3;</code>
       */
      LOCATION(3),
      /**
       * <code>TRANSACTION = 4;</code>
       */
      TRANSACTION(4),
      /**
       * <code>AGE_VERIFICATION_SECRET = 5;</code>
       */
      AGE_VERIFICATION_SECRET(5),
      /**
       * <code>THIRD_PARTY_ATTRIBUTE = 6;</code>
       */
      THIRD_PARTY_ATTRIBUTE(6),
      UNRECOGNIZED(-1),
      ;

      /**
       * <code>UNDEFINED = 0;</code>
       */
      public static final int UNDEFINED_VALUE = 0;
      /**
       * <code>INVOICE = 1;</code>
       */
      public static final int INVOICE_VALUE = 1;
      /**
       * <code>PAYMENT_TRANSACTION = 2;</code>
       */
      public static final int PAYMENT_TRANSACTION_VALUE = 2;
      /**
       * <code>LOCATION = 3;</code>
       */
      public static final int LOCATION_VALUE = 3;
      /**
       * <code>TRANSACTION = 4;</code>
       */
      public static final int TRANSACTION_VALUE = 4;
      /**
       * <code>AGE_VERIFICATION_SECRET = 5;</code>
       */
      public static final int AGE_VERIFICATION_SECRET_VALUE = 5;
      /**
       * <code>THIRD_PARTY_ATTRIBUTE = 6;</code>
       */
      public static final int THIRD_PARTY_ATTRIBUTE_VALUE = 6;


      public final int getNumber() {
        if (this == UNRECOGNIZED) {
          throw new java.lang.IllegalArgumentException(
              "Can't get the number of an unknown enum value.");
        }
        return value;
      }

      /**
       * @param value The numeric wire value of the corresponding enum entry.
       * @return The enum associated with the given numeric wire value.
       * @deprecated Use {@link #forNumber(int)} instead.
       */
      @java.lang.Deprecated
      public static Type valueOf(int value) {
        return forNumber(value);
      }

      /**
       * @param value The numeric wire value of the corresponding enum entry.
       * @return The enum associated with the given numeric wire value.
       */
      public static Type forNumber(int value) {
        switch (value) {
          case 0: return UNDEFINED;
          case 1: return INVOICE;
          case 2: return PAYMENT_TRANSACTION;
          case 3: return LOCATION;
          case 4: return TRANSACTION;
          case 5: return AGE_VERIFICATION_SECRET;
          case 6: return THIRD_PARTY_ATTRIBUTE;
          default: return null;
        }
      }

      public static com.google.protobuf.Internal.EnumLiteMap<Type>
          internalGetValueMap() {
        return internalValueMap;
      }
      private static final com.google.protobuf.Internal.EnumLiteMap<
          Type> internalValueMap =
            new com.google.protobuf.Internal.EnumLiteMap<Type>() {
              public Type findValueByNumber(int number) {
                return Type.forNumber(number);
              }
            };

      public final com.google.protobuf.Descriptors.EnumValueDescriptor
          getValueDescriptor() {
        if (this == UNRECOGNIZED) {
          throw new java.lang.IllegalStateException(
              "Can't get the descriptor of an unrecognized enum value.");
        }
        return getDescriptor().getValues().get(ordinal());
      }
      public final com.google.protobuf.Descriptors.EnumDescriptor
          getDescriptorForType() {
        return getDescriptor();
      }
      public static final com.google.protobuf.Descriptors.EnumDescriptor
          getDescriptor() {
        return com.yoti.api.client.spi.remote.proto.DataEntryProto.DataEntry.getDescriptor().getEnumTypes().get(0);
      }

      private static final Type[] VALUES = values();

      public static Type valueOf(
          com.google.protobuf.Descriptors.EnumValueDescriptor desc) {
        if (desc.getType() != getDescriptor()) {
          throw new java.lang.IllegalArgumentException(
            "EnumValueDescriptor is not for this type.");
        }
        if (desc.getIndex() == -1) {
          return UNRECOGNIZED;
        }
        return VALUES[desc.getIndex()];
      }

      private final int value;

      private Type(int value) {
        this.value = value;
      }

      // @@protoc_insertion_point(enum_scope:sharepubapi_v1.DataEntry.Type)
    }

    public static final int TYPE_FIELD_NUMBER = 1;
    private int type_;
    /**
     * <code>.sharepubapi_v1.DataEntry.Type type = 1;</code>
     * @return The enum numeric value on the wire for type.
     */
    @java.lang.Override public int getTypeValue() {
      return type_;
    }
    /**
     * <code>.sharepubapi_v1.DataEntry.Type type = 1;</code>
     * @return The type.
     */
    @java.lang.Override public com.yoti.api.client.spi.remote.proto.DataEntryProto.DataEntry.Type getType() {
      @SuppressWarnings("deprecation")
      com.yoti.api.client.spi.remote.proto.DataEntryProto.DataEntry.Type result = com.yoti.api.client.spi.remote.proto.DataEntryProto.DataEntry.Type.valueOf(type_);
      return result == null ? com.yoti.api.client.spi.remote.proto.DataEntryProto.DataEntry.Type.UNRECOGNIZED : result;
    }

    public static final int VALUE_FIELD_NUMBER = 2;
    private com.google.protobuf.ByteString value_;
    /**
     * <code>bytes value = 2;</code>
     * @return The value.
     */
    @java.lang.Override
    public com.google.protobuf.ByteString getValue() {
      return value_;
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
      if (type_ != com.yoti.api.client.spi.remote.proto.DataEntryProto.DataEntry.Type.UNDEFINED.getNumber()) {
        output.writeEnum(1, type_);
      }
      if (!value_.isEmpty()) {
        output.writeBytes(2, value_);
      }
      unknownFields.writeTo(output);
    }

    @java.lang.Override
    public int getSerializedSize() {
      int size = memoizedSize;
      if (size != -1) return size;

      size = 0;
      if (type_ != com.yoti.api.client.spi.remote.proto.DataEntryProto.DataEntry.Type.UNDEFINED.getNumber()) {
        size += com.google.protobuf.CodedOutputStream
          .computeEnumSize(1, type_);
      }
      if (!value_.isEmpty()) {
        size += com.google.protobuf.CodedOutputStream
          .computeBytesSize(2, value_);
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
      if (!(obj instanceof com.yoti.api.client.spi.remote.proto.DataEntryProto.DataEntry)) {
        return super.equals(obj);
      }
      com.yoti.api.client.spi.remote.proto.DataEntryProto.DataEntry other = (com.yoti.api.client.spi.remote.proto.DataEntryProto.DataEntry) obj;

      if (type_ != other.type_) return false;
      if (!getValue()
          .equals(other.getValue())) return false;
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
      hash = (37 * hash) + TYPE_FIELD_NUMBER;
      hash = (53 * hash) + type_;
      hash = (37 * hash) + VALUE_FIELD_NUMBER;
      hash = (53 * hash) + getValue().hashCode();
      hash = (29 * hash) + unknownFields.hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static com.yoti.api.client.spi.remote.proto.DataEntryProto.DataEntry parseFrom(
        java.nio.ByteBuffer data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static com.yoti.api.client.spi.remote.proto.DataEntryProto.DataEntry parseFrom(
        java.nio.ByteBuffer data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static com.yoti.api.client.spi.remote.proto.DataEntryProto.DataEntry parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static com.yoti.api.client.spi.remote.proto.DataEntryProto.DataEntry parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static com.yoti.api.client.spi.remote.proto.DataEntryProto.DataEntry parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static com.yoti.api.client.spi.remote.proto.DataEntryProto.DataEntry parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static com.yoti.api.client.spi.remote.proto.DataEntryProto.DataEntry parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static com.yoti.api.client.spi.remote.proto.DataEntryProto.DataEntry parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }
    public static com.yoti.api.client.spi.remote.proto.DataEntryProto.DataEntry parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input);
    }
    public static com.yoti.api.client.spi.remote.proto.DataEntryProto.DataEntry parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }
    public static com.yoti.api.client.spi.remote.proto.DataEntryProto.DataEntry parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static com.yoti.api.client.spi.remote.proto.DataEntryProto.DataEntry parseFrom(
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
    public static Builder newBuilder(com.yoti.api.client.spi.remote.proto.DataEntryProto.DataEntry prototype) {
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
     * Protobuf type {@code sharepubapi_v1.DataEntry}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
        // @@protoc_insertion_point(builder_implements:sharepubapi_v1.DataEntry)
        com.yoti.api.client.spi.remote.proto.DataEntryProto.DataEntryOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return com.yoti.api.client.spi.remote.proto.DataEntryProto.internal_static_sharepubapi_v1_DataEntry_descriptor;
      }

      @java.lang.Override
      protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return com.yoti.api.client.spi.remote.proto.DataEntryProto.internal_static_sharepubapi_v1_DataEntry_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                com.yoti.api.client.spi.remote.proto.DataEntryProto.DataEntry.class, com.yoti.api.client.spi.remote.proto.DataEntryProto.DataEntry.Builder.class);
      }

      // Construct using com.yoti.api.client.spi.remote.proto.DataEntryProto.DataEntry.newBuilder()
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
        type_ = 0;

        value_ = com.google.protobuf.ByteString.EMPTY;

        return this;
      }

      @java.lang.Override
      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return com.yoti.api.client.spi.remote.proto.DataEntryProto.internal_static_sharepubapi_v1_DataEntry_descriptor;
      }

      @java.lang.Override
      public com.yoti.api.client.spi.remote.proto.DataEntryProto.DataEntry getDefaultInstanceForType() {
        return com.yoti.api.client.spi.remote.proto.DataEntryProto.DataEntry.getDefaultInstance();
      }

      @java.lang.Override
      public com.yoti.api.client.spi.remote.proto.DataEntryProto.DataEntry build() {
        com.yoti.api.client.spi.remote.proto.DataEntryProto.DataEntry result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      @java.lang.Override
      public com.yoti.api.client.spi.remote.proto.DataEntryProto.DataEntry buildPartial() {
        com.yoti.api.client.spi.remote.proto.DataEntryProto.DataEntry result = new com.yoti.api.client.spi.remote.proto.DataEntryProto.DataEntry(this);
        result.type_ = type_;
        result.value_ = value_;
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
        if (other instanceof com.yoti.api.client.spi.remote.proto.DataEntryProto.DataEntry) {
          return mergeFrom((com.yoti.api.client.spi.remote.proto.DataEntryProto.DataEntry)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(com.yoti.api.client.spi.remote.proto.DataEntryProto.DataEntry other) {
        if (other == com.yoti.api.client.spi.remote.proto.DataEntryProto.DataEntry.getDefaultInstance()) return this;
        if (other.type_ != 0) {
          setTypeValue(other.getTypeValue());
        }
        if (other.getValue() != com.google.protobuf.ByteString.EMPTY) {
          setValue(other.getValue());
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
        com.yoti.api.client.spi.remote.proto.DataEntryProto.DataEntry parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (com.yoti.api.client.spi.remote.proto.DataEntryProto.DataEntry) e.getUnfinishedMessage();
          throw e.unwrapIOException();
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }

      private int type_ = 0;
      /**
       * <code>.sharepubapi_v1.DataEntry.Type type = 1;</code>
       * @return The enum numeric value on the wire for type.
       */
      @java.lang.Override public int getTypeValue() {
        return type_;
      }
      /**
       * <code>.sharepubapi_v1.DataEntry.Type type = 1;</code>
       * @param value The enum numeric value on the wire for type to set.
       * @return This builder for chaining.
       */
      public Builder setTypeValue(int value) {
        
        type_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>.sharepubapi_v1.DataEntry.Type type = 1;</code>
       * @return The type.
       */
      @java.lang.Override
      public com.yoti.api.client.spi.remote.proto.DataEntryProto.DataEntry.Type getType() {
        @SuppressWarnings("deprecation")
        com.yoti.api.client.spi.remote.proto.DataEntryProto.DataEntry.Type result = com.yoti.api.client.spi.remote.proto.DataEntryProto.DataEntry.Type.valueOf(type_);
        return result == null ? com.yoti.api.client.spi.remote.proto.DataEntryProto.DataEntry.Type.UNRECOGNIZED : result;
      }
      /**
       * <code>.sharepubapi_v1.DataEntry.Type type = 1;</code>
       * @param value The type to set.
       * @return This builder for chaining.
       */
      public Builder setType(com.yoti.api.client.spi.remote.proto.DataEntryProto.DataEntry.Type value) {
        if (value == null) {
          throw new NullPointerException();
        }
        
        type_ = value.getNumber();
        onChanged();
        return this;
      }
      /**
       * <code>.sharepubapi_v1.DataEntry.Type type = 1;</code>
       * @return This builder for chaining.
       */
      public Builder clearType() {
        
        type_ = 0;
        onChanged();
        return this;
      }

      private com.google.protobuf.ByteString value_ = com.google.protobuf.ByteString.EMPTY;
      /**
       * <code>bytes value = 2;</code>
       * @return The value.
       */
      @java.lang.Override
      public com.google.protobuf.ByteString getValue() {
        return value_;
      }
      /**
       * <code>bytes value = 2;</code>
       * @param value The value to set.
       * @return This builder for chaining.
       */
      public Builder setValue(com.google.protobuf.ByteString value) {
        if (value == null) {
    throw new NullPointerException();
  }
  
        value_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>bytes value = 2;</code>
       * @return This builder for chaining.
       */
      public Builder clearValue() {
        
        value_ = getDefaultInstance().getValue();
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


      // @@protoc_insertion_point(builder_scope:sharepubapi_v1.DataEntry)
    }

    // @@protoc_insertion_point(class_scope:sharepubapi_v1.DataEntry)
    private static final com.yoti.api.client.spi.remote.proto.DataEntryProto.DataEntry DEFAULT_INSTANCE;
    static {
      DEFAULT_INSTANCE = new com.yoti.api.client.spi.remote.proto.DataEntryProto.DataEntry();
    }

    public static com.yoti.api.client.spi.remote.proto.DataEntryProto.DataEntry getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<DataEntry>
        PARSER = new com.google.protobuf.AbstractParser<DataEntry>() {
      @java.lang.Override
      public DataEntry parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
        return new DataEntry(input, extensionRegistry);
      }
    };

    public static com.google.protobuf.Parser<DataEntry> parser() {
      return PARSER;
    }

    @java.lang.Override
    public com.google.protobuf.Parser<DataEntry> getParserForType() {
      return PARSER;
    }

    @java.lang.Override
    public com.yoti.api.client.spi.remote.proto.DataEntryProto.DataEntry getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }

  }

  private static final com.google.protobuf.Descriptors.Descriptor
    internal_static_sharepubapi_v1_DataEntry_descriptor;
  private static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_sharepubapi_v1_DataEntry_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\017DataEntry.proto\022\016sharepubapi_v1\"\335\001\n\tDa" +
      "taEntry\022,\n\004type\030\001 \001(\0162\036.sharepubapi_v1.D" +
      "ataEntry.Type\022\r\n\005value\030\002 \001(\014\"\222\001\n\004Type\022\r\n" +
      "\tUNDEFINED\020\000\022\013\n\007INVOICE\020\001\022\027\n\023PAYMENT_TRA" +
      "NSACTION\020\002\022\014\n\010LOCATION\020\003\022\017\n\013TRANSACTION\020" +
      "\004\022\033\n\027AGE_VERIFICATION_SECRET\020\005\022\031\n\025THIRD_" +
      "PARTY_ATTRIBUTE\020\006B\345\001\n$com.yoti.api.clien" +
      "t.spi.remote.protoB\016DataEntryProtoZ0gith" +
      "ub.com/getyoti/yoti-go-sdk/v3/yotiprotos" +
      "hare\252\002\030Yoti.Auth.ProtoBuf.Share\312\002\031Yoti\\P" +
      "rotobuf\\Sharepubapi\342\002%Yoti\\Protobuf\\Shar" +
      "epubapi\\GPBMetadata\352\002\033Yoti::Protobuf::Sh" +
      "arepubapib\006proto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        });
    internal_static_sharepubapi_v1_DataEntry_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_sharepubapi_v1_DataEntry_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_sharepubapi_v1_DataEntry_descriptor,
        new java.lang.String[] { "Type", "Value", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}
