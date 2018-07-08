package com.miner.coinbig.utils;

import java.security.MessageDigest;

public class Md5Util {

	public static String getEncryption(String text) {
		String result = "";
		if (text != null) {
			try {
				// 指定加密的方式为MD5
				MessageDigest md = MessageDigest.getInstance("MD5");
				// 进行加密运算
				byte bytes[] = md.digest(text.getBytes("ISO8859-1"));
				for (int i = 0; i < bytes.length; i++) {
					// 将整数转换成十六进制形式的字符串 这里与0xff进行与运算的原因是保证转换结果为32位
					String str = Integer.toHexString(bytes[i] & 0xFF);
					if (str.length() == 1) {
						str += "F";
					}
					result += str;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	public static void main(String[] args) {
		System.out.println(getEncryption("123456").toUpperCase());
	}
}
