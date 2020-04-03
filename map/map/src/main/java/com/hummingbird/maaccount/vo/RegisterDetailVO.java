/**
 * 
 * RegisterDetailVO.java
 * 版本所有 深圳市蜂鸟娱乐有限公司 2013-2014
 */
package com.hummingbird.maaccount.vo;

/**
 * @author john huang
 * 2015年2月5日 上午11:12:27
 * 本类主要做为 注册明细对象
 */
public class RegisterDetailVO extends SmsCodeVo {

	/**
	 * 登录密码
	 */
	protected String password;
	/**
	 * 支付密码
	 */
	protected String paymentCodeMD5;
	
	/**
	 * 用户属性
	 */
	protected String[] attrs;
	/**
	 * 登录密码
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * 登录密码
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * 支付密码
	 */
	public String getPaymentCodeMD5() {
		return paymentCodeMD5;
	}
	/**
	 * 支付密码
	 */
	public void setPaymentCodeMD5(String paymentCodeMD5) {
		this.paymentCodeMD5 = paymentCodeMD5;
	}
	
	
	
	public String[] getAttrs() {
		return attrs;
	}
	public void setAttrs(String[] attrs) {
		this.attrs = attrs;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "RegisterDetailVO [password=" + password + ", paymentCodeMD5="
				+ paymentCodeMD5 + ", getPassword()=" + getPassword()
				+ ", getPaymentCodeMD5()=" + getPaymentCodeMD5() + ",attrs ="+attrs+ "]";
	}
	
	
}
