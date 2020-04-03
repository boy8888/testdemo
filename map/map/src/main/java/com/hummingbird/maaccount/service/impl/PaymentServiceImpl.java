/**
 * 
 * PaymentServiceImpl.java
 * 版本所有 深圳市蜂鸟娱乐有限公司 2013-2014
 */
package com.hummingbird.maaccount.service.impl;

import java.text.DecimalFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hummingbird.common.exception.DataInvalidException;
import com.hummingbird.common.exception.ValidateException;
import com.hummingbird.common.util.PropertiesUtil;
import com.hummingbird.common.util.ValidateUtil;
import com.hummingbird.common.vo.ValidateResult;
import com.hummingbird.commonbiz.vo.UserToken;
import com.hummingbird.maaccount.entity.User;
import com.hummingbird.maaccount.entity.UserAccountCode;
import com.hummingbird.maaccount.mapper.UserAccountCodeMapper;
import com.hummingbird.maaccount.mapper.UserMapper;
import com.hummingbird.maaccount.service.PaymentService;
import com.hummingbird.maaccount.vo.AccountCodeRequest;
import com.hummingbird.maaccount.vo.PaymentcodeSetting;

/**
 * @author huangjiej_2
 * 2015年1月18日 下午4:58:43
 * 本类主要做为 支付相关实现
 */
@Service
public class PaymentServiceImpl implements PaymentService {

	org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory
			.getLog(this.getClass());
	@Autowired
	UserAccountCodeMapper mapper;
	@Autowired(required = true)
	private UserMapper userDao;
	
	
	/* (non-Javadoc)
	 * @see com.hummingbird.maaccount.service.PaymentService#genAccountCode(com.hummingbird.maaccount.vo.AccountCodeRequest)
	 */
	@Override
	public UserAccountCode genAccountCode(AccountCodeRequest request) {
		//如果已经存在，会清除并重新生成
		UserAccountCode ac = mapper.selectUserAccountCode(request);
		if(ac!=null){
			mapper.deleteByPrimaryKey(ac.getIdtUserAccountcode());
		}
		String authCode = new DecimalFormat("0000000000").format(Math.random()*10000000000L).substring(0,6);
		PropertiesUtil pu = new PropertiesUtil();
		boolean accountcodefortest = pu.getBoolean("accountcode.fortest");
		String testcode = pu.getProperty("accountcode.code.fortest");
		if(accountcodefortest&&testcode!=null){
			if (log.isDebugEnabled()) {
				log.debug(String.format("测试开启，帐户验证码指定为%s",testcode));
			}

			authCode=testcode;
		}
		
		UserAccountCode uac = new UserAccountCode();
		uac.setAppId(request.getAppId());
		uac.setMobileNum(request.getMobileNum());
		uac.setExpirein(60);
		uac.setSendtime(new Date());
		uac.setSmscode(authCode);
		mapper.insert(uac);
		return uac;
	}

	/* (non-Javadoc)
	 * @see com.hummingbird.maaccount.service.PaymentService#validateAccountCode(com.hummingbird.maaccount.entity.UserAccountCode)
	 */
	@Override
	public ValidateResult validateAccountCode(UserToken	 accountcode) throws DataInvalidException {
		UserAccountCode ac = mapper.selectUserAccountCode(accountcode);
		if (log.isDebugEnabled()) {
			log.debug(String.format("根据app[%s],mobile[%s]查询帐户验证码",accountcode.getAppId(),accountcode.getMobileNum()));
		}
		if(ac==null){
			if (log.isDebugEnabled()) {
				log.debug(String.format("帐户验证码不存在"));
			}
			throw ValidateException.ERROR_MATCH_VALIDATECODE;
		}
		//删除记录
		mapper.deleteByPrimaryKey(ac.getIdtUserAccountcode());
		//是否超时
		if((ac.getSendtime().getTime()+ac.getExpirein()*1000)<System.currentTimeMillis()){
			if (log.isDebugEnabled()) {
				log.debug(String.format("帐户验证码已超时"));
			}
			throw ValidateException.ERROR_MATCH_VALIDATECODE;
		}
		ValidateResult vr = new ValidateResult();
		vr.setValidateMsg("验证通过");
		
		return vr;
	}

