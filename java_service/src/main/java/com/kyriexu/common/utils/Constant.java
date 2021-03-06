package com.kyriexu.common.utils;

/**
 * @author KyrieXu
 * @since 2021/3/23 12:49
 **/
public class Constant {
    public final static int SUCCESS = 200;
    public final static int COMMON_FAIL = 400;
    public final static int SERVER_ERROR = 500;

    public final static int SIZE_LIMIT = 50;

    public final static String SERVICE_ALL = "all";
    public final static String SERVICE_TCP = "tcp";
    public final static String SERVICE_HTTP = "http";
    public final static String SERVICE_GRPC = "grpc";

    public final static String GO_SERVICE = "gateway-go";

    public final static int SERVICE_DESC_MIN = 1;
    public final static int SERVICE_DESC_MAX = 255;

    public final static String REDIS_FLOW_DAY_KEY = "flow_day_count";
    public final static String REDIS_FLOW_HOUR_KEY = "flow_hour_count";

    public final static String FLOW_TOTAL = "flow_total";
    public final static String FLOW_SERVICE_PREFIX = "flow_service_";
    public final static String FLOW_APP_PREFIX = "flow_app_";

    public final static int REDIS_DAY_KEY_EXPIRE = 86400 * 2;
    public final static int REDIS_HOUR_KEY_EXPIRE = 86400 * 2;

    public final static String CODE = "code";

    public final static int HTTPLoadType = 0;
    public final static int TCPLoadType = 1;
    public final static int GrpcLoadType = 2;


    public final static int HTTPRuleTypePrefixURL = 0;
    public final static int HTTPRuleTypeDomain = 1;

    public final static int NeedHttps = 1;
    public final static int NoNeedHttps = 0;

    public final static String X_GATEWAY_TOKEN = "X-GATEWAY-TOKEN";

    public final static String UUID_REGEX = "^[0-9a-f]{8}(-[0-9a-f]{4}){3}-[0-9a-f]{12}$";

    public final static String ContentTypeJson = "application/json";

    public final static String FlowAppPrefix = "flow_app_";

    public final static String PASSWORD_SALT = "admin";

    public final static int MIN_PAGE = 1;

    public final static int RandomRoundType = 0;
    public final static int RoundRobinRoundType = 1;
    public final static int WeightRoundType = 2;
    public final static int IpHashRoundType = 3;

    public final static String[] SERVICE_TYPE = new String[]{
            SERVICE_HTTP,
            SERVICE_TCP,
            SERVICE_GRPC
    };


    public final static String USER = "user";
}
