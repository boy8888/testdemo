package com.hummingbird.maaccount.controller;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.PublicKey;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hummingbird.common.constant.CommonStatusConst;
import com.hummingbird.common.controller.BaseController;
import com.hummingbird.common.exception.DataInvalidException;
import com.hummingbird.common.exception.ValidateException;
import com.hummingbird.common.ext.AccessRequered;
import com.hummingbird.common.util.CertificateUtils;
import com.hummingbird.common.util.DESUtil;
import com.hummingbird.common.util.JsonUtil;
import com.hummingbird.common.util.Md5Util;
import com.hummingbird.common.util.PropertiesUtil;
import com.hummingbird.common.util.RequestUtil;
import com.hummingbird.common.util.SmsSenderUtil;
import com.hummingbird.common.util.ValidateUtil;
import com.hummingbird.common.vo.ResultModel;
import com.hummingbird.common.vo.ValidateResult;
import com.hummingbird.commonbiz.service.IAuthenticationService;
import com.hummingbird.commonbiz.service.ISmsCodeService;
import com.hummingbird.commonbiz.util.AuthCodeUtil;
import com.hummingbird.commonbiz.vo.BaseTransVO;
import com.hummingbird.commonbiz.vo.BaseUserToken;
import com.hummingbird.commonbiz.vo.UserToken;
import com.hummingbird.maaccount.entity.AppInfo;
import com.hummingbird.maaccount.entity.AppLog;
import com.hummingbird.maaccount.entity.BatchAddUserResult;
import com.hummingbird.maaccount.entity.BatchAddUserResultDetail;
import com.hummingbird.maaccount.entity.BindBankcard;
import com.hummingbird.maaccount.entity.RealNameAuth;
import com.hummingbird.maaccount.entity.SmsCode;
import com.hummingbird.maaccount.entity.Token;
import com.hummingbird.maaccount.entity.User;
import com.hummingbird.maaccount.entity.UserAccountCode;
import com.hummingbird.maaccount.entity.UserAttr;
import com.hummingbird.maaccount.exception.MaAccountException;
import com.hummingbird.maaccount.mapper.AppLogMapper;
import com.hummingbird.maaccount.mapper.BatchAddUserResultDetailMapper;
import com.hummingbird.maaccount.mapper.BatchAddUserResultMapper;
import com.hummingbird.maaccount.mapper.RealNameAuthMapper;
import com.hummingbird.maaccount.mapper.UserSmsCodeMapper;
import com.hummingbird.maaccount.service.AppInfoService;
import com.hummingbird.maaccount.service.BindBankCardService;
import com.hummingbird.maaccount.service.ITokenService;
import com.hummingbird.maaccount.service.PaymentService;
import com.hummingbird.maaccount.service.UserAttrService;
import com.hummingbird.maaccount.service.impl.SmsSendService;
import com.hummingbird.maaccount.service.impl.UserService;
import com.hummingbird.maaccount.util.AccountFactory;
import com.hummingbird.maaccount.util.BatchProcessReporter;
import com.hummingbird.maaccount.util.IndexObject;
import com.hummingbird.maaccount.util.LoginType;
import com.hummingbird.maaccount.util.NumRandom;
import com.hummingbird.maaccount.util.OrgType;
import com.hummingbird.maaccount.util.PospEncyptUtil;
import com.hummingbird.maaccount.util.SmsCodeValidateUtil;
import com.hummingbird.maaccount.util.synuserinfo.SynUserCenterData;
import com.hummingbird.maaccount.vo.AuthGetPayCodeStatusVO;
import com.hummingbird.maaccount.vo.BankCardVO;
import com.hummingbird.maaccount.vo.BatchAddUserTransVO;
import com.hummingbird.maaccount.vo.BindVO;
import com.hummingbird.maaccount.vo.GetSmsVo;
import com.hummingbird.maaccount.vo.JifenOrderVO;
import com.hummingbird.maaccount.vo.LoginByPasswdVo;
import com.hummingbird.maaccount.vo.LoginVo;
import com.hummingbird.maaccount.vo.LoginWithPaymentCodeVO;
import com.hummingbird.maaccount.vo.PaymentCodeSettingVO;
import com.hummingbird.maaccount.vo.RedPaperOrderVO;
import com.hummingbird.maaccount.vo.RegistByPhoneOnlyVO;
import com.hummingbird.maaccount.vo.RegisterVO;
import com.hummingbird.maaccount.vo.ResetPasswordVO;
import com.hummingbird.maaccount.vo.ResetPasswordWithSmscodeVO;
import com.hummingbird.maaccount.vo.ResetPaymentCodeBySmsCodeVO;
import com.hummingbird.maaccount.vo.SpendJifenOrderVO;
import com.hummingbird.maaccount.vo.SpendRedPaperOrderVO;
import com.hummingbird.maaccount.vo.TransOrderVO2;
import com.hummingbird.maaccount.vo.UnbindCardVO;
import com.hummingbird.maaccount.vo.UnbindVO;
import com.hummingbird.maaccount.vo.UndoRedPaperTransOrderVO;
import com.hummingbird.maaccount.vo.UpdateUserInfoVO;
import com.hummingbird.maaccount.vo.UploadPictureVO;
import com.hummingbird.maaccount.vo.UploadVO;
import com.hummingbird.maaccount.vo.UserAttrTransVO;
import com.hummingbird.maaccount.vo.VerifyPaymentCodeDESVO;
import com.hummingbird.maaccount.vo.VerifyPaymentCodeMD5BaseVO;
import com.hummingbird.maaccount.vo.VerifyTokenTransVo;
import com.hummingbird.maaccount.vo.VerifyTokenVo;

/**
 * 营销帐户，登录相关控制器
 * 
 * @author huangjiej_2 2014年11月10日 下午11:42:07
 */
@Controller
@RequestMapping("/userAuth")
public class AuthController extends BaseController {

	// @Autowired(required = true)
	// private IOrderPayService orderPayService;
	@Autowired(required = true)
	private AppInfoService appService;
	@Autowired(required = true)
	private UserService userSrv;
	@Autowired(required = true)
	private IAuthenticationService authService;
	@Autowired(required = true)
	private ISmsCodeService smsService;
	@Autowired(required = true)
	private ITokenService tokensrv;

	@Autowired(required = true)
	private UserSmsCodeMapper smscodemapper;
	@Autowired(required = true)
	private PaymentService paymentSrv;
	@Autowired(required = true)
	private BindBankCardService bankSer;
	@Autowired
	protected RealNameAuthMapper realNamedao;
	@Autowired
	protected AppLogMapper logdao;
	@Autowired
	protected UserAttrService userAttrSrv;
	@Autowired
	protected BatchAddUserResultDetailMapper batchAdduserResultDetailDao;
	@Autowired
	protected BatchAddUserResultMapper batchAdduserResultDao;
	@Autowired
	protected SmsSendService smsSender;
	
	/**
	 * 日志的本地线程
	 */
	protected static ThreadLocal<AppLog> applogTL = new ThreadLocal<AppLog>();
	

