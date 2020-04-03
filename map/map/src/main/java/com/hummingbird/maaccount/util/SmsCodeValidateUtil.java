package com.hummingbird.maaccount.util;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.hummingbird.common.util.DateUtil;
import com.hummingbird.common.util.PropertiesUtil;
import com.hummingbird.maaccount.exception.MaAccountException;
import com.hummingbird.maaccount.vo.SmsCodeValidateVO;

public class SmsCodeValidateUtil {
	static org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory
			.getLog(OrderValidateUtil.class);
	public static Map<String,SmsCodeValidateVO> SmsCode=new HashMap<String, SmsCodeValidateVO>();

	public static String datelog;
	
	public static void validateSmsCode(String appId,String mobileNum,Date datetest) throws MaAccountException{
		if(log.isDebugEnabled()){
			log.debug("短信校验开始。");
		}
		
		
		
		PropertiesUtil pu = new PropertiesUtil();
		//这里改一下，便于测试
		Date date=datetest;//new Date();
		String dateNow=DateUtil.format(date, "yyyyMMdd");
		//清空昨天的记录
		Calendar beginDate  = Calendar.getInstance();
		beginDate.setTime(date);
		beginDate.set(Calendar.DATE, beginDate.get(Calendar.DATE) - 1);
		String yesdate=DateUtil.format(beginDate.getTime(), "yyyyMMdd");
		if(datelog!=yesdate){
			cleanMap(yesdate);
		}
		
		//拼接key值
		String key=dateNow+appId+mobileNum;
		SmsCodeValidateVO smsVO=SmsCode.get(key);
		if(smsVO==null){
			smsVO=new SmsCodeValidateVO();
			smsVO.setAppId(appId);
			smsVO.setCreatTime(date);
			smsVO.setMobileNum(mobileNum);
			smsVO.setNumber(1);
			SmsCode.put(key, smsVO);
		}else{
			//校验短信的发送次数
			if(smsVO.getNumber()>=Integer.valueOf(pu.getProperty("smsCode.num"))){
				if(log.isDebugEnabled()){
					log.debug(String.format("应用[%s],手机号[%s]今天发送的短信过多。",appId,mobileNum));
				}throw new MaAccountException(MaAccountException.ERR_USER_EXCEPTION,"用户请求发送短信的数量过多，请联系管理员查看详情");
			}
			//校验短信的发送时间间隔
			Calendar calendar = Calendar.getInstance();    
			calendar.setTime(smsVO.getCreatTime());    
			calendar.add(Calendar.SECOND,Integer.valueOf(pu.getProperty("smsCode.time")));    
			if(date.before(calendar.getTime())){
				if(log.isDebugEnabled()){
					log.debug(String.format("应用[%s],手机号[%s]发送短信过于频繁，请稍后再试。",appId,mobileNum));
				}throw new MaAccountException(MaAccountException.ERR_USER_EXCEPTION,"发送短信过于频繁");
			}
			//如果都正常，该记录的number加一
			smsVO.setCreatTime(date);
			smsVO.setNumber(smsVO.getNumber()+1);
			SmsCode.put(key,smsVO);
			
		}
		
		
		
	}
	
	public static void cleanMap(String yesdate){
		Iterator iterator = SmsCode.keySet().iterator();    
		while (iterator.hasNext()) {   
		    String key = (String) iterator.next();
		    String datekey=key.substring(0, 8);
		    if (datekey.equals(yesdate)) {   
		       iterator.remove();        //添加该行代码   
		       SmsCode.remove(key);       
		     }   
		 } 
		datelog=yesdate;
		
	}
	public static void main(String[] args) throws MaAccountException {
		Calendar beginDate  = Calendar.getInstance();
		beginDate.setTime(new Date());
		beginDate.set(Calendar.DATE, beginDate.get(Calendar.DATE) - 1);
		validateSmsCode("zjhtwallet","13714560857",beginDate.getTime());
		validateSmsCode("zjhtwallet","13714560857",beginDate.getTime());
		validateSmsCode("zjhtwallet","13714560857",beginDate.getTime());
		validateSmsCode("zjhtwallet","13714560857",beginDate.getTime());
		validateSmsCode("zjhtwallet","13714560857",beginDate.getTime());
		validateSmsCode("zjhtwallet","13714560857",beginDate.getTime());
		validateSmsCode("zjhtwallet","13714560857",beginDate.getTime());
		validateSmsCode("zjhtwallet","13714560857",beginDate.getTime());
		validateSmsCode("zjhtwallet","13714560857",beginDate.getTime());
		validateSmsCode("zjhtwallet","13714560857",beginDate.getTime());
		validateSmsCode("zjhtwallet","13714560857",beginDate.getTime());
		validateSmsCode("zjhtwallet","13714560857",beginDate.getTime());
		validateSmsCode("zjhtwallet","13714560857",beginDate.getTime());
		validateSmsCode("zjhtwallet","13714560857",beginDate.getTime());
		validateSmsCode("zjhtwallet","13714560857",beginDate.getTime());
		validateSmsCode("zjhtwallet","13714560857",beginDate.getTime());
		validateSmsCode("zjhtwallet","13714560857",beginDate.getTime());
		
		System.out.println("通过Map.keySet遍历key和value:");   
		for (Map.Entry<String,SmsCodeValidateVO> entry : SmsCode.entrySet()) {   
	         System.out.println("key= " + entry.getKey() + "  and  value= "  
	                    + entry.getValue());   
	     }  
		validateSmsCode("zjhtwallet","13714560857",new Date());
		System.out.println("----分割线---");
		for (Map.Entry<String,SmsCodeValidateVO> entry : SmsCode.entrySet()) {   
	         System.out.println("key= " + entry.getKey() + "  and  value= "  
	                    + entry.getValue());   
	     } 
	}
}
