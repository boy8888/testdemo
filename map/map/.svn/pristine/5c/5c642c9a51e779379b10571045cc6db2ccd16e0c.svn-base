/**
 * 
 * SmsSendService.java
 * 版本所有 深圳市蜂鸟娱乐有限公司 2013-2014
 */
package com.hummingbird.maaccount.service.impl;

import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.hummingbird.common.constant.CommonStatusConst;
import com.hummingbird.common.exception.SignatureException;
import com.hummingbird.common.exception.ValidateException;
import com.hummingbird.common.util.JsonUtil;
import com.hummingbird.common.util.Md5Util;
import com.hummingbird.common.util.PropertiesUtil;
import com.hummingbird.common.util.RequestUtil;
import com.hummingbird.common.util.http.HttpRequester;
import com.hummingbird.common.util.json.JSONException;
import com.hummingbird.common.util.json.JSONObject;
import com.hummingbird.common.vo.ResultModel;
import com.hummingbird.maaccount.entity.AppSmsActionSetting;
import com.hummingbird.maaccount.entity.AppSmsSetting;
import com.hummingbird.maaccount.entity.SmsMTDelay;
import com.hummingbird.maaccount.mapper.AppSmsActionSettingMapper;
import com.hummingbird.maaccount.mapper.AppSmsSettingMapper;
import com.hummingbird.maaccount.mapper.SmsMTDelayMapper;
import com.hummingbird.maaccount.service.ZjSmsSender;
import com.hummingbird.maaccount.util.xuanwuSMS.XuanwuConfig;
import com.hummingbird.maaccount.util.xuanwuSMS.XuanwuSender;
import com.zjhtc.htm.message.serializable.MessageRequest;
import com.zjhtc.htm.message.serializable.factory.MessageFactory;
import com.zjhtc.htm.message.serializable.factory.SimpleSmsMessageFactory;

import jodd.util.ObjectUtil;

/**
 * @author huangjiej_2
 * 2014年12月27日 上午8:39:30
 * 本类主要做为
 */
@Service("smsSendService")
public class SmsSendService {

	org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory
			.getLog(this.getClass());
	@Autowired
	SmsMTDelayMapper smsDelayDao;
	@Autowired
	AppSmsSettingMapper appSmsDao;
	@Autowired
	AppSmsActionSettingMapper appSmsActionDao;
	
	public static String ACTION_NAME_SMSCODE="ACTION_NAME_SMSCODE";
	public static String ACTION_NAME_OPENCARD="ACTION_NAME_OPENCARD";
	public static String ACTION_NAME_REGISTER="ACTION_NAME_REGISTER";
	public static String ACTION_NAME_PAY="ACTION_NAME_PAY";
	public static String ACTION_NAME_CANCLE="ACTION_NAME_CANCLE";
	public static String ACTION_NAME_UNDO="ACTION_NAME_UNDO";
	public static String ACTION_NAME_RECHARGE="ACTION_NAME_RECHARGE";
	
	/**
	 * 延时发送短信
	 */
	public void delaySend(){
		if (log.isDebugEnabled()) {
			log.debug(String.format("延时短信发送开始"));
		}
		List<SmsMTDelay> smslist = smsDelayDao.selectDelaySmses();
		for (Iterator iterator = smslist.iterator(); iterator.hasNext();) {
			SmsMTDelay smsMTDelay = (SmsMTDelay) iterator.next();
			try {
				send(smsMTDelay.getMtContent(),smsMTDelay.getAppId(),smsMTDelay.getAction(),smsMTDelay.getMobileNum());
			} catch (Exception e) {
				log.error(String.format("短信发送出错"),e);
			}
			smsMTDelay.setActTime(new Date());
			smsMTDelay.setStatus(CommonStatusConst.STATUS_OK);
			smsDelayDao.updateByPrimaryKeySelective(smsMTDelay);
		}
		if (log.isDebugEnabled()) {
			log.debug(String.format("延时短信发送完成"));
		}
	}
	