	/**
	 * 向服务器请求下发验证码
	 * 
	 * @param getsmsvo
	 * @return
	 */
	@RequestMapping("/get_smscode")
	public @ResponseBody Object getSmsCode(@RequestBody GetSmsVo getsmsvo) {
		if (log.isDebugEnabled()) {
			log.debug("向服务器请求下发验证码：" + getsmsvo);
		}
		// 捕捉所有异常,不要由于有异常而不返回信息
		ResultModel rm = new ResultModel();
		int baseerrcode = 20100;
		rm.setBaseErrorCode(baseerrcode);
		rm.setErrmsg("发送短信验证码成功");
		
//		GetSmsVo getsmsvo
		try {
			ValidateUtil.validateMobile(getsmsvo.getMobileNum());
			// 校验token
			Object validateAuth = authService.validateAuth(getsmsvo);
			if (log.isDebugEnabled()) {
				log.debug("检验通过，发送验证码");
			}
			// 检查手机
			// User user = userDao.selectByMobile(getsmsvo.getMobileNum());
			// ValidateUtil.assertNull(user, "手机号码未注册", 4111);
			validateSmssend(getsmsvo.getAppId(),getsmsvo.getMobileNum());
			UserToken createToken = smsService.createToken(getsmsvo.getApp()
					.getAppId(), getsmsvo.getMobileNum(), 4);
			String content = AuthCodeUtil.getAuthCodeByTemplate(
					createToken.getToken(), "sms.authcode");
			// 调用webservice 发送模板
			if (log.isDebugEnabled()) {
				log.debug("发送手机验证码请求:" + content);
			}

			try {
				PropertiesUtil pu = new PropertiesUtil();
				boolean deleteaftervalidate = pu.getBoolean("smscode.fortest");
				if(deleteaftervalidate){
					if (log.isDebugEnabled()) {
						log.debug(String.format("测试开启，不发送验证码到手机"));
					}
				}
				else{
//					SmsSenderUtil.sendSms(getsmsvo.getMobileNum(), content);
					ResultModel resultModel = smsSender.send(getsmsvo.getMobileNum(),content, getsmsvo.getAppId(),SmsSendService.ACTION_NAME_SMSCODE);
					boolean sendresult = resultModel!=null&&resultModel.isSuccessed();
					if (log.isDebugEnabled()) {
						if(sendresult){
							log.debug("验证码发送成功");
						}
						else{
							log.debug("验证码发送失败,"+resultModel!=null?resultModel.getErrmsg():"短信访问出错");
						}
					}
					if(!sendresult){
						rm.setErr(10000,"发送验证码失败"+ resultModel!=null?resultModel.getErrmsg():"短信访问出错");
					}
				}

			} catch (Exception e) {
				log.error("发送验证码出错:", e);
//				rm.setErr(22101, "发送验证码出错,其它原因");
				rm.mergeException(e);
			}

		} catch (Exception e1) {
			log.error(String.format("发送短信验证码[%s]，处理失败", getsmsvo), e1);
			rm.mergeException(e1);
		} finally {
			if (log.isDebugEnabled()) {
				log.debug("向服务器请求下发验证码完成");
			}
		}
		return rm;
	}
	/**
	 * 向服务器请求下发验证码
	 * 当支付时，会通过这个接口获取验证码，验证码应该每次都不一样，数据库中保存一条记录每手机
	 * @param getsmsvo
	 * @return
	 */
	@RequestMapping("/get_accountcode")
	@AccessRequered(methodName="向服务器请求下发帐户验证码")
	public @ResponseBody Object getAccountCode(@RequestBody GetSmsVo getsmsvo) {
		if (log.isDebugEnabled()) {
			log.debug("向服务器请求下发帐户验证码：" + getsmsvo);
		}
		// 捕捉所有异常,不要由于有异常而不返回信息
		ResultModel rm = new ResultModel();
		int baseerrcode = 20200;
		rm.setBaseErrorCode(baseerrcode);
		rm.setErrmsg("向服务器请求下发帐户验证码成功");
		try {
			ValidateUtil.validateMobile(getsmsvo.getMobileNum());
			// 校验token
			Object validateAuth = authService.validateAuth(getsmsvo);
			if (log.isDebugEnabled()) {
				log.debug("检验通过，发送验证码");
			}
			UserAccountCode genAccountCode = paymentSrv.genAccountCode(getsmsvo);
			String content = AuthCodeUtil.getAuthCodeByTemplate(
					genAccountCode.getSmscode(), "accountcode.authcode");
			content = content.replaceAll("\\$\\{time\\}", DateFormatUtils.format(new Date(), "HH:mm"));
			// 调用webservice 发送模板
			if (log.isDebugEnabled()) {
				log.debug("发送手机验证码至"+getsmsvo.getMobileNum()+"请求:" + content);
			}
			
			try {
				PropertiesUtil pu = new PropertiesUtil();
				boolean deleteaftervalidate = pu.getBoolean("accountcode.fortest");
				if(deleteaftervalidate){
					if (log.isDebugEnabled()) {
						log.debug(String.format("测试开启，不发送帐户验证码到手机"));
					}
				}
				else{
					ResultModel sendrm = smsSender.send(getsmsvo.getMobileNum(),content, getsmsvo.getAppId(), SmsSendService.ACTION_NAME_SMSCODE);
					boolean sendresult = sendrm!=null&&sendrm.isSuccessed();
					String sendresultmsg = sendrm!=null?sendrm.getErrmsg():"短信访问出错";

					if(!sendresult){
						rm.setErr(10000,"发送验证码失败"+ sendresultmsg);
					}
					if (log.isDebugEnabled()) {
						log.debug("向服务器请求下发帐户验证码"+(sendresult?"成功":"失败"));
					}
				}
				
			} catch (Exception e) {
				log.error("发送验证码出错:", e);
				rm.setErr(baseerrcode, "向服务器请求下发帐户验证码出错,其它原因");
			}
			
		} catch (Exception e1) {
			log.error(String.format("向服务器请求下发帐户验证码[%s]，处理失败", getsmsvo), e1);
			rm.mergeException(e1);
		} finally {
//			if (log.isDebugEnabled()) {
//				log.debug("向服务器请求下发帐户验证码完成");
//			}
		}
		return rm;
	}
	/**
	 * 验证用户支付密码DES
	 * @return
	 */
	@RequestMapping(value="/verify_paymentCodeDES",method=RequestMethod.POST)
	@AccessRequered(methodName="验证用户支付密码DES")
	public @ResponseBody Object verifyPaymentCodeDES(HttpServletRequest request) {
		/*{
		    "app":{"appId":"dianziquan","timeStamp":"TIMESTAMP", "nonce":"NONCE","signature":"MD5(appId+appKey+nonce+timeStamp)"},
		    "verifly":{
		        "paymentCodeDES":"DES(1234567)"
		    }
		} */ 
		ResultModel rm = new ResultModel();
		int baseerrcode = 27200;
		rm.setBaseErrorCode(baseerrcode);
		rm.setErrmsg("验证用户支付密码成功");
		VerifyPaymentCodeDESVO transorder;
		try {
			String jsonstr = RequestUtil.getRequestPostData(request);
			request.setAttribute("rawjson", jsonstr);
			transorder = RequestUtil.convertJson2Obj(jsonstr, VerifyPaymentCodeDESVO.class);
			if (log.isDebugEnabled()) {
				log.debug(String.format("验证用户支付密码DES,参数为%s",transorder));
			}
		} catch (Exception e) {
			log.error(String.format("获取订单参数出错"),e);
			rm.mergeException(ValidateException.ERROR_PARAM_FORMAT_ERROR.cloneAndAppend(null, "订单参数"));
			return rm;
		}
		if (log.isDebugEnabled()) {
			log.debug("验证用户支付密码");
		}
		// 捕捉所有异常,不要由于有异常而不返回信息
		
		try{
			logWithBegin(transorder.getApp().getAppId(),request);
			// 检验手机
			ValidateUtil.validateMobile(transorder.getMobileNum());
			User user = userSrv.getUserByMobile(transorder.getMobileNum());
			if(user==null){
				if (log.isDebugEnabled()) {
					log.debug(String.format("根据手机号%s查询用户，用户不存在，无法获取支付密码",transorder.getMobileNum()));
				}
				throw ValidateException.ERROR_EXISTING_USER_NOT_EXISTS;
			}
			//获取Appkey
			Map validateAuth = (Map) authService.validateAuth(transorder);
			String appkey = ObjectUtils.toString(validateAuth.get("appKey"));
			//对密码进行解密,
			boolean paymentencrypt = new PropertiesUtil().getBoolean("set_paymentcode.payment.encrypt",false);
			//尝试进行解密
			String decpcdes="";//现在是明文
			if(StringUtils.isNotBlank(transorder.getVerifly().getPaymentCodeDES())){
				try {
					decpcdes = DESUtil.decodeDES(transorder.getVerifly().getPaymentCodeDES(), appkey);
				} catch (Exception e) {
					log.error(String.format("PaymentCodeDES解密出错"),e);
					if(paymentencrypt){
						throw ValidateException.ERROR_PARAM_FORMAT_ERROR.clone(e,"支付密码DES解密错误");
					}
				}
			}
			//String dec=PospEncyptUtil.encypt(decpcdes, transorder.getMobileNum());
			String dec = Md5Util.Encrypt(decpcdes);
			if(!dec.equals(user.getPaymentCodeDES())){
				if (log.isDebugEnabled()) {
					log.debug(String.format("验证用户支付密码失败，密码不符",transorder.getVerifly().getPaymentCodeDES()));
				}
				throw ValidateException.ERROR_MATCH_PASSWORD;
			}
		}catch(Exception e1){
			log.error(String.format("验证用户支付密码DES[%s]，处理失败", transorder), e1);
			rm.mergeException(e1);
		} finally {
			logWithFinish(rm);
		}
		
		return rm;
	}
	/**
	 * 重置用户支付密码DES
	 * @return
	 */
	@RequestMapping(value="/resetPaymentCodeBySmsCode",method=RequestMethod.POST)
	@AccessRequered(methodName="重置用户支付密码DES")
	public @ResponseBody Object resetPaymentCodeBySmsCode(@RequestBody ResetPaymentCodeBySmsCodeVO transorder) {
		/*{
		    "app":{
		        "appId":"zjhtwallet","timeStamp":"TIMESTAMP","nonce":"NONCE","signature":"SIGNATURE"
		    },
		    "reset":{
		        "mobileNum":"13912345678","smsCode":"123456", "newPaymentCodeDES":"DES(12345678)"
		    }
		}  */ 
		if (log.isDebugEnabled()) {
			log.debug("重置用户支付密码");
		}
		// 捕捉所有异常,不要由于有异常而不返回信息
		ResultModel rm = new ResultModel();
		int baseerrcode = 27300;
		rm.setBaseErrorCode(baseerrcode);
		rm.setErrmsg("重置用户支付密码成功");
		try{
			// 检验手机
			ValidateUtil.validateMobile(transorder.getMobileNum());
			
			//获取Appkey
			Map validateAuth = (Map) authService.validateAuth(transorder);
			String appkey = ObjectUtils.toString(validateAuth.get("appKey"));
			User user = userSrv.getUserByMobile(transorder.getMobileNum());
			if(user==null){
				if (log.isDebugEnabled()) {
					log.debug(String.format("根据手机号%s查询用户，用户不存在",transorder.getMobileNum()));
				}
				throw ValidateException.ERROR_EXISTING_USER_NOT_EXISTS;
			}
			//校验验证码
			boolean authCodeSuccess = validateSMSCode(transorder.getApp()
					.getAppId(), transorder.getMobileNum(),transorder.getReset().getSmsCode());

			if (!authCodeSuccess) {
				if (log.isDebugEnabled()) {
					log.debug(String.format("验证码不正确,%s", transorder));
				}
//				rm.setErr(ValidateException.ERRCODE_SIGNATURE_FAIL, "短信验证码不正确");
//				rm.setBaseErrorCode(2000);
				rm.mergeException(ValidateException.ERROR_MATCH_SMSCODE);
				return rm;
			}
			//对密码进行解密,
			boolean paymentencrypt = new PropertiesUtil().getBoolean("set_paymentcode.payment.encrypt",false);
			//尝试进行解密
			if(StringUtils.isNotBlank(transorder.getReset().getNewPaymentCodeDES())){
				try {
					String decpcdes = DESUtil.decodeDES(transorder.getReset().getNewPaymentCodeDES(), appkey);
					//String enc=PospEncyptUtil.encypt(decpcdes, transorder.getMobileNum());
					//要求对支付密码进行md5加密
					
					user.setPaymentCodeDES(decpcdes);
					user.setUpdateTime(new Date());
					userSrv.updateUser(user);
				} catch (Exception e) {
					log.error(String.format("PaymentCodeDES解密出错"),e);
					if(paymentencrypt){
						throw ValidateException.ERROR_PARAM_FORMAT_ERROR.clone(e,"支付密码DES解密错误");
					}
				}
			}
			
			
			
		}catch(Exception e1){
			log.error(String.format("验证用户支付密码DES[%s]，处理失败", transorder), e1);
			rm.mergeException(e1);
		} finally {
			logWithFinish(rm);
		}
		
		return rm;
	}
	/**
	 * 修改用户支付密码DES接口(通过短信验证码和登录密码)
	 * @return
	 */
	@RequestMapping(value="/resetPaymentCodeBySmsCodeAndPassword",method=RequestMethod.POST)
	@AccessRequered(methodName="修改用户支付密码DES接口(通过短信验证码和登录密码)")
	public @ResponseBody Object resetPaymentCodeBySmsCodeAndPassword(HttpServletRequest request) {
		/*{
		    "app":{
		        "appId":"zjhtwallet","timeStamp":"TIMESTAMP","nonce":"NONCE","signature":"SIGNATURE"
		    },
		    "reset":{
		        "mobileNum":"13912345678","smsCode":"123456", "newPaymentCodeDES":"DES(12345678)"
		    }
		}  */ 
		ResetPaymentCodeBySmsCodeVO transorder;
		ResultModel rm = new ResultModel();
		String messagebase = "修改用户线下支付密码";
		int baseerrcode=27300;
		rm.setBaseErrorCode(baseerrcode);
		rm.setErrmsg(messagebase+"成功");
		try {
			String jsonstr = RequestUtil.getRequestPostData(request);
			request.setAttribute("rawjson", jsonstr);
			transorder = RequestUtil.convertJson2Obj(jsonstr, ResetPaymentCodeBySmsCodeVO.class);
			if (log.isDebugEnabled()) {
				log.debug(String.format("%s,参数为%s",messagebase,transorder));
			}
		} catch (Exception e) {
			log.error(String.format("获取订单参数出错"),e);
			rm.mergeException(ValidateException.ERROR_PARAM_FORMAT_ERROR.cloneAndAppend(null, "订单参数"));
			return rm;
		}
		if (log.isDebugEnabled()) {
			log.debug("修改用户支付密码DES接口(通过短信验证码和登录密码)");
		}
		try{
			if(StringUtils.isBlank(transorder.getReset().getNewPaymentCodeDES())){
				log.error(String.format("新密码为空"));
				throw ValidateException.ERROR_PARAM_NULL.clone(null,"新密码为空");
				
			}
			// 检验手机
			ValidateUtil.validateMobile(transorder.getMobileNum());
			
			//获取Appkey
			Map validateAuth = (Map) authService.validateAuth(transorder);
			String appkey = ObjectUtils.toString(validateAuth.get("appKey"));
			User user = userSrv.getUserByMobile(transorder.getMobileNum());
			if(user==null){
				if (log.isDebugEnabled()) {
					log.debug(String.format("根据手机号%s查询用户，用户不存在",transorder.getMobileNum()));
				}
				throw ValidateException.ERROR_EXISTING_USER_NOT_EXISTS;
			}
			//校验验证码
			boolean authCodeSuccess = validateSMSCode(transorder.getApp()
					.getAppId(), transorder.getMobileNum(),transorder.getReset().getSmsCode());
			
			if (!authCodeSuccess) {
				if (log.isDebugEnabled()) {
					log.debug(String.format("验证码不正确,%s", transorder));
				}
//				rm.setErr(ValidateException.ERRCODE_SIGNATURE_FAIL, "短信验证码不正确");
//				rm.setBaseErrorCode(2000);
				rm.mergeException(ValidateException.ERROR_MATCH_SMSCODE);
				return rm;
			}
			//比较登录密码
			String cryptpassword = transorder.getReset().getPassword();
			String password = DESUtil.decodeDESwithCBC(cryptpassword, appkey);
			if(!StringUtils.equals(password, user.getPassword())){
				log.error(String.format("用户登录密码不正确,加密密码为%s", cryptpassword));
				throw ValidateException.ERROR_MATCH_PASSWORD;
			}
			//对密码进行解密,
//			boolean paymentencrypt = new PropertiesUtil().getBoolean("set_paymentcode.payment.encrypt",false);
			//尝试进行解密
				try {
					String decpcdes = DESUtil.decodeDESwithCBC(transorder.getReset().getNewPaymentCodeDES(), appkey);
					//String enc=PospEncyptUtil.encypt(decpcdes, transorder.getMobileNum());
					user.setPaymentCodeDES(decpcdes);
					user.setUpdateTime(new Date());
					userSrv.updateUser(user);
				} catch (Exception e) {
					log.error(String.format("PaymentCodeDES解密出错"),e);
//					if(paymentencrypt){
						throw ValidateException.ERROR_PARAM_FORMAT_ERROR.clone(e,"支付密码解密错误");
//					}
				}
		}catch(Exception e1){
			log.error(String.format("修改用户线下支付密码处理失败"), e1);
			rm.mergeException(e1);
		} finally {
			logWithFinish(rm);
		}
		
		return rm;
	}
	/**
	 * 设置账户支付密码
	 * 账户支付密码会设置到user表中
	 * @param getsmsvo
	 * @return
	 */
	@RequestMapping(value="/set_paymentcode",method=RequestMethod.POST)
	@AccessRequered(methodName="设置账户支付密码")
	public @ResponseBody Object setPaymentCode(HttpServletRequest request) {
		// 捕捉所有异常,不要由于有异常而不返回信息
		ResultModel rm = new ResultModel();
		int baseerrcode = 20300;
		rm.setBaseErrorCode(baseerrcode);
		rm.setErrmsg("设置账户支付密码成功");
		PaymentCodeSettingVO transorder;
		try {
			String jsonstr = RequestUtil.getRequestPostData(request);
			request.setAttribute("rawjson", jsonstr);
			transorder = RequestUtil.convertJson2Obj(jsonstr, PaymentCodeSettingVO.class);
		} catch (Exception e) {
			log.error(String.format("设置账户支付参数出错"),e);
			rm.mergeException(ValidateException.ERROR_PARAM_FORMAT_ERROR.cloneAndAppend(null, "设置账户支付参数"));
			return rm;
		}
		PaymentCodeSettingVO paymentcode = transorder;
		try {
			logWithBegin(transorder.getAppId(),request);
			ValidateUtil.validateMobile(paymentcode.getMobileNum());
			// 校验token
			Map validateAuth = (Map) authService.validateAuth(paymentcode);
			String appkey = ObjectUtils.toString(validateAuth.get("appKey"));
			if (log.isDebugEnabled()) {
				log.debug("验证帐户验证码");
			}
			UserToken ut=new BaseUserToken(paymentcode.getAppId(),paymentcode.getMobileNum(),paymentcode.getAccountCode());
			ValidateResult vr = paymentSrv.validateAccountCodeTrue(ut);
			if(vr==null||!vr.isValidate())
			{
				throw ValidateException.ERROR_MATCH_VALIDATECODE.clone(null,"用户验证码不正确");
			}
			if (log.isDebugEnabled()) {
				log.debug("开始保存支付密码");
			}
			User user = userSrv.getUserByMobile(paymentcode.getMobileNum());
			if(user==null){
				if (log.isDebugEnabled()) {
					log.debug(String.format("根据手机号%s查询用户，用户不存在，无法设置支付密码",paymentcode.getMobileNum()));
				}
				throw ValidateException.ERROR_EXISTING_USER_NOT_EXISTS;
			}
			//对密码进行解密,
			boolean paymentencrypt = new PropertiesUtil().getBoolean("set_paymentcode.payment.encrypt",false);
			//尝试进行解密
			if(StringUtils.isNotBlank(paymentcode.getPaymentCodeMD5())){
				try {
					String decpcmd5 = DESUtil.decodeDES(paymentcode.getPaymentCodeMD5(), appkey);
					paymentcode.setPaymentCodeMD5(decpcmd5);
				} catch (Exception e) {
					log.error(String.format("PaymentCodeMD5解密出错"),e);
					if(paymentencrypt){
						throw ValidateException.ERROR_PARAM_FORMAT_ERROR.clone(e,"支付密码MD5解密错误");
					}
				}
			}
			if(StringUtils.isNotBlank(paymentcode.getPaymentCodeDES())){
				try {
					String decpcdes = DESUtil.decodeDES(paymentcode.getPaymentCodeDES(), appkey);
					paymentcode.setPaymentCodeDES(decpcdes);
				} catch (Exception e) {
					log.error(String.format("PaymentCodeDES解密出错"),e);
					if(paymentencrypt){
						throw ValidateException.ERROR_PARAM_FORMAT_ERROR.clone(e,"支付密码DES解密错误");
					}
				}
			}
			paymentSrv.savePaymentCode(user,paymentcode);
			
			
		} catch (Exception e1) {
			log.error(String.format("设置账户支付密码[%s]，处理失败", paymentcode), e1);
			rm.mergeException(e1);
		} finally {
			logWithFinish(rm);
		}
		return rm;
	}

