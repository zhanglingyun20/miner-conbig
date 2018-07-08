package com.miner.coinbig.model;

public class CoinbigResponse<T> {

	private int code;
	private String msg;
	private T data;
	
	public boolean success(){
		return code ==0;
	}
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	@Override
	public String toString() {
		return "CoinbigResponse [code=" + code + ", msg=" + msg + ", data=" + data + "]";
	}
	
	
}
