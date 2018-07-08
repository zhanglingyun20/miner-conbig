package com.miner.coinbig.utils;

import java.io.IOException;
import java.util.LinkedHashMap;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.miner.coinbig.model.CoinbigResponse;
import com.miner.coinbig.model.TickerVO;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OkHttp {

	/**
	 * . 签名生成规则
		如何对请求参数进行签名
		
		首先，将待签名字符串要求按照参数名进行排序(首先比较所有参数名的第一个字母，按abcd顺序排列，若遇到相同首字母，则看第二个字母，以此类推)。
		
		例如：对于如下的参数进行签名
		
		string[] parameters={
		"apikey=c821db84-6fbd-11e4-a9e3-c86000d26d7c",
		"symbol=btc_cny",
		"type=buy",
		"price=680",
		"amount=1.0"};
		生成待签名的字符串 amount=1.0&apikey=c821db84-6fbd-11e4-a9e3-c86000d26d7c&price=680&symbol=btc_cny&type=buy
		
		将待签名字符串添加私钥参数生成最终待签名字符串。
		
		例如： amount=1.0&apikey=c821db84-6fbd-11e4-a9e3-c86000d26d7c&price=680&symbol=btc_cny&type=buy&secret_key=secretKey
		
		最后，是利用32位MD5算法，对最终待签名字符串进行签名运算，从而得到签名结果字符串(该字符串赋值于参数sign)，MD5计算结果中字母全部大写。
		
		注意：公钥(即apikey)和签名(即sign)为POST请求必传参数
		
		签名：用户提交的参数除sign外，都要参与签名
		
		构造签名时,使用的私钥字段名称：secret_key
		
		统一的API调用站点：https://www.coinbig.com
	 */
	public static String DOMAIN = "https://www.coinbig.com/";
	
	public static String APIKEY = "1233475FBD832A3BC6603C922FA2C0CA";
	public static String SECRETKEY = "4B13BAE33B376EB7A1536BEC2C4E1CB1";

	public static void main(String[] args) throws IOException {
//		
//		ticker();
		trade();
//		getOrder();
	}

	/**
	 * 
	 * apikey	String	是	用户申请的apiKey
		type	String	是	买卖类型: 限价单(buy/sell) 市价单(buy_market/sell_market)
		price	double	否	下单价格 [限价买单(必填)
		amount	double	否	交易数量 [限价卖单（必填)
		sign	String	否	请求参数的签名
		symbol	String	是	btc_usdt: 比特币
	 */
	public static void trade() {
		String url = DOMAIN + "/api/publics/v1/trade";
		LinkedHashMap<String, String> paramMap =new LinkedHashMap<String, String>();
		paramMap.put("apikey", APIKEY);
		paramMap.put("type", "sell");
		paramMap.put("price", "474.01");
		paramMap.put("amount", "0.001");
		paramMap.put("symbol", "eth_usdt");
		paramMap.put("secret_key", SECRETKEY);
		String result = OkHttpUtil.post(paramMap, url);
		System.out.println(result);
	}
	
	
	public static void ticker() {
		///api/publics/v1/ticker
		//symbol	String	是	btc_usdt:比特币
		String path = "api/publics/v1/ticker";
		StringBuffer sb = new StringBuffer();
		sb.append(DOMAIN).append(path);
		LinkedHashMap<String, String> paramMap =new LinkedHashMap<String, String>();
		paramMap.put("symbol", "btc_usdt");
		System.out.println();
		Long start = System.currentTimeMillis();
		String result = OkHttpUtil.get(paramMap, sb.toString());
		System.out.println("查询行情耗时："+(System.currentTimeMillis()-start));
		CoinbigResponse<TickerVO> coinbigResponse = (CoinbigResponse<TickerVO>) JSON.parseObject(result, new TypeReference<CoinbigResponse<TickerVO>>(){});
		TickerVO tickerVO = coinbigResponse.getData();
//		TickerVO tickerVO = coinbigResponse.getData();
		System.out.println(tickerVO);

	}
	
	
	public static void getOrder() {
		String url = DOMAIN + "/api/publics/v1/order_info";
		LinkedHashMap<String, String> paramMap =new LinkedHashMap<String, String>();
		paramMap.put("apikey", APIKEY);
		paramMap.put("order_id", "123456");
		paramMap.put("secret_key", SECRETKEY);
		String result = OkHttpUtil.post(paramMap, url);
		System.out.println(result);
	}
	
}
