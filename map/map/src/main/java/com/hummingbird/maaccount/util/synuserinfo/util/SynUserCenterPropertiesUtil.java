package com.hummingbird.maaccount.util.synuserinfo.util;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class SynUserCenterPropertiesUtil {
	
	private Properties properties;
	
	public SynUserCenterPropertiesUtil(String path) {
		System.out.println("初始化用户中心配置文件开始");
		try {
			InputStream inputStream=new FileInputStream(path);
			properties=new Properties();
			properties.load(inputStream);
		} catch (Exception e) {
			throw new RuntimeException("初始化用户中心配置文件失败",e);
		}
		System.out.println("初始化用户中心配置文件成功");
	}
	
	public String getString(String key){
		return properties.getProperty(key);
	}
	
	public Integer getInteger(String key,Integer defalut){
		String result=properties.getProperty(key);
		try {
			return Integer.valueOf(result);
		} catch (Exception e) {
			System.out.println(String.format("%s不是一个整数的字符串,返回默认的数字", result));
			// 忽略异常
			return defalut;
		}
	}

}
