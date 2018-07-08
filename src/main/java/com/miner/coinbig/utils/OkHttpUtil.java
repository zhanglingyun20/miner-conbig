package com.miner.coinbig.utils;

import java.io.IOException;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSONObject;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OkHttpUtil {

	private final static Logger logger = LoggerFactory.getLogger(OkHttpUtil.class);
	
	public static String get(LinkedHashMap<String, String> paramMap,String url){
		OkHttpClient client = new OkHttpClient();
		List<String> keys = paramMap.keySet().stream().collect(Collectors.toList());
		Collections.sort(keys);
		StringBuffer sb = new StringBuffer();
		keys.stream().forEach(key ->{
			sb.append(key).append("=").append(paramMap.get(key)).append("&");
		});
		if(StringUtils.isNoneEmpty(sb.toString())&&sb.toString().indexOf("&")>0){
			sb.append("sign=").append(getSign(paramMap));
		}
		if(StringUtils.isNotBlank(sb.toString())){
			url = url + "?"+sb.toString();
		}
		Request request = new Request.Builder()
				.url(url).build();
		Response response;
		try {
			response = client.newCall(request).execute();
			if (response.isSuccessful()) {
				return response.body().string();
			}
		} catch (IOException e) {
			logger.error("get error url ："+url, e);
		}
		return  null;
	}
	
	/**
	 * 如何对请求参数进行签名
		首先，将待签名字符串要求按照参数名进行排序(首先比较所有参数名的第一个字母，
		按abcd顺序排列，若遇到相同首字母，则看第二个字母，以此类推)。
		amount=1.0&apikey=c821db84-6fbd-11e4-a9e3-c86000d26d7c&price=680&symbol=btc_cny&type=buy
	 * @param param
	 * @param url
	 * @return
	 */
	public static String post(LinkedHashMap<String, String> paramMap,String url){
		try {
			if(CollectionUtils.isEmpty(paramMap)||StringUtils.isEmpty(url)){
				return null;
			}
			List<String> keys = paramMap.keySet().stream().collect(Collectors.toList());
			Collections.sort(keys);
			// 3, 发起新的请求,获取返回信息
			OkHttpClient client = new OkHttpClient();
			FormBody.Builder builder = new FormBody.Builder();
			keys.forEach(key ->{
				builder.add(key, paramMap.get(key));
			});
			builder.add("sign", getSign(paramMap));
			RequestBody body = builder.build();
			Request request = new Request.Builder().url(url).post(body).build();
			Response response = client.newCall(request).execute();
			System.out.println(response);
			if (response.isSuccessful()) {
				return response.body().string();
			} 
		} catch (Exception e) {
			StringBuffer sb = new StringBuffer();
			sb.append("post error,url:").append(url).append(",param:").append(JSONObject.toJSON(paramMap));
			logger.error(sb.toString(), e);
		}
		return null;
	}
	
	/**
	 *  构建post签名
	 * @param param
	 * @return
	 */
	public static String  getSign(LinkedHashMap<String, String> paramMap){
		StringBuffer sb = new StringBuffer();
		List<String> keys = paramMap.keySet().stream().collect(Collectors.toList());
		Collections.sort(keys);
		keys.forEach(item ->{
			sb.append(item).append("=").append(paramMap.get(item)).append("&");
		});
		String signStr = "";
		if(StringUtils.isNotEmpty(sb.toString())&&sb.toString().indexOf("&")>0){
			signStr = sb.substring(0, sb.length()-1);
		}
		System.out.println("加密字符串："+signStr);
		String sign = Md5Util.getEncryption(signStr).toUpperCase();
		System.out.println("sign："+sign);
		return  sign;
		
	}
	
}
