/**
 * 
 * DefaultPaymentCode.java
 * 版本所有 深圳市蜂鸟娱乐有限公司 2013-2014
 */
package com.hummingbird.maaccount.vo;

/**
 * @author huangjiej_2
 * 2015年1月20日 上午8:49:59
 * 本类主要做为
 */
public class DefaultPaymentCode implements PaymentcodeSetting {

	private String paymentCodeMD5;
	
	private String mobileNum;
	
	/**
	 * 加密的支付密码
	 */
	private String paymentCodeDES;
	
	/* (non-Javadoc)
	 * @see com.hummingbird.maaccount.vo.PaymentcodeSetting#getMobileNum()
	 */
	@Override
	public String getMobileNum() {
		return mobileNum;
	}

	/* (non-Javadoc)
	 * @see com.hummingbird.maaccount.vo.PaymentcodeSetting#getPaymentCodeMD5()
	 */
	@Override
	public String getPaymentCodeMD5() {
		return paymentCodeMD5;
	}

	public DefaultPaymentCode() {
		super();
	}

	public DefaultPaymentCode(String mobileNum, String paymentCodeMD5) {
		super();
		this.mobileNum = mobileNum;
		this.paymentCodeMD5 = paymentCodeMD5;
	}

	/**
	 * @param paymentCodeMD5 the paymentCodeMD5 to set
	 */
	public void setPaymentCodeMD5(String paymentCodeMD5) {
		this.paymentCodeMD5 = paymentCodeMD5;
	}

	public DefaultPaymentCode(String mobileNum, String paymentCodeMD5,
			String paymentCodeDES) {
		super();
		this.mobileNum = mobileNum;
		this.paymentCodeMD5 = paymentCodeMD5;
		this.paymentCodeDES = paymentCodeDES;
	}

	/**
	 * @param mobileNum the mobileNum to set
	 */
	public void setMobileNum(String mobileNum) {
		this.mobileNum = mobileNum;
	}

	/**
	 * @return the paymentCodeDES
	 */
	public String getPaymentCodeDES() {
		return paymentCodeDES;
	}

	/**
	 * @param paymentCodeDES the paymentCodeDES to set
	 */
	public void setPaymentCodeDES(String paymentCodeDES) {
		this.paymentCodeDES = paymentCodeDES;
	}

}
