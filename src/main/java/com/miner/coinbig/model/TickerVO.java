package com.miner.coinbig.model;

/**
 * 行情实体 "ticker": { "high": 0,最高价 "vol": 1.2,成交量(最近的24小时) "last": 7000,最新成交价
 * "low": 0,最低价 "buy": 0,买一价 "sell": "100.0000"卖一价 }
 * 
 * @author sawyer
 *
 */
public class TickerVO {

	private Long date;

	private Ticker ticker;
	public Long getDate() {
		return date;
	}

	public void setDate(Long date) {
		this.date = date;
	}

	public Ticker getTicker() {
		return ticker;
	}

	public void setTicker(Ticker ticker) {
		this.ticker = ticker;
	}



	public static class Ticker {
		private double high; // 最高价
		private double vol; // 成交量(最近的24小时)
		private double last; // 7000,最新成交价
		private double low; // 最低价
		private double buy; // 买一价
		private double sell; // 卖一价

		public double getHigh() {
			return high;
		}

		public void setHigh(double high) {
			this.high = high;
		}

		public double getVol() {
			return vol;
		}

		public void setVol(double vol) {
			this.vol = vol;
		}

		public double getLast() {
			return last;
		}

		public void setLast(double last) {
			this.last = last;
		}

		public double getLow() {
			return low;
		}

		public void setLow(double low) {
			this.low = low;
		}

		public double getBuy() {
			return buy;
		}

		public void setBuy(double buy) {
			this.buy = buy;
		}

		public double getSell() {
			return sell;
		}

		public void setSell(double sell) {
			this.sell = sell;
		}

		@Override
		public String toString() {
			return "[最高价:" + high + ", 成交量:" + vol + ", 最新成交价:" + last + ", 最低价:" + low + ", 买一价=" + buy
					+ ", 卖一价:" + sell + "]";
		}
	}



	@Override
	public String toString() {
		return ticker.toString();
	}
	
	
}
