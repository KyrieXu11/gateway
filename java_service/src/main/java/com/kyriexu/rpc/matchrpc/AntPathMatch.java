// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: antpathmatcher.proto

package com.kyriexu.rpc.matchrpc;

public final class AntPathMatch {
  private AntPathMatch() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_rpc_GoRequest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_rpc_GoRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_rpc_GoRequest_HeaderEntry_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_rpc_GoRequest_HeaderEntry_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_rpc_Header_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_rpc_Header_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_rpc_Result_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_rpc_Result_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    String[] descriptorData = {
      "\n\024antpathmatcher.proto\022\003rpc\"\270\001\n\tGoReques" +
      "t\022\020\n\010realPath\030\001 \001(\t\022\016\n\006method\030\002 \001(\t\022\022\n\nr" +
      "emoteAddr\030\003 \001(\t\022\r\n\005proto\030\004 \001(\t\022*\n\006header" +
      "\030\005 \003(\0132\032.rpc.GoRequest.HeaderEntry\032:\n\013He" +
      "aderEntry\022\013\n\003key\030\001 \001(\t\022\032\n\005value\030\002 \001(\0132\013." +
      "rpc.Header:\0028\001\"\035\n\006Header\022\023\n\013HeaderValue\030" +
      "\001 \003(\t\"\025\n\006Result\022\013\n\003res\030\001 \001(\0102:\n\016AntPathM" +
      "atcher\022(\n\005match\022\016.rpc.GoRequest\032\013.rpc.Re" +
      "sult\"\000(\001B>\n\030com.kyriexu.rpc.matchrpcB\014An" +
      "tPathMatchP\001Z\022gateway/common/rpcb\006proto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        });
    internal_static_rpc_GoRequest_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_rpc_GoRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_rpc_GoRequest_descriptor,
        new String[] { "RealPath", "Method", "RemoteAddr", "Proto", "Header", });
    internal_static_rpc_GoRequest_HeaderEntry_descriptor =
      internal_static_rpc_GoRequest_descriptor.getNestedTypes().get(0);
    internal_static_rpc_GoRequest_HeaderEntry_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_rpc_GoRequest_HeaderEntry_descriptor,
        new String[] { "Key", "Value", });
    internal_static_rpc_Header_descriptor =
      getDescriptor().getMessageTypes().get(1);
    internal_static_rpc_Header_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_rpc_Header_descriptor,
        new String[] { "HeaderValue", });
    internal_static_rpc_Result_descriptor =
      getDescriptor().getMessageTypes().get(2);
    internal_static_rpc_Result_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_rpc_Result_descriptor,
        new String[] { "Res", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}
