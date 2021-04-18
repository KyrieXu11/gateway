package com.kyriexu.common.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.kyriexu.dto.InternResp;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Map;

/**
 * @author KyrieXu
 * @since 2021/4/14 16:50
 **/
public class HttpUtils {

    public static final Logger logger = LoggerFactory.getLogger(HttpUtils.class);

    private static final ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        // 2006-01-02T15:04:05Z07:00
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'"));
    }

    public static <T, V> T get(String url, Map<String, V> params, Class<T> tClass) throws IOException {
        StringBuilder sb = new StringBuilder(url);
        if (params != null && params.size() != 0) {
            sb.append("?");
            params.forEach((k, v) -> sb.append(k)
                    .append("=")
                    .append(v)
                    .append("&"));
        }
        url = sb.toString();
        String res;
        try (CloseableHttpClient client = HttpClientBuilder.create().build()) {
            HttpGet get = new HttpGet(url);
            try (CloseableHttpResponse response = client.execute(get)) {
                if (response.getStatusLine().getStatusCode() == Constant.SUCCESS) {
                    HttpEntity entity = response.getEntity();
                    res = EntityUtils.toString(entity);
                    InternResp<T> resp = objectMapper.readValue(res, InternResp.class);
                    if (resp.getCode() == Constant.SUCCESS) {
                        T data = resp.getData();
                        return objectMapper.convertValue(data, tClass);
                    }
                }
            }
        }
        return null;
    }

    public static <T, A> T post(String url, A o, Class<T> tClass) throws IOException {
        String s = objectMapper.writeValueAsString(o);
        String res;
        try (CloseableHttpClient client = HttpClientBuilder.create().build()) {
            HttpPost post = new HttpPost(url);
            StringEntity se = new StringEntity(s, "utf-8");
            post.setEntity(se);
            Header header = new BasicHeader("Content-Type", "application/json;charset=utf8");
            post.setHeader(header);
            try (CloseableHttpResponse response = client.execute(post)) {
                if (response.getStatusLine().getStatusCode() == HttpStatus.OK.value()) {
                    HttpEntity entity = response.getEntity();
                    res = EntityUtils.toString(entity);
                    InternResp<T> resp = objectMapper.readValue(res, InternResp.class);
                    if (resp.getCode() == Constant.SUCCESS) {
                        logger.info("[SUCCESS] call {} successfully", url);
                        T data = resp.getData();
                        return objectMapper.convertValue(data, tClass);
                    } else {
                        logger.error("[FAIL] msg:{}, traceId:{}",resp.getMsg(),resp.getTraceId());
                        return null;
                    }
                }
                logger.error("[FAIL] call api failed");
            }
        }
        return null;
    }
}
