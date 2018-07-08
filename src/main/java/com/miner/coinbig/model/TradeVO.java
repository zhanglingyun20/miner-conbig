package com.miner.coinbig.model;

public class TradeVO {

	private boolean result;
	private String order_id;
	public boolean isResult() {
		return result;
	}
	public void setResult(boolean result) {
		this.result = result;
	}
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	@Override
	public String toString() {
		return "TradeVO [result=" + result + ", order_id=" + order_id + "]";
	}
	
	
}
