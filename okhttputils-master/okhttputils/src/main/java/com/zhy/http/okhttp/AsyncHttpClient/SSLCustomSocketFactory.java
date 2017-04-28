package com.zhy.http.okhttp.AsyncHttpClient;

import android.content.Context;

import com.orhanobut.logger.Logger;

import org.apache.http.conn.ssl.SSLSocketFactory;

import java.io.InputStream;
import java.security.KeyStore;


/**
 * <ul>
 * <li>文件包名 : com.acjt.aycx.client.utils</li>
 * <li>创建时间 : 2016/11/14 18:23</li>
 * <li>修改记录 : 无</li>
 * </ul>
 * 类说明：自定义的证书工具类
 * 证书的位置在res/raw目录下
 *
 * @author daijun
 * @version 2.0.0
 */
public class SSLCustomSocketFactory extends SSLSocketFactoryEx {
    private static final String TAG = "SSLCustomSocketFactory";

    private static final String KEY_PASS = "123456";

    public SSLCustomSocketFactory(KeyStore trustStore) throws Throwable {
        super(trustStore);
    }

    public static SSLSocketFactory getSocketFactory(Context context) {
        try {
            int i = 0; // 这是Https的证书
            InputStream ins = context.getResources().openRawResource(i);

            KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
            try {
                trustStore.load(ins, KEY_PASS.toCharArray());
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                ins.close();
            }
            SSLSocketFactory factory = new SSLCustomSocketFactory(trustStore);
            return factory;
        } catch (Throwable e) {
            Logger.d(TAG, e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
}

