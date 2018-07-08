package com.miner.coinbig.enums;

/**
 * 1,状态:1：未完成,2：部分成交,3：完全成交,4：用户撤销、5：部分撤销、6：成交失败
 * @author sawyer
 *
 */
public enum OrderStatus {
	
	UN_DEAL(1,"未完成"),
	PART_DEAL(2,"部分成交"),
	SUCCESCC(3,"完全成交"),
	REVOKE(4,"用户撤销"),
	PART_REVOKE(5,"部分撤销"),
	FAIL(6,"成交失败"),
	;
	
	private int status;
	private String name;
	private OrderStatus(int status, String name) {
		this.status = status;
		this.name = name;
	}
	
	public static String getNameByType(int status){
		for (OrderStatus sellType : OrderStatus.values()) {
			if (sellType.status ==status) {
				return sellType.name;
			}
		}
		return "";
	}



	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
