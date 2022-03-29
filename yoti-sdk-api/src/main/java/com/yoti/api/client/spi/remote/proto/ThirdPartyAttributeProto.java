// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: ThirdPartyAttribute.proto

package com.yoti.api.client.spi.remote.proto;

public final class ThirdPartyAttributeProto {
  private ThirdPartyAttributeProto() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  public interface ThirdPartyAttributeOrBuilder extends
      // @@protoc_insertion_point(interface_extends:sharepubapi_v1.ThirdPartyAttribute)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <code>bytes issuance_token = 1;</code>
     * @return The issuanceToken.
     */
    com.google.protobuf.ByteString getIssuanceToken();

    /**
     * <code>.sharepubapi_v1.IssuingAttributes issuing_attributes = 2;</code>
     * @return Whether the issuingAttributes field is set.
     */
    boolean hasIssuingAttributes();
    /**
     * <code>.sharepubapi_v1.IssuingAttributes issuing_attributes = 2;</code>
     * @return The issuingAttributes.
     */
    com.yoti.api.client.spi.remote.proto.IssuingAttributesProto.IssuingAttributes getIssuingAttributes();
    /**
     * <code>.sharepubapi_v1.IssuingAttributes issuing_attributes = 2;</code>
     */
    com.yoti.api.client.spi.remote.proto.IssuingAttributesProto.IssuingAttributesOrBuilder getIssuingAttributesOrBuilder();
  }
  /**
   * Protobuf type {@code sharepubapi_v1.ThirdPartyAttribute}
   */
  public static final class ThirdPartyAttribute extends
      com.google.protobuf.GeneratedMessageV3 implements
      // @@protoc_insertion_point(message_implements:sharepubapi_v1.ThirdPartyAttribute)
      ThirdPartyAttributeOrBuilder {
  private static final long serialVersionUID = 0L;
    // Use ThirdPartyAttribute.newBuilder() to construct.
    private ThirdPartyAttribute(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }
    private ThirdPartyAttribute() {
      issuanceToken_ = com.google.protobuf.ByteString.EMPTY;
    }

    @java.lang.Override
    @SuppressWarnings({"unused"})
    protected java.lang.Object newInstance(
        UnusedPrivateParameter unused) {
      return new ThirdPartyAttribute();
    }

    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet
    getUnknownFields() {
      return this.unknownFields;
    }
    private ThirdPartyAttribute(
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
            case 10: {

              issuanceToken_ = input.readBytes();
              break;
            }
            case 18: {
              com.yoti.api.client.spi.remote.proto.IssuingAttributesProto.IssuingAttributes.Builder subBuilder = null;
              if (issuingAttributes_ != null) {
                subBuilder = issuingAttributes_.toBuilder();
              }
              issuingAttributes_ = input.readMessage(com.yoti.api.client.spi.remote.proto.IssuingAttributesProto.IssuingAttributes.parser(), extensionRegistry);
              if (subBuilder != null) {
                subBuilder.mergeFrom(issuingAttributes_);
                issuingAttributes_ = subBuilder.buildPartial();
              }

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
      return com.yoti.api.client.spi.remote.proto.ThirdPartyAttributeProto.internal_static_sharepubapi_v1_ThirdPartyAttribute_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.yoti.api.client.spi.remote.proto.ThirdPartyAttributeProto.internal_static_sharepubapi_v1_ThirdPartyAttribute_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.yoti.api.client.spi.remote.proto.ThirdPartyAttributeProto.ThirdPartyAttribute.class, com.yoti.api.client.spi.remote.proto.ThirdPartyAttributeProto.ThirdPartyAttribute.Builder.class);
    }

    public static final int ISSUANCE_TOKEN_FIELD_NUMBER = 1;
    private com.google.protobuf.ByteString issuanceToken_;
    /**
     * <code>bytes issuance_token = 1;</code>
     * @return The issuanceToken.
     */
    @java.lang.Override
    public com.google.protobuf.ByteString getIssuanceToken() {
      return issuanceToken_;
    }

    public static final int ISSUING_ATTRIBUTES_FIELD_NUMBER = 2;
    private com.yoti.api.client.spi.remote.proto.IssuingAttributesProto.IssuingAttributes issuingAttributes_;
    /**
     * <code>.sharepubapi_v1.IssuingAttributes issuing_attributes = 2;</code>
     * @return Whether the issuingAttributes field is set.
     */
    @java.lang.Override
    public boolean hasIssuingAttributes() {
      return issuingAttributes_ != null;
    }
    /**
     * <code>.sharepubapi_v1.IssuingAttributes issuing_attributes = 2;</code>
     * @return The issuingAttributes.
     */
    @java.lang.Override
    public com.yoti.api.client.spi.remote.proto.IssuingAttributesProto.IssuingAttributes getIssuingAttributes() {
      return issuingAttributes_ == null ? com.yoti.api.client.spi.remote.proto.IssuingAttributesProto.IssuingAttributes.getDefaultInstance() : issuingAttributes_;
    }
    /**
     * <code>.sharepubapi_v1.IssuingAttributes issuing_attributes = 2;</code>
     */
    @java.lang.Override
    public com.yoti.api.client.spi.remote.proto.IssuingAttributesProto.IssuingAttributesOrBuilder getIssuingAttributesOrBuilder() {
      return getIssuingAttributes();
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
      if (!issuanceToken_.isEmpty()) {
        output.writeBytes(1, issuanceToken_);
      }
      if (issuingAttributes_ != null) {
        output.writeMessage(2, getIssuingAttributes());
      }
      unknownFields.writeTo(output);
    }

    @java.lang.Override
    public int getSerializedSize() {
      int size = memoizedSize;
      if (size != -1) return size;

      size = 0;
      if (!issuanceToken_.isEmpty()) {
        size += com.google.protobuf.CodedOutputStream
          .computeBytesSize(1, issuanceToken_);
      }
      if (issuingAttributes_ != null) {
        size += com.google.protobuf.CodedOutputStream
          .computeMessageSize(2, getIssuingAttributes());
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
      if (!(obj instanceof com.yoti.api.client.spi.remote.proto.ThirdPartyAttributeProto.ThirdPartyAttribute)) {
        return super.equals(obj);
      }
      com.yoti.api.client.spi.remote.proto.ThirdPartyAttributeProto.ThirdPartyAttribute other = (com.yoti.api.client.spi.remote.proto.ThirdPartyAttributeProto.ThirdPartyAttribute) obj;

      if (!getIssuanceToken()
          .equals(other.getIssuanceToken())) return false;
      if (hasIssuingAttributes() != other.hasIssuingAttributes()) return false;
      if (hasIssuingAttributes()) {
        if (!getIssuingAttributes()
            .equals(other.getIssuingAttributes())) return false;
      }
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
      hash = (37 * hash) + ISSUANCE_TOKEN_FIELD_NUMBER;
      hash = (53 * hash) + getIssuanceToken().hashCode();
      if (hasIssuingAttributes()) {
        hash = (37 * hash) + ISSUING_ATTRIBUTES_FIELD_NUMBER;
        hash = (53 * hash) + getIssuingAttributes().hashCode();
      }
      hash = (29 * hash) + unknownFields.hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static com.yoti.api.client.spi.remote.proto.ThirdPartyAttributeProto.ThirdPartyAttribute parseFrom(
        java.nio.ByteBuffer data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static com.yoti.api.client.spi.remote.proto.ThirdPartyAttributeProto.ThirdPartyAttribute parseFrom(
        java.nio.ByteBuffer data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static com.yoti.api.client.spi.remote.proto.ThirdPartyAttributeProto.ThirdPartyAttribute parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static com.yoti.api.client.spi.remote.proto.ThirdPartyAttributeProto.ThirdPartyAttribute parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static com.yoti.api.client.spi.remote.proto.ThirdPartyAttributeProto.ThirdPartyAttribute parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static com.yoti.api.client.spi.remote.proto.ThirdPartyAttributeProto.ThirdPartyAttribute parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static com.yoti.api.client.spi.remote.proto.ThirdPartyAttributeProto.ThirdPartyAttribute parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static com.yoti.api.client.spi.remote.proto.ThirdPartyAttributeProto.ThirdPartyAttribute parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }
    public static com.yoti.api.client.spi.remote.proto.ThirdPartyAttributeProto.ThirdPartyAttribute parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input);
    }
    public static com.yoti.api.client.spi.remote.proto.ThirdPartyAttributeProto.ThirdPartyAttribute parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }
    public static com.yoti.api.client.spi.remote.proto.ThirdPartyAttributeProto.ThirdPartyAttribute parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static com.yoti.api.client.spi.remote.proto.ThirdPartyAttributeProto.ThirdPartyAttribute parseFrom(
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
    public static Builder newBuilder(com.yoti.api.client.spi.remote.proto.ThirdPartyAttributeProto.ThirdPartyAttribute prototype) {
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
     * Protobuf type {@code sharepubapi_v1.ThirdPartyAttribute}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
        // @@protoc_insertion_point(builder_implements:sharepubapi_v1.ThirdPartyAttribute)
        com.yoti.api.client.spi.remote.proto.ThirdPartyAttributeProto.ThirdPartyAttributeOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return com.yoti.api.client.spi.remote.proto.ThirdPartyAttributeProto.internal_static_sharepubapi_v1_ThirdPartyAttribute_descriptor;
      }

      @java.lang.Override
      protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return com.yoti.api.client.spi.remote.proto.ThirdPartyAttributeProto.internal_static_sharepubapi_v1_ThirdPartyAttribute_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                com.yoti.api.client.spi.remote.proto.ThirdPartyAttributeProto.ThirdPartyAttribute.class, com.yoti.api.client.spi.remote.proto.ThirdPartyAttributeProto.ThirdPartyAttribute.Builder.class);
      }

      // Construct using com.yoti.api.client.spi.remote.proto.ThirdPartyAttributeProto.ThirdPartyAttribute.newBuilder()
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
        issuanceToken_ = com.google.protobuf.ByteString.EMPTY;

        if (issuingAttributesBuilder_ == null) {
          issuingAttributes_ = null;
        } else {
          issuingAttributes_ = null;
          issuingAttributesBuilder_ = null;
        }
        return this;
      }

      @java.lang.Override
      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return com.yoti.api.client.spi.remote.proto.ThirdPartyAttributeProto.internal_static_sharepubapi_v1_ThirdPartyAttribute_descriptor;
      }

      @java.lang.Override
      public com.yoti.api.client.spi.remote.proto.ThirdPartyAttributeProto.ThirdPartyAttribute getDefaultInstanceForType() {
        return com.yoti.api.client.spi.remote.proto.ThirdPartyAttributeProto.ThirdPartyAttribute.getDefaultInstance();
      }

      @java.lang.Override
      public com.yoti.api.client.spi.remote.proto.ThirdPartyAttributeProto.ThirdPartyAttribute build() {
        com.yoti.api.client.spi.remote.proto.ThirdPartyAttributeProto.ThirdPartyAttribute result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      @java.lang.Override
      public com.yoti.api.client.spi.remote.proto.ThirdPartyAttributeProto.ThirdPartyAttribute buildPartial() {
        com.yoti.api.client.spi.remote.proto.ThirdPartyAttributeProto.ThirdPartyAttribute result = new com.yoti.api.client.spi.remote.proto.ThirdPartyAttributeProto.ThirdPartyAttribute(this);
        result.issuanceToken_ = issuanceToken_;
        if (issuingAttributesBuilder_ == null) {
          result.issuingAttributes_ = issuingAttributes_;
        } else {
          result.issuingAttributes_ = issuingAttributesBuilder_.build();
        }
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
        if (other instanceof com.yoti.api.client.spi.remote.proto.ThirdPartyAttributeProto.ThirdPartyAttribute) {
          return mergeFrom((com.yoti.api.client.spi.remote.proto.ThirdPartyAttributeProto.ThirdPartyAttribute)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(com.yoti.api.client.spi.remote.proto.ThirdPartyAttributeProto.ThirdPartyAttribute other) {
        if (other == com.yoti.api.client.spi.remote.proto.ThirdPartyAttributeProto.ThirdPartyAttribute.getDefaultInstance()) return this;
        if (other.getIssuanceToken() != com.google.protobuf.ByteString.EMPTY) {
          setIssuanceToken(other.getIssuanceToken());
        }
        if (other.hasIssuingAttributes()) {
          mergeIssuingAttributes(other.getIssuingAttributes());
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
        com.yoti.api.client.spi.remote.proto.ThirdPartyAttributeProto.ThirdPartyAttribute parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (com.yoti.api.client.spi.remote.proto.ThirdPartyAttributeProto.ThirdPartyAttribute) e.getUnfinishedMessage();
          throw e.unwrapIOException();
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }

      private com.google.protobuf.ByteString issuanceToken_ = com.google.protobuf.ByteString.EMPTY;
      /**
       * <code>bytes issuance_token = 1;</code>
       * @return The issuanceToken.
       */
      @java.lang.Override
      public com.google.protobuf.ByteString getIssuanceToken() {
        return issuanceToken_;
      }
      /**
       * <code>bytes issuance_token = 1;</code>
       * @param value The issuanceToken to set.
       * @return This builder for chaining.
       */
      public Builder setIssuanceToken(com.google.protobuf.ByteString value) {
        if (value == null) {
    throw new NullPointerException();
  }
  
        issuanceToken_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>bytes issuance_token = 1;</code>
       * @return This builder for chaining.
       */
      public Builder clearIssuanceToken() {
        
        issuanceToken_ = getDefaultInstance().getIssuanceToken();
        onChanged();
        return this;
      }

      private com.yoti.api.client.spi.remote.proto.IssuingAttributesProto.IssuingAttributes issuingAttributes_;
      private com.google.protobuf.SingleFieldBuilderV3<
          com.yoti.api.client.spi.remote.proto.IssuingAttributesProto.IssuingAttributes, com.yoti.api.client.spi.remote.proto.IssuingAttributesProto.IssuingAttributes.Builder, com.yoti.api.client.spi.remote.proto.IssuingAttributesProto.IssuingAttributesOrBuilder> issuingAttributesBuilder_;
      /**
       * <code>.sharepubapi_v1.IssuingAttributes issuing_attributes = 2;</code>
       * @return Whether the issuingAttributes field is set.
       */
      public boolean hasIssuingAttributes() {
        return issuingAttributesBuilder_ != null || issuingAttributes_ != null;
      }
      /**
       * <code>.sharepubapi_v1.IssuingAttributes issuing_attributes = 2;</code>
       * @return The issuingAttributes.
       */
      public com.yoti.api.client.spi.remote.proto.IssuingAttributesProto.IssuingAttributes getIssuingAttributes() {
        if (issuingAttributesBuilder_ == null) {
          return issuingAttributes_ == null ? com.yoti.api.client.spi.remote.proto.IssuingAttributesProto.IssuingAttributes.getDefaultInstance() : issuingAttributes_;
        } else {
          return issuingAttributesBuilder_.getMessage();
        }
      }
      /**
       * <code>.sharepubapi_v1.IssuingAttributes issuing_attributes = 2;</code>
       */
      public Builder setIssuingAttributes(com.yoti.api.client.spi.remote.proto.IssuingAttributesProto.IssuingAttributes value) {
        if (issuingAttributesBuilder_ == null) {
          if (value == null) {
            throw new NullPointerException();
          }
          issuingAttributes_ = value;
          onChanged();
        } else {
          issuingAttributesBuilder_.setMessage(value);
        }

        return this;
      }
      /**
       * <code>.sharepubapi_v1.IssuingAttributes issuing_attributes = 2;</code>
       */
      public Builder setIssuingAttributes(
          com.yoti.api.client.spi.remote.proto.IssuingAttributesProto.IssuingAttributes.Builder builderForValue) {
        if (issuingAttributesBuilder_ == null) {
          issuingAttributes_ = builderForValue.build();
          onChanged();
        } else {
          issuingAttributesBuilder_.setMessage(builderForValue.build());
        }

        return this;
      }
      /**
       * <code>.sharepubapi_v1.IssuingAttributes issuing_attributes = 2;</code>
       */
      public Builder mergeIssuingAttributes(com.yoti.api.client.spi.remote.proto.IssuingAttributesProto.IssuingAttributes value) {
        if (issuingAttributesBuilder_ == null) {
          if (issuingAttributes_ != null) {
            issuingAttributes_ =
              com.yoti.api.client.spi.remote.proto.IssuingAttributesProto.IssuingAttributes.newBuilder(issuingAttributes_).mergeFrom(value).buildPartial();
          } else {
            issuingAttributes_ = value;
          }
          onChanged();
        } else {
          issuingAttributesBuilder_.mergeFrom(value);
        }

        return this;
      }
      /**
       * <code>.sharepubapi_v1.IssuingAttributes issuing_attributes = 2;</code>
       */
      public Builder clearIssuingAttributes() {
        if (issuingAttributesBuilder_ == null) {
          issuingAttributes_ = null;
          onChanged();
        } else {
          issuingAttributes_ = null;
          issuingAttributesBuilder_ = null;
        }

        return this;
      }
      /**
       * <code>.sharepubapi_v1.IssuingAttributes issuing_attributes = 2;</code>
       */
      public com.yoti.api.client.spi.remote.proto.IssuingAttributesProto.IssuingAttributes.Builder getIssuingAttributesBuilder() {
        
        onChanged();
        return getIssuingAttributesFieldBuilder().getBuilder();
      }
      /**
       * <code>.sharepubapi_v1.IssuingAttributes issuing_attributes = 2;</code>
       */
      public com.yoti.api.client.spi.remote.proto.IssuingAttributesProto.IssuingAttributesOrBuilder getIssuingAttributesOrBuilder() {
        if (issuingAttributesBuilder_ != null) {
          return issuingAttributesBuilder_.getMessageOrBuilder();
        } else {
          return issuingAttributes_ == null ?
              com.yoti.api.client.spi.remote.proto.IssuingAttributesProto.IssuingAttributes.getDefaultInstance() : issuingAttributes_;
        }
      }
      /**
       * <code>.sharepubapi_v1.IssuingAttributes issuing_attributes = 2;</code>
       */
      private com.google.protobuf.SingleFieldBuilderV3<
          com.yoti.api.client.spi.remote.proto.IssuingAttributesProto.IssuingAttributes, com.yoti.api.client.spi.remote.proto.IssuingAttributesProto.IssuingAttributes.Builder, com.yoti.api.client.spi.remote.proto.IssuingAttributesProto.IssuingAttributesOrBuilder> 
          getIssuingAttributesFieldBuilder() {
        if (issuingAttributesBuilder_ == null) {
          issuingAttributesBuilder_ = new com.google.protobuf.SingleFieldBuilderV3<
              com.yoti.api.client.spi.remote.proto.IssuingAttributesProto.IssuingAttributes, com.yoti.api.client.spi.remote.proto.IssuingAttributesProto.IssuingAttributes.Builder, com.yoti.api.client.spi.remote.proto.IssuingAttributesProto.IssuingAttributesOrBuilder>(
                  getIssuingAttributes(),
                  getParentForChildren(),
                  isClean());
          issuingAttributes_ = null;
        }
        return issuingAttributesBuilder_;
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


      // @@protoc_insertion_point(builder_scope:sharepubapi_v1.ThirdPartyAttribute)
    }

    // @@protoc_insertion_point(class_scope:sharepubapi_v1.ThirdPartyAttribute)
    private static final com.yoti.api.client.spi.remote.proto.ThirdPartyAttributeProto.ThirdPartyAttribute DEFAULT_INSTANCE;
    static {
      DEFAULT_INSTANCE = new com.yoti.api.client.spi.remote.proto.ThirdPartyAttributeProto.ThirdPartyAttribute();
    }

    public static com.yoti.api.client.spi.remote.proto.ThirdPartyAttributeProto.ThirdPartyAttribute getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<ThirdPartyAttribute>
        PARSER = new com.google.protobuf.AbstractParser<ThirdPartyAttribute>() {
      @java.lang.Override
      public ThirdPartyAttribute parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
        return new ThirdPartyAttribute(input, extensionRegistry);
      }
    };

    public static com.google.protobuf.Parser<ThirdPartyAttribute> parser() {
      return PARSER;
    }

    @java.lang.Override
    public com.google.protobuf.Parser<ThirdPartyAttribute> getParserForType() {
      return PARSER;
    }

    @java.lang.Override
    public com.yoti.api.client.spi.remote.proto.ThirdPartyAttributeProto.ThirdPartyAttribute getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }

  }

  private static final com.google.protobuf.Descriptors.Descriptor
    internal_static_sharepubapi_v1_ThirdPartyAttribute_descriptor;
  private static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_sharepubapi_v1_ThirdPartyAttribute_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\031ThirdPartyAttribute.proto\022\016sharepubapi" +
      "_v1\032\027IssuingAttributes.proto\"l\n\023ThirdPar" +
      "tyAttribute\022\026\n\016issuance_token\030\001 \001(\014\022=\n\022i" +
      "ssuing_attributes\030\002 \001(\0132!.sharepubapi_v1" +
      ".IssuingAttributesB\357\001\n$com.yoti.api.clie" +
      "nt.spi.remote.protoB\030ThirdPartyAttribute" +
      "ProtoZ0github.com/getyoti/yoti-go-sdk/v3" +
      "/yotiprotoshare\252\002\030Yoti.Auth.ProtoBuf.Sha" +
      "re\312\002\031Yoti\\Protobuf\\Sharepubapi\342\002%Yoti\\Pr" +
      "otobuf\\Sharepubapi\\GPBMetadata\352\002\033Yoti::P" +
      "rotobuf::Sharepubapib\006proto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
          com.yoti.api.client.spi.remote.proto.IssuingAttributesProto.getDescriptor(),
        });
    internal_static_sharepubapi_v1_ThirdPartyAttribute_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_sharepubapi_v1_ThirdPartyAttribute_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_sharepubapi_v1_ThirdPartyAttribute_descriptor,
        new java.lang.String[] { "IssuanceToken", "IssuingAttributes", });
    com.yoti.api.client.spi.remote.proto.IssuingAttributesProto.getDescriptor();
  }

  // @@protoc_insertion_point(outer_class_scope)
}
