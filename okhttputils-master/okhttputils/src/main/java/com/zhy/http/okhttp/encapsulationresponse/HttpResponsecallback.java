package com.zhy.http.okhttp.encapsulationresponse;

/**
 * Created by Android on 2017/4/25.
 */

public abstract class HttpResponsecallback<T>  {
    public HttpResponsecallback() {
    }

    public void onError(Exception e){ // 网络错误

    }

    public void onResponse(T response) {  //网络请求成功
    }

    public void onResponse(float progress, T response) { // 下载文件用的
    }
}
