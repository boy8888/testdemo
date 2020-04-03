package com.hummingbird.maaccount.util;

import java.util.Calendar;
import java.util.Date;

import com.hummingbird.common.constant.CommonStatusConst;
import com.hummingbird.common.util.DateUtil;
import com.hummingbird.common.util.PropertiesUtil;
import com.hummingbird.common.util.SpringBeanUtil;
import com.hummingbird.maaccount.entity.SmsMTDelay;
import com.hummingbird.maaccount.mapper.SmsMTDelayMapper;

/**
 * @author john huang
 * 2015年5月29日 下午12:00:47
 * 本类主要做为 短信发送工具类
 */
public class SmsSendUtil {
	static org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory
			.getLog(OrderValidateUtil.class);
	
	
	public static void setSendtime(SmsMTDelay smsmt){
		PropertiesUtil propertiesUtil = new PropertiesUtil();
		int starttime = propertiesUtil.getInt("sms.delay.starttime",8);
		int endtime = propertiesUtil.getInt("sms.delay.endtime",20);
		Calendar cal = Calendar.getInstance();
		cal.setTime(smsmt.getSendTime());
		cal.set(Calendar.HOUR_OF_DAY,starttime);//定位到早上8点
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(smsmt.getSendTime());
		cal2.set(Calendar.HOUR_OF_DAY, endtime);//定位到晚上8点
		if(smsmt.getSendTime().before(cal.getTime())){//8点前的短信，在8点后发
			smsmt.setSendTime(cal.getTime());
		}
		else if(smsmt.getSendTime().after(cal2.getTime())){//晚上8点后的短信，在第二天8点后发
			cal.add(Calendar.DAY_OF_MONTH, 1);
			smsmt.setSendTime(cal.getTime());
		}
		//否则不处理
//		if(new Date().after(cal.getTime())&&new Date().before(cal2.getTime())){
//			cal = Calendar.getInstance();
//		}
//		else if(new Date().after(cal2.getTime())){
//			cal.add(Calendar.DAY_OF_MONTH, 1);//如果发送的时间小于当前时间，如晚上11点执行，到明天发送
//		}
//		smsmt.setSendtime(cal.getTime());
	}
	
	/**
	 * 延迟发送，就算是指定现在发送，也不一定马上发，因为定时器有一个周期来检查
	 * @param content 内容
	 * @param sendTime 指定时间，如果为空，则为当前时间
	 * @param mobiles 手机号
	 */
	public static void delaySmsSend(String  mobile,String content,Date sendTime,String appId,String action){
		if(sendTime==null){
			sendTime = new Date();
		}
		
		SmsMTDelayMapper smsDelayDao = SpringBeanUtil.getInstance().getBean(SmsMTDelayMapper.class);
		SmsMTDelay smsmt=new SmsMTDelay();
		smsmt.setMobileNum(mobile);
		smsmt.setMtContent(content);
		smsmt.setSendTime(sendTime);
		SmsSendUtil.setSendtime(smsmt);
		smsmt.setStatus(CommonStatusConst.STATUS_CREATE);
		smsmt.setInsertTime(new Date());
		if (log.isDebugEnabled()) {
			log.debug(String.format("设置延时短信[%s]",smsmt));
		}
		smsDelayDao.insert(smsmt);
	}
	/**
	 * 延迟发送，就算是指定现在发送，也不一定马上发，因为定时器有一个周期来检查
	 * @param content 内容
	 * @param sendTime 指定时间，如果为空，则为当前时间
	 * @param mobiles 手机号
	 */
	public static void delaySmsSend(String content,Date sendTime,String ... mobiles){
		if(sendTime==null){
			sendTime = new Date();
		}
		
		SmsMTDelayMapper smsDelayDao = SpringBeanUtil.getInstance().getBean(SmsMTDelayMapper.class);
		
		for (int i = 0; i < mobiles.length; i++) {
			String mobilenum = mobiles[i];
			
			SmsMTDelay smsmt=new SmsMTDelay();
			smsmt.setMobileNum(mobilenum);
			smsmt.setMtContent(content);
			smsmt.setSendTime(sendTime);
			SmsSendUtil.setSendtime(smsmt);
			smsmt.setStatus(CommonStatusConst.STATUS_CREATE);
			smsmt.setInsertTime(new Date());
			if (log.isDebugEnabled()) {
				log.debug(String.format("设置延时短信[%s]",smsmt));
			}
			smsDelayDao.insert(smsmt);
		}
		
	}
	
	
	
	
}
