package com.hummingbird.maaccount.util;

import java.util.Random;

import com.hummingbird.common.util.DateUtil;

public class AccountGenerationUtil {
	/**
	 * 创建红包账号
	 * @return
	 */
	public static String genRedOrderAccountNo(){
		return genNO("HB");
	}
	/**
	 * 创建积分账号
	 * @return
	 */
	public static String genJifenAccountNo(){
		return genNO("JF");
	}
	public static String genNO(String prefix){
		return prefix+DateUtil.getCurrentTimeStr() + String.format("%014d", new Random().nextInt(999999999));
	}
	public static String genNO(String prefix,int len){
		return prefix+DateUtil.getCurrentTimeStr() + String.format("%"+len+"d", new Random().nextInt(999999999));
	}
}
