package com.hummingbird.maaccount.controller;

import java.net.URLEncoder;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StopWatch;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hummingbird.common.controller.BaseController;
import com.hummingbird.common.util.Md5Util;
import com.hummingbird.common.util.PropertiesUtil;
import com.hummingbird.common.util.http.HttpRequester;
import com.hummingbird.common.util.json.JSONException;
import com.hummingbird.common.util.json.JSONObject;
import com.hummingbird.common.vo.ResultModel;
import com.hummingbird.common.vo.StatusCheckResult;
import com.hummingbird.maaccount.service.impl.SmsSendService;
import com.hummingbird.maaccount.vo.SmsSendVo;

/**
 * 通道处理
 * @author huangjiej_2
 * 2014年9月3日 下午6:20:29
 */
@Controller
@RequestMapping("/sms")
public class SmsSendController extends BaseController {
	private static final Log log = LogFactory.getLog(SmsSendController.class);

	@Autowired
	SmsSendService smssendSrv;
	
	static Object lock = new Object();
	
	static String url;
	static String user;
	static String password;
	
	/**
	 * 初始化
	 * @return
	 */
//	public WebServiceSoap initWs(){
//		if(webservice==null){
//			synchronized (lock) {
//				if(webservice==null){
//					PropertiesUtil propertiesUtil = new PropertiesUtil();
//					url=propertiesUtil.getProperty("smsWS");
//					user=propertiesUtil.getProperty("user");
//					password=Md5Util.Encrypt((user+propertiesUtil.getProperty("password")),null);
//					
//					if(StringUtils.isEmpty(url)){
//						WebServiceSoapProxy proxy = new WebServiceSoapProxy();
//						webservice = proxy.getWebServiceSoap();
//						
//					}
//					else{
//						WebServiceSoapProxy proxy = new WebServiceSoapProxy(url);
//						webservice = proxy.getWebServiceSoap();
//						
//					}
//				}
//			}
//		}
//		
//		return webservice;
//	}
	
	
	@RequestMapping("/send")
	public @ResponseBody Object send(@RequestBody SmsSendVo smssend) {
		log.debug("请求发送短信：" + smssend.toString());
		
		StopWatch sw = new StopWatch();
		ResultModel rm = new ResultModel();
		rm.setErrmsg("发送成功");
		try{
			sw.start();
//			ResultModel initrm = initRequest();
//			if(initrm!=null)
//			{
//				return initrm;
//			}
			//校验用户名和密码
			PropertiesUtil propertiesUtil = new PropertiesUtil();
			//TODO 这里没有办法找到合适的模板,发送不了
			rm.putAll(smssendSrv.send( smssend.getMobileNum(),smssend.getContent(),"ZJHT",SmsSendService.ACTION_NAME_SMSCODE));
			//rm.put("wsresult", result);
		}
		catch(Exception e){
			log.error("发送短信出错",e);
			rm.mergeException(e);
		}
		finally{
			try {
				sw.stop();
			} catch (IllegalStateException e) {
				e.printStackTrace();
			}
			log.debug("统计短信发送时间，方法为send,耗时(s)="+sw.getTotalTimeSeconds());
			return rm;
		}
		
	}

	

	/**
	 * 初始化
	 * @return
	 */
	private ResultModel initRequest() {
		ResultModel rm = new ResultModel();
		if(url==null){
			synchronized (lock) {
				if(url==null){
					PropertiesUtil propertiesUtil = new PropertiesUtil();
					url=propertiesUtil.getProperty("smsserver.smsWS");
					user=propertiesUtil.getProperty("smsserver.user");
					password=Md5Util.Encrypt((user+propertiesUtil.getProperty("smsserver.password"))).toUpperCase();
					
					if(StringUtils.isEmpty(url)){
						log.error("短信发送地址为空");
						rm.setErr(26101, "短信发送地址为空");
						return rm;
						
					}
				}
			}
		}
		return null;
	}


	
	
	/**
     * 状态报告
     * @return
     */
    @RequestMapping("/statuscheck")
    public @ResponseBody Object statusCheck(){
    	if (log.isDebugEnabled()) {
			log.debug(String.format("状态报告开始"));
		}
    	
    	PropertiesUtil propertiesUtil = new PropertiesUtil();
    	boolean checkreceive = "true".equals(propertiesUtil.getProperty("statuscheck.checkreceive"));
    	boolean checksend = "true".equals(propertiesUtil.getProperty("statuscheck.checksend"));
    	String errormsg = "";
    	StatusCheckResult statusCheckResult = new StatusCheckResult();
//    	if(!SmsReceiveService.isInit()){
//    		if (log.isDebugEnabled()) {
//	    		log.debug(String.format("本应用无CMPP接收"));
//	    	}
//    	}
//    	else{
    	if(checksend){
    		
    		if (log.isDebugEnabled()) {
    			log.debug(String.format("检查发送程序"));
    		}
    		HttpRequester request = new HttpRequester();
    		initRequest();
    		Map param =new HashMap();
    		param.put("sn", user);
    		param.put("pwd", password);
    		
    		String checkurl=propertiesUtil.getProperty("smsserver.smsWSCheck");
    		if(StringUtils.isEmpty(checkurl)){
    			if (log.isDebugEnabled()) {
    				log.debug(String.format("发送程序访问地址不存在，不发送"));
    			}
    		}
    		else{
    			//发送信息
    			String wsresultstr = request.sendPost(checkurl,param);
    			if (log.isDebugEnabled()) {
    				log.debug(String.format("发送程序返回%s",wsresultstr));
    			}
    			String sendstatus = "短信发送程序异常。";
    			if(wsresultstr.indexOf("{")!=-1){
    				if(wsresultstr.lastIndexOf("}")!=-1){
    					wsresultstr = wsresultstr.substring(wsresultstr.indexOf("{"),wsresultstr.lastIndexOf("}")+1);
    				}
    				
    				JSONObject jo;
    				try {
    					jo = new JSONObject(wsresultstr);
    					if("999".equals(jo.optString("Flag"))){
    						sendstatus="短信发送程序正常。";
    						
    					}
    				} catch (JSONException e) {
    					log.error(String.format("内容转json出错"),e);
    				}
    			}
    			//statusCheckResult.setErrmsg(errmsg);
    			errormsg+=sendstatus;
    			if (log.isDebugEnabled()) {
        			log.debug(String.format("检查发送程序="+errormsg));
        		}
    		}
    	}
    	statusCheckResult.setErrmsg(errormsg);
		
    	return statusCheckResult;
    }
}
