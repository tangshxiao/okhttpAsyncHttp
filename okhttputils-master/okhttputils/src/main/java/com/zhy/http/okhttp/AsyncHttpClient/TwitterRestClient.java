package com.zhy.http.okhttp.AsyncHttpClient;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.ResponseHandlerInterface;
import com.orhanobut.logger.Logger;

import java.io.UnsupportedEncodingException;
import java.util.List;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.client.entity.UrlEncodedFormEntity;
import cz.msebera.android.httpclient.conn.scheme.PlainSocketFactory;
import cz.msebera.android.httpclient.conn.scheme.Scheme;
import cz.msebera.android.httpclient.conn.scheme.SchemeRegistry;
import cz.msebera.android.httpclient.conn.scheme.SocketFactory;

/**
 * 请求控制类
 * Created by dai on 2016/10/26.
 */
public class TwitterRestClient {
    public AsyncHttpClient getHttpClient() {
        return mHttpClient;
    }

    private AsyncHttpClient mHttpClient;
    private Context mContext;
    public static final String ACCESS_TOKEN = "Token";
    public static final String LOGIN_NAME = "LoginName";
    public static final String VERSION = "Version";
    public static final String SEQUENCE = "Sequence";
    private SchemeRegistry schRego;
//    private BebeingRouteDialog bebeingRouteDialog;
    private static   boolean networkisok=true;

    public TwitterRestClient(Context con) {
        mContext = con;
        schRego = new SchemeRegistry();
        schRego.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
        schRego.register(new Scheme("https", (SocketFactory) SSLCustomSocketFactory.getSocketFactory(con), 443));
        if (mHttpClient == null) {
            mHttpClient = new AsyncHttpClient(schRego);
        }
        mHttpClient.cancelRequests(mContext, true);
    }

    public void setTimeout(int time) {
        mHttpClient.setConnectTimeout(time);
        mHttpClient.setTimeout(time);
        mHttpClient.setResponseTimeout(time);
    }

    /**
     * 清除cookie
     */
    public void cleanHttp() {
        PersistentCookieStore mCookieStore = new PersistentCookieStore(mContext);
        mCookieStore.clear();
    }

    /**
     * get请求
     *
     * @param url                      请求地址
     * @param asyncHttpResponseHandler 回调接口
     */
    public void get(String url, String headerValue, ResponseHandlerInterface asyncHttpResponseHandler) {
        PersistentCookieStore mCookieStore = new PersistentCookieStore(mContext);
        mHttpClient.setCookieStore(mCookieStore);
        Logger.i("http:"+url);
        if (null != headerValue) {
            mHttpClient.addHeader(ACCESS_TOKEN, headerValue);
        }
        mHttpClient.get(url, asyncHttpResponseHandler);
    }

    /**
     * get请求带参
     *
     * @param url                      请求地址
     * @param params                   参数
     * @param asyncHttpResponseHandler 回调接口
     */
    public void get(String url, String headerValue, RequestParams params, ResponseHandlerInterface asyncHttpResponseHandler) {
        PersistentCookieStore mCookieStore = new PersistentCookieStore(mContext);
        mHttpClient.setCookieStore(mCookieStore);
        if (null != headerValue) {
            mHttpClient.addHeader(ACCESS_TOKEN, headerValue);
        }
        mHttpClient.get(url, asyncHttpResponseHandler);
    }

