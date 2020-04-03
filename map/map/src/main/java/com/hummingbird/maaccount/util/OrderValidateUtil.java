/**
 * 
 * OrderValidateUtil.java
 * 版本所有 深圳市蜂鸟娱乐有限公司 2013-2014
 */
package com.hummingbird.maaccount.util;

import org.apache.commons.lang.StringUtils;

import com.hummingbird.common.exception.DataInvalidException;
import com.hummingbird.common.exception.ValidateException;
import com.hummingbird.common.util.PropertiesUtil;
import com.hummingbird.common.util.SpringBeanUtil;
import com.hummingbird.common.util.ValidateUtil;
import com.hummingbird.common.vo.ValidateResult;
import com.hummingbird.maaccount.entity.UserAccountCode;
import com.hummingbird.maaccount.service.PaymentService;
import com.hummingbird.maaccount.vo.AccountCodeable;
import com.hummingbird.maaccount.vo.DefaultPaymentCode;
import com.hummingbird.maaccount.vo.PaymentCodeMD5able;
import com.hummingbird.maaccount.vo.PaymentcodeSetting;
import com.hummingbird.maaccount.vo.TransOrderVO;
import com.hummingbird.maaccount.vo.TransOrderVO2;

/**
 * @author huangjiej_2
 * 2014年12月28日 上午10:35:14
 * 本类主要做为
 */
public abstract class OrderValidateUtil {

	static org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory
			.getLog(OrderValidateUtil.class);
	/**
	 * 验证订单
	 * @param transorder
	 * @return
	 * @throws DataInvalidException
	 */
	public static ValidateResult validateOrder(TransOrderVO transorder)throws DataInvalidException {
		//订单金额不能小于0
		if(transorder.getOrder().getSum()!=null&&transorder.getOrder().getSum()<0)
			throw ValidateException.ERROR_PARAM_FORMAT_ERROR.clone(null, "金额不能为负数");
		//ValidateUtil.assertNull(transorder.getOrder().get, name);
		if(transorder.isStrictCheck()){
			if (log.isDebugEnabled()) {
				log.debug(String.format("设置为验证帐户验证码和支付码"));
			}
			//通过配置文件实现配置是否严格检查帐户验证码和支付码,默认为否
			boolean strictcheck = "true".equalsIgnoreCase(new PropertiesUtil().getProperty("validate.strictcheck."+transorder.getMethod()));
			if(!strictcheck){
				//如果有值就较验，无值就不管
				ValidateResult vr=null;
				if(StringUtils.isNotBlank(transorder.getOrder().getAccountCode())){
					vr=validateUserAccountCode(transorder);
				}
				if((vr==null||vr.isValidate())&&StringUtils.isNotBlank(transorder.getOrder().getPaymentCodeMD5())){
					vr=validatePaymentCode(transorder);
				}
				if(vr==null){
					vr = new ValidateResult();
					vr.setValidateMsg("订单验证通过");
				}
				return vr;
			}
			else{
				//支付时验证帐户验证码和支付密码
				ValidateResult vr = validateUserAccountCode(transorder);
				if(vr.isValidate()){
					vr=validatePaymentCode(transorder);
					if(vr.isValidate()){
						vr = new ValidateResult();
						vr.setValidateMsg("订单验证通过");
						return vr;
					}
				}
			}
			throw ValidateException.ERROR_MATCH_VALIDATECODE;
		}
		else{
			if (log.isDebugEnabled()) {
				log.debug(String.format("设置为不验证帐户验证码和支付码"));
			}
			ValidateResult vr = new ValidateResult();
			vr.setValidateMsg("订单验证通过");
			return vr;
		}
		
	}
	
	/**
	 * @param transorder
	 * @return 
	 * @throws DataInvalidException 
	 */
	private static ValidateResult validatePaymentCode(TransOrderVO transorder) throws DataInvalidException {
		PaymentService paymentService = SpringBeanUtil.getInstance().getBean(PaymentService.class);
		ValidateResult vr = paymentService.validatePaymentCode(new DefaultPaymentCode(transorder.getOrder().getMobileNum(), transorder.getOrder().getPaymentCodeMD5()));
		return vr;
	}

