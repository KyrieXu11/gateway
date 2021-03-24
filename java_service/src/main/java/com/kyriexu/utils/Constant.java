package com.kyriexu.utils;

/**
 * @author KyrieXu
 * @since 2021/3/23 12:49
 **/
public class Constant {
    public final static int SUCCESS = 200;
    public final static int COMMON_FAIL = 400;

    public final static int SIZE_LIMIT = 50;

    public final static String SERVICE_ALL = "all";
    public final static String SERVICE_TCP = "tcp";
    public final static String SERVICE_HTTP = "http";
    public final static String SERVICE_GRPC = "grpc";

    public final static int HTTPLoadType = 0;
    public final static int TCPLoadType = 1;
    public final static int GrpcLoadType = 2;


    public final static int HTTPRuleTypePrefixURL = 0;
    public final static int HTTPRuleTypeDomain = 1;

    public final static int NeedHttps = 1;
    public final static int NoNeedHttps = 0;


    public final static String[] SERVICE_TYPE = new String[]{
            SERVICE_ALL,
            SERVICE_TCP,
            SERVICE_HTTP,
            SERVICE_GRPC
    };


    public final static String USER = "user";
}
