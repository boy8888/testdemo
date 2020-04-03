/**
 * 
 * YYDSmsSender.java
 * 版本所有 深圳市蜂鸟娱乐有限公司 2013-2014
 */
package com.hummingbird.maaccount.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ObjectUtils;

import com.hummingbird.common.exception.DataInvalidException;
import com.hummingbird.common.util.JsonUtil;
import com.hummingbird.common.vo.ResultModel;
import com.hummingbird.maaccount.entity.AppSmsActionSetting;
import com.hummingbird.maaccount.entity.AppSmsSetting;
import com.hummingbird.maaccount.service.ZjSmsSender;
import com.zjhtc.htm.message.serializable.MessageRequest;
import com.zjhtc.htm.message.serializable.factory.MessageFactory;
import com.zjhtc.htm.message.serializable.factory.SimpleSmsMessageFactory;

/**
 * @author john huang
 * 2015年12月8日 下午7:51:30
 * 本类主要做为 有油贷的短信发送
 */
public class YYDSmsSender extends AbstractSmsSender {

	
	/**
	 * 发送短信
	 * @param mobileNum
	 * @param content
	 * @return
	 * @throws DataInvalidException
	 */
	@Override
	public ResultModel send(String mobileNum,String content) throws DataInvalidException{
		if (log.isDebugEnabled()) {
			log.debug(String.format("使用新的短信发送器发送内容:%s",this.getClass()));
		}
		ResultModel rm = new ResultModel(0,"短信发送成功");
		SimpleSmsMessageFactory factory = SimpleSmsMessageFactory.getFactory(appSmsSetting.getChannel(), appSmsSetting.getCorporationCode(), action.getBusinessName());//DIANSHANG_YYD=接入渠道(消息平台需验证)；用户注册=业务系统功能点；例如：用户注册、用户开卡、用户退卡、用户借款
        factory.createOnlyMessage(mobileNum, content);
        MessageRequest message = factory.getMessage();
        if (log.isDebugEnabled()) {
			log.debug(String.format("短信通道请求地址:%s,channel=%s,CorporationCode=%s,BusinessName=%s",url,appSmsSetting.getChannel(),appSmsSetting.getCorporationCode(),action.getBusinessName()));
		}
        String respMsg = com.zjhtc.htm.message.serializable.util.RequestUtil.getInstance(url).send(message, MessageFactory.Method.METHOD_SEND_SMS); //发送消息
        if (log.isDebugEnabled()) {
			log.debug(String.format("响应参数:%s", respMsg));
		}
        //解析结果
        HashMap resultmap = JsonUtil.convertJson2Obj(respMsg, HashMap.class);
        String statecode = ObjectUtils.toString(resultmap.getOrDefault("stateCode", ""));
        if("00000".equals(statecode)){
        	//检查每一条记录的结果
        	List smsList =(List) resultmap.get("smsList");
        	if(smsList==null||smsList.isEmpty()){
        		if (log.isDebugEnabled()) {
        			log.debug(String.format("内容节点不存在,不知道是否ok"));
        		}
        		rm.setErr(10000, "未能识别发送状态");
        		return rm;
        	}
        	Map contentresultmap = (Map) smsList.get(0);
    		String respCode = ObjectUtils.toString(contentresultmap.getOrDefault("respCode", ""));
    		if("00000".equals(respCode)){
    			//发送成功
    			return rm;
    		}
    		else{
    			rm.setErr(10000, "发送失败:"+ ObjectUtils.toString(resultmap.getOrDefault("respMsg", "")));
    		}
        }
        else {
        	rm.setErr(10000, "发送失败:"+ ObjectUtils.toString(resultmap.getOrDefault("stateCodeDesc", "发送错误")));

        }
        return rm;
	}

}