	/* (non-Javadoc)
	 * @see com.hummingbird.maaccount.service.PaymentService#validatePaymentCode(com.hummingbird.maaccount.entity.UserAccountCode)
	 */
	@Override
	public ValidateResult validatePaymentCode(PaymentcodeSetting paymentCode) throws DataInvalidException {
		
		User user = userDao.selectByMobile(paymentCode.getMobileNum());
		ValidateUtil.assertNull(user, "用户");
		if (log.isDebugEnabled()) {
			log.debug(String.format("验证用户%s与传入的支付码%s是否一致",paymentCode.getMobileNum(),paymentCode.getPaymentCodeMD5()));
		}
		ValidateUtil.assertNotEqual(user.getPaymentcodemd5(), paymentCode.getPaymentCodeMD5(),"支付密码不正确", ValidateException.ERROR_MATCH_VALIDATECODE.getErrcode());
		ValidateResult vr = new ValidateResult();
		vr.setValidateMsg("支付验证码验证成功");
		return vr;
	}
	
	/**
	 * 保存支付码
	 */
	public void savePaymentCode(User user,PaymentcodeSetting paymentcode){
		if(StringUtils.isNotBlank(paymentcode.getPaymentCodeMD5())){
//			if (log.isDebugEnabled()) {
//				log.debug(String.format("设置md5:"+paymentcode.getPaymentCodeMD5()));
//			}
			user.setPaymentcodemd5(paymentcode.getPaymentCodeMD5());
		}
		if(StringUtils.isNotBlank(paymentcode.getPaymentCodeDES())){
//			if (log.isDebugEnabled()) {
//				log.debug(String.format("设置des:"+paymentcode.getPaymentCodeDES()));
//			}
			user.setPaymentCodeDES(paymentcode.getPaymentCodeDES());
		}
		user.setUpdateTime(new Date());
		userDao.updateByPrimaryKey(user);
	}
	
	public static void main(String[] args) {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				String authCode = new DecimalFormat("0000000000").format(Math.random()*10000000000L).substring(0,6);
				System.out.println("生成的验证码是"+authCode);
			}
		}).start();
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				String authCode = new DecimalFormat("0000000000").format(Math.random()*10000000000L).substring(0,6);
				System.out.println("生成的验证码是"+authCode);
			}
		}).start();
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				String authCode = new DecimalFormat("0000000000").format(Math.random()*10000000000L).substring(0,6);
				System.out.println("生成的验证码是"+authCode);
			}
		}).start();
	}

    @Override
    public ValidateResult validateAccountCodeTrue(UserToken accountcode) throws DataInvalidException {
        UserAccountCode ac = mapper.selectUserAccountCode(accountcode);
        if (log.isDebugEnabled()) {
            log.debug(String.format("根据app[%s],mobile[%s]查询帐户验证码",accountcode.getAppId(),accountcode.getMobileNum()));
        }
        if(ac==null){
            if (log.isDebugEnabled()) {
                log.debug(String.format("帐户验证码不存在"));
            }
            throw ValidateException.ERROR_MATCH_VALIDATECODE;
        }
        
        //判断验证码是否正确
        String dbAccountCode=ac.getSmscode();
        String appAccountCode=accountcode==null?null:accountcode.getToken();
        if(StringUtils.isBlank(dbAccountCode)||StringUtils.isBlank(appAccountCode)||!dbAccountCode.equals(appAccountCode)){
            if (log.isDebugEnabled()) {
                log.debug(String.format("帐户验证码不正确"));
            }
            throw ValidateException.ERROR_MATCH_VALIDATECODE;
        }
        
        //删除记录
        mapper.deleteByPrimaryKey(ac.getIdtUserAccountcode());
        //是否超时
        if((ac.getSendtime().getTime()+ac.getExpirein()*1000)<System.currentTimeMillis()){
            if (log.isDebugEnabled()) {
                log.debug(String.format("帐户验证码已超时"));
            }
            throw ValidateException.ERROR_MATCH_VALIDATECODE;
        }
        ValidateResult vr = new ValidateResult();
        vr.setValidateMsg("验证通过");
        
        return vr;
    }

}
