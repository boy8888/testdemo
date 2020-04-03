/**
 * 
 * PaymentService.java
 * 版本所有 深圳市蜂鸟娱乐有限公司 2013-2014
 */
package com.hummingbird.maaccount.service;

import com.hummingbird.common.exception.DataInvalidException;
import com.hummingbird.common.vo.ValidateResult;
import com.hummingbird.commonbiz.vo.UserToken;
import com.hummingbird.maaccount.entity.User;
import com.hummingbird.maaccount.entity.UserAccountCode;
import com.hummingbird.maaccount.vo.AccountCodeRequest;
import com.hummingbird.maaccount.vo.DefaultPaymentCode;
import com.hummingbird.maaccount.vo.PaymentCodeSettingVO;
import com.hummingbird.maaccount.vo.PaymentcodeSetting;

/**
 * @author huangjiej_2
 * 2015年1月18日 下午4:41:25
 * 本类主要做为 支付相关的service
 */
public interface PaymentService {

	/**
	 * 生成验证码
	 * @param request
	 * @return
	 */
	public UserAccountCode genAccountCode(AccountCodeRequest request);
	
	/**
	 * 验证验证码
	 * @param accountcode
	 * @return
	 * @throws DataInvalidException 
	 */
	public ValidateResult validateAccountCode(UserToken token) throws DataInvalidException;

	/**
	 * 验证支付码
	 * @param paymentCode
	 * @return
	 * @throws DataInvalidException 
	 */
	public ValidateResult validatePaymentCode(PaymentcodeSetting paymentCode) throws DataInvalidException;

	/**
	 * 保存支付码
	 * @param user 
	 */
	public void savePaymentCode(User user, PaymentcodeSetting paymentcode);
	
	
	/**
	 * 验证账户验证码！真实去对比验证码的。
	 * @param accountcode
	 * @return
	 * @throws DataInvalidException
	 */
	public ValidateResult validateAccountCodeTrue(UserToken accountcode) throws DataInvalidException;
}