	/**
	 * sdk注册手机号（默认登录方式为手机号loginType=MOBILE）
	 * 
	 * @param getsmsvo
	 * @return
	 */
	@RequestMapping("/login")
	public @ResponseBody Object login(@RequestBody LoginVo loginvo) {
		// {"appId":"smsmarketing","timeStamp":"TIMESTAMP",
		// "nonce":"NONCE","signature":"MD5(appId+appKey+nonce+timeStamp)",
		// "loginInfo":{"mobileNum":"13912345678","smsCode":"223344"},
		// "deviceInfo":{"mac":"3ere:eee:3434:34434","deviceId":"设备标识","imsi":"SIM卡设备号"}}
		if (log.isDebugEnabled()) {
			log.debug("手机号码登录：" + loginvo);
		}
		// 捕捉所有异常,不要由于有异常而不返回信息
		ResultModel rm = new ResultModel();
		int baseerrcode = 20400;
		rm.setBaseErrorCode(baseerrcode);
		rm.setErrmsg("手机号码登录成功");
		try {
			// 检验手机
			ValidateUtil.validateMobile(loginvo.getMobileNum());
			User user = userSrv.getUserByMobile(loginvo.getMobileNum());
			// if(user!=null)
			// {
			// if (log.isDebugEnabled()) {
			// log.debug(String.format("手机号码%s已注册为用户%s[%s]所用",loginvo.getMobileNum(),user.getName(),user.getUserId()));
			// }
			// throw ValidateException.ERROR_EXISTING_USER_EXISTS;
			// }
			// ValidateUtil.assertNull(user, "手机号码已注册", 4121);
			// 校验签名
			Object afterauth = authService.validateAuth(loginvo);
			if (log.isDebugEnabled()) {
				log.debug("检验签名通过，验证验证码");
			}
			// 这里不使用原来的代码，因为smscode方法改变了
			// boolean authCodeSuccess =
			// AuthCodeUtil.validateAuthCode(loginvo.getApp().getAppId(),
			// loginvo.getLogin().getMobileNum(),loginvo.getLogin().getSmsCode());
			boolean authCodeSuccess = validateSMSCode(loginvo.getApp()
					.getAppId(), loginvo.getLogin().getMobileNum(), loginvo
					.getLogin().getSmsCode());

			if (!authCodeSuccess) {
				if (log.isDebugEnabled()) {
					log.debug(String.format("验证码不正确,%s", loginvo));
				}
//				rm.setErr(ValidateException.ERRCODE_SIGNATURE_FAIL, "短信验证码不正确");
//				rm.setBaseErrorCode(2000);
				rm.mergeException(ValidateException.ERROR_MATCH_SMSCODE);
				return rm;
			}
			if (log.isDebugEnabled()) {
				log.debug(String.format("校验通过，现在为手机号码%s创建帐户",
						loginvo.getMobileNum()));
			}
			if (user == null) {
				// 创建用户
				user = new User();
				user.setLoginType("MOBILE");
				user.setOrgType("PERSONAL");
				user.setInsertTime(new Date());
				user.setMobilenum(loginvo.getMobileNum());
				userSrv.createUser(user,loginvo.getAppId());
			}
			UserToken selectToken = tokensrv.getOrCreateToken(loginvo.getApp()
					.getAppId(), user.getUserId());
			rm.put("token", selectToken.getToken());
			rm.put("expireIn", selectToken.getExpirein());
//			UserToken selectToken = tokensrv.queryToken(loginvo.getApp()
//					.getAppId(), user.getUserId());
//			if (selectToken == null) {
//				// 如果已记录就不处理
//				UserToken createToken = tokensrv.createToken(loginvo.getApp()
//						.getAppId(), user.getUserId());
//				// 返回token和信用信息
//				rm.put("token", createToken.getToken());
//				rm.put("expireIn", createToken.getExpirein());
//			} else {
//				tokensrv.updateExpireIn(selectToken);
//				rm.put("token", selectToken.getToken());
//				rm.put("expireIn", selectToken.getExpirein());
//			}
			// 创建帐户，避免新开其它帐户时，旧用户没有创建该帐户而造成问题
			AccountFactory.createAccounts(user.getUserId());

		} catch (Exception e1) {
			log.error(String.format("手机号码登录出错[%s]", loginvo), e1);
			rm.mergeException(e1);
		} finally {
			if (log.isDebugEnabled()) {
				log.debug("手机号码登录完成");
			}
		}
		return rm;
	}
	
	/**
	 * 注册接口
	 * 
	 * @param getsmsvo
	 * @return
	 */
	@SuppressWarnings("unused")
	@RequestMapping("/register")
	@AccessRequered(methodName="用户注册")
	public @ResponseBody Object register(@RequestBody RegisterVO loginvo) {
		//{"app":{"appId":"zjhtwallet","timeStamp":"TIMESTAMP", "nonce":"NONCE","signature":"SIGNATURE"},
		//"login":{"mobileNum":"13912345678","smsCode":"4567","password":"1234","paymentCodeMD5":"4321"}} 
		if (log.isDebugEnabled()) {
			log.debug("新用户注册：" + loginvo);
		}
		// 捕捉所有异常,不要由于有异常而不返回信息
		ResultModel rm = new ResultModel();
		int baseerrcode = 20500;
		rm.setBaseErrorCode(baseerrcode);
		rm.setErrmsg("新用户注册成功");
		try {
			Object afterauth = authService.validateAuth(loginvo);
			// 检验手机
			ValidateUtil.validateMobile(loginvo.getMobileNum());
//			 ValidateUtil.assertNull(user, "手机号码已注册", 4121);
			// 校验签名
			if (log.isDebugEnabled()) {
				log.debug("检验签名通过，验证验证码");
			}
			boolean authCodeSuccess = validateSMSCode(loginvo.getApp()
					.getAppId(), loginvo.getLogin().getMobileNum(), loginvo
					.getLogin().getSmsCode());
			
			if (!authCodeSuccess) {
				if (log.isDebugEnabled()) {
					log.debug(String.format("验证码不正确,%s", loginvo));
				}
				rm.mergeException(ValidateException.ERROR_MATCH_SMSCODE);
				return rm;
			}
			User user = userSrv.getUserByMobile(loginvo.getMobileNum());
			 if(user!=null)
			 {
				 if (log.isDebugEnabled()) {
					 log.debug(String.format("手机号码%s已注册为用户%s[%s]所用",loginvo.getMobileNum(),user.getName(),user.getUserId()));
				 }
				//modify by john 201509221547 如果用户已存在,但用户属性存在值,需要进一步判断
				 boolean setattrsuccess = try2setAttr(user,loginvo.getLogin().getAttrs());
				 if(!setattrsuccess){
					 
					 throw ValidateException.ERROR_EXISTING_USER_EXISTS;
				 }
				 else{
					 if (log.isDebugEnabled()) {
						log.debug(String.format("为用户添加了用户属性,不报用户已存在异常"));
					}
				 }
				 
			 }
			 else{
				 if (log.isDebugEnabled()) {
					 log.debug(String.format("校验通过，现在为手机号码%s创建帐户",
							 loginvo.getMobileNum()));
				 }
				// 创建用户
					user = new User();
					user.setLoginType("MOBILE");
		            user.setOrgType("PERSONAL");
					user.setInsertTime(new Date());
					user.setMobilenum(loginvo.getMobileNum());
					user.setPassword(loginvo.getLogin().getPassword());
					if(StringUtils.isNotBlank(loginvo.getLogin().getPaymentCodeMD5())){
						user.setPaymentcodemd5(loginvo.getLogin().getPaymentCodeMD5());
					}
					userSrv.createUser(user,loginvo.getAppId());
					//测试用户属性
					if(loginvo.getLogin().getAttrs()!=null&&loginvo.getLogin().getAttrs().length>0){
						userAttrSrv.addAttr(user.getUserId(),loginvo.getLogin().getAttrs());
					}
			 }

			UserToken selectToken = tokensrv.getOrCreateToken(loginvo.getApp()
					.getAppId(), user.getUserId());
			rm.put("token", selectToken.getToken());
			rm.put("expireIn", selectToken.getExpirein());
			rm.put("orgType", user.getOrgType());
//			UserToken selectToken = tokensrv.queryToken(loginvo.getApp()
//					.getAppId(), user.getUserId());
//			if (selectToken == null) {
//				// 如果已记录就不处理
//				UserToken createToken = tokensrv.createToken(loginvo.getApp()
//						.getAppId(), user.getUserId());
//				// 返回token和信用信息
//				rm.put("token", createToken.getToken());
//				rm.put("expireIn", createToken.getExpirein());
//			} else {
//				tokensrv.updateExpireIn(selectToken);
//				rm.put("token", selectToken.getToken());
//				rm.put("expireIn", selectToken.getExpirein());
//			}
			// 创建帐户，避免新开其它帐户时，旧用户没有创建该帐户而造成问题
			AccountFactory.createAccounts(user.getUserId());
			
		} catch (Exception e1) {
			log.error(String.format("新用户注册出错[%s]", loginvo), e1);
			rm.mergeException(e1);
		} finally {
			if (log.isDebugEnabled()) {
				log.debug("新用户注册完成");
			}
		}
		return rm;
	}
	
	/**
	 * 尝试为用户添加属性
	 * @param user
	 * @param attrs
	 * @return 
	 * @throws ValidateException 
	 */
	private boolean try2setAttr(User user, String[] attrs) throws ValidateException {
		
		if(attrs!=null&&attrs.length>0){
//			userAttrSrv.addAttr(user.getUserId(),loginvo.getLogin().getAttrs());
			boolean addedattr = false;
			for (int i = 0; i < attrs.length; i++) {
				String attr = attrs[i];
				UserAttr userAttr = userAttrSrv.getUserAttr(user.getMobilenum(), attr);
				if(userAttr==null)
				{
					if (log.isDebugEnabled()) {
						log.debug(String.format("为已存在用户添加用户属性%s",attr));
					}
					UserAttr added = userAttrSrv.addAttr(user.getUserId(),attr);
					if(added==null){
						throw  ValidateException.ERROR_SYSTEM_INTERNAL.clone(null,"为用户添加属性失败");
					}
					addedattr=true;
				}
			}
			return addedattr;
		}
		return false;
	}
	/**
	 * 重置密码接口
	 * 
	 * @param getsmsvo
	 * @return
	 */
	@RequestMapping("/resetPassword")
	@AccessRequered(methodName="重置密码")
	public @ResponseBody Object resetPassword(@RequestBody ResetPasswordVO loginvo) {
		// {"appId":"smsmarketing","timeStamp":"TIMESTAMP",
		// "nonce":"NONCE","signature":"MD5(appId+appKey+nonce+timeStamp)",
		// "loginInfo":{"mobileNum":"13912345678","smsCode":"223344"},
		// "deviceInfo":{"mac":"3ere:eee:3434:34434","deviceId":"设备标识","imsi":"SIM卡设备号"}}
		if (log.isDebugEnabled()) {
			log.debug("重置密码:" + loginvo);
		}
		// 捕捉所有异常,不要由于有异常而不返回信息
		ResultModel rm = new ResultModel();
		int baseerrcode = 20600;
		rm.setBaseErrorCode(baseerrcode);
		rm.setErrmsg("重置密码成功");
		try {
			Object afterauth = authService.validateAuth(loginvo);
			// 校验签名
			if (log.isDebugEnabled()) {
				log.debug("检验签名通过，验证验证码");
			}
			// 检验手机
			ValidateUtil.validateMobile(loginvo.getMobileNum());
			ValidateUtil.assertNull(loginvo.getLogin().getNewpassword(), "新密码");
			User user = userSrv.getUserByMobile(loginvo.getMobileNum());
			if(user==null)
			{
				if (log.isDebugEnabled()) {
					log.debug(String.format("手机号码%s找不到用户",loginvo.getMobileNum()));
				}
				throw ValidateException.ERROR_EXISTING_USER_NOT_EXISTS;
			}
			if(!StringUtils.equals(user.getPassword(),loginvo.getLogin().getOldpassword())){
				if (log.isDebugEnabled()) {
					log.debug(String.format("旧密码不正确"));
				}
				throw ValidateException.ERROR_MATCH_PASSWORD;
			}
			if (log.isDebugEnabled()) {
				log.debug(String.format("校验通过，现在更新密码"));
			}
			user.setPassword(loginvo.getLogin().getNewpassword());
			//同步密码到用户中心
			boolean flag=SynUserCenterData.synUserPassword(user.getMobilenum(), user.getPassword());
			if(!flag){
				log.info("同步密码到用户中心失败");
				throw new RuntimeException("同步密码到用户中心失败,请重新尝试");
			}
			userSrv.updateUser(user);
			
		} catch (Exception e1) {
			log.error(String.format("重置密码出错[%s]", loginvo), e1);
			rm.mergeException(e1);
		} finally {
			if (log.isDebugEnabled()) {
				log.debug("重置密码完成");
			}
		}
		return rm;
	}
	
