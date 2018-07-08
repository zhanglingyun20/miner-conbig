package com.miner.coinbig.service;

import java.util.LinkedHashMap;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.miner.coinbig.config.CoinbigConfig;
import com.miner.coinbig.enums.OrderStatus;
import com.miner.coinbig.enums.SellType;
import com.miner.coinbig.model.CoinbigResponse;
import com.miner.coinbig.model.OrderInfoVO;
import com.miner.coinbig.model.TickerVO;
import com.miner.coinbig.model.TickerVO.Ticker;
import com.miner.coinbig.model.TradeVO;
import com.miner.coinbig.utils.OkHttpUtil;

@Service
public class TradeService {

	@Autowired
	private CoinbigConfig config;
	
	private final static Logger logger = LoggerFactory.getLogger(TradeService.class);
	
	ExecutorService threadPool = Executors.newCachedThreadPool();
	
	private BlockingQueue<String> sellQueue = new ArrayBlockingQueue<String>(1000);
	
	/**
	 * 获取最新行情
	 * @return
	 */
	public TickerVO getTicker() {
		try {
			///api/publics/v1/ticker
			String path = "/api/publics/v1/ticker";
			StringBuffer sb = new StringBuffer();
			sb.append(config.getDomain()).append(path);
			LinkedHashMap<String, String> paramMap = new LinkedHashMap<String, String>();
			paramMap.put("symbol", config.getSymbol());
			String result = OkHttpUtil.get(paramMap, sb.toString());
			CoinbigResponse<TickerVO> coinbigResponse = (CoinbigResponse<TickerVO>) JSON.parseObject(result,
					new TypeReference<CoinbigResponse<TickerVO>>() {
					});
			if (Objects.nonNull(coinbigResponse) && coinbigResponse.success()) {
				TickerVO tickerVO = coinbigResponse.getData();
				logger.info("当前{}行情：{}", config.getSymbol(), tickerVO.toString());
				return tickerVO;
			} 
		} catch (Exception e) {
			logger.error("查询行情失败",e);
		}
		return null;
	}
	
	/**
	 * 交易
	 */
	public  void execute() {
		
		//获取行情
		TickerVO tickerVO = getTicker();
		if(Objects.isNull(tickerVO)){
			throw new RuntimeException("查询行情失败");
		}
		//挂买1 和卖1 取平均值买卖
		Ticker ticker = tickerVO.getTicker();
		double sell = ticker.getSell();
		double buy = ticker.getBuy();
		Double price = (sell+buy)/2;
		TradeVO sellVo = trade(price,SellType.SELL);//挂卖单
		if(Objects.nonNull(sellVo)&&sellVo.isResult()){
			String sellOrderId = sellVo.getOrder_id();
			sellQueue.add(sellOrderId);//卖单队列，用来查询买单成交情况
			//买
			TradeVO buyVO = trade(price,SellType.BUY);//挂买单
			if(Objects.nonNull(buyVO)&&sellVo.isResult()){
				String buyOrderId = buyVO.getOrder_id();
				//查询买单是否成交，如果没有成交，说明挂卖单已经被成交
				//查3次，每个一秒查一次，3秒没成交，撤单
				for(int i=1;i<=3;i++){
					OrderInfoVO orderInfoVO = getOrder(buyOrderId);
					if(Objects.nonNull(orderInfoVO)&&orderInfoVO.getResult()){
						if(OrderStatus.SUCCESCC.getStatus()==orderInfoVO.getOrderInfo().getStatus()){
							//买单成交，记录日志
							break;
						}else {
							try {
								// 其他情况，
								Thread.sleep(1200);
								//再查一次
								orderInfoVO = getOrder(buyOrderId);
								if(i==3){
									//如果还没成交，撤单
									cancelOrder(buyOrderId);
								}
							} catch (Exception e) {
								logger.error("sleep error" ,e);
							}
						}
					}
				}
			}
		}
		
	}
	
	private void cancelOrder(String orderId){
		String url = config.getDomain() + "/api/publics/v1/cancel_order";
		LinkedHashMap<String, String> paramMap =new LinkedHashMap<String, String>();
		paramMap.put("apikey", config.getApikey());
		paramMap.put("order_id", orderId);
		String result = OkHttpUtil.post(paramMap, url);
		CoinbigResponse<?> coinbigResponse = (CoinbigResponse<?>) JSON.parseObject(result, new TypeReference<CoinbigResponse<?>>(){});
		logger.info(result);
		if(!coinbigResponse.success()){
			for (int i = 0; i < 10; i++) {
				logger.info("撤单失败，orderId:{},第{}次",orderId,i);
				cancelOrder(orderId);
			}
		}
	}
	
	
	/**
	 * 交易
	 * @param price
	 * @param type
	 * @return
	 */
	private TradeVO trade(Double price,SellType type){
		String url = config.getDomain() + "/api/publics/v1/trade";
		LinkedHashMap<String, String> paramMap =new LinkedHashMap<String, String>();
		paramMap.put("apikey", config.getApikey());
		paramMap.put("type", type.getType());
		paramMap.put("price", price.toString());
		paramMap.put("amount", config.getAmount().toString());
		paramMap.put("symbol", config.getSymbol());
		String result = OkHttpUtil.post(paramMap, url);
		CoinbigResponse<TradeVO> coinbigResponse = (CoinbigResponse<TradeVO>) JSON.parseObject(result, new TypeReference<CoinbigResponse<TradeVO>>(){});
		if(Objects.nonNull(coinbigResponse)&&coinbigResponse.success()){
			logger.info("挂单成功：{},单价：{},数量：{}",type.getName(),price,config.getAmount());
			return coinbigResponse.getData();
		}
		return null;
	}
	
	/**
	 * 查询挂单信息
	 * @param orderId
	 * @return
	 */
	private  OrderInfoVO getOrder(String orderId) {
		String url = config.getDomain() + "/api/publics/v1/order_info";
		LinkedHashMap<String, String> paramMap =new LinkedHashMap<String, String>();
		paramMap.put("apikey", config.getApikey());
		paramMap.put("order_id", orderId);
		String result = OkHttpUtil.post(paramMap, url);
		CoinbigResponse<OrderInfoVO> coinbigResponse = (CoinbigResponse<OrderInfoVO>) JSON.parseObject(result, new TypeReference<CoinbigResponse<OrderInfoVO>>(){});
		if(Objects.nonNull(coinbigResponse)&&coinbigResponse.success()){
			OrderInfoVO orderInfoVO = coinbigResponse.getData();
			logger.info("订单信息:{}",orderInfoVO.toString());
			return orderInfoVO;
		}
		return null;
	}
	
	public void sellConsumer(){
		while (!sellQueue.isEmpty()) {
			try {
				String orderId = sellQueue.take();
				getOrder(orderId);
				Thread.sleep(2000);
			} catch (Exception e) {
				logger.error("sellConsumer error",e);
			}
		}
	}
	
}
