package com.hummingbird.maaccount.util.xuanwuSMS;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.esms.common.entity.Account;
import com.esms.common.entity.GsmsResponse;
import com.esms.common.entity.MTPack.MsgType;
import com.esms.common.entity.MTPack.SendType;
import com.hummingbird.common.vo.ResultModel;

public class XuanwuSender {
    private static org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory.getLog(XuanwuSender.class);
    
    private static final String APPID_YYD="app_yyd";
    
    public static ResultModel send(String appId,String content,String ... mobiles) throws Exception{
        //判断基础是否为空如果为空则重新初始化一次
        if(XuanwuSenderClient.pm==null){
            XuanwuSenderClient.init();
        }
        Account ac=null;
        if(APPID_YYD.equals(appId)){
            ac=XuanwuSenderClient.YYDaccount;
        }
        return send(ac,content,mobiles);
    }
    
    
    
    private static ResultModel send(Account ac,String content,String ... mobiles) throws Exception{
        if(mobiles==null||mobiles.length==0){
            throw new Exception("发送短信的地址为空");
        }
        if(content==null||StringUtils.isBlank(content)){
            throw new Exception("发送短信的内容为空");
        }
        
        //组装短信信息，map的key为手机号value为发送的内容
        Map<String,String> map=new HashMap<String,String>(mobiles.length);
        for (String mobile : mobiles) {
            map.put(mobile, content);
        }
        //组装短信信息
        MTPackBuilder mtPackBuilder=MTPackBuilder.create();
        mtPackBuilder.setMsgType(MsgType.SMS).setSendType(SendType.MASS).setDistinctFlag(false).setMsgs(map);
        ResultModel rm = new ResultModel(0,"短信发送成功");
        try{
            log.debug("xuanwu短信通道正在发送短信。。。");
           //发送短信
           GsmsResponse grsp=XuanwuSenderClient.pm.post(ac, mtPackBuilder.build());
           //判断返回码
           if(grsp==null||grsp.getResult()!=0){
               throw new Exception(grsp==null?"grsp is null":grsp.toString());
           }
           log.debug("xuanwu短信通道发送短信成功。。。");
        }catch(Throwable e){
            log.error("xuanwu发送短信失败："+map, e);
            throw new Exception("发送短信失败");
        }
        return rm;
    }
}
