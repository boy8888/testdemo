package com.hummingbird.maaccount.util.huiTongBusinessUtil.util;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.hummingbird.common.util.PropertiesUtil;


public class HuiTongBusinessConfig {
    
     private static final Log    log    = LogFactory.getLog(HuiTongBusinessConfig.class);
	
     public final static String HUI_TONG_ADDRESS;
     
     public final static int HUI_TONG_PORT;
     
     public final static String  HUI_TONG_INPUT_CHARSET;
     
     public final static boolean HUI_TONG_OPEN_SSL;
     
     public final static int HUI_TONG_READ_BUFF_SIZE;
     
     public final static List<String> HUI_TONG_APP_LIST;
     
     
     /************************接口ID和渠道不允许随意变更*********************************/
     
     public final static Long HUI_TONG_VALIDATE_PWD_TXN_ID=319L;//验证支付密码接口
     
     public final static String HUI_TONG_CHANNEL="A0002";//渠道ID
     
     static{
         PropertiesUtil propertiesUtil=new PropertiesUtil();
         HUI_TONG_ADDRESS=propertiesUtil.getProperty("HUI_TONG_ADDRESS");
         HUI_TONG_PORT=Integer.valueOf(propertiesUtil.getProperty("HUI_TONG_PORT"));
         HUI_TONG_INPUT_CHARSET=propertiesUtil.getProperty("HUI_TONG_INPUT_CHARSET");
         HUI_TONG_OPEN_SSL=propertiesUtil.getBoolean("HUI_TONG_OPEN_SSL");
         HUI_TONG_READ_BUFF_SIZE=Integer.valueOf(propertiesUtil.getProperty("HUI_TONG_READ_BUFF_SIZE"));
         String appList=propertiesUtil.getProperty("HUI_TONG_APP_LIST");
         if(appList!=null&&appList.length()>0){
             HUI_TONG_APP_LIST=Arrays.asList(appList.split(","));
         }else{
             HUI_TONG_APP_LIST=null;
         }
     }
     
     public static boolean allowList(String appId){
         
         if(StringUtils.isBlank(appId)){
             log.info("Huitong接口不允许appId为空的调用。。。此次调用APPID="+appId);
             return false;
         }
         if(HUI_TONG_APP_LIST==null||HUI_TONG_APP_LIST.isEmpty()){
             log.info("Huitong接口HUI_TONG_APP_LIST为空。。。");
             return false;
         }
         log.info("HUI_TONG_APP_LIST数据为："+HUI_TONG_APP_LIST+"  此次操作的APPID为："+appId);
         return HUI_TONG_APP_LIST.contains(appId);
     }
     
}
