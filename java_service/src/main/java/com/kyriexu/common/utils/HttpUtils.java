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
import org.springframework.http.HttpStatus;

import java.io.IOException;

/**
 * @author KyrieXu
 * @since 2021/4/14 16:50
 **/
public class HttpUtils {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
    }

    public static <T> T get(String url) throws IOException {
        String res;
        try (CloseableHttpClient client = HttpClientBuilder.create().build()) {
            HttpGet get = new HttpGet(url);
            try (CloseableHttpResponse response = client.execute(get)) {
                if (response.getStatusLine().getStatusCode() == Constant.SUCCESS) {
                    HttpEntity entity = response.getEntity();
                    res = EntityUtils.toString(entity);
                    InternResp<T> resp = objectMapper.readValue(res, InternResp.class);
                    if (resp.getCode() == Constant.SUCCESS) {
                        return resp.getData();
                    }
                }
            }
        }
        return null;
    }

    public static <T, A> T post(String url, A o) throws IOException {
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
                        return resp.getData();
                    }
                }
            }
        }
        return null;
    }
}
