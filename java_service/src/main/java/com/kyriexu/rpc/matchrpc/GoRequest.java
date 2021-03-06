// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: antpathmatcher.proto

package com.kyriexu.rpc.matchrpc;

/**
 * Protobuf type {@code rpc.GoRequest}
 */
public final class GoRequest extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:rpc.GoRequest)
    GoRequestOrBuilder {
private static final long serialVersionUID = 0L;
  // Use GoRequest.newBuilder() to construct.
  private GoRequest(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private GoRequest() {
    realPath_ = "";
    method_ = "";
    remoteAddr_ = "";
    proto_ = "";
  }

  @Override
  @SuppressWarnings({"unused"})
  protected Object newInstance(
      UnusedPrivateParameter unused) {
    return new GoRequest();
  }

  @Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return this.unknownFields;
  }
  private GoRequest(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    this();
    if (extensionRegistry == null) {
      throw new NullPointerException();
    }
    int mutable_bitField0_ = 0;
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
            String s = input.readStringRequireUtf8();

            realPath_ = s;
            break;
          }
          case 18: {
            String s = input.readStringRequireUtf8();

            method_ = s;
            break;
          }
          case 26: {
            String s = input.readStringRequireUtf8();

            remoteAddr_ = s;
            break;
          }
          case 34: {
            String s = input.readStringRequireUtf8();

            proto_ = s;
            break;
          }
          case 42: {
            if (!((mutable_bitField0_ & 0x00000001) != 0)) {
              header_ = com.google.protobuf.MapField.newMapField(
                  HeaderDefaultEntryHolder.defaultEntry);
              mutable_bitField0_ |= 0x00000001;
            }
            com.google.protobuf.MapEntry<String, Header>
            header__ = input.readMessage(
                HeaderDefaultEntryHolder.defaultEntry.getParserForType(), extensionRegistry);
            header_.getMutableMap().put(
                header__.getKey(), header__.getValue());
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
    return AntPathMatch.internal_static_rpc_GoRequest_descriptor;
  }

  @SuppressWarnings({"rawtypes"})
  @Override
  protected com.google.protobuf.MapField internalGetMapField(
      int number) {
    switch (number) {
      case 5:
        return internalGetHeader();
      default:
        throw new RuntimeException(
            "Invalid map field number: " + number);
    }
  }
  @Override
  protected FieldAccessorTable
      internalGetFieldAccessorTable() {
    return AntPathMatch.internal_static_rpc_GoRequest_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            GoRequest.class, Builder.class);
  }

  public static final int REALPATH_FIELD_NUMBER = 1;
  private volatile Object realPath_;
  /**
   * <code>string realPath = 1;</code>
   * @return The realPath.
   */
  @Override
  public String getRealPath() {
    Object ref = realPath_;
    if (ref instanceof String) {
      return (String) ref;
    } else {
      com.google.protobuf.ByteString bs = 
          (com.google.protobuf.ByteString) ref;
      String s = bs.toStringUtf8();
      realPath_ = s;
      return s;
    }
  }
  /**
   * <code>string realPath = 1;</code>
   * @return The bytes for realPath.
   */
  @Override
  public com.google.protobuf.ByteString
      getRealPathBytes() {
    Object ref = realPath_;
    if (ref instanceof String) {
      com.google.protobuf.ByteString b = 
          com.google.protobuf.ByteString.copyFromUtf8(
              (String) ref);
      realPath_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  public static final int METHOD_FIELD_NUMBER = 2;
  private volatile Object method_;
  /**
   * <code>string method = 2;</code>
   * @return The method.
   */
  @Override
  public String getMethod() {
    Object ref = method_;
    if (ref instanceof String) {
      return (String) ref;
    } else {
      com.google.protobuf.ByteString bs = 
          (com.google.protobuf.ByteString) ref;
      String s = bs.toStringUtf8();
      method_ = s;
      return s;
    }
  }
  /**
   * <code>string method = 2;</code>
   * @return The bytes for method.
   */
  @Override
  public com.google.protobuf.ByteString
      getMethodBytes() {
    Object ref = method_;
    if (ref instanceof String) {
      com.google.protobuf.ByteString b = 
          com.google.protobuf.ByteString.copyFromUtf8(
              (String) ref);
      method_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  public static final int REMOTEADDR_FIELD_NUMBER = 3;
  private volatile Object remoteAddr_;
  /**
   * <code>string remoteAddr = 3;</code>
   * @return The remoteAddr.
   */
  @Override
  public String getRemoteAddr() {
    Object ref = remoteAddr_;
    if (ref instanceof String) {
      return (String) ref;
    } else {
      com.google.protobuf.ByteString bs = 
          (com.google.protobuf.ByteString) ref;
      String s = bs.toStringUtf8();
      remoteAddr_ = s;
      return s;
    }
  }
  /**
   * <code>string remoteAddr = 3;</code>
   * @return The bytes for remoteAddr.
   */
  @Override
  public com.google.protobuf.ByteString
      getRemoteAddrBytes() {
    Object ref = remoteAddr_;
    if (ref instanceof String) {
      com.google.protobuf.ByteString b = 
          com.google.protobuf.ByteString.copyFromUtf8(
              (String) ref);
      remoteAddr_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  public static final int PROTO_FIELD_NUMBER = 4;
  private volatile Object proto_;
  /**
   * <code>string proto = 4;</code>
   * @return The proto.
   */
  @Override
  public String getProto() {
    Object ref = proto_;
    if (ref instanceof String) {
      return (String) ref;
    } else {
      com.google.protobuf.ByteString bs = 
          (com.google.protobuf.ByteString) ref;
      String s = bs.toStringUtf8();
      proto_ = s;
      return s;
    }
  }
  /**
   * <code>string proto = 4;</code>
   * @return The bytes for proto.
   */
  @Override
  public com.google.protobuf.ByteString
      getProtoBytes() {
    Object ref = proto_;
    if (ref instanceof String) {
      com.google.protobuf.ByteString b = 
          com.google.protobuf.ByteString.copyFromUtf8(
              (String) ref);
      proto_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  public static final int HEADER_FIELD_NUMBER = 5;
  private static final class HeaderDefaultEntryHolder {
    static final com.google.protobuf.MapEntry<
        String, Header> defaultEntry =
            com.google.protobuf.MapEntry
            .<String, Header>newDefaultInstance(
                AntPathMatch.internal_static_rpc_GoRequest_HeaderEntry_descriptor,
                com.google.protobuf.WireFormat.FieldType.STRING,
                "",
                com.google.protobuf.WireFormat.FieldType.MESSAGE,
                Header.getDefaultInstance());
  }
  private com.google.protobuf.MapField<
      String, Header> header_;
  private com.google.protobuf.MapField<String, Header>
  internalGetHeader() {
    if (header_ == null) {
      return com.google.protobuf.MapField.emptyMapField(
          HeaderDefaultEntryHolder.defaultEntry);
    }
    return header_;
  }

  public int getHeaderCount() {
    return internalGetHeader().getMap().size();
  }
  /**
   * <code>map&lt;string, .rpc.Header&gt; header = 5;</code>
   */

  @Override
  public boolean containsHeader(
      String key) {
    if (key == null) { throw new NullPointerException(); }
    return internalGetHeader().getMap().containsKey(key);
  }
  /**
   * Use {@link #getHeaderMap()} instead.
   */
  @Override
  @Deprecated
  public java.util.Map<String, Header> getHeader() {
    return getHeaderMap();
  }
  /**
   * <code>map&lt;string, .rpc.Header&gt; header = 5;</code>
   */
  @Override

  public java.util.Map<String, Header> getHeaderMap() {
    return internalGetHeader().getMap();
  }
  /**
   * <code>map&lt;string, .rpc.Header&gt; header = 5;</code>
   */
  @Override

  public Header getHeaderOrDefault(
      String key,
      Header defaultValue) {
    if (key == null) { throw new NullPointerException(); }
    java.util.Map<String, Header> map =
        internalGetHeader().getMap();
    return map.containsKey(key) ? map.get(key) : defaultValue;
  }
  /**
   * <code>map&lt;string, .rpc.Header&gt; header = 5;</code>
   */
  @Override

  public Header getHeaderOrThrow(
      String key) {
    if (key == null) { throw new NullPointerException(); }
    java.util.Map<String, Header> map =
        internalGetHeader().getMap();
    if (!map.containsKey(key)) {
      throw new IllegalArgumentException();
    }
    return map.get(key);
  }

  private byte memoizedIsInitialized = -1;
  @Override
  public final boolean isInitialized() {
    byte isInitialized = memoizedIsInitialized;
    if (isInitialized == 1) return true;
    if (isInitialized == 0) return false;

    memoizedIsInitialized = 1;
    return true;
  }

  @Override
  public void writeTo(com.google.protobuf.CodedOutputStream output)
                      throws java.io.IOException {
    if (!getRealPathBytes().isEmpty()) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 1, realPath_);
    }
    if (!getMethodBytes().isEmpty()) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 2, method_);
    }
    if (!getRemoteAddrBytes().isEmpty()) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 3, remoteAddr_);
    }
    if (!getProtoBytes().isEmpty()) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 4, proto_);
    }
    com.google.protobuf.GeneratedMessageV3
      .serializeStringMapTo(
        output,
        internalGetHeader(),
        HeaderDefaultEntryHolder.defaultEntry,
        5);
    unknownFields.writeTo(output);
  }

  @Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (!getRealPathBytes().isEmpty()) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(1, realPath_);
    }
    if (!getMethodBytes().isEmpty()) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(2, method_);
    }
    if (!getRemoteAddrBytes().isEmpty()) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(3, remoteAddr_);
    }
    if (!getProtoBytes().isEmpty()) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(4, proto_);
    }
    for (java.util.Map.Entry<String, Header> entry
         : internalGetHeader().getMap().entrySet()) {
      com.google.protobuf.MapEntry<String, Header>
      header__ = HeaderDefaultEntryHolder.defaultEntry.newBuilderForType()
          .setKey(entry.getKey())
          .setValue(entry.getValue())
          .build();
      size += com.google.protobuf.CodedOutputStream
          .computeMessageSize(5, header__);
    }
    size += unknownFields.getSerializedSize();
    memoizedSize = size;
    return size;
  }

  @Override
  public boolean equals(final Object obj) {
    if (obj == this) {
     return true;
    }
    if (!(obj instanceof GoRequest)) {
      return super.equals(obj);
    }
    GoRequest other = (GoRequest) obj;

    if (!getRealPath()
        .equals(other.getRealPath())) return false;
    if (!getMethod()
        .equals(other.getMethod())) return false;
    if (!getRemoteAddr()
        .equals(other.getRemoteAddr())) return false;
    if (!getProto()
        .equals(other.getProto())) return false;
    if (!internalGetHeader().equals(
        other.internalGetHeader())) return false;
    if (!unknownFields.equals(other.unknownFields)) return false;
    return true;
  }

  @Override
  public int hashCode() {
    if (memoizedHashCode != 0) {
      return memoizedHashCode;
    }
    int hash = 41;
    hash = (19 * hash) + getDescriptor().hashCode();
    hash = (37 * hash) + REALPATH_FIELD_NUMBER;
    hash = (53 * hash) + getRealPath().hashCode();
    hash = (37 * hash) + METHOD_FIELD_NUMBER;
    hash = (53 * hash) + getMethod().hashCode();
    hash = (37 * hash) + REMOTEADDR_FIELD_NUMBER;
    hash = (53 * hash) + getRemoteAddr().hashCode();
    hash = (37 * hash) + PROTO_FIELD_NUMBER;
    hash = (53 * hash) + getProto().hashCode();
    if (!internalGetHeader().getMap().isEmpty()) {
      hash = (37 * hash) + HEADER_FIELD_NUMBER;
      hash = (53 * hash) + internalGetHeader().hashCode();
    }
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static GoRequest parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static GoRequest parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static GoRequest parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static GoRequest parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static GoRequest parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static GoRequest parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static GoRequest parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static GoRequest parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static GoRequest parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static GoRequest parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static GoRequest parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static GoRequest parseFrom(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  @Override
  public Builder newBuilderForType() { return newBuilder(); }
  public static Builder newBuilder() {
    return DEFAULT_INSTANCE.toBuilder();
  }
  public static Builder newBuilder(GoRequest prototype) {
    return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
  }
  @Override
  public Builder toBuilder() {
    return this == DEFAULT_INSTANCE
        ? new Builder() : new Builder().mergeFrom(this);
  }

  @Override
  protected Builder newBuilderForType(
      BuilderParent parent) {
    Builder builder = new Builder(parent);
    return builder;
  }
  /**
   * Protobuf type {@code rpc.GoRequest}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:rpc.GoRequest)
      GoRequestOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return AntPathMatch.internal_static_rpc_GoRequest_descriptor;
    }

    @SuppressWarnings({"rawtypes"})
    protected com.google.protobuf.MapField internalGetMapField(
        int number) {
      switch (number) {
        case 5:
          return internalGetHeader();
        default:
          throw new RuntimeException(
              "Invalid map field number: " + number);
      }
    }
    @SuppressWarnings({"rawtypes"})
    protected com.google.protobuf.MapField internalGetMutableMapField(
        int number) {
      switch (number) {
        case 5:
          return internalGetMutableHeader();
        default:
          throw new RuntimeException(
              "Invalid map field number: " + number);
      }
    }
    @Override
    protected FieldAccessorTable
        internalGetFieldAccessorTable() {
      return AntPathMatch.internal_static_rpc_GoRequest_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              GoRequest.class, Builder.class);
    }

    // Construct using com.kyriexu.rpc.matchrpc.GoRequest.newBuilder()
    private Builder() {
      maybeForceBuilderInitialization();
    }

    private Builder(
        BuilderParent parent) {
      super(parent);
      maybeForceBuilderInitialization();
    }
    private void maybeForceBuilderInitialization() {
      if (com.google.protobuf.GeneratedMessageV3
              .alwaysUseFieldBuilders) {
      }
    }
    @Override
    public Builder clear() {
      super.clear();
      realPath_ = "";

      method_ = "";

      remoteAddr_ = "";

      proto_ = "";

      internalGetMutableHeader().clear();
      return this;
    }

    @Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return AntPathMatch.internal_static_rpc_GoRequest_descriptor;
    }

    @Override
    public GoRequest getDefaultInstanceForType() {
      return GoRequest.getDefaultInstance();
    }

    @Override
    public GoRequest build() {
      GoRequest result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @Override
    public GoRequest buildPartial() {
      GoRequest result = new GoRequest(this);
      int from_bitField0_ = bitField0_;
      result.realPath_ = realPath_;
      result.method_ = method_;
      result.remoteAddr_ = remoteAddr_;
      result.proto_ = proto_;
      result.header_ = internalGetHeader();
      result.header_.makeImmutable();
      onBuilt();
      return result;
    }

    @Override
    public Builder clone() {
      return super.clone();
    }
    @Override
    public Builder setField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        Object value) {
      return super.setField(field, value);
    }
    @Override
    public Builder clearField(
        com.google.protobuf.Descriptors.FieldDescriptor field) {
      return super.clearField(field);
    }
    @Override
    public Builder clearOneof(
        com.google.protobuf.Descriptors.OneofDescriptor oneof) {
      return super.clearOneof(oneof);
    }
    @Override
    public Builder setRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        int index, Object value) {
      return super.setRepeatedField(field, index, value);
    }
    @Override
    public Builder addRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        Object value) {
      return super.addRepeatedField(field, value);
    }
    @Override
    public Builder mergeFrom(com.google.protobuf.Message other) {
      if (other instanceof GoRequest) {
        return mergeFrom((GoRequest)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(GoRequest other) {
      if (other == GoRequest.getDefaultInstance()) return this;
      if (!other.getRealPath().isEmpty()) {
        realPath_ = other.realPath_;
        onChanged();
      }
      if (!other.getMethod().isEmpty()) {
        method_ = other.method_;
        onChanged();
      }
      if (!other.getRemoteAddr().isEmpty()) {
        remoteAddr_ = other.remoteAddr_;
        onChanged();
      }
      if (!other.getProto().isEmpty()) {
        proto_ = other.proto_;
        onChanged();
      }
      internalGetMutableHeader().mergeFrom(
          other.internalGetHeader());
      this.mergeUnknownFields(other.unknownFields);
      onChanged();
      return this;
    }

    @Override
    public final boolean isInitialized() {
      return true;
    }

    @Override
    public Builder mergeFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      GoRequest parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (GoRequest) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }
    private int bitField0_;

    private Object realPath_ = "";
    /**
     * <code>string realPath = 1;</code>
     * @return The realPath.
     */
    public String getRealPath() {
      Object ref = realPath_;
      if (!(ref instanceof String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        String s = bs.toStringUtf8();
        realPath_ = s;
        return s;
      } else {
        return (String) ref;
      }
    }
    /**
     * <code>string realPath = 1;</code>
     * @return The bytes for realPath.
     */
    public com.google.protobuf.ByteString
        getRealPathBytes() {
      Object ref = realPath_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (String) ref);
        realPath_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <code>string realPath = 1;</code>
     * @param value The realPath to set.
     * @return This builder for chaining.
     */
    public Builder setRealPath(
        String value) {
      if (value == null) {
    throw new NullPointerException();
  }
  
      realPath_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>string realPath = 1;</code>
     * @return This builder for chaining.
     */
    public Builder clearRealPath() {
      
      realPath_ = getDefaultInstance().getRealPath();
      onChanged();
      return this;
    }
    /**
     * <code>string realPath = 1;</code>
     * @param value The bytes for realPath to set.
     * @return This builder for chaining.
     */
    public Builder setRealPathBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
      
      realPath_ = value;
      onChanged();
      return this;
    }

    private Object method_ = "";
    /**
     * <code>string method = 2;</code>
     * @return The method.
     */
    public String getMethod() {
      Object ref = method_;
      if (!(ref instanceof String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        String s = bs.toStringUtf8();
        method_ = s;
        return s;
      } else {
        return (String) ref;
      }
    }
    /**
     * <code>string method = 2;</code>
     * @return The bytes for method.
     */
    public com.google.protobuf.ByteString
        getMethodBytes() {
      Object ref = method_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (String) ref);
        method_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <code>string method = 2;</code>
     * @param value The method to set.
     * @return This builder for chaining.
     */
    public Builder setMethod(
        String value) {
      if (value == null) {
    throw new NullPointerException();
  }
  
      method_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>string method = 2;</code>
     * @return This builder for chaining.
     */
    public Builder clearMethod() {
      
      method_ = getDefaultInstance().getMethod();
      onChanged();
      return this;
    }
    /**
     * <code>string method = 2;</code>
     * @param value The bytes for method to set.
     * @return This builder for chaining.
     */
    public Builder setMethodBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
      
      method_ = value;
      onChanged();
      return this;
    }

    private Object remoteAddr_ = "";
    /**
     * <code>string remoteAddr = 3;</code>
     * @return The remoteAddr.
     */
    public String getRemoteAddr() {
      Object ref = remoteAddr_;
      if (!(ref instanceof String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        String s = bs.toStringUtf8();
        remoteAddr_ = s;
        return s;
      } else {
        return (String) ref;
      }
    }
    /**
     * <code>string remoteAddr = 3;</code>
     * @return The bytes for remoteAddr.
     */
    public com.google.protobuf.ByteString
        getRemoteAddrBytes() {
      Object ref = remoteAddr_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (String) ref);
        remoteAddr_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <code>string remoteAddr = 3;</code>
     * @param value The remoteAddr to set.
     * @return This builder for chaining.
     */
    public Builder setRemoteAddr(
        String value) {
      if (value == null) {
    throw new NullPointerException();
  }
  
      remoteAddr_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>string remoteAddr = 3;</code>
     * @return This builder for chaining.
     */
    public Builder clearRemoteAddr() {
      
      remoteAddr_ = getDefaultInstance().getRemoteAddr();
      onChanged();
      return this;
    }
    /**
     * <code>string remoteAddr = 3;</code>
     * @param value The bytes for remoteAddr to set.
     * @return This builder for chaining.
     */
    public Builder setRemoteAddrBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
      
      remoteAddr_ = value;
      onChanged();
      return this;
    }

    private Object proto_ = "";
    /**
     * <code>string proto = 4;</code>
     * @return The proto.
     */
    public String getProto() {
      Object ref = proto_;
      if (!(ref instanceof String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        String s = bs.toStringUtf8();
        proto_ = s;
        return s;
      } else {
        return (String) ref;
      }
    }
    /**
     * <code>string proto = 4;</code>
     * @return The bytes for proto.
     */
    public com.google.protobuf.ByteString
        getProtoBytes() {
      Object ref = proto_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (String) ref);
        proto_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <code>string proto = 4;</code>
     * @param value The proto to set.
     * @return This builder for chaining.
     */
    public Builder setProto(
        String value) {
      if (value == null) {
    throw new NullPointerException();
  }
  
      proto_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>string proto = 4;</code>
     * @return This builder for chaining.
     */
    public Builder clearProto() {
      
      proto_ = getDefaultInstance().getProto();
      onChanged();
      return this;
    }
    /**
     * <code>string proto = 4;</code>
     * @param value The bytes for proto to set.
     * @return This builder for chaining.
     */
    public Builder setProtoBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
      
      proto_ = value;
      onChanged();
      return this;
    }

    private com.google.protobuf.MapField<
        String, Header> header_;
    private com.google.protobuf.MapField<String, Header>
    internalGetHeader() {
      if (header_ == null) {
        return com.google.protobuf.MapField.emptyMapField(
            HeaderDefaultEntryHolder.defaultEntry);
      }
      return header_;
    }
    private com.google.protobuf.MapField<String, Header>
    internalGetMutableHeader() {
      onChanged();;
      if (header_ == null) {
        header_ = com.google.protobuf.MapField.newMapField(
            HeaderDefaultEntryHolder.defaultEntry);
      }
      if (!header_.isMutable()) {
        header_ = header_.copy();
      }
      return header_;
    }

    public int getHeaderCount() {
      return internalGetHeader().getMap().size();
    }
    /**
     * <code>map&lt;string, .rpc.Header&gt; header = 5;</code>
     */

    @Override
    public boolean containsHeader(
        String key) {
      if (key == null) { throw new NullPointerException(); }
      return internalGetHeader().getMap().containsKey(key);
    }
    /**
     * Use {@link #getHeaderMap()} instead.
     */
    @Override
    @Deprecated
    public java.util.Map<String, Header> getHeader() {
      return getHeaderMap();
    }
    /**
     * <code>map&lt;string, .rpc.Header&gt; header = 5;</code>
     */
    @Override

    public java.util.Map<String, Header> getHeaderMap() {
      return internalGetHeader().getMap();
    }
    /**
     * <code>map&lt;string, .rpc.Header&gt; header = 5;</code>
     */
    @Override

    public Header getHeaderOrDefault(
        String key,
        Header defaultValue) {
      if (key == null) { throw new NullPointerException(); }
      java.util.Map<String, Header> map =
          internalGetHeader().getMap();
      return map.containsKey(key) ? map.get(key) : defaultValue;
    }
    /**
     * <code>map&lt;string, .rpc.Header&gt; header = 5;</code>
     */
    @Override

    public Header getHeaderOrThrow(
        String key) {
      if (key == null) { throw new NullPointerException(); }
      java.util.Map<String, Header> map =
          internalGetHeader().getMap();
      if (!map.containsKey(key)) {
        throw new IllegalArgumentException();
      }
      return map.get(key);
    }

    public Builder clearHeader() {
      internalGetMutableHeader().getMutableMap()
          .clear();
      return this;
    }
    /**
     * <code>map&lt;string, .rpc.Header&gt; header = 5;</code>
     */

    public Builder removeHeader(
        String key) {
      if (key == null) { throw new NullPointerException(); }
      internalGetMutableHeader().getMutableMap()
          .remove(key);
      return this;
    }
    /**
     * Use alternate mutation accessors instead.
     */
    @Deprecated
    public java.util.Map<String, Header>
    getMutableHeader() {
      return internalGetMutableHeader().getMutableMap();
    }
    /**
     * <code>map&lt;string, .rpc.Header&gt; header = 5;</code>
     */
    public Builder putHeader(
        String key,
        Header value) {
      if (key == null) { throw new NullPointerException(); }
      if (value == null) { throw new NullPointerException(); }
      internalGetMutableHeader().getMutableMap()
          .put(key, value);
      return this;
    }
    /**
     * <code>map&lt;string, .rpc.Header&gt; header = 5;</code>
     */

    public Builder putAllHeader(
        java.util.Map<String, Header> values) {
      internalGetMutableHeader().getMutableMap()
          .putAll(values);
      return this;
    }
    @Override
    public final Builder setUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.setUnknownFields(unknownFields);
    }

    @Override
    public final Builder mergeUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.mergeUnknownFields(unknownFields);
    }


    // @@protoc_insertion_point(builder_scope:rpc.GoRequest)
  }

  // @@protoc_insertion_point(class_scope:rpc.GoRequest)
  private static final GoRequest DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new GoRequest();
  }

  public static GoRequest getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<GoRequest>
      PARSER = new com.google.protobuf.AbstractParser<GoRequest>() {
    @Override
    public GoRequest parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return new GoRequest(input, extensionRegistry);
    }
  };

  public static com.google.protobuf.Parser<GoRequest> parser() {
    return PARSER;
  }

  @Override
  public com.google.protobuf.Parser<GoRequest> getParserForType() {
    return PARSER;
  }

  @Override
  public GoRequest getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

