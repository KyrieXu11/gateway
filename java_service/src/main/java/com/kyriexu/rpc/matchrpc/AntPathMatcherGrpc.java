package com.kyriexu.rpc;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.35.0)",
    comments = "Source: antpathmatcher.proto")
public final class AntPathMatcherGrpc {

  private AntPathMatcherGrpc() {}

  public static final String SERVICE_NAME = "rpc.AntPathMatcher";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.kyriexu.rpc.GoRequest,
      com.kyriexu.rpc.Result> getMatchMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "match",
      requestType = com.kyriexu.rpc.GoRequest.class,
      responseType = com.kyriexu.rpc.Result.class,
      methodType = io.grpc.MethodDescriptor.MethodType.CLIENT_STREAMING)
  public static io.grpc.MethodDescriptor<com.kyriexu.rpc.GoRequest,
      com.kyriexu.rpc.Result> getMatchMethod() {
    io.grpc.MethodDescriptor<com.kyriexu.rpc.GoRequest, com.kyriexu.rpc.Result> getMatchMethod;
    if ((getMatchMethod = AntPathMatcherGrpc.getMatchMethod) == null) {
      synchronized (AntPathMatcherGrpc.class) {
        if ((getMatchMethod = AntPathMatcherGrpc.getMatchMethod) == null) {
          AntPathMatcherGrpc.getMatchMethod = getMatchMethod =
              io.grpc.MethodDescriptor.<com.kyriexu.rpc.GoRequest, com.kyriexu.rpc.Result>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.CLIENT_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "match"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.kyriexu.rpc.GoRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.kyriexu.rpc.Result.getDefaultInstance()))
              .setSchemaDescriptor(new AntPathMatcherMethodDescriptorSupplier("match"))
              .build();
        }
      }
    }
    return getMatchMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static AntPathMatcherStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<AntPathMatcherStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<AntPathMatcherStub>() {
        @java.lang.Override
        public AntPathMatcherStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new AntPathMatcherStub(channel, callOptions);
        }
      };
    return AntPathMatcherStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static AntPathMatcherBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<AntPathMatcherBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<AntPathMatcherBlockingStub>() {
        @java.lang.Override
        public AntPathMatcherBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new AntPathMatcherBlockingStub(channel, callOptions);
        }
      };
    return AntPathMatcherBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static AntPathMatcherFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<AntPathMatcherFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<AntPathMatcherFutureStub>() {
        @java.lang.Override
        public AntPathMatcherFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new AntPathMatcherFutureStub(channel, callOptions);
        }
      };
    return AntPathMatcherFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class AntPathMatcherImplBase implements io.grpc.BindableService {

    /**
     */
    public io.grpc.stub.StreamObserver<com.kyriexu.rpc.GoRequest> match(
        io.grpc.stub.StreamObserver<com.kyriexu.rpc.Result> responseObserver) {
      return io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall(getMatchMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getMatchMethod(),
            io.grpc.stub.ServerCalls.asyncClientStreamingCall(
              new MethodHandlers<
                com.kyriexu.rpc.GoRequest,
                com.kyriexu.rpc.Result>(
                  this, METHODID_MATCH)))
          .build();
    }
  }

  /**
   */
  public static final class AntPathMatcherStub extends io.grpc.stub.AbstractAsyncStub<AntPathMatcherStub> {
    private AntPathMatcherStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected AntPathMatcherStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new AntPathMatcherStub(channel, callOptions);
    }

    /**
     */
    public io.grpc.stub.StreamObserver<com.kyriexu.rpc.GoRequest> match(
        io.grpc.stub.StreamObserver<com.kyriexu.rpc.Result> responseObserver) {
      return io.grpc.stub.ClientCalls.asyncClientStreamingCall(
          getChannel().newCall(getMatchMethod(), getCallOptions()), responseObserver);
    }
  }

  /**
   */
  public static final class AntPathMatcherBlockingStub extends io.grpc.stub.AbstractBlockingStub<AntPathMatcherBlockingStub> {
    private AntPathMatcherBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected AntPathMatcherBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new AntPathMatcherBlockingStub(channel, callOptions);
    }
  }

  /**
   */
  public static final class AntPathMatcherFutureStub extends io.grpc.stub.AbstractFutureStub<AntPathMatcherFutureStub> {
    private AntPathMatcherFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected AntPathMatcherFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new AntPathMatcherFutureStub(channel, callOptions);
    }
  }

  private static final int METHODID_MATCH = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final AntPathMatcherImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(AntPathMatcherImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_MATCH:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.match(
              (io.grpc.stub.StreamObserver<com.kyriexu.rpc.Result>) responseObserver);
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class AntPathMatcherBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    AntPathMatcherBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.kyriexu.rpc.AntPathMatch.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("AntPathMatcher");
    }
  }

  private static final class AntPathMatcherFileDescriptorSupplier
      extends AntPathMatcherBaseDescriptorSupplier {
    AntPathMatcherFileDescriptorSupplier() {}
  }

  private static final class AntPathMatcherMethodDescriptorSupplier
      extends AntPathMatcherBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    AntPathMatcherMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (AntPathMatcherGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new AntPathMatcherFileDescriptorSupplier())
              .addMethod(getMatchMethod())
              .build();
        }
      }
    }
    return result;
  }
}
