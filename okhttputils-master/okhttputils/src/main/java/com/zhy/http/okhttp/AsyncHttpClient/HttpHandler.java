package com.zhy.http.okhttp.AsyncHttpClient;

import android.content.Context;

import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.orhanobut.logger.Logger;

import java.lang.reflect.Type;

import cz.msebera.android.httpclient.Header;


/**
 * Created by dai on 2016/10/26.
 */
public abstract class HttpHandler<T> extends BaseJsonHttpResponseHandler<Result<T>> {
    private Context mContext;
    protected Type type;

    /**
     * @param context activity
     */
    public HttpHandler(Context context) {
        type = ClassTypeReflect.getModelClazz(getClass());
        mContext = context;
//        isDialog = true;

    }


    @Override
    public void onStart() {
        super.onStart();
//        showDialog();

    }

    @Override
    public void onFinish() {
        super.onFinish();
//        dismissDialog();
    }

    @Override
    public void onSuccess(int statusCode, Header[] headers, String responseString, Result<T> result) {
        if(result == null){
            Logger.e("HETTP_REQUEST"+"当前请求返回的数据为NULL");
            return;
        }

        Logger.json(responseString);


        if (result.getCode() == 0) {
//            onFinish(StatusType.SUCCESS, result);
        } else {
            if (result.getCode() == 1) {
//                SPUtils.clearToken(mContext);
                // 获取context地址 如果是后台服务的context那么不做任何处理 @ 后面的地址随时变动因此不需要所以截取
                if(!mContext.toString().substring(0,mContext.toString().indexOf("@"))
                        .equals("com.acjt.aycx.driver.service.DataUploadAndDownService")){
//                    ConfirmPasswordDialog d=new ConfirmPasswordDialog(mContext,result.getMsg());
//                    d.show();
                }else {
//                    setBroadcast(10000,mContext);
                }
            }
//            else if(result.getCode() == -1){
//                ToastUtil.show(mContext,"网络出错了!!!");
//            }
            else{
//                onFinish(StatusType.FAIL, result);
            }
        }
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, byte[] responseBytes, Throwable throwable) {
        super.onFailure(statusCode, headers, responseBytes, throwable);
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, Result<T> errorResponse) {

    }


    //    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
//    @Override
//    public void onSuccess(int statusCode, Header[] headers, String responseString, Result<T> result) {
//

//    }
//
//    /**
//     * 发送具体的数据上报与下发事件
//     *
//     */
//    private void setBroadcast(int eventType, Context context) {
//        DataReportAndIssueBean dataBean = new DataReportAndIssueBean();
//        Bundle bundle = new Bundle();
//        Intent intent = new Intent();
//        Logger.e("UPLOADDOWN事件类型"+ "各种操作触发的事件类型有:" + eventType);
//        //广播
//        intent.setAction("com.acjt.aycx.driver.dataUploadAndDownBroadCast");
//        //事件类型
//        intent.putExtra("eventType", eventType);
//        //具体数据
//        bundle.putSerializable("uploadAndDownDataBean", dataBean);
//        intent.putExtras(bundle);
//        context.sendBroadcast(intent);
//    }

//
//    @Override
//    public void onFailure(int statusCode, Header[] headers, Throwable throwable, String responseString, Result<T> result1) {
//        Logger.i("http_txt"+ "onFailure:" + statusCode + "\n" + responseString + "\n" + throwable.getMessage());
//        throwable.printStackTrace();
//        if (throwable instanceof SocketTimeoutException) {//超时异常
//            Logger.i("http_txt"+"超时异常");
//            if (!mContext.toString().substring(0, mContext.toString().indexOf("@"))
//                    .equals("com.acjt.aycx.driver.service.DataUploadAndDownService")) {
//                ToastUtil.show(mContext, "网络出错了!!!");
//            }
//        } else if (throwable instanceof JSONException) {//解析错误
//            Logger.i("http_txt"+ "解析错误");
//        } else if (statusCode == 303) {//代表重定向
//            Logger.i("http_txt"+ "代表重定向");
//        } else if (statusCode == 401) {//代表未授权
//            Logger.i("http_txt"+ "代表未授权");
//        } else if (statusCode == 403) {//代表禁止访问
//            Logger.i("http_txt"+ "代表禁止访问");
//        } else if (statusCode == 404) {//代表文件未找到
//            Logger.i("http_txt"+ "代表文件未找到");
//        } else if (statusCode == 500) {//代表服务器错误
//            Logger.i("http_txt"+ "代表服务器错误");
//        } else {
////            CommonUtils.isNetOkWithToast(mContext);
//            Logger.i("http_txt"+"其它错误");
//        }
//
//        Result result = new Result<T>();
//        result.setStatus("fail");
//        result.setMsg(responseString);
//        result.setThrowableString(throwable.getMessage());
//        result.setData(null);
//        onFinish(StatusType.EXCEPTION, result);
//    }
//
//    @Override
//    protected Result<T> parseResponse(String txt, boolean b) throws Throwable {
//        Logger.json(txt);
//        Result<T> result = JSON.parseObject(txt, new TypeReference<Result<T>>() {
//        });
//        if (type == String.class) {
//            Logger.i("http_txt"+ "进来1");
//            result.setData((T) result.getData());
//        } else {
//            if (null == result.getData()) {
//                result.setData(null);
//            } else {
//                result.setData((T) JSON.parseObject(result.getData().toString(), type));
//            }
//        }
//        Logger.i("http_txt"+ "进来3");
//        return result;
//    }
//
//    /**
//     * @param type    1成功，2成功返回数据，3异常
//     * @param content
//     */
//    public abstract void onFinish(StatusType type, Result<T> content);
//
////    private void showDialog() {
////        if (isDialog) {
////            if (customProgressDialog == null) {
////                customProgressDialog = new CustomProgressDialog(mContext, "");
////            }
////            customProgressDialog.show();
////        }
////    }
//
////    private void dismissDialog() {
////        if (isDialog) {
////            try {
////                if (customProgressDialog != null && customProgressDialog.isShowing())
////                    customProgressDialog.dismiss();
////            } catch (Exception e) {
////            }
////        }
////    }

}