	/**
	 * 重置密码接口
	 * 
	 * @param getsmsvo
	 * @return
	 */
	@RequestMapping("/resetPasswordWithSmscode")
	@AccessRequered(methodName="通过手机验证码重置密码")
	public @ResponseBody Object resetPasswordWithSmscode(HttpServletRequest request) {
		// {"appId":"smsmarketing","timeStamp":"TIMESTAMP",
		// "nonce":"NONCE","signature":"MD5(appId+appKey+nonce+timeStamp)",
		// "loginInfo":{"mobileNum":"13912345678","smsCode":"223344"},
		// "deviceInfo":{"mac":"3ere:eee:3434:34434","deviceId":"设备标识","imsi":"SIM卡设备号"}}
		ResetPasswordWithSmscodeVO loginvo;
		ResultModel rm = new ResultModel();
		int baseerrcode = 20700;
		rm.setBaseErrorCode(baseerrcode);
		try {
			String jsonstr = RequestUtil.getRequestPostData(request);
			request.setAttribute("rawjson", jsonstr);
			loginvo = RequestUtil.convertJson2Obj(jsonstr, ResetPasswordWithSmscodeVO.class);
		} catch (Exception e) {
			log.error(String.format("获取通过手机验证码重置密码参数出错"),e);
			rm.mergeException(ValidateException.ERROR_PARAM_FORMAT_ERROR.cloneAndAppend(null, "通过手机验证码重置密码参数"));
			return rm;
		}
		// 捕捉所有异常,不要由于有异常而不返回信息
		rm.setErrmsg("重置密码成功");
		try {
			Object afterauth = authService.validateAuth(loginvo);
			// 校验签名
			if (log.isDebugEnabled()) {
				log.debug("检验签名通过，验证验证码");
			}
			boolean authCodeSuccess = validateSMSCode(loginvo.getApp()
					.getAppId(), loginvo.getLogin().getMobileNum(), loginvo
					.getLogin().getSmsCode());
			
			if (!authCodeSuccess) {
				if (log.isDebugEnabled()) {
					log.debug(String.format("验证码不正确,%s", loginvo));
				}
				rm.mergeException(ValidateException.ERROR_MATCH_SMSCODE);
				return rm;
			}
			// 检验手机
			ValidateUtil.validateMobile(loginvo.getMobileNum());
			ValidateUtil.assertNull(loginvo.getLogin().getNewpassword(), "新密码");
			User user = userSrv.getUserByMobile(loginvo.getMobileNum());
			if(user==null)
			{
				if (log.isDebugEnabled()) {
					log.debug(String.format("手机号码%s找不到用户",loginvo.getMobileNum()));
				}
				throw ValidateException.ERROR_EXISTING_USER_NOT_EXISTS;
			}
			if (log.isDebugEnabled()) {
				log.debug(String.format("校验通过，现在更新密码"));
			}
			user.setPassword(loginvo.getLogin().getNewpassword());
			//同步密码到用户中心
			boolean flag=SynUserCenterData.synUserPassword(user.getMobilenum(), user.getPassword());
			if(!flag){
				log.info("同步密码到用户中心失败");
				throw new RuntimeException("同步密码到用户中心失败,请重新尝试");
			}
			userSrv.updateUser(user);
			
		} catch (Exception e1) {
			log.error(String.format("通过手机验证码重置密码出错[%s]", loginvo), e1);
			rm.mergeException(e1);
		} finally {
			if (log.isDebugEnabled()) {
				log.debug("通过手机验证码重置密码完成");
			}
		}
		return rm;
	}
	
	/**
	 * 用户登录密码登录（默认登录方式为手机号loginType=MOBILE）
	 * 
	 * @param getsmsvo
	 * @return
	 */
	@RequestMapping("/logina")
	@AccessRequered(methodName="用户登录密码登录")
	public @ResponseBody Object logina(@RequestBody LoginByPasswdVo loginvo) {
		// {"appId":"smsmarketing","timeStamp":"TIMESTAMP",
		// "nonce":"NONCE","signature":"MD5(appId+appKey+nonce+timeStamp)",
		// "loginInfo":{"mobileNum":"13912345678","smsCode":"223344"},
		// "deviceInfo":{"mac":"3ere:eee:3434:34434","deviceId":"设备标识","imsi":"SIM卡设备号"}}
		if (log.isDebugEnabled()) {
			log.debug("登录密码登录：" + loginvo);
		}
		// 捕捉所有异常,不要由于有异常而不返回信息
		ResultModel rm = new ResultModel();
		int baseerrcode = 20800;
		rm.setBaseErrorCode(baseerrcode);
		rm.setErrmsg("登录密码登录成功");
		try {
			// 检验手机
			ValidateUtil.validateMobile(loginvo.getMobileNum());
			User user = userSrv.getUserByMobile(loginvo.getMobileNum());
			if(user==null)
			{
				if (log.isDebugEnabled()) {
					log.debug(String.format("手机号码%s未注册",loginvo.getMobileNum()));
				}
				throw ValidateException.ERROR_EXISTING_USER_NOT_EXISTS;
			}
			// if(user!=null)
			// {
			// if (log.isDebugEnabled()) {
			// log.debug(String.format("手机号码%s已注册为用户%s[%s]所用",loginvo.getMobileNum(),user.getName(),user.getUserId()));
			// }
			// throw ValidateException.ERROR_EXISTING_USER_EXISTS;
			// }
			// ValidateUtil.assertNull(user, "手机号码已注册", 4121);
			// 校验签名
			Object afterauth = authService.validateAuth(loginvo);
			if (log.isDebugEnabled()) {
				log.debug("检验签名通过，验证登录密码");
			}
			// 这里不使用原来的代码，因为smscode方法改变了
			// boolean authCodeSuccess =
			// AuthCodeUtil.validateAuthCode(loginvo.getApp().getAppId(),
			// loginvo.getLogin().getMobileNum(),loginvo.getLogin().getSmsCode());
			if(user.getPassword()==null){
				if (log.isDebugEnabled()) {
					log.debug(String.format("用户没有设置登录密码"));
				}
				throw ValidateException.ERROR_MATCH_PASSWORD;
			}
			if(!user.getPassword().equals(loginvo.getLogin().getPassword())){
				if (log.isDebugEnabled()) {
					log.debug(String.format("登录密码不正确"));
				}
				throw ValidateException.ERROR_MATCH_PASSWORD;
				
			}
			UserToken selectToken = tokensrv.getOrCreateToken(loginvo.getApp()
					.getAppId(), user.getUserId());
			rm.put("token", selectToken.getToken());
			rm.put("expireIn", selectToken.getExpirein());
			rm.put("orgType", user.getOrgType());//组织机构类型
//			UserToken selectToken = tokensrv.queryToken(loginvo.getApp()
//					.getAppId(), user.getUserId());
//			if(selectToken!=null){
//				tokensrv.isOvertime(selectToken);
//			}
//			if (selectToken == null) {
//				// 如果已记录就不处理
//				UserToken createToken = tokensrv.createToken(loginvo.getApp()
//						.getAppId(), user.getUserId());
//				// 返回token和信用信息
//				rm.put("token", createToken.getToken());
//				rm.put("expireIn", createToken.getExpirein());
//			} else {
//				tokensrv.updateExpireIn(selectToken);
//				rm.put("token", selectToken.getToken());
//				rm.put("expireIn", selectToken.getExpirein());
//			}
			// 创建帐户
			AccountFactory.createAccounts(user.getUserId());
			
		} catch (Exception e1) {
			log.error(String.format("用户登录出错[%s]", loginvo), e1);
			rm.mergeException(e1);
		} finally {
			if (log.isDebugEnabled()) {
				log.debug("用户登录完成");
			}
		}
		return rm;
	}
	
	/**
	 * 用户支付密码登录
	 * 
	 * @param getsmsvo
	 * @return
	 */
	@RequestMapping("/loginWithPaymentCodeDES")
	@AccessRequered(methodName="用户支付密码登录")
	public @ResponseBody Object loginWithPaymentCodeDES(HttpServletRequest request) {
		
		BaseTransVO<LoginWithPaymentCodeVO> loginvo;
		ResultModel rm = new ResultModel();
		int baseerrcode = 20700;
		String messagebase = "用户支付密码登录";
		rm.setBaseErrorCode(baseerrcode);
		try {
			String jsonstr = RequestUtil.getRequestPostData(request);
			request.setAttribute("rawjson", jsonstr);
			loginvo = RequestUtil.convertJson2Obj(jsonstr, BaseTransVO.class,LoginWithPaymentCodeVO.class);
		} catch (Exception e) {
			log.error(String.format("获取%s参数出错",messagebase),e);
			rm.mergeException(ValidateException.ERROR_PARAM_FORMAT_ERROR.cloneAndAppend(null,String.format("获取%s参数出错",messagebase)));
			return rm;
		}
		if (log.isDebugEnabled()) {
			log.debug("用户支付密码登录：" + loginvo);
		}
		// 捕捉所有异常,不要由于有异常而不返回信息
		try {
			// 检验手机
			String mobileNum = loginvo.getBody().getMobileNum();
			ValidateUtil.validateMobile(mobileNum);
			User user = userSrv.getUserByMobile(mobileNum);
			if(user==null)
			{
				if (log.isDebugEnabled()) {
					log.debug(String.format("手机号码%s未注册",mobileNum));
				}
				throw ValidateException.ERROR_EXISTING_USER_NOT_EXISTS;
			}
			// 校验签名
			Map afterauth = (Map) authService.validateAuth(loginvo.getApp());
			if (log.isDebugEnabled()) {
				log.debug("检验签名通过，验证登录密码");
			}
			// 这里不使用原来的代码，因为smscode方法改变了
			// boolean authCodeSuccess =
			// AuthCodeUtil.validateAuthCode(loginvo.getApp().getAppId(),
			// loginvo.getLogin().getMobileNum(),loginvo.getLogin().getSmsCode());
			boolean authCodeSuccess = validateSMSCode(loginvo.getApp().getAppId(), mobileNum,loginvo.getBody().getSmsCode());
			if (!authCodeSuccess) {
				if (log.isDebugEnabled()) {
					log.debug(String.format("验证码不正确,%s", loginvo.getBody().getSmsCode()));
				}
//				rm.setErr(ValidateException.ERRCODE_SIGNATURE_FAIL, "短信验证码不正确");
//				rm.setBaseErrorCode(2000);
				rm.mergeException(ValidateException.ERROR_MATCH_SMSCODE);
				return rm;
			}
			if(user.getPaymentCodeDES()==null){
				if (log.isDebugEnabled()) {
					log.debug(String.format("用户没有设置支付密码"));
				}
				throw ValidateException.ERROR_MATCH_PASSWORD;
			}
			String encryptpaymentcode = loginvo.getBody().getPaymentCodeDES();
			String encpaymentcode;//加密处理后的支付密码
			try {
				encpaymentcode = DESUtil.decodeDESwithCBC(encryptpaymentcode,ObjectUtils.toString(afterauth.get("appKey")));
				//encpaymentcode = PospEncyptUtil.encypt(paymentCodeDES, mobileNum);
				//要求对支付密码进行md5加密
			} catch (Exception e) {
				log.error(String.format("支付密码解密错"),e);
				throw ValidateException.ERROR_MATCH_PASSWORD.clone(e, "密码不正确,加解密出错!");
			}
			if(!user.getPaymentCodeDES().equals(encpaymentcode)){
				if (log.isDebugEnabled()) {
					log.debug(String.format("支付密码不正确"));
				}
				throw ValidateException.ERROR_MATCH_PASSWORD;
				
			}
			UserToken selectToken = tokensrv.getOrCreateToken(loginvo.getApp()
					.getAppId(), user.getUserId());
			rm.put("token", selectToken.getToken());
			rm.put("expireIn", selectToken.getExpirein());
			
		} catch (Exception e1) {
			log.error(String.format("用户支付密码登录出错[%s]", loginvo), e1);
			rm.mergeException(e1);
		} finally {
			if (log.isDebugEnabled()) {
				log.debug("用户支付密码登录完成");
			}
		}
		return rm;
	}
	
