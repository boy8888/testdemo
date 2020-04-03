package com.hummingbird.maaccount.util;

import com.zjhtc.htm.message.serializable.MessageRequest;
import com.zjhtc.htm.message.serializable.factory.MessageFactory;
import com.zjhtc.htm.message.serializable.factory.SimpleSmsMessageFactory;
import com.zjhtc.htm.message.serializable.util.RequestUtil;

public class TestSendMessage {

    public static final String ZJHT = "ZJHT";                                     //中经汇通

    public static final String ZJDS = "ZJDS";                                     //中经电商

    public static final String ZJYR = "ZJYR";                                     //中经油融

    public static final String HTB  = "HTB";                                      //汇通宝

    public static final String url  = "http://121.8.155.5:9616/message/send.htm"; //生产环境内网：10.103.1.13 生产环境外网：121.14.62.202


    public static void main(String[] args) {
        testSms();
    }

    /**
     * 发送短信测试
     */
    public static void testSms() {
        SimpleSmsMessageFactory factory = SimpleSmsMessageFactory.getFactory("DIANSHANG_YYD", ZJYR, "12");//DIANSHANG_YYD=接入渠道(消息平台需验证)；用户注册=业务系统功能点；例如：用户注册、用户开卡、用户退卡、用户借款
        factory.createOnlyMessage("18922260815", "您的短信验证码是123456，有效期10分钟");
        MessageRequest message = factory.getMessage();
        String respMsg = RequestUtil.getInstance(url).send(message, MessageFactory.Method.METHOD_SEND_SMS); //发送消息
        System.out.println("响应参数:" + respMsg);
        //响应参数:{"stateCodeDesc":"处理成功","smsList":[{"respCode":"00000","respMsg":"校验成功!","mobile":"18922260815"}],"stateCode":"00000"}

    }

}
