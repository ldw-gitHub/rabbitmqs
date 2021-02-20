package com.framemark.util;

import java.util.Random;

public class RandomUtils {
	
	static char[] identifyStr = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 
			'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H',	'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
			'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
	
	/**
	 * 随机N个数字的字符串
	 * @param n
	 * @return 
	 * @date 2018年3月9日
	 * @author sky_luan
	 */
	public static String randomN(int n){
		StringBuffer sb = new StringBuffer();
		Random random = new Random();
		for (int i = 0; i < n; i++) {
			sb.append(random.nextInt(10));
		}
		return sb.toString();
	}
	
	
	/**
	 * 生成随机类字符
	 * @param len
	 * @return 
	 * @date 2020年9月11日
	 * @author sky_luan
	 */
	public static String generateChatAndNumberIdentifyCode(int len) {
		int min = 0;
		int maxnum = identifyStr.length;
		StringBuilder codeStr = new StringBuilder(len);
		for (int i = 0; i < len; i++) {
			int num = (int) ((maxnum - min) * Math.random() + min);
			codeStr.append(identifyStr[num]);
		}
		return codeStr.toString();
	}
	
	
}