	/**
	 * 验证用户token
	 * 
	 * @param 
	 * @return
	 */
	@RequestMapping("/verify_token_original")
	@AccessRequered(methodName="验证用户token")
	public @ResponseBody Object verifyToken(HttpServletRequest request) {
		// {"appId":"smsmarketing","timeStamp":"TIMESTAMP",
		// "nonce":"NONCE","signature":"MD5(appId+appKey+nonce+timeStamp)",
		// "loginInfo":{"mobileNum":"13912345678","smsCode":"223344"},
		// "deviceInfo":{"mac":"3ere:eee:3434:34434","deviceId":"设备标识","imsi":"SIM卡设备号"}}
		VerifyTokenVo transorder;
		ResultModel rm = new ResultModel();
		String messagebase = "验证用户token";
		int baseerrcode=20900;
		rm.setBaseErrorCode(baseerrcode);
		rm.setErrmsg(messagebase+"成功");
		try {
			String jsonstr = RequestUtil.getRequestPostData(request);
			request.setAttribute("rawjson", jsonstr);
			transorder = RequestUtil.convertJson2Obj(jsonstr, VerifyTokenVo.class);
		} catch (Exception e) {
			log.error(String.format("获取订单参数出错"),e);
			rm.mergeException(ValidateException.ERROR_PARAM_FORMAT_ERROR.cloneAndAppend(null, "订单参数"));
			return rm;
		}
		try {
			Object afterauth = authService.validateAuth(transorder);
			if (log.isDebugEnabled()) {
				log.debug("检验签名通过，验证token");
			}
			Token token = tokensrv.getToken(transorder.getUserToken().getToken());
			if(token!=null){
				User user = userSrv.getUserByUserId(token.getUserId());
				if(user==null)
				{
					if (log.isDebugEnabled()) {
						log.debug(String.format("根据用户id[%s]查找不到用户",token.getUserId()));
					}
					rm.setErr(baseerrcode+1, "无效的token，其他错误");
				}
				else{
					rm.put("name", user.getName());
					rm.put("mobileNum", user.getMobilenum());
					
				}
			}
			else{
				rm.setErr(baseerrcode+1, "无效的token，其他错误");
			}
			
		} catch (Exception e1) {
			log.error(String.format("验证用户token出错[%s]", transorder), e1);
			rm.mergeException(e1);
		} finally {
			if (log.isDebugEnabled()) {
				log.debug("验证用户token完成");
			}
		}
		return rm;
	}
	
	
	/**
	 * 验证用户token
	 * 
	 * @param 
	 * @return
	 */
	@RequestMapping("/verify_token")
	@AccessRequered(methodName="验证用户token新接口")
	public @ResponseBody Object verifyTokenNew(HttpServletRequest request) {
//		{
//		    "appId":"dianziquan","timeStamp":"TIMESTAMP", "nonce":"NONCE","signature":"MD5(appCode+appKey+nonce+timeStamp)",
//		    "userToken":{
//		        "appId":"dianziquan","timeStamp":"TIMESTAMP", "nonce":"NONCE","signature":"MD5(appId+appKey+nonce+timeStamp+token)","token":"qpoiwreq99eekkeuurqwerq23ewrm"
//		    }
//		} 
		VerifyTokenTransVo transorder;
		ResultModel rm = new ResultModel();
		String messagebase = "验证用户token";
		int baseerrcode=21000;
		rm.setBaseErrorCode(baseerrcode);
		rm.setErrmsg(messagebase+"成功");
		try {
			String jsonstr = RequestUtil.getRequestPostData(request);
			request.setAttribute("rawjson", jsonstr);
			transorder = RequestUtil.convertJson2Obj(jsonstr, VerifyTokenTransVo.class);
		} catch (Exception e) {
			log.error(String.format("获取订单参数出错"),e);
			rm.mergeException(ValidateException.ERROR_PARAM_FORMAT_ERROR.cloneAndAppend(null, "订单参数"));
			return rm;
		}
		try {
			Object afterauth = authService.validateAuth(transorder.getUserToken());
			if (log.isDebugEnabled()) {
				log.debug("检验签名通过，验证token");
			}
			Token token = tokensrv.getToken(transorder.getUserToken().getToken(),transorder.getUserToken().getAppId());
			if(token!=null){
				User user = userSrv.getUserByUserId(token.getUserId());
				if(user==null)
				{
					if (log.isDebugEnabled()) {
						log.debug(String.format("根据用户id[%s]查找不到用户",token.getUserId()));
					}
					rm.setErr(baseerrcode+1, "无效的token，其他错误");
				}
				else{
					rm.put("name", user.getName());
					rm.put("mobileNum", user.getMobilenum());
					rm.put("insertTime",user.getInsertTime());
					//验证token通过，更新token的超时时间
					tokensrv.resetExpireIn(transorder.getUserToken().getToken(),transorder.getUserToken().getAppId());
				}
			}
			else{
				if (log.isDebugEnabled()) {
					log.debug(String.format("token不存在"));
				}
				rm.setErr(baseerrcode+1, "无效的token，其他错误");
			}
			
		} catch (Exception e1) {
			log.error(String.format("验证用户token出错[%s]", transorder), e1);
			rm.mergeException(e1);
		} finally {
			if (log.isDebugEnabled()) {
				log.debug("验证用户token完成");
			}
		}
		return rm;
	}
	
	
	
	/**
	 * 更新用户基本信息
	 * 
	 * @param 
	 * @return
	 */
	@RequestMapping("/update_userInfo")
	@AccessRequered(methodName="更新用户基本信息")
	public @ResponseBody Object updateUserInfo(HttpServletRequest request) {
		UpdateUserInfoVO transorder;
		ResultModel rm = new ResultModel();
		String messagebase = "更新用户基本信息";
		int baseerrcode=21100;
		rm.setBaseErrorCode(baseerrcode);
		rm.setErrmsg(messagebase+"成功");
		try {
			String jsonstr = RequestUtil.getRequestPostData(request);
			request.setAttribute("rawjson", jsonstr);
			transorder = RequestUtil.convertJson2Obj(jsonstr, UpdateUserInfoVO.class);
		} catch (Exception e) {
			log.error(String.format("获取更新用户基本信息参数出错"),e);
			rm.mergeException(ValidateException.ERROR_PARAM_FORMAT_ERROR.cloneAndAppend(null, "更新用户基本信息"));
			return rm;
		}
		try {
			Object afterauth = authService.validateAuth(transorder);
			if (log.isDebugEnabled()) {
				log.debug("检验签名通过，更新用户基本信息");
			}
			//根据手机号查询用户
			User user = userSrv.getUserByMobile(transorder.getMobileNum());
			if(user==null){
				if (log.isDebugEnabled()) {
					log.debug(String.format("根据用户手机号[%s]查找不到用户",transorder.getMobileNum()));
				}
				throw ValidateException.ERROR_EXISTING_USER_NOT_EXISTS;
			}
			user.setEmail(transorder.getUpdate().getEmail());
			user.setHeadimage(transorder.getUpdate().getHeadImage());
			user.setName(transorder.getUpdate().getName());
			user.setId(transorder.getUpdate().getID());
			user.setNickname(transorder.getUpdate().getNickname());
			user.setUpdateTime(new Date());
			userSrv.updateUser(user);
			
		} catch (Exception e1) {
			log.error(String.format("更新用户基本信息出错[%s]", transorder), e1);
			rm.mergeException(e1);
		} finally {
			if (log.isDebugEnabled()) {
				log.debug("更新用户基本信息完成");
			}
		}
		return rm;
	}
	
	/**
	 * 查询用户支付密码状态
	 * @author liudou
	 * @2015/7/14
	 * @param 
	 * @return
	 */
	@RequestMapping("/getPaymentCodeStatus")
	@AccessRequered(methodName="查询用户支付密码状态")
	public @ResponseBody Object getPaymentCodeStatus(HttpServletRequest request) {
		AuthGetPayCodeStatusVO transorder;
		ResultModel rm = new ResultModel();
		String messagebase = "查询用户支付密码状态";
		int baseerrcode=27100;
		rm.setBaseErrorCode(baseerrcode);
		rm.setErrmsg(messagebase+"成功");
		try {
			String jsonstr = RequestUtil.getRequestPostData(request);
			request.setAttribute("rawjson", jsonstr);
			transorder = RequestUtil.convertJson2Obj(jsonstr, AuthGetPayCodeStatusVO.class);
		} catch (Exception e) {
			log.error(String.format("获取查询用户支付密码状态参数出错"),e);
			rm.mergeException(ValidateException.ERROR_PARAM_FORMAT_ERROR.cloneAndAppend(null, "查询用户支付密码状态"));
			return rm;
		}
		try {
			// 检验手机
			ValidateUtil.validateMobile(transorder.getMobileNum());
			Object afterauth = authService.validateAuth(transorder);
			if (log.isDebugEnabled()) {
				log.debug("检验签名通过，查询用户支付密码状态");
			}
			//根据手机号查询用户
			User user = userSrv.getUserByMobile(transorder.getMobileNum());
			if(user==null){
				if (log.isDebugEnabled()) {
					log.debug(String.format("根据用户手机号[%s]查找不到用户",transorder.getMobileNum()));
				}
				throw ValidateException.ERROR_EXISTING_USER_NOT_EXISTS;
			}
			//判断有油贷支付密码的状态
			if(StringUtils.isBlank(user.getPaymentcodemd5())){
				rm.put("paymentCodeMD5", "NON");
				
			}
			else if(user.getPaymentcodemd5().equals(user.getPassword())){
				rm.put("paymentCodeMD5", "EQ#");
			}else{
				rm.put("paymentCodeMD5", "NEQ");
			}
			//判断用户的pos消费支付密码
			String enc_pw=null;
			if(StringUtils.isBlank(user.getPaymentCodeDES())){
				rm.put("paymentCodeDES", "NON");
			}else {
				PropertiesUtil pu = new PropertiesUtil();
				// 校验token
				//获取Appkey
				//Map validateAuth = (Map) authService.validateAuth(transorder);
				//String appkey = ObjectUtils.toString(validateAuth.get("appKey"));
				//String dec_pw = PospEncyptUtil.decypt(user.getPaymentCodeDES(), transorder.getMobileNum());
				//enc_pw =Md5Util.Encrypt(dec_pw);
				enc_pw = user.getPaymentCodeDES();
				
				if(enc_pw.equals(user.getPassword())){
					rm.put("paymentCodeDES", "EQ#");
				}else{
					rm.put("paymentCodeDES", "NEQ");
				}
				
			}
			//有油贷支付密码和pos消费支付密码是否相同
			if(StringUtils.equals(enc_pw,user.getPaymentcodemd5())){
				rm.put("bothPaymentCode", "EQ#");
			}else{
				rm.put("bothPaymentCode", "NEQ");
			}
			
		} catch (Exception e1) {
			log.error(String.format("查询用户支付密码状态出错[%s]", transorder), e1);
			rm.mergeException(e1);
		} finally {
			if (log.isDebugEnabled()) {
				log.debug("查询用户支付密码状态完成");
			}
		}
		return rm;
	}
	

	/**
	 * @param appId
	 * @param mobileNum
	 * @param smsCode
	 * @return
	 */
	private boolean validateSMSCode(String appId, String mobileNum,
			String authCode) {
		SmsCode query = new SmsCode();
		query.setAppId(appId);
		query.setMobilenum(mobileNum);
		SmsCode code = smscodemapper.getAuthCode(query);
		if (log.isTraceEnabled()) {
			log.trace("手机验证码信息是：" + code);
		}
		try{
			if (code != null
					&& code.getSmscode().equals(authCode)
					&& (code.getSendTime().getTime() + code.getExpirein() * 1000) > System
							.currentTimeMillis()) {
				return true;
			}
			return false;
		}
		finally{
			// 删除验证码
			smscodemapper.deleteAuthCode(query);
			
		}
	}

	@RequestMapping("/gencert")
	public @ResponseBody Object genCert(@RequestBody HashMap certparam) {
		if (log.isDebugEnabled()) {
			log.debug("生成证书：" + certparam);
		}
		ResultModel rm = new ResultModel();
		rm.setErrmsg("生成证书成功");
		try {
			String appId = ObjectUtils.toString(certparam.get("appId"));
			String certPath = ObjectUtils.toString(certparam.get("certPath"),
					"");
			// String certPath =
			// ObjectUtils.toString(certparam.get("expireTime"));
			if (certPath.lastIndexOf("/") == -1) {
				certPath = certPath + "/";
			}
			// String certfilepath = certPath+appId
			// CaUtil.generatePfxCert(certPath, 265*2);

			// 登录key
			// rm.put("<p><p>●登录key和获取验证码以及帐户操作接口","(appid+appkey+nonce+TIMESTAMP)明文="+(ValidateUtil.sortbyValues(appid,appkey,appnonce,appTIMESTAMP))+",Md5字典值="+Md5Util.Encrypt(ValidateUtil.sortbyValues(appid,appkey,appnonce,appTIMESTAMP)));

		} catch (Exception e1) {
			// log.error(String.format("手机号码登录出错[%s]",loginvo),e1);
			rm.mergeException(e1);
		} finally {
			if (log.isDebugEnabled()) {
				log.debug("genkey完成");
			}
		}
		if (log.isDebugEnabled()) {
			log.debug("生成证书完成：" + certparam);
		}
		return rm;
	}
	
