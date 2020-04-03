package com.hummingbird.maaccount.controller;


import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hummingbird.common.controller.BaseController;
import com.hummingbird.common.exception.DataInvalidException;
import com.hummingbird.common.exception.ValidateException;
import com.hummingbird.common.util.ValidateUtil;
import com.hummingbird.common.vo.ResultModel;
import com.hummingbird.commonbiz.service.IAuthenticationService;
import com.hummingbird.maaccount.entity.SMSMessage;
import com.hummingbird.maaccount.exception.MaAccountException;
import com.hummingbird.maaccount.mapper.SMSMessageMapper;
import com.hummingbird.maaccount.service.impl.SmsSendService;
import com.hummingbird.maaccount.util.NumRandom;
import com.hummingbird.maaccount.vo.GetSmsVo;
import com.hummingbird.maaccount.vo.QuerySMSMessageVO;
import com.hummingbird.maaccount.vo.ValidateSmsMessageVo;

@Controller
@RequestMapping("/messageHandle")
public class MessageController extends BaseController{
	
	@Autowired(required = true)
	private IAuthenticationService authService;
	
	@Autowired
	private SmsSendService smsSender;
	
	@Autowired
	private SMSMessageMapper sMSMessageMapper;
	
	private final long send_interval=60000;   //发送间隔最短时间
	
	private final long SMS_expir=300000;      //短信有效时间
	
	private final  static Log log = LogFactory.getLog(MessageController.class);
	
	@RequestMapping("/send")
	@ResponseBody
    public Object send(@RequestBody GetSmsVo getsmsvo){
		log.debug("发送短信验证码业务开始...");
		log.debug("发送短信验证码业务传入的参数为:"+getsmsvo);
		ResultModel rm = new ResultModel();
		rm.setBaseErrorCode(20188);
		rm.setErrmsg("发送短信验证码成功");
		
		try {
			
			ValidateUtil.validateMobile(getsmsvo.getMobileNum());
			// 校验签名
			authService.validateAuth(getsmsvo);
			//验证发送是否过于频繁
			validateSMSSendTime(getsmsvo.getMobileNum(), getsmsvo.getAppId());
			//生成随机数
			String randomCode=NumRandom.getRandom(4);
			//生成发送模版
			String content=createSMS_Message(randomCode);
			
			boolean flag=sendMessageToMobile(getsmsvo,content);
			
			if(!flag){
				rm.setErr(10000,"发送验证码失败");
			}else{
				//删除APPID和手机对应的所有验证码数据
				sMSMessageMapper.deleteByMobileAndAppId(getsmsvo.getMobileNum(), getsmsvo.getAppId());
				//保存到数据库
				sMSMessageMapper.insert(createSMSMessage(getsmsvo.getMobileNum(), getsmsvo.getAppId(), randomCode));
			}
			
		} catch (Exception e) {
			log.error(String.format("发送短信验证码[%s]失败", getsmsvo), e);
			rm.mergeException(e);
		}
		log.debug("发送短信验证码业务返回的结果为:"+rm);
		log.debug("发送短信验证码业务结束...");
    	return rm;
    }
	
	
	
	@RequestMapping("/validateSMS")
	@ResponseBody
	public Object validateSMS(@RequestBody ValidateSmsMessageVo validateSmsMessageVo){
		log.debug("验证短信验证码业务开始...");
		log.debug("验证短信验证码业务传入的参数为:"+validateSmsMessageVo);
		ResultModel rm = new ResultModel();
		rm.setBaseErrorCode(20189);
		rm.setErrmsg("验证短信验证码成功");
		
		try {
			
			ValidateUtil.validateMobile(validateSmsMessageVo.getMobileNum());
			// 校验签名
			authService.validateAuth(validateSmsMessageVo);
			// 验证短信
			validateSmsCode(validateSmsMessageVo);
			//验证通过的话删除该短信
			sMSMessageMapper.deleteByMobileAndAppId(validateSmsMessageVo.getMobileNum(), validateSmsMessageVo.getAppId());
		} catch (Exception e) {
			log.error(String.format("验证短信验证码[%s]失败", validateSmsMessageVo), e);
			rm.mergeException(e);
		}
		log.debug("验证短信验证码业务返回的结果为:"+rm);
		log.debug("验证短信验证码业务结束...");
    	return rm;
	}
	
	//发送短信到手机
	private boolean sendMessageToMobile(GetSmsVo getsmsvo,String content) throws Exception{
		ResultModel resultModel = smsSender.send(getsmsvo.getMobileNum(), content, getsmsvo.getAppId(), SmsSendService.ACTION_NAME_REGISTER);
		if(resultModel != null && resultModel.isSuccessed()){
			log.debug("验证码发送成功");
			return true;
		}
		log.debug("验证码发送失败,"+resultModel!=null?resultModel.getErrmsg():"短信访问出错");
		//发送失败
		return false;
	}
	
	//生成短信字符串
	private String createSMS_Message(String randomCode){
		String template = "您的短信验证码是%s，有效期%s分钟";
		return String.format(template, randomCode,SMS_expir/(1000*60));
	}
	
	//验证发送时间是否间隔过短
	private void validateSMSSendTime(String mobileNum,String appId) throws MaAccountException{
		int result=sMSMessageMapper.validateSMSInterval(createQuerySMSMessageVO(mobileNum,appId,null,send_interval));
		if(result>0){
			throw new MaAccountException(MaAccountException.ERR_USER_EXCEPTION,"发送短信过于频繁");
		}
	}
	
	//验证短信验证码
	private void validateSmsCode(ValidateSmsMessageVo validateSmsMessageVo) throws DataInvalidException{
		int result=sMSMessageMapper.validateSMS(createQuerySMSMessageVO(validateSmsMessageVo.getMobileNum(),validateSmsMessageVo.getAppId(),validateSmsMessageVo.getSmsCode(),SMS_expir));
		if(result==0){
			throw ValidateException.ERROR_MATCH_SMSCODE;
		}
	}
	
	private String getSendTime(long expir){
		long now=System.currentTimeMillis();
		long sendTime=now-expir;
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(sendTime));
	}
	
	//创建查询条件
	private QuerySMSMessageVO createQuerySMSMessageVO(String mobileNum,String appId,String smsCode,long expir){
		QuerySMSMessageVO QuerySMSMessageVO=new QuerySMSMessageVO();
		QuerySMSMessageVO.setAppId(appId);
		QuerySMSMessageVO.setMobileNum(mobileNum);
		QuerySMSMessageVO.setSmscode(smsCode);
		QuerySMSMessageVO.setSendtime(getSendTime(expir));
		return QuerySMSMessageVO;
	}
	
    private SMSMessage createSMSMessage(String mobileNum,String appId,String smsCode){
    	SMSMessage SMSMessage=new SMSMessage();
    	SMSMessage.setAppId(appId);
    	SMSMessage.setMobileNum(mobileNum);
    	SMSMessage.setSmscode(smsCode);
    	SMSMessage.setExpirein(Long.valueOf(SMS_expir/1000).intValue());
    	SMSMessage.setSendtime(new Date());
    	return SMSMessage;
    }
	
}
