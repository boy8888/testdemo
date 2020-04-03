package com.hummingbird.maaccount.util.xuanwuSMS;

import com.esms.PostMsg;
import com.esms.common.entity.Account;

public class XuanwuSenderClient {
    public static PostMsg pm;
    
    public static Account YYDaccount;
    
    public static void init(){
        synchronized (XuanwuSenderClient.class) {
            if(pm==null){
                XuanwuConfig.init();
                //用户信息
                YYDaccount=new Account(XuanwuConfig.YYDuserName, XuanwuConfig.YYDpassword);
                pm = new PostMsg();
                //以下为mos地址
                pm.getCmHost().setHost(XuanwuConfig.cmHost, XuanwuConfig.cmHostPort);//设置网关的IP和port，用于发送信息，建议可配
                pm.getWsHost().setHost(XuanwuConfig.wsHost, XuanwuConfig.wsHostPort);//设置网关的IP和port，用于获取账号信息、上行、状态报告等等，建议可配
            }
        }
    }
}