	@RequestMapping("/genParam")
	public @ResponseBody Object genParam(String param){
		/*{
		    "appId":"dianziquan","timeStamp":"TIMESTAMP", "nonce":"NONCE","signature":"MD5(appCode+appKey+nonce+timeStamp)",
		    "userToken":{
		        "appId":"dianziquan","timeStamp":"TIMESTAMP", "nonce":"NONCE","signature":"MD5(appId+appKey+nonce+timeStamp+token)","token":"qpoiwreq99eekkeuurqwerq23ewrm"
		    }
		} */
		if (log.isDebugEnabled()) {
			log.debug("改造签名：" + param);
		}
		ResultModel rm = new ResultModel();
		rm.setErrmsg("成功");
		String ordermd5=null;
		try{
			HashMap transorder = RequestUtil.convertJson2Obj(param, HashMap.class);
			Map app = (Map) transorder.get("app");
			String appkey = ObjectUtils.toString(app.get("appKey"), "");
			String appid = ObjectUtils.toString(app.get("appId"), "");
			String apppublickeystr = "";
			if (StringUtils.isBlank(appkey)) {
				AppInfo appByAppid = appService.getAppByAppid(appid);
				appkey = appByAppid.getAppKey();
				apppublickeystr = appByAppid.getAppcert();
			}
			String nonce = ObjectUtils.toString(app.get("nonce"), "");
			String TIMESTAMP = ObjectUtils.toString(app.get("timeStamp"), "");
			String appnonce = ObjectUtils.toString(app.get("nonce"), "");
			String appTIMESTAMP = ObjectUtils
					.toString(app.get("timeStamp"), "");
			String appsign =  Md5Util.Encrypt(ValidateUtil.sortbyValues(appnonce,
					appTIMESTAMP, appkey, appid));
			app.put("signature", appsign);
			//尝试使用赠送红包
			try {
				TransOrderVO2<RedPaperOrderVO> ts = RequestUtil.convertJson2Obj(param, TransOrderVO2.class,RedPaperOrderVO.class);
				ordermd5 = Md5Util.Encrypt(ts.getOrder().getPaintText());
			} catch (Exception e) {
			}
			if(ordermd5==null){
				//尝试使用消费红包
				try {
					TransOrderVO2<SpendRedPaperOrderVO> ts = RequestUtil.convertJson2Obj(param, TransOrderVO2.class,SpendRedPaperOrderVO.class);
					ordermd5 = Md5Util.Encrypt(ts.getOrder().getPaintText());
				} catch (Exception e) {
				}
			}
			if(ordermd5==null){
			//尝试使用撤销消费红包
			try {
				UndoRedPaperTransOrderVO ts = RequestUtil.convertJson2Obj(param, UndoRedPaperTransOrderVO.class);
				ordermd5 = Md5Util.Encrypt(ts.getPaintText());
			} catch (Exception e) {
			}
			}
			//尝试使用积分
			if(ordermd5==null){
			try {
				TransOrderVO2<JifenOrderVO> ts = RequestUtil.convertJson2Obj(param, TransOrderVO2.class,JifenOrderVO.class);
				ordermd5 = Md5Util.Encrypt(ts.getOrder().getPaintText());
			} catch (Exception e) {
			}
			}
			if(ordermd5==null){
			//尝试使用积分兑换
			try {
				TransOrderVO2<SpendJifenOrderVO> ts = RequestUtil.convertJson2Obj(param, TransOrderVO2.class,SpendJifenOrderVO.class);
				ordermd5 = Md5Util.Encrypt(ts.getOrder().getPaintText());
			} catch (Exception e) {
			}
			}
			
			if (transorder.containsKey("tsig")) {
				// 帐户相关
				Map tsig = (Map) transorder.get("tsig");
				if (transorder.get("order") instanceof Map) {
					Map ordermap = (Map) transorder.get("order");
					nonce = ObjectUtils.toString(tsig.get("nonce"), "");
					TIMESTAMP = ObjectUtils.toString(tsig.get("timeStamp"), "");
//				String orderMD5 = ObjectUtils
//						.toString(tsig.get("orderMD5"), "");
//				String signature = ObjectUtils.toString(tsig.get("signature"),
//						"");
					
					String mobileNum = ObjectUtils.toString(
							ordermap.get("mobileNum"), "");
					String productName = ObjectUtils.toString(
							ordermap.get("productName"), "");
					String remark = ObjectUtils
							.toString(ordermap.get("remark"), "");
					String appOrderId = ObjectUtils.toString(
							ordermap.get("appOrderId"), "");
					String sum = ObjectUtils.toString(ordermap.get("sum"), "");
					String externalOrderId = ObjectUtils.toString(ordermap.get("externalOrderId"), "");
					String externalOrderTime = ObjectUtils.toString(ordermap.get("externalOrderTime"), "");
					String peerAccountId = ObjectUtils.toString(ordermap.get("peerAccountId"), "");
					String peerAccountUnit = ObjectUtils.toString(ordermap.get("peerAccountUnit"), "");
					String orderId = ObjectUtils.toString(ordermap.get("orderId"), "");
					String accountCode = ObjectUtils.toString(ordermap.get("accountCode"), "");
					String paymentCodeMD5 = ObjectUtils.toString(ordermap.get("paymentCodeMD5"), "");
					String payOrderId = ObjectUtils.toString(ordermap.get("payOrderId"), "");
					if(ordermd5==null){
						String ordertext = ValidateUtil.sortbyValues(mobileNum,
								productName, remark, appOrderId, sum,externalOrderId,
								externalOrderTime,peerAccountId,peerAccountUnit,accountCode,
								orderId,paymentCodeMD5,payOrderId);
						ordermd5 = Md5Util.Encrypt(ordertext);
					}
					if("true".equals(new PropertiesUtil().getProperty("verifybypublickey")))
					{
						String transordertext = ValidateUtil.sortbyValues(ordermd5,
								nonce, TIMESTAMP, appid);
						String certsign="";
						try {
							certsign=Base64.encodeBase64String(CertificateUtils.signByCert(transordertext.getBytes("utf8"), System.getProperty("web.root")+"/x.pfx", "accountant_portal"));
//							String tsigSignature = CertificateUtils.signWithDigitalCertByProperties(ordermd5,nonce, TIMESTAMP, appid);
						} catch (Exception e) {
							e.printStackTrace();
							certsign="签名出错";
						}
						tsig.put("signature",certsign);
					}
					else{
						String tsigSignature = Md5Util.Encrypt(ValidateUtil.sortbyValues( ordermd5,nonce, TIMESTAMP, appid));
							tsig.put("signature",tsigSignature);
						}
						tsig.put("orderMD5",ordermd5);
//				tsig.put("signature",certsign);
				}
				else{
					if(ordermd5!=null)
					{
						if("true".equals(new PropertiesUtil().getProperty("verifybypublickey")))
						{
							String transordertext = ValidateUtil.sortbyValues(ordermd5,
									nonce, TIMESTAMP, appid);
							String certsign="";
							try {
								certsign=Base64.encodeBase64String(CertificateUtils.signByCert(transordertext.getBytes("utf8"), System.getProperty("web.root")+"/x.pfx", "accountant_portal"));
//								String tsigSignature = CertificateUtils.signWithDigitalCertByProperties(ordermd5,nonce, TIMESTAMP, appid);
							} catch (Exception e) {
								e.printStackTrace();
								certsign="签名出错";
							}
							tsig.put("signature",certsign);
//						String tsigSignature = Md5Util.Encrypt(ValidateUtil.sortbyValues( ordermd5,nonce, TIMESTAMP, appid));
						}
						else{
							String tsigSignature = Md5Util.Encrypt(ValidateUtil.sortbyValues( ordermd5,nonce, TIMESTAMP, appid));
							tsig.put("signature",tsigSignature);
						}
						tsig.put("orderMD5",ordermd5);
					}
				}
				
			}
			return transorder;
		}
		catch(Exception e){
			log.error("生成参数签名出错",e);
			rm.setErrmsg("生成参数签名出错:"+e.getMessage());
			rm.setErrcode(-1);
			return rm;
		}
	}

	@RequestMapping("/genkey")
	public @ResponseBody Object genkey(@RequestBody HashMap transorder) {
		if (log.isDebugEnabled()) {
			log.debug("生成key：" + transorder);
		}
		// 捕捉所有异常,不要由于有异常而不返回信息
		ResultModel rm = new ResultModel();
		rm.setErrmsg("成功");
		try {
			Map app = (Map) transorder.get("app");

			String appkey = ObjectUtils.toString(app.get("appKey"), "");
			String appid = ObjectUtils.toString(app.get("appId"), "");
			String apppublickeystr = "";
			if (StringUtils.isBlank(appkey)) {
				AppInfo appByAppid = appService.getAppByAppid(appid);
				appkey = appByAppid.getAppKey();
				apppublickeystr = appByAppid.getAppcert();
			}
			String nonce = ObjectUtils.toString(app.get("nonce"), "");
			String TIMESTAMP = ObjectUtils.toString(app.get("timeStamp"), "");
			String appnonce = ObjectUtils.toString(app.get("nonce"), "");
			String appTIMESTAMP = ObjectUtils
					.toString(app.get("timeStamp"), "");
			if (transorder.containsKey("tsig")) {
				// 帐户相关
				Map tsig = (Map) transorder.get("tsig");
				Map ordermap = (Map) transorder.get("order");
				nonce = ObjectUtils.toString(tsig.get("nonce"), "");
				TIMESTAMP = ObjectUtils.toString(tsig.get("timeStamp"), "");
				String orderMD5 = ObjectUtils
						.toString(tsig.get("orderMD5"), "");
				String signature = ObjectUtils.toString(tsig.get("signature"),
						"");

				String mobileNum = ObjectUtils.toString(
						ordermap.get("mobileNum"), "");
				String productName = ObjectUtils.toString(
						ordermap.get("productName"), "");
				String remark = ObjectUtils
						.toString(ordermap.get("remark"), "");
				String appOrderId = ObjectUtils.toString(
						ordermap.get("appOrderId"), "");
				String sum = ObjectUtils.toString(ordermap.get("sum"), "");
				String externalOrderId = ObjectUtils.toString(ordermap.get("externalOrderId"), "");
				String externalOrderTime = ObjectUtils.toString(ordermap.get("externalOrderTime"), "");
				String peerAccountId = ObjectUtils.toString(ordermap.get("peerAccountId"), "");
				String peerAccountUnit = ObjectUtils.toString(ordermap.get("peerAccountUnit"), "");
				String orderId = ObjectUtils.toString(ordermap.get("orderId"), "");
				String accountCode = ObjectUtils.toString(ordermap.get("accountCode"), "");
				String paymentCodeMD5 = ObjectUtils.toString(ordermap.get("paymentCodeMD5"), "");
				String ordertext = ValidateUtil.sortbyValues(mobileNum,
						productName, remark, appOrderId, sum,externalOrderId,
						externalOrderTime,peerAccountId,peerAccountUnit,accountCode,
						orderId,paymentCodeMD5);
				String ordermd5 = Md5Util.Encrypt(ordertext);
				String transordertext = ValidateUtil.sortbyValues(ordermd5,
						nonce, TIMESTAMP, appid);
				String transordermd5 = Md5Util.Encrypt(transordertext);
				if (StringUtils.isNotBlank(apppublickeystr)) {
					boolean success = false;
					try {
						PublicKey pkey = CertificateUtils.getPublicKeyFromCer(new ByteArrayInputStream(Base64.decodeBase64(apppublickeystr)));
						success = CertificateUtils.verifySignatureByPublicKey(transordertext.getBytes(), signature, pkey);
					} catch (Exception e) {
						if (log.isDebugEnabled()) {
							log.debug(String.format("app请求签名验签出错", e));
						}
					}
					rm.put("<p><p>●app公钥验签结果为",
							"(orderMD5+appId+tsig.timeStamp+tsig.nonce)明文="
									+ (transordertext) + ",验签结果为=" + false);
					rm.put("<p><p>●app公钥", apppublickeystr);

				}
				String certsign="";
				try {
					certsign=Base64.encodeBase64String(CertificateUtils.signByCert(transordertext.getBytes("utf8"), System.getProperty("web.root")+"/x.pfx", "accountant_portal"));
				} catch (Exception e) {
					e.printStackTrace();
					certsign="签名出错";
				}

				rm.put("<p><p>●订单明细签名",
						"(mobileNum+productName+remark+appOrderId+sum)明文="
								+ (ordertext) + ",Md5字典值=" + ordermd5);
				rm.put("<p><p>●帐户订单签名(MD5)",
						"(orderMD5+appId+tsig.timeStamp+tsig.nonce)明文="
								+ (transordertext) + ",Md5字典值=" + transordermd5);
				rm.put("<p><p>●帐户订单签名(证书,根目录下x.pfx密码123)",
						"(orderMD5+appId+tsig.timeStamp+tsig.nonce)明文="
								+ (transordertext) + ",证书私钥签名=" +certsign );
			}
			rm.put("<p><p>●appkey", appkey);
			// 登录key
			rm.put("<p><p>●登录key和获取验证码以及帐户操作接口",
					"(appid+appkey+nonce+TIMESTAMP)明文="
							+ (ValidateUtil.sortbyValues(appid, appkey,
									appnonce, appTIMESTAMP))
							+ ",Md5字典值="
							+ Md5Util.Encrypt(ValidateUtil.sortbyValues(appid,
									appkey, appnonce, appTIMESTAMP)));

		} catch (Exception e1) {
			// log.error(String.format("手机号码登录出错[%s]",loginvo),e1);
			rm.mergeException(e1);
		} finally {
			if (log.isDebugEnabled()) {
				log.debug("genkey完成");
			}
		}
		return rm;
	}
	
