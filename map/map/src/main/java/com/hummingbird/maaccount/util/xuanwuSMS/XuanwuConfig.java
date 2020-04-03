package com.hummingbird.maaccount.util.xuanwuSMS;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.hummingbird.common.util.PropertiesUtil;

public class XuanwuConfig {
    private static org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory.getLog(XuanwuConfig.class);
    
    public static String cmHost;
    
    public static String wsHost;
    
    public static int cmHostPort;
    
    public static int wsHostPort;
    
    public static String YYDuserName;
    
    public static String YYDpassword;
    
    private static List<String> allowAPP; 
    
    public static void init(){
        PropertiesUtil propertiesUtil=new PropertiesUtil();
        cmHost=propertiesUtil.getProperty("xuanwu_cm_host");
        wsHost=propertiesUtil.getProperty("xuanwu_ws_host");
        cmHostPort=propertiesUtil.getInt("xuanwu_cm_port",9080);
        wsHostPort=propertiesUtil.getInt("xuanwu_ws_port",9070);
        YYDuserName=propertiesUtil.getProperty("xuanwu_YYD_username");
        YYDpassword=propertiesUtil.getProperty("xuanwu_YYD_password");
        initAllowAppList(propertiesUtil);
    } 
    
    private static void initAllowAppList(PropertiesUtil propertiesUtil){
        String appidList=propertiesUtil.getProperty("xuanwu_allow_appids");
        if(appidList!=null&&appidList.length()>0){
            allowAPP=Arrays.asList(appidList.split(","));
        }
    }
    
    public static boolean inAllowAppList(String appId){
        if(allowAPP==null){
            log.debug("初始化玄武短信允许的APP列表");
            initAllowAppList(new PropertiesUtil());
        }
        if(StringUtils.isBlank(appId)){
            return false;
        }
        if(allowAPP==null||allowAPP.isEmpty()){
            return false;
        }
        log.debug("玄武短信允许的APP列表为："+allowAPP+"    本次发送短信的APPID为："+appId);
        return allowAPP.contains(appId);
    }
    
    
}
