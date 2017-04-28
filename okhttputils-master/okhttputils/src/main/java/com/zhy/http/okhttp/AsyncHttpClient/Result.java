package com.zhy.http.okhttp.AsyncHttpClient;

import java.io.Serializable;

public class Result<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	private int count =-1;
	private int code=-1;
	private String status;
	private T data;
	private String msg ;
	private String throwableString="";

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getThrowableString() {
		return throwableString;
	}

	public void setThrowableString(String throwableString) {
		this.throwableString = throwableString;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public String getMsg() {
		if(msg==null){
			msg="网络出错了！！！";
		}
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	@Override
	public String toString() {
		return "Result{" +
				"count=" + count +
				", code=" + code +
				", status='" + status + '\'' +
				", data=" + data +
				", msg='" + msg + '\'' +
				", throwableString='" + throwableString + '\'' +
				'}';
	}
}
