package com.zhy.http.okhttp.encapsulationresponse;

/**
 * Created by Android on 2017/4/25.
 */

public  interface  HttpExceptionInfo {
     void onError(RootBen model);
     void onError(String  message);
     String parseError(String  url, Exception e);

}
