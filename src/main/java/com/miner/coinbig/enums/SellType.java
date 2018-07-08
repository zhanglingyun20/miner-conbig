package com.miner.coinbig.enums;

/**
 * 买卖类型: 限价单(buy/sell) 市价单(buy_market/sell_market)
 * @author sawyer
 *
 */
public enum SellType {
	
	BUY("buy","买"),
	SELL("sell","卖"),
	BUY_MARKET("buy_market","市价买"),
	SELL_MARKET("buy_market","市价卖"),
	;
	
	private String type;
	private String name;
	private SellType(String type, String name) {
		this.type = type;
		this.name = name;
	}
	
	private String getNameByType(String type){
		for (SellType sellType : SellType.values()) {
			if (sellType.type.equals(type)) {
				return sellType.name;
			}
		}
		return "";
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
