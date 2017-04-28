package com.zhy.sample_okhttp;

import com.zhy.http.okhttp.builder.OkHttpRequestBuilder;

import java.util.Map;

/**
 * Created by Android on 2017/4/25.
 */

public interface HasParamsable {
    OkHttpRequestBuilder params(Map<String, String> var1);

    OkHttpRequestBuilder addParams(String var1, String var2);
}