	/**
	 * 绑定银行卡接口
	 * 如果已绑定,则返回21210
	 * 其它错误 ,返回2120X
	 * @param getsmsvo
	 * @return
	 */
	@RequestMapping("/bind_bankCard")
	@AccessRequered(methodName="绑定银行卡")
	public @ResponseBody Object BindBankCard(HttpServletRequest request) {
		/*{"app":{"appId":"zjhtwallet","timeStamp":"TIMESTAMP", "nonce":"NONCE","signature":"SIGNATURE"},
			 "bind":{
        "mobileNum":"13912345678",
        "bankName":"建设银行",
        "branchName":"支行名称"
        "cardNo":"5600101981111123456",
        "name":"张三",
        "ID":"5600101981111123456"
    } */
		BankCardVO transorder;
		ResultModel rm = new ResultModel();
		String messagebase = "绑定银行卡";
		int baseerrcode=21200;
		rm.setBaseErrorCode(baseerrcode);
		rm.setErrmsg(messagebase+"成功");
		try {
			String jsonstr = RequestUtil.getRequestPostData(request);
			request.setAttribute("rawjson", jsonstr);
			transorder = RequestUtil.convertJson2Obj(jsonstr, BankCardVO.class);
			if (log.isDebugEnabled()) {
				log.debug(String.format("%s,参数为%s",messagebase,transorder));
			}
		} catch (Exception e) {
			log.error(String.format("%s获取订单参数出错",messagebase),e);
			rm.mergeException(ValidateException.ERROR_PARAM_FORMAT_ERROR.cloneAndAppend(null, messagebase+"参数"));
			return rm;
		}
		try {
			Object afterauth = authService.validateAuth(transorder);
			// 检验手机
			BindVO bind = transorder.getBind();
			ValidateUtil.validateMobile(bind.getMobileNum());
			User user = userSrv.getUserByMobile(bind.getMobileNum());
			if(user==null){
				if (log.isDebugEnabled()) {
					log.debug(String.format("手机号%s无法找到用户",bind.getMobileNum()));
				}
				throw ValidateException.ERROR_MATCH_USER; 
			}
//			 ValidateUtil.assertNull(user, "手机号码已注册", 4121);
			// 校验签名
			 if(user.getName()!=null){
				 if(!StringUtils.equals(user.getName(),bind.getName())){
					 if (log.isDebugEnabled()) {
							log.debug("用户名与注册用户名不同，无法绑定");
						}
					 throw new MaAccountException(MaAccountException.ERR_ACCOUNT_EXCEPTION, "用户名与注册用户名不一致");
				 }
			 }
			 if(user.getId()!=null){
				 if(!StringUtils.equals(user.getId(),bind.getID())){
					 if (log.isDebugEnabled()) {
							log.debug("用户身份证号不一致，无法绑定");
						}
					 throw new MaAccountException(MaAccountException.ERR_ACCOUNT_EXCEPTION, "用户身份证号不一致");
				 }
				}
			if (log.isDebugEnabled()) {
				log.debug("检验签名通过，验证验证码");
			}
			
			
			BindBankcard bankCard=new BindBankcard();
			bankCard.setArea(bind.getArea());
			bankCard.setBankName(bind.getBankName());
			bankCard.setBranchName(bind.getBranchName());
			bankCard.setCardNo(bind.getCardNo());
			bankCard.setUserId(user.getUserId());
			bankSer.creatBankCard(bankCard);
		} catch (Exception e1) {
			
			log.error(String.format("银行卡绑定失败[%s]", transorder), e1);
			rm.mergeException(e1);
		} finally {
			if (log.isDebugEnabled()) {
				log.debug("银行卡绑定完成");
			}
		}
		return rm;
	}
	
	/**
	 * 解除绑定银行卡接口
	 * 
	 * @param getsmsvo
	 * @return
	 */
	@RequestMapping("/unbind_bankCard")
	@AccessRequered(methodName="解除绑定银行卡")
	public @ResponseBody Object UnbindBankCard(@RequestBody UnbindCardVO transorder) {
		/*{
		    "app":{
		        "appId":"zjhtwallet","timeStamp":"TIMESTAMP","nonce":"NONCE","signature":"SIGNATURE"
		    },
		    "unbind":{
		        "mobileNum":"13912345678",
		        "cardNo":"5600101981111123456",
		    }
		} */ 
		if (log.isDebugEnabled()) {
			log.debug("解除绑定银行卡：" + transorder);
		}
		// 捕捉所有异常,不要由于有异常而不返回信息
		ResultModel rm = new ResultModel();
		int baseerrcode=21300;
		rm.setBaseErrorCode(baseerrcode);
		rm.setErrmsg("解除绑定银行卡成功");
		try {
			Object afterauth = authService.validateAuth(transorder);
			// 检验手机
			UnbindVO unbind = transorder.getUnbind();
			ValidateUtil.validateMobile(unbind.getMobileNum());
			User user = userSrv.getUserByMobile(unbind.getMobileNum());
			if(user==null){
				if (log.isDebugEnabled()) {
					log.debug(String.format("手机号%s无法找到用户",unbind.getMobileNum()));
				}
				throw ValidateException.ERROR_MATCH_USER; 
			}
			BindBankcard bindBankcard = bankSer.getCard(user.getUserId(),transorder.getUnbind().getCardNo());
			if(bindBankcard==null){
				 if (log.isDebugEnabled()) {
					 log.debug(String.format("解除绑定银行卡失败，无此银行卡[%s]的绑定记录",unbind.getCardNo()));
					 }
					 throw new MaAccountException(MaAccountException.ERR_BANKCARD_EXCEPTION,"无此银行卡的绑定记录");
			}
			
//			 if(user==null)
//			 {
//				 if (log.isDebugEnabled()) {
//				 log.debug(String.format("手机号码%s不存在",unbind.getMobileNum()));
//				 }
//				 throw ValidateException.ERROR_EXISTING_ACCOUNT_NOT_EXISTS;
//			 }
			 if(!bindBankcard.getUserId().equals(user.getUserId())){
				 if (log.isDebugEnabled()) {
					 log.debug(String.format("手机号码%s与银行卡号%s不匹配",unbind.getMobileNum(),unbind.getCardNo()));
					 }
					 throw ValidateException.ERROR_MATCH_USER;
			 }
			
			if (log.isDebugEnabled()) {
				log.debug("检验签名通过，验证验证码");
			}
			try{
				bankSer.delete(bindBankcard);
			}catch(Exception e){
				log.error(String.format("解除银行卡绑定失败[%s]", transorder), e);
				rm.mergeException(e);
			}
		} catch (Exception e1) {
			
			log.error(String.format("解除银行卡绑定失败[%s]", transorder), e1);
			rm.mergeException(e1);
		} finally {
			if (log.isDebugEnabled()) {
				log.debug("解除银行卡绑定完成");
			}
		}
		return rm;
	}

	/**
	 * 上传证件照片接口
	 * 
	 * @param getsmsvo
	 * @return
	 */
	@RequestMapping("/uploadPicture")
	@AccessRequered(methodName="上传证件照片接口")
	public @ResponseBody Object uploadPicture(@RequestBody UploadPictureVO transorder)  throws FileNotFoundException, IOException{
		
		/*{
			    "app":{
			        "appId":"zjhtwallet","timeStamp":"TIMESTAMP", "nonce":"NONCE","signature":"SIGNATURE"
			    },
			     "upload":{
		        "mobileNum":"13912345678",
		        "name":"张三",
		        "idType":"ID#",
		        "idCode":"441233343143432141413"
		        "picture":"BASE64后的图片",
		        "picture2":"BASE64后的图片"
		    }
			} */ 
		if (log.isDebugEnabled()) {
			log.debug("上传证件照片接口：" + transorder);
		}
		// 捕捉所有异常,不要由于有异常而不返回信息
		ResultModel rm = new ResultModel();
		int baseerrcode=26300;
		rm.setBaseErrorCode(baseerrcode);
		rm.setErrmsg("上传证件照片成功");
		try {
			Object afterauth = authService.validateAuth(transorder);
			// 检验手机
			UploadVO upload = transorder.getUpload();
			ValidateUtil.validateMobile(upload.getMobileNum());
			User user = userSrv.getUserByMobile(upload.getMobileNum());
			 if(user==null)
			 {
				 if (log.isDebugEnabled()) {
				 log.debug(String.format("手机号码%s不存在",upload.getMobileNum()));
				 }
				 throw ValidateException.ERROR_EXISTING_ACCOUNT_NOT_EXISTS;
			 }
			
			if (log.isDebugEnabled()) {
				log.debug("检验签名通过，验证验证码");
			}
			try{
				//存照片的路径
				String picturePath1="";
				String picturePath2="";
				//判断传了几张账片
				int size=0;
				
				if(!StringUtils.isBlank(upload.getPicture())){
					size+=1;
					picturePath1=base64StringToImage(upload.getPicture(),upload.getMobileNum(),1);
					
				}if(!StringUtils.isBlank(upload.getPicture2())){
					size+=1;
					picturePath2=base64StringToImage(upload.getPicture2(),upload.getMobileNum(),2);
				}
				//保存到数据库
				//从实名认证表中取出记录，暂时不做验证
				RealNameAuth realuser=realNamedao.selectByPrimaryKey(user.getUserId());
				//当传一张照片时 当传两张照片时，判断两个路径是否都存在
				if(realuser==null){
					
					if(StringUtils.isBlank(picturePath1)&&StringUtils.isBlank(picturePath2)){
						if (log.isDebugEnabled()) {
							 log.debug(String.format("请上传证件照片"));
						}
						throw ValidateException.ERROR_PARAM_NOTEXIST;
					}
					
					realuser=new RealNameAuth();
					realuser.setUserid(user.getUserId());
					realuser.setName(upload.getName());
					realuser.setIdtype(upload.getIdType());
					realuser.setIdcode(upload.getIdCode());
					realuser.setPicture(picturePath1);
					realuser.setPicture2(picturePath2);
					realuser.setStatus("CHK");
					realuser.setApplytime(new Date());
					realuser.setInserttime(new Date());
					realuser.setUpdatetime(new Date());
					realNamedao.insert(realuser);
					if (log.isDebugEnabled()) {
						log.debug("证件照片已上传");
					}
					
				}
				if(realuser.getStatus()=="OK#"){
					if (log.isDebugEnabled()) {
						 log.debug(String.format("该手机号码已通过实名验证",upload.getMobileNum()));
						 }
						 throw ValidateException.ERROR_USER_ALREADY_AUTHED;
				}
				if(!StringUtils.isBlank(picturePath1)||!StringUtils.isBlank(picturePath2)){
					realuser.setName(upload.getName());
					realuser.setIdtype(upload.getIdType());
					realuser.setIdcode(upload.getIdCode());
					realuser.setStatus("CHK");
					realuser.setPicture(picturePath1);
					realuser.setPicture2(picturePath2);
					realuser.setUpdatetime(new Date());
					realNamedao.updateByPrimaryKey(realuser);
					if (log.isDebugEnabled()) {
						log.debug("证件照片已上传");
					}
				}
			
			}catch(Exception e){
				log.error(String.format("上传证件照片失败[%s]", transorder), e);
				rm.mergeException(e);
			}
		} catch (Exception e1) {
			
			log.error(String.format("上传证件照片失败[%s]", transorder), e1);
			rm.mergeException(e1);
		} finally {
			
		}
		return rm;
		
	}
	
	
	
	/**
	 * 将base64转为byte[]，写入png文件
	 * @param base64String
	 * @param mobileNum
	 * @param i 一个用户可以有两张图片，i标识第一张还是第二张
	 * @return 返回保存的路径，保存失败时返回null
	 */
	private String base64StringToImage(String base64String,String mobileNum,Integer i) throws IOException{
	    java.util.Base64.Decoder decoder = java.util.Base64.getDecoder();
	    PropertiesUtil pu = new PropertiesUtil();
	    String photoPath = pu.getProperty("upload.filepath");
	    //文件名 日期+手机号+编号
		String filename = new SimpleDateFormat("yyyyMMdd").format(new Date())+mobileNum+i+".png";
		FileOutputStream fos=null;
		try {   
				//base64解码
				byte[] bytes1 = decoder.decode(base64String);                  
				
				File photoDir = new File(photoPath);
				//文件不存在就创建
				if(!photoDir.exists()){
					photoDir.mkdirs();
				}
				//创建字节流
	            fos = new FileOutputStream(new File(photoDir,filename));
	            //写入文件
	            fos.write(bytes1);
	            if (log.isDebugEnabled()) {
					log.debug("照片已保存%s"+photoPath+filename);
				}
	            //返回路径 modify by john ,实名认证时门户只需要文件名
	            return filename;
	        } catch (IOException e) {    
	        	log.error(String.format("文件写入失败"),e);   
	        }finally{
	        	//关闭流
	        	if (fos != null) {
	                try {
	                	fos.close();
	                } catch (IOException e1) {
	                    throw e1;
	                }
	            }      
	        }
		 return null;
	}

	
	
	/**
	 * 校验短信发送是否过于频繁,恶意发送短信
	 * @param appId
	 * @param mobileNum
	 * @throws MaAccountException
	 */
	protected void validateSmssend(String appId, String mobileNum)throws MaAccountException{
		SmsCodeValidateUtil.validateSmsCode(appId, mobileNum, new Date());
	}
	
	/**
	 * 在开始时记录日志
	 * @param transorder
	 * @param request
	 */
	protected void logWithBegin(String  appid,
			HttpServletRequest request) {
		AppLog al = applogTL.get();
		if(al==null){
			
			al = new AppLog();
			al.setAppid(appid);
			String requestURI = request.getRequestURI();
			requestURI=requestURI.replace(request.getContextPath(), "");
			al.setMethod(requestURI);
			al.setInserttime(new Date());
			String requestjson = ObjectUtils.toString(request.getAttribute("rawjson"));
			if(requestjson.length()>2000){
				requestjson = requestjson.substring(0,2000);
			}
			al.setRequest(requestjson);
		}
		logdao.insert(al);
		//通过本地线程绑定
		applogTL.set(al);
	}
	/**
	 * 处理完成更新日志
	 * @param transorder
	 * @param request
	 */
	protected void logWithFinish(ResultModel obj) {
		AppLog al = applogTL.get();
		if(al==null){
			//日志不存在
			return;
		}
		applogTL.remove();
		try {
			al.setRespone(StringUtils.substring((JsonUtil.convert2Json(obj)),0,2048));
		} catch (DataInvalidException e) {
			log.error(String.format("转换结果成json字符串失败"),e);
		}
		if (log.isDebugEnabled()) {
			log.debug(String.format("业务处理完成，返回结果为%S",al.getRespone()));
		}
		logdao.updateByPrimaryKey(al);
	}
	
