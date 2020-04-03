package com.hummingbird.maaccount.util.huiTongBusinessUtil.util;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hummingbird.common.util.Md5Util;




public class HuiTongBusinessUtil {
	 //获取随机数并且第一位不会为0
     public static String createRandNum(int len){
    	 if(len<=0){
    		 return null;
    	 }
    	 StringBuilder sb=new StringBuilder(len);
    	 Random rd=new Random();
    	 for (int i = 0; i < len; i++) {
			if(i==0){
				sb.append(rd.nextInt(9)+1);
				continue;
			}
			sb.append(rd.nextInt(10));
		 }
    	 return sb.toString();
     }
     
     //获取排序后的key=value&key=value
     public static String getSignPlaintext(Map<String,Object> param){
    	 if(param==null||param.size()==0){
    		 return null;
    	 }
    	 //将key放到list中
    	 List<String> keys=new ArrayList<String>(param.keySet());
    	 //排序
    	 Collections.sort(keys);
    	 //创建字符串
    	 StringBuilder sb=new StringBuilder();
    	 for (String key : keys) {
    		Object o=param.get(key);
    		if(o!=null){
    			sb.append(key).append("=").append(o).append("&");
    		}
		 }
    	 sb.deleteCharAt(sb.length()-1);
    	 return sb.toString();
     }
     
     //获取MD5值
     public static String MD5(String str){
    	 return Md5Util.Encrypt(str).toUpperCase();
     }
     
     //将对象转成map
     public static Map<String,Object> ObjectToMap(Object o){
    	 
    	 Gson gson=new Gson();
	   	 //将对象转换成MAP
	   	 Map<String,Object> result=gson.fromJson(gson.toJson(o),new TypeToken<Map<String, String>>() { }.getType());
	   	 
	   	 return result;
     }
     
     //将对象转成json
     public static String ObjectToJson(Object o){
    	 Gson gson=new Gson();
    	 return gson.toJson(o);
     }
     
     //将json转换成对象
     public static <T> T JsonToObject(String jsonText,Class<T> clx){
    	 return new Gson().fromJson(jsonText, clx);
     }
     
}