    /**
     * get请求utf-8编码
     *
     * @param url                      请求地址
     * @param list                     参数
     * @param asyncHttpResponseHandler 回调接口
     */
    public void get(String url, String headerValue, List<NameValuePair> list, ResponseHandlerInterface asyncHttpResponseHandler) {
        PersistentCookieStore mCookieStore = new PersistentCookieStore(mContext);
        mHttpClient.setCookieStore(mCookieStore);
        HttpEntity entity = null;
        try {
            entity = new UrlEncodedFormEntity(list, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return;
        }
        if (null != headerValue) {
            mHttpClient.addHeader(ACCESS_TOKEN, headerValue);
        }
        mHttpClient.get(mContext, url, entity, "application/json;charset=UTF-8", asyncHttpResponseHandler);
    }

    /**
     * 有参数
     *
     * @param url                      请求地址
     * @param asyncHttpResponseHandler 接口回调
     */
    public void post(String url, RequestParams params, ResponseHandlerInterface asyncHttpResponseHandler) {
        String output = params.toString();
        Logger.e(":HTTP"+(TextUtils.isEmpty(output) ? url : url + "?" + output));

        PersistentCookieStore mCookieStore = new PersistentCookieStore(mContext);
        mHttpClient.setCookieStore(mCookieStore);
//        setHeader();
        if (isNetOk(mContext)) {
            mHttpClient.post(url, params, asyncHttpResponseHandler);
            networkisok = true;
        } else {
            Logger.e("是否有网络"+"url"+url + ":network:" +networkisok);
            if(networkisok){
//                isNetwork();
                networkisok = false;
            }
        }
    }


    /**
     * 没有参数
     *
     * @param url                      请求地址
     * @param asyncHttpResponseHandler 接口回调
     */
    public void post(String url, ResponseHandlerInterface asyncHttpResponseHandler) {
        post(url, new RequestParams(), asyncHttpResponseHandler);
    }


    /**
     * 表示服务要用的请求方式不用弹出弹框
     * DataUploadAndDownService
     *
     * @param url                      请求地址
     * @param asyncHttpResponseHandler 接口回调
     * @param DataUploadAndDownService 表示方法重载表示的
     */
    public void post(String url, RequestParams params, ResponseHandlerInterface asyncHttpResponseHandler, int DataUploadAndDownService) {
        String output = params.toString();
        Logger.e("HTTP:"+(TextUtils.isEmpty(output) ? url : url + "?" + output));

//        setHeader();
        PersistentCookieStore mCookieStore = new PersistentCookieStore(mContext);
        mHttpClient.setCookieStore(mCookieStore);
        // 添加头部
        mHttpClient.addHeader(ACCESS_TOKEN, "token");
        mHttpClient.addHeader(LOGIN_NAME, "");
        mHttpClient.addHeader(VERSION, "");
        mHttpClient.post(url, params, asyncHttpResponseHandler);
    }

    /**
     * 方法说明： setTokenPhoneNumber
     */
//    public void setHeader() {
//        String str = "header:&";
//        if (!TextUtils.isEmpty(SPUtils.getSPString(mContext, "token"))) {
//            mHttpClient.addHeader(ACCESS_TOKEN, SPUtils.getSPString(mContext, "token"));
//            str += ACCESS_TOKEN + "=" + SPUtils.getSPString(mContext, "token") + "&";
//        }
//        if (!TextUtils.isEmpty(SPUtils.getSPString(mContext, "cellphone"))) {
//            mHttpClient.addHeader(LOGIN_NAME, SPUtils.getSPString(mContext, "cellphone"));
//            str += LOGIN_NAME + "=" + SPUtils.getSPString(mContext, "cellphone") + "&";
//        }
//        mHttpClient.addHeader(VERSION, CommonUtils.getAppVersionName(mContext));
//        mHttpClient.addHeader(SEQUENCE, "" + CommonUtils.getAppVersionCode(mContext));
//        str += VERSION + "=" + CommonUtils.getAppVersionName(mContext)+"&"+SEQUENCE+"="+CommonUtils.getAppVersionCode(mContext) ;
//        Logger.e("HTTP"+ str);
//    }


    /**
     * 网络不佳的时候弹框
     */
//    public void isNetwork() {
//        if (bebeingRouteDialog == null) {
//            bebeingRouteDialog = new BebeingRouteDialog(mContext, "网络不给力,请检查网络是否连接", "", "取消", "设置", 0);
//            bebeingRouteDialog.setNo_entrance(new BebeingRouteDialog.noEntranceListener() {
//                @Override
//                public void no_entrance() {
//                    bebeingRouteDialog.dismiss();
//                }
//            });
//            bebeingRouteDialog.setYse_entrance(new BebeingRouteDialog.yesEntranceListener() {
//                @Override
//                public void yes_entrance() {
//                    Intent intent = new Intent(Settings.ACTION_AIRPLANE_MODE_SETTINGS);
//                    mContext.startActivity(intent);
//                    bebeingRouteDialog.dismiss();
//                }
//            });
//            bebeingRouteDialog.show();
//        }else {
//        }
//    }


//    /**
//     * @param url 要下载的文件URL
//     * @throws Exception
//     */
//    public static void downloadFile(final Context mContext, String url,) {
//
//        AsyncHttpClient client = new AsyncHttpClient();
//        // 指定文件类型
//        String[] fileTypes = new String[]{"apk"};
//        // 获取二进制数据如图片和其他文件
//        client.get(url, new BinaryHttpResponseHandler(fileTypes) {
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, byte[] binaryData) {
//                String tempPath = Environment.getExternalStorageDirectory().getPath() + "/temp.jpg";
//                //
////                Logger.e("binaryData:", "共下载了：" + binaryData.length);
//                //
//                Bitmap bmp = BitmapFactory.decodeByteArray(binaryData, 0, binaryData.length);
//
//                File file = new File(tempPath);
//                // 压缩格式
//                Bitmap.CompressFormat format = Bitmap.CompressFormat.JPEG;
//                // 压缩比例
//                int quality = 100;
//                try {
//                    // 若存在则删除
//                    if (file.exists())
//                        file.delete();
//                    // 创建文件
//                    file.createNewFile();
//                    //
//                    OutputStream stream = new FileOutputStream(file);
//                    // 压缩输出
//                    bmp.compress(format, quality, stream);
//                    // 关闭
//                    stream.close();
//                    //
//                    Toast.makeText(mContext, "下载成功\n" + tempPath, Toast.LENGTH_LONG).show();
//
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//            }
//
//            @Override
//            public void onFailure(int statusCode, Header[] headers, byte[] binaryData, Throwable error) {
//                Toast.makeText(mContext, "下载失败", Toast.LENGTH_LONG).show();
//            }
//
//            @Override
//            public void onProgress(long bytesWritten, long totalSize) {
//                int count = (int) ((bytesWritten * 1.0 / totalSize) * 100);
//                // 下载进度显示
//                progress.setProgress(count);
////                Logger.e("下载 Progress>>>>>", bytesWritten + " / " + totalSize);
//                super.onProgress(bytesWritten, totalSize);
//            }
//
//            @Override
//            public void onRetry(int retryNo) {
//                super.onRetry(retryNo);
//                // 返回重试次数
//            }
//
//        });
//    }

    /**
     * 判断网络是否连接
     *
     * @param context
     * @return
     */
    public static boolean isNetOk(Context context) {
        return getCurrentNetType(context) != 0;
    }

    /**
     * @param context
     * @return 1 : wifi 2:3g 3:gprs 0: no net
     */
    public static int getCurrentNetType(Context context) {
        if (null == context) {
            return 0;
        }
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);// 获取系统的连接服务
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();// 获取网络的连接情况
        if (null != activeNetInfo) {
            // L.v(TAG, "getNetType : ", activeNetInfo.toString());
            if (activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                // 判断WIFI网
                return 1;
            } else if (activeNetInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                TelephonyManager mTelephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                int type = mTelephonyManager.getNetworkType();
                if (type == TelephonyManager.NETWORK_TYPE_UNKNOWN || type == TelephonyManager.NETWORK_TYPE_GPRS || type == TelephonyManager.NETWORK_TYPE_EDGE) {
                    // 判断gprs网
                    return 3;
                } else {
                    // 判断3g网
                    return 2;
                }
            }else if(activeNetInfo.getType() == ConnectivityManager.TYPE_MOBILE_DUN){  //适用场合：需要使用运营商无线热点的，CMCC、ChinaNet等
                return 4;
            }
        }
        return 0;
    }
}