	/**
	 * 判断拥有用户属性
	 * @param 
	 * @return
	 */
	@RequestMapping("/hasAttr")
	@AccessRequered(methodName="判断拥有用户属性")
	public @ResponseBody Object hasAttr(HttpServletRequest request) {
		UserAttrTransVO transorder;
		ResultModel rm = new ResultModel();
		String messagebase = "判断拥有用户属性";
		int baseerrcode=27800;
		rm.setBaseErrorCode(baseerrcode);
		rm.setErrmsg(messagebase+"成功");
		try {
			String jsonstr = RequestUtil.getRequestPostData(request);
			request.setAttribute("rawjson", jsonstr);
			transorder = RequestUtil.convertJson2Obj(jsonstr, UserAttrTransVO.class);
			if (log.isDebugEnabled()) {
				log.debug(String.format("判断拥有用户属性,参数为%s",transorder));
			}
		} catch (Exception e) {
			log.error(String.format("获取订单参数出错"),e);
			rm.mergeException(ValidateException.ERROR_PARAM_FORMAT_ERROR.cloneAndAppend(null, "订单参数"));
			return rm;
		}
		try {
			logWithBegin(transorder.getApp().getAppId(),request);
			Object afterauth = authService.validateAuth(transorder);
			if (log.isDebugEnabled()) {
				log.debug("检验签名通过，判断拥有用户属性");
			}
			UserAttr userAttr = userAttrSrv.getUserAttr(transorder.getBody().getMobileNum(),transorder.getBody().getAttr());
			rm.put("contain", userAttr!=null);
			
		} catch (Exception e1) {
			log.error(String.format("%s出错[%s]",messagebase, transorder), e1);
			rm.mergeException(e1);
		} finally {
			if (log.isDebugEnabled()) {
				log.debug(messagebase+"完成");
			}
			logWithFinish(rm);
		}
		return rm;
	}
	
	/**
	 * 批量添加用户接口
	 * @param 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/batchAddUser")
	@AccessRequered(methodName="批量添加用户")
	public @ResponseBody Object batchAddUser(HttpServletRequest request) {
		final long start = System.currentTimeMillis();
		BatchAddUserTransVO transorder;
		ResultModel rm = new ResultModel();
		String messagebase = "批量添加用户";
		int baseerrcode=27500;
		rm.setBaseErrorCode(baseerrcode);
		rm.setErrmsg(messagebase+"成功");
		try {
			String jsonstr = RequestUtil.getRequestPostData(request);
			request.setAttribute("rawjson", jsonstr);
			transorder = RequestUtil.convertJson2Obj(jsonstr, BatchAddUserTransVO.class);
			if (log.isDebugEnabled()) {
				log.debug(String.format("批量添加用户,参数为%s",transorder));
			}
		} catch (Exception e) {
			log.error(String.format("获取订单参数出错"),e);
			rm.mergeException(ValidateException.ERROR_PARAM_FORMAT_ERROR.cloneAndAppend(null, "订单参数"));
			return rm;
		}
		try {
			logWithBegin(transorder.getApp().getAppId(),request);
			Map afterauth = (Map) authService.validateAuth(transorder.getApp());
			AppInfo app = new AppInfo();
			app.setAppId(transorder.getApp().getAppId());
			app.setAppKey(ObjectUtils.toString(afterauth.get("appKey")));
			if (log.isDebugEnabled()) {
				log.debug("检验签名通过，批量添加用户");
			}
			//设置为异步
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					if (log.isDebugEnabled()) {
						log.debug(String.format("开启新线程批量添加用户"));
					}
					try{
						
						BatchProcessReporter<Map> processReporter = userSrv.createUserBatch(transorder.getBody(),app);
						BatchAddUserResult result = new BatchAddUserResult();
						result.setTotalRequest(processReporter.getTotal());
						result.setSuccessdRequest(processReporter.getSuccess());
//					result.setChannelNo(transorder.);
						result.setInsertTime(new Date());
						result.setErrMsg("导入完成");
						result.setStatus(CommonStatusConst.STATUS_OK);
						result.setSpentTime((int)(System.currentTimeMillis()-start)/1000);
						batchAdduserResultDao.insert(result);
//					rm.put("total", processReporter.getTotal());
//					rm.put("successed", processReporter.getSuccess());
//					List fails = new ArrayList();
						List<IndexObject<Map>> failmsgs = processReporter.getFailmsgs();
						for (Iterator iterator = failmsgs.iterator(); iterator.hasNext();) {
							IndexObject<Map> indexObject = (IndexObject<Map>) iterator
									.next();
//						fails.add(indexObject.getObject());
							BatchAddUserResultDetail detail = new BatchAddUserResultDetail();
							detail.setBatchId(result.getBatchId());
							detail.setMobileNum(ObjectUtils.toString(indexObject.getObject().get("mobileNum")));
							detail.setStatus(CommonStatusConst.STATUS_FAIL);
							detail.setErrMsg(StringUtils.substring(ObjectUtils.toString(indexObject.getObject().get("msg")),0,100));
							batchAdduserResultDetailDao.insert(detail);
						}
	//					rm.put("fails", fails);
					}
					catch(Exception e){
						log.error("批量添加用户出错",e);
						BatchAddUserResult result = new BatchAddUserResult();
						result.setTotalRequest(transorder.getBody().getUsers().size());
						result.setSuccessdRequest(0);
						result.setInsertTime(new Date());
						result.setStatus(CommonStatusConst.STATUS_FAIL);
						result.setErrMsg(StringUtils.substring(e.getMessage(),0,100));
						result.setSpentTime((int)(System.currentTimeMillis()-start)/1000);
						batchAdduserResultDao.insert(result);
					}
					if (log.isDebugEnabled()) {
						log.debug(String.format("开启新线程批量添加用户完成"));
					}
				}
			}).start();
			rm.setErr(0, "接收导入请求完成");
			
		} catch (Exception e1) {
			log.error(String.format("%s出错[%s]",messagebase, transorder), e1);
			rm.mergeException(e1);
		} finally {
			if (log.isDebugEnabled()) {
				log.debug(messagebase+"完成");
			}
			logWithFinish(rm);
		}
		return rm;
	}
	
	
	/**
     * 验证用户支付密码Md5
     * @return
     */
    @RequestMapping(value="/verify_paymentCodeMD5",method=RequestMethod.POST)
    @AccessRequered(methodName="验证用户支付密码MD5")
    public @ResponseBody Object verifyPaymentCodeMD5(HttpServletRequest request) {
        /*{
            "app":{"appId":"dianziquan","timeStamp":"TIMESTAMP", "nonce":"NONCE","signature":"MD5(appId+appKey+nonce+timeStamp)"},
            "verifly":{
                 mobileNum:"12345678911",
                "paymentCodeMd5":"DES(paymentCodeMd5)"
            }
        } */ 
        ResultModel rm = new ResultModel();
        int baseerrcode = 27201;
        rm.setBaseErrorCode(baseerrcode);
        rm.setErrmsg("验证用户支付密码成功");
        VerifyPaymentCodeMD5BaseVO transorder;
        try {
            String jsonstr = RequestUtil.getRequestPostData(request);
            request.setAttribute("rawjson", jsonstr);
            transorder = RequestUtil.convertJson2Obj(jsonstr, VerifyPaymentCodeMD5BaseVO.class);
            if (log.isDebugEnabled()) {
                log.debug(String.format("验证用户支付密码MD5,参数为%s",transorder));
            }
        } catch (Exception e) {
            log.error(String.format("获取订单参数出错"),e);
            rm.mergeException(ValidateException.ERROR_PARAM_FORMAT_ERROR.cloneAndAppend(null, "订单参数"));
            return rm;
        }
        if (log.isDebugEnabled()) {
            log.debug("验证用户支付密码");
        }
        // 捕捉所有异常,不要由于有异常而不返回信息
        
        try{
            logWithBegin(transorder.getApp().getAppId(),request);
            // 检验手机
            ValidateUtil.validateMobile(transorder.getMobileNum());
            User user = userSrv.getUserByMobile(transorder.getMobileNum());
            if(user==null){
                if (log.isDebugEnabled()) {
                    log.debug(String.format("根据手机号%s查询用户，用户不存在，无法获取支付密码",transorder.getMobileNum()));
                }
                throw ValidateException.ERROR_EXISTING_USER_NOT_EXISTS;
            }
            //获取Appkey
            Map validateAuth = (Map) authService.validateAuth(transorder);
            String appkey = ObjectUtils.toString(validateAuth.get("appKey"));
            //对密码进行解密,
            boolean paymentencrypt = new PropertiesUtil().getBoolean("set_paymentcode.payment.encrypt",false);
            //尝试进行解密
            String decpcdes="";//现在是明文
            if(StringUtils.isNotBlank(transorder.getVerify().getPaymentCodeMD5())){
                try {
                    decpcdes = DESUtil.decodeDES(transorder.getVerify().getPaymentCodeMD5(), appkey);
                } catch (Exception e) {
                    log.error(String.format("PaymentCodeDES解密出错"),e);
                    if(paymentencrypt){
                        throw ValidateException.ERROR_PARAM_FORMAT_ERROR.clone(e,"支付密码DES解密错误");
                    }
                }
            }
            //String dec=PospEncyptUtil.encypt(decpcdes, transorder.getMobileNum());
            //String dec = Md5Util.Encrypt(decpcdes);
            String dec=decpcdes;
            if(StringUtils.isBlank(dec)||!dec.equals(user.getPaymentcodemd5())){
                if (log.isDebugEnabled()) {
                    log.debug(String.format("验证用户支付密码失败，密码不符",transorder.getVerify().getPaymentCodeMD5()));
                }
                throw ValidateException.ERROR_MATCH_PASSWORD;
            }
        }catch(Exception e1){
            log.error(String.format("验证用户支付密码MD5[%s]，处理失败", transorder), e1);
            rm.mergeException(e1);
        } finally {
            logWithFinish(rm);
        }
        
        return rm;
    }
    
    /**
     * 注册接口(只需要一个手机号即可，其他参数不需要)
     * 
     * @param getsmsvo
     * @return
     */
    @RequestMapping("/registerByPhoneOnly")
    @AccessRequered(methodName="用户根据手机号注册")
    public @ResponseBody Object registerByPhoneOnly(@RequestBody RegistByPhoneOnlyVO registvo) {
        //{"app":{"appId":"zjhtwallet","timeStamp":"TIMESTAMP", "nonce":"NONCE","signature":"SIGNATURE"},
        //"regist":{"mobileNum":"13912345678","attrs":[asdffs,asdfasf]}} 
        if (log.isDebugEnabled()) {
            log.debug("新用户注册：" + registvo);
        }
        // 捕捉所有异常,不要由于有异常而不返回信息
        ResultModel rm = new ResultModel();
        int baseerrcode = 20501;
        rm.setBaseErrorCode(baseerrcode);
        rm.setErrmsg("新用户注册成功");
        try {
            // 验证APP签名
            authService.validateAuth(registvo);
            // 检验手机
            ValidateUtil.validateMobile(registvo.getMobileNum());
            log.debug("检验签名通过");
            User user = userSrv.getUserByMobile(registvo.getMobileNum());
            //用户已存在则直接抛异常
            if(user!=null){
                throw ValidateException.ERROR_EXISTING_USER_EXISTS;
            }
            // 创建用户
            user=new User();
            user.setMobilenum(registvo.getMobileNum());
            user.setLoginType(LoginType.MOBILE.name());
            user.setOrgType(OrgType.PERSONAL.name());
            user.setInsertTime(new Date());
            String pwd="888888";
            user.setPassword(Md5Util.Encrypt(pwd));
            //插入新用户到数据库
            userSrv.createUser(user,registvo.getAppId());
            //发送短信
            sendRegisterByPhoneOnlyMessage(registvo,pwd);
            //生成token
            UserToken selectToken = tokensrv.getOrCreateToken(registvo.getApp().getAppId(), user.getUserId());
            rm.put("token", selectToken.getToken());
            rm.put("expireIn", selectToken.getExpirein());
            rm.put("orgType", user.getOrgType());
            rm.put("registerTime", user.getInsertTime());
            // 创建帐户，避免新开其它帐户时，旧用户没有创建该帐户而造成问题
            AccountFactory.createAccounts(user.getUserId());
            
        } catch (Exception e1) {
            log.error(String.format("新用户注册出错[%s]", registvo), e1);
            rm.mergeException(e1);
        } finally {
            if (log.isDebugEnabled()) {
                log.debug("新用户注册完成");
            }
        }
        return rm;
    }
    
    private void sendRegisterByPhoneOnlyMessage(RegistByPhoneOnlyVO registvo,String pwd){
    	if("false".equals(registvo.getRegist().getSend())){
    		log.info("本次自动注册不需要发送短信...");
    		return;
    	}
        //发送短信
        String content="尊敬的用户您好，您已在中经油马成功开通了会员。登录账户为%s，登录密码为%s，请妥善保管。为了安全，请尽快登录中经油马APP进行密码修改，详询可咨询4006630666。";
        content=String.format(content, registvo.getMobileNum(),pwd);
        try {
            
            ResultModel resultModel = smsSender.send(registvo.getMobileNum(), content,registvo.getAppId(), SmsSendService.ACTION_NAME_REGISTER);
            
            if (resultModel != null && resultModel.isSuccessed()) {
                log.debug("短信发送成功");
            } else {
                log.debug("短信发送失败," + resultModel != null ? resultModel.getErrmsg() : "短信访问出错");
            }
        
        } catch (Exception e) {
            log.error("发送短信失败",e);
        }
    }
    
}
