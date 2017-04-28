package com.zhy.http.okhttp.encapsulationresponse;

import android.content.Context;

import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.OkHttpRequestBuilder;
import com.zhy.http.okhttp.callback.Callback;
import com.zhy.http.okhttp.https.HttpsUtils;
import com.zhy.http.okhttp.request.RequestCall;

import java.io.InputStream;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.Proxy;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Response;

/**
 * Created by Android on 2017/4/25.
 */

public class HttpRequest  {
    public static HttpRequest instance;
    public static HashMap<String, Object> params = new HashMap(); // 请求参数
    public static HashMap<String, String> header = new HashMap(); // 请求头 设置HttpRequest .header
    public static Context context;
    MethodType method; // 排序
    Long readTimeOut;
    Long writeTimeOut;
    Long connTimeOut;
    String server;
    Gson gson;
    ACJTExceptionInfo parse;
    public HttpRequest() {
        this.method = MethodType.POST; // 默认为post 请求
        this.server = "";
        this.readTimeOut = Long.valueOf(10000L);
        this.writeTimeOut = Long.valueOf(10000L);
        this.connTimeOut = Long.valueOf(10000L);
        this.gson = new Gson();
        this.parse =new ACJTExceptionInfo();
    }


    public void setHMExceptionInfo(ACJTExceptionInfo exceptionInfo) {
        instance.parse = exceptionInfo;
    }

    /**
     * 初始化
     * @param context  上下文
     * @param bksFile HTTps bks文件
     * @param password HTTps 密码
     */
    public static void init(Context context, InputStream bksFile, String password) {
        instance = new HttpRequest();
        init(context, bksFile, password, (InputStream[])null, instance.connTimeOut.longValue(), instance.readTimeOut.longValue(), instance.writeTimeOut.longValue(), "HMRequest", (Proxy)null);

    }

    /**
     * @param context  上下文
     * @param bksFile  HTTps bks文件
     * @param password HTTps 密码
     * @param certificates HTTPS证书
     * @param connectTimeout 超时
     * @param readTimeout 读取时间
     * @param writeTimeOut  写入时间
     * @param tag
     * @param proxy
     */
    public static void init(Context context, InputStream bksFile, String password, InputStream[] certificates, long connectTimeout, long readTimeout, long writeTimeOut, String tag, Proxy proxy) {
        instance = new HttpRequest();
        PersistentCookieJar cookieJar = new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(context));
        HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory(certificates, bksFile, password);
        OkHttpClient okHttpClient = (new OkHttpClient.Builder()).connectTimeout(connectTimeout, TimeUnit.MILLISECONDS).readTimeout(readTimeout, TimeUnit.MILLISECONDS).writeTimeout(writeTimeOut, TimeUnit.MILLISECONDS).cookieJar(cookieJar).proxy(proxy).hostnameVerifier(new HostnameVerifier() {
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        }).sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager).build();
        OkHttpUtils.initClient(okHttpClient);
    }


    public static <T> RequestCall go(String url, HashMap<String, Object> params, Context context,HttpResponsecallback response) {
        header.put("user","1");
        HttpRequest.context=context;
        return go(url, params, header, response);
    }

    public static <T> RequestCall go(String url, HashMap<String, Object> params, HashMap<String, String> header, HttpResponsecallback response) {
        return go(url, params, header,HttpRequest.instance.method,response);
    }

    public static <T> RequestCall go(String url, HashMap<String, Object> params, HashMap<String, String> header, MethodType method, HttpResponsecallback response) {
        return go(url, params, header, method, context, response);
    }
    /**
     *
     * @param url  地址
     * @param params 参数
     * @param header 头部
     * @param method 类型
     * @param activity  页面
     * @param completionHandler 请求回调
     * @param <T>
     * @return
     */
    public static <T> RequestCall go(final String url, HashMap<String, Object> params, HashMap<String, String> header, final MethodType method, final Context activity,  final HttpResponsecallback completionHandler) {
       Logger.e("Context:"+activity);
        Object builder=null;
        if(method==MethodType.GET){
            builder = OkHttpUtils.get();
        }else {
            builder=OkHttpUtils.post();
        }
        // 迭代器 迭代参数
        Iterator headerLog = params.keySet().iterator();
        String fullUrl;
        while(headerLog.hasNext()) {
            fullUrl = (String)headerLog.next();
            ((OkHttpRequestBuilder)builder).addParams(fullUrl, String.valueOf(params.get(fullUrl)));
        }
        // 迭代请求头
        String var16 = "header:";
        String log;
        for(Iterator var17 = header.keySet().iterator(); var17.hasNext(); var16 = var16 + log + "=" + (String)header.get(log) + "&") {
            log = (String)var17.next();
            ((OkHttpRequestBuilder)builder).addHeader(log, (String)header.get(log));
        }
        final RequestCall call = ((OkHttpRequestBuilder)builder).url(url).build();  // 设置地址
        call.connTimeOut(instance.connTimeOut.longValue()); // 设置超时时间
        call.readTimeOut(instance.readTimeOut.longValue()); // 设置读取超时时间
        call.writeTimeOut(instance.writeTimeOut.longValue());// 设置写入超时时间
        call.execute(new Callback() {
            @Override
            public Object parseNetworkResponse(Response response, int id) throws Exception {
                call.setHasResponse(true);
                String str = response.body().string();
                Logger.json(str);
                Type mySuperClass = completionHandler.getClass().getGenericSuperclass(); // 得到类
                Type entityClass = ((ParameterizedType)mySuperClass).getActualTypeArguments()[0];//得到java ben
                Object obj = HttpRequest.instance.gson.fromJson(str, entityClass);
                if (obj!=null){
                    completionHandler.onResponse(obj);
                }
                return obj;
            }

            @Override
            public void onError(Call call, Exception e, int id) {  //网络问题在在这里处理
                Logger.e(e.getMessage());

            }

            @Override
            public void onResponse(Object var1, int var2) {

            }

        });
        return call;
    }


}
