package com.toocol.common.web;

import com.alibaba.fastjson.JSON;
import lombok.Getter;
import lombok.Setter;

/**
 * @author ZhaoZhe (joezane.cn@gmail.com)
 * @date 2022/3/11 14:37
 */
@Getter
@Setter
public class HttpRequestResult {
    private int code;

    private boolean success;

    private boolean encode;

    private Object data;

    private String message;

    public static RequestResultBuilder builder() {
        return new RequestResultBuilder();
    }

    public static final class RequestResultBuilder {
        private final HttpRequestResult httpRequestResult;

        private RequestResultBuilder() {
            httpRequestResult = new HttpRequestResult();
        }

        public static RequestResultBuilder aRequestResult() {
            return new RequestResultBuilder();
        }

        public RequestResultBuilder code(int code) {
            httpRequestResult.setCode(code);
            return this;
        }

        public RequestResultBuilder success(boolean success) {
            httpRequestResult.setSuccess(success);
            return this;
        }

        public RequestResultBuilder encode(boolean encode) {
            httpRequestResult.setEncode(encode);
            return this;
        }

        public RequestResultBuilder data(Object data) {
            httpRequestResult.setData(data);
            return this;
        }

        public RequestResultBuilder message(String message) {
            httpRequestResult.setMessage(message);
            return this;
        }

        public HttpRequestResult build() {
            return httpRequestResult;
        }

        @Override
        public String toString() {
            return JSON.toJSONString(this.build());
        }
    }
}