	/**
	 * @param transorder
	 * @return 
	 * @throws DataInvalidException 
	 */
	private static ValidateResult validateUserAccountCode(TransOrderVO transorder) throws DataInvalidException {
		//modify by john ,添加开关处理,可以校验或不校验
		boolean skipvalidate = new PropertiesUtil().getBoolean("validate.skip.accountcode."+transorder.getMethod(), false);
		if(skipvalidate){
			if (log.isDebugEnabled()) {
				log.debug(String.format("设置跳过帐户校验码"+transorder.getMethod()));
			}
			ValidateResult vr = new ValidateResult();
			vr.setValidate(true);
			return vr;
		}
		PaymentService paymentService = SpringBeanUtil.getInstance().getBean(PaymentService.class);
		UserAccountCode uac = new UserAccountCode();
		uac.setAppId(transorder.getApp().getAppId());
		uac.setMobileNum(transorder.getOrder().getMobileNum());
		uac.setSmscode(transorder.getOrder().getAccountCode());
		ValidateResult vr = paymentService.validateAccountCode(uac);
		return vr;
	}

	/**
	 * 检验appid
	 * @param appId
	 * @return
	 * @throws DataInvalidException
	 */
	public static ValidateResult validateOrder(String appId)throws DataInvalidException {
		return null;
		
	}

	/**
	 * @param transorder
	 * @throws DataInvalidException 
	 */
	public static ValidateResult validateOrder(TransOrderVO2 transorder) throws DataInvalidException {
		//订单金额不能小于0
//				if(transorder.getOrder().getSum()!=null&&transorder.getOrder().getSum()<0)
//					throw ValidateException.ERROR_PARAM_FORMAT_ERROR.clone(null, "金额不能为负数");
//				//ValidateUtil.assertNull(transorder.getOrder().get, name);
				if(transorder.isStrictCheck()){
					if (log.isDebugEnabled()) {
						log.debug(String.format("设置为验证帐户验证码和支付码"));
					}
					boolean strictcheck = "true".equalsIgnoreCase(new PropertiesUtil().getProperty("validate.strictcheck"));
					if(!strictcheck){
						//如果有值就校验，无值就不管
							ValidateResult vr=null;
							vr=validateUserAccountCode(transorder);
							if((vr==null||vr.isValidate())){
								vr=validatePaymentCode(transorder);
							}
							if(vr==null){
								vr = new ValidateResult();
								vr.setValidateMsg("订单验证通过");
							}
							return vr;
					}
					else{
						//支付时验证帐户验证码和支付密码
						ValidateResult vr = validateUserAccountCode(transorder);
						if(vr==null||vr.isValidate()){
							vr=validatePaymentCode(transorder);
							if(vr==null||vr.isValidate()){
								vr = new ValidateResult();
								vr.setValidateMsg("订单验证通过");
								return vr;
							}
						}
					}
					throw ValidateException.ERROR_MATCH_VALIDATECODE;
				}
				else{
					if (log.isDebugEnabled()) {
						log.debug(String.format("设置为不验证帐户验证码和支付码"));
					}
					ValidateResult vr = new ValidateResult();
					vr.setValidateMsg("订单验证通过");
					return vr;
				}
//		return null;
	}

	/**
	 * @param transorder
	 * @return
	 * @throws DataInvalidException 
	 */
	private static ValidateResult validatePaymentCode(TransOrderVO2 transorder) throws DataInvalidException {
		if (transorder.getOrder() instanceof PaymentCodeMD5able) {
			PaymentCodeMD5able paymentmd5 = (PaymentCodeMD5able) transorder.getOrder();
			
			PaymentService paymentService = SpringBeanUtil.getInstance().getBean(PaymentService.class);
			ValidateResult vr = paymentService.validatePaymentCode(new DefaultPaymentCode(paymentmd5.getMobileNum(),paymentmd5.getPaymentCodeMD5()));
			return vr;
		}
		return null;
	}

	/**
	 * @param transorder
	 * @return
	 * @throws DataInvalidException 
	 */
	private static ValidateResult validateUserAccountCode(
			TransOrderVO2 transorder) throws DataInvalidException {
		if (transorder.getOrder() instanceof AccountCodeable) {
			//modify by john ,添加开关处理,可以校验或不校验
			boolean skipvalidate = new PropertiesUtil().getBoolean("validate.skip.accountcode."+transorder.getMethod(), false);
			if(skipvalidate){
				if (log.isDebugEnabled()) {
					log.debug(String.format("设置跳过帐户校验码"+transorder.getMethod()));
				}
				ValidateResult vr = new ValidateResult();
				vr.setValidate(true);
				return vr;
			}
			AccountCodeable ac = (AccountCodeable) transorder.getOrder();
			PaymentService paymentService = SpringBeanUtil.getInstance().getBean(PaymentService.class);
			UserAccountCode uac = new UserAccountCode();
			uac.setAppId(transorder.getApp().getAppId());
			uac.setMobileNum(ac.getMobileNum());
			uac.setSmscode(ac.getAccountCode());
			ValidateResult vr = paymentService.validateAccountCode(uac);
			return vr;
		}
		return null;
	}
	
	
}
