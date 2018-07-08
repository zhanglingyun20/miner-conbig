package com.miner.coinbig.model;

import com.miner.coinbig.enums.EntrustType;
import com.miner.coinbig.enums.OrderStatus;

/**
 * orderinfo": {
            "price": 100,委托价格
            "count": 1,委托数量
            "avg_price": 0,平均成交价
            "isLimit": 0,是否是限价单，0为限价单，1市价单
            "entrustType": 1,类型:0买入,1卖出
            "create_date": 1530879291000,委托时间
            "order_id": 61530879291376,
            "status": 1,状态:1：未完成,2：部分成交,3：完全成交,4：用户撤销、5：部分撤销、6：成交失败
            "leftCount": 1未成交数量
        }
 * @author sawyer
 *
 */
public class OrderInfoVO {

	private Boolean result;
	private OrderInfo orderInfo;
	
	
	
	public Boolean getResult() {
		return result;
	}



	public void setResult(Boolean result) {
		this.result = result;
	}



	public OrderInfo getOrderInfo() {
		return orderInfo;
	}



	public void setOrderInfo(OrderInfo orderInfo) {
		this.orderInfo = orderInfo;
	}


	

	@Override
	public String toString() {
		return orderInfo.toString();
	}




	public static class OrderInfo{
		private Double  price;
		private Double  count;
		private Double  avg_price;
		private Double  isLimit;
		private Integer  entrustType;
		private Long  create_date;
		private String  order_id;
		private Integer  status;
		private Integer  leftCount;
		public Double getPrice() {
			return price;
		}
		public void setPrice(Double price) {
			this.price = price;
		}
		public Double getCount() {
			return count;
		}
		public void setCount(Double count) {
			this.count = count;
		}
		public Double getAvg_price() {
			return avg_price;
		}
		public void setAvg_price(Double avg_price) {
			this.avg_price = avg_price;
		}
		public Double getIsLimit() {
			return isLimit;
		}
		public void setIsLimit(Double isLimit) {
			this.isLimit = isLimit;
		}
		public Integer getEntrustType() {
			return entrustType;
		}
		public void setEntrustType(Integer entrustType) {
			this.entrustType = entrustType;
		}
		public Long getCreate_date() {
			return create_date;
		}
		public void setCreate_date(Long create_date) {
			this.create_date = create_date;
		}
		public String getOrder_id() {
			return order_id;
		}
		public void setOrder_id(String order_id) {
			this.order_id = order_id;
		}
		public Integer getStatus() {
			return status;
		}
		public void setStatus(Integer status) {
			this.status = status;
		}
		public Integer getLeftCount() {
			return leftCount;
		}
		public void setLeftCount(Integer leftCount) {
			this.leftCount = leftCount;
		}


		@Override
		public String toString() {
			return " [委托价格:" + price + ", 委托数量=" + count + ", 平均成交价:" + avg_price + ", isLimit:"
					+ isLimit + ", 类型:" + EntrustType.getNameByType(entrustType) + ", create_date:" + create_date + ", order_id:"
					+ order_id + ", 状态:" + OrderStatus.getNameByType(status) + ", 未成交数量:" + leftCount + "]";
		}
		
	}
}