	/**
	 * 使用中经的新短信通道发送短信(这个短信必须符合短信模板规范)
	 * @param content
	 * @param appId 应用id,用于获取短信
	 * @param actionName 操作,如用户注册,重置密码等
	 * @param mobile
	 * @return
	 * @throws Exception
	 */
	public ResultModel send(String  mobile,String content,String appId,String actionName) throws Exception{
//		appId = "app_yyd";
//		actionName="12";
		if (log.isDebugEnabled()) {
			log.debug(String.format("发送短信内容%s到手机号%s",content,mobile));
		}
		//有油贷的所有短信通过玄武接口发送
		if(XuanwuConfig.inAllowAppList(appId)){
		    log.error(appId+"的短信设置使用玄武短信接口发送");
		    return XuanwuSender.send(appId, content, mobile);
		}
		AppSmsSetting appSmsSetting = appSmsDao.selectByPrimaryKey(appId);
		ResultModel rm = new ResultModel(0,"短信发送成功");
		if(appSmsSetting==null){
//			log.error(appId+"的短信设置不存在,无法发送短信");
//			throw ValidateException.ERROR_PARAM_NULL.clone(null, "短信设置不存在,无法发送短信");
			log.error(appId+"的短信设置(AppSmsSetting)不存在,使用原来的短信通道");
			rm = send(content, mobile);
			return rm;
		}
		AppSmsActionSetting action=  appSmsActionDao.selectByAppAction(appId,actionName);
		if(action==null){
//			log.error(appId+"的短信设置不存在,无法发送短信");
//			throw ValidateException.ERROR_PARAM_NULL.clone(null, "短信设置不存在,无法发送短信");
			log.error(appId+"的短信设置(AppSmsActionSetting)不存在,使用原来的短信通道");
			rm = send(content, mobile);
			return rm;
		}
		if(org.apache.commons.lang.StringUtils.equalsIgnoreCase(appSmsSetting.getSenderImpl(),"default")){
			//使用原来的短信发送
			rm = send(content, mobile);
		}
		else{
			//不同的系统 使用不同的发送器
			ZjSmsSender zjsender = ZjSmsSenderFactory.getZjSmsSender(appSmsSetting,action);
			rm=zjsender.send(mobile, content);
		}
		
		return rm;
	}
	
