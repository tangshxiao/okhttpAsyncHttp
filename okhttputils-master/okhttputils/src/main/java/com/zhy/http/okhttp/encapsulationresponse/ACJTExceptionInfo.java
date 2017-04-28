package com.zhy.http.okhttp.encapsulationresponse;

/**
 * Created by Android on 2017/4/25.
 */

public  class ACJTExceptionInfo implements HttpExceptionInfo {
    public ACJTExceptionInfo(){

    }

    @Override
    public void onError(RootBen model) {

    }

    @Override
    public void onError(String message) {

    }

    @Override
    public String parseError(String url, Exception e) {
        return url;
    }


}
