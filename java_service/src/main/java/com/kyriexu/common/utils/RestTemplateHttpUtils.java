package com.kyriexu.common.utils;

import com.kyriexu.dto.InternResp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.Objects;

/**
 * @author KyrieXu
 * @since 2021/4/14 14:24
 **/
@Component
public class RestTemplateHttpUtils {

    public static final Logger logger = LoggerFactory.getLogger(RestTemplateHttpUtils.class);

    @Autowired
    private RestTemplate restTemplate;

    public <T, V> T getInternalGenericObject(String url, Map<String, V> urlParams, Class<T> tClass) {
        StringBuilder sb = new StringBuilder(url);
        if (urlParams != null && urlParams.size() != 0) {
            sb.append("?");
            urlParams.forEach((k, v) -> sb.append(k)
                    .append("=")
                    .append(v)
                    .append("&"));
        }
        ParameterizedTypeReference<InternResp<T>> reference = new ParameterizedTypeReference<InternResp<T>>() {
        };
        ResponseEntity<InternResp<T>> resp = restTemplate.exchange(sb.toString(), HttpMethod.GET, null, reference);
        return tClass.cast(getBody(resp));
    }

    @Deprecated
    public <T,V> T postForObject(String url, Map<String, V> body, Class<T> tClass) {
        HttpEntity<Map<String, V>> entity = new HttpEntity<>(body);
        ParameterizedTypeReference<InternResp<T>> reference = new ParameterizedTypeReference<InternResp<T>>() {
        };
        ResponseEntity<InternResp<T>> resp = restTemplate.exchange(url, HttpMethod.POST, entity, reference);
        return tClass.cast(getBody(resp));
    }

    private <T> T getBody(ResponseEntity<InternResp<T>> resp) {
        if (resp.getStatusCode().is2xxSuccessful()) {
            logger.info("[SUCCESS] call internal api success");
            InternResp<T> body = resp.getBody();
            return getBody(body);
        }
        logger.error("[FAIL] call internal api response code is not 2xx");
        return null;
    }

    private <T> T getBody(InternResp<T> body) {
        if (Objects.isNull(body)) {
            logger.error("[FAIL] internal api call failed，cause response body is null");
            return null;
        }
        if (body.getCode() == Constant.SUCCESS) {
            logger.info("[SUCCESS] internal api call successfully");
            return body.getData();
        }
        logger.error("[FAIL] msg : {}, traceId : {}", body.getMsg(), body.getTraceId());
        return null;
    }
}