	/**
	 * 发送短信
	 * @param content
	 * @param mobile
	 * @return 
	 */
	public ResultModel send(String content,String ... mobiles) throws Exception{
		if(log.isInfoEnabled())
		{
			log.info(String.format("为手机号码%s发送短信:%s",Arrays.deepToString(mobiles),content));
		}
		ResultModel rm = new ResultModel("短信发送成功");
		if(StringUtils.isEmpty(content)){
			log.error("信息内容为空");
			throw new Exception("信息内容为空");
		}
		if(mobiles==null||mobiles.length==0){
			log.error("无要发送的手机短信");
			throw new Exception("无要发送的手机短信");
		}
		PropertiesUtil propertiesUtil = new PropertiesUtil();
		String url=propertiesUtil.getProperty("zj.sms.url");
		String appname=propertiesUtil.getProperty("zj.sms.appname");
		String appNo=propertiesUtil.getProperty("zj.sms.appNo");
		String key=propertiesUtil.getProperty("zj.sms.key");
		String service=propertiesUtil.getProperty("zj.sms.service");
		String crtpath=propertiesUtil.getProperty("zj.sms.crtpath");
		String ver=propertiesUtil.getProperty("zj.sms.ver");
		if(StringUtils.isEmpty(url)){
			log.error("短信发送地址为空");
			throw new Exception("短信发送地址为空");
		}
		
		//组装发送内容
		JSONObject jsonmessage=combineMsg(content,appname,mobiles);
		JSONObject json=new JSONObject();
		json.put("content", jsonmessage);
		json.put("ver", ver);
		json.put("service", service);
		json.put("appNo", appNo);
		String jsonstr = json.toString();
		//生成签名内容
//		jsonstr="{\"content\":{\"subList\":[{\"content\":\"我不到半小时就配好了，赶紧配置吧\",\"mobile\":\"18922260815\"}],\"channel\":\"DIANSHANG_GUANGZHOUYIDONG\"},\"appNo\":\"ZJ206180\",\"service\":\"trade.sendMobileSms\",\"ver\":\"1.0\"}";
		String signature = genSign(jsonstr,key);
		//组装访问url
		StringBuffer serviceUrl=new StringBuffer(url);
		serviceUrl.append(service).append("/").append(ver).append("/");
		String smsurl = serviceUrl.toString();
		//组装条件
		Map<String,String> map=new HashMap<String, String>();
		map.put("appNo", appNo);
		map.put("service", service);
		map.put("ver", ver);
		map.put("msg", jsonstr);
		map.put("sign", signature);
		if (log.isDebugEnabled()) {
			log.debug(String.format("生成的请求参数为%s",map));
		}
		//发送请求
		HttpRequester httpRequester = new HttpRequester();
		String param = httpRequester.convert2formdataParam(map);
//		System.out.println(param);
		//param="sign=rvG%2B8C2goHJz9%2FaUAl9Ax%2B%2BAzwI%3D&appNo=ZJ206180&service=trade.sendMobileSms&ver=1.0&msg=%7B%22content%22%3A%7B%22subList%22%3A%5B%7B%22content%22%3A%22%E6%88%91%E4%B8%8D%E5%88%B0%E5%8D%8A%E5%B0%8F%E6%97%B6%E5%B0%B1%E9%85%8D%E5%A5%BD%E4%BA%86%EF%BC%8C%E8%B5%B6%E7%B4%A7%E9%85%8D%E7%BD%AE%E5%90%A7%22%2C%22mobile%22%3A%2218922260815%22%7D%5D%2C%22channel%22%3A%22DIANSHANG_GUANGZHOUYIDONG%22%7D%2C%22appNo%22%3A%22ZJ206180%22%2C%22service%22%3A%22trade.sendMobileSms%22%2C%22ver%22%3A%221.0%22%7D";
		
		String result = httpRequester.postRequest(smsurl, param);
		if (log.isDebugEnabled()) {
			log.debug(String.format("向中经汇通短信接口[%s]发送请求[%s]，得到结果为%s", smsurl,map,result));
		}
		if(org.apache.commons.lang.StringUtils.isBlank(result))
		{
			if (log.isDebugEnabled()) {
				log.debug(String.format("返回结果无内容"));
			}
			rm.setErr(0, "短信已发送，返回结果无内容");
			return rm;
		}
		JSONObject resultjs;
		try {
			resultjs = new JSONObject(result);
			Matcher matcher = java.util.regex.Pattern.compile("^0+$").matcher(resultjs.optString("state",""));
			if(!matcher.find()){
				//非成功
				rm.setErr(ValidateException.ERROR_SYSTEM_INTERNAL.getErrcode(), "短信发送失败");
			}
		} catch (Exception e) {
			log.error(String.format("返回结果非json格式"),e);
			rm.setErr(0, "短信已发送，返回结果解析出错");
			return rm;
		}
//		if (resultjs.has("content")) {
//			if (log.isDebugEnabled()) {
//				log.debug(resultjs.optString(content));
//			}
//		}
		

		return rm;
		
	}
	/**
	 * 生成签名
	 * @param jsonmessage
	 * @return
	 * @throws SignatureException 
	 */
	private String genSign(String jsonmessage,String key) throws SignatureException {
		String mingwen=key+jsonmessage+key;
		String ALGORITHM="SHA-1";
		//使用sha-1加密
		 try {
            MessageDigest messageDigest = MessageDigest.getInstance(ALGORITHM);
            messageDigest.update(mingwen.getBytes("utf8"));
            return Base64.encodeBase64String(messageDigest.digest());
        } catch (Exception e) {
            throw ValidateException.ERROR_SIGNATURE_GENERATE_SHA1;
        }
	}
	/**
	 * 组装发送内容
	 * @param content
	 * @param mobiles
	 * @return
	 * @throws JSONException 
	 */
	private JSONObject combineMsg(String content,String appname, String[] mobiles) throws JSONException {
		List<JSONObject> messages = new ArrayList<JSONObject>();
		for (int i = 0; i < mobiles.length; i++) {
			String mobile = mobiles[i];
			JSONObject msg = new JSONObject();
			msg.put("mobile",mobile);
			msg.put("content",content);
			messages.add(msg);
		}
        JSONObject contentJson = new JSONObject();
        contentJson.put("channel",appname);
        //contentJson.put("channel","DIANSHANG_GUANGZHOUYIDONG");
        contentJson.put("subList", messages);
		return contentJson;
	}
	
	
	public static void main(String[] args) throws Exception {
		System.out.println(new SmsSendService().genSign("{\"content\":{\"subList\":[{\"content\":\"我不到半小时就配好了，赶紧配置吧\",\"mobile\":\"18922260815\"}],\"channel\":\"DIANSHANG_GUANGZHOUYIDONG\"},\"appNo\":\"ZJ206180\",\"service\":\"trade.sendMobileSms\",\"ver\":\"1.0\"}", "wZ4q/r9o7633wbyd9w3WpA6RTO/C+BUN"));
		
		new SmsSendService().send("我不到半小时就配好了，赶紧配置吧", "18922260815");
		
	}
	
	
}
