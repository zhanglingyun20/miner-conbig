package com.miner.coinbig.enums;

/**
 * 1,类型:0买入,1卖出
 * @author sawyer
 *
 */
public enum EntrustType {
	
	BUY(0,"买入"),
	SELL(1,"卖出"),
	;
	
	private int status;
	private String name;
	private EntrustType(int status, String name) {
		this.status = status;
		this.name = name;
	}
	
	public static String getNameByType(int status){
		for (EntrustType sellType : EntrustType.values()) {
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
