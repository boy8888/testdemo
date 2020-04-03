package com.hummingbird.maaccount.vo;

public class ResetPaymentCodeVO {
	/*"reset":{
	    "mobileNum":"13912345678","smsCode":"123456", "newPaymentCodeDES":"DES(12345678)"
	}*/
	protected String mobileNum;
	protected String smsCode;
	protected String newPaymentCodeDES;
	/**
	 * md5 登录密码
	 */
	protected String password;
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ResetPaymentCodeVO [mobileNum=" + mobileNum + ", smsCode=" + smsCode + ", newPaymentCodeDES="
				+ newPaymentCodeDES + ", password=" + password + "]";
	}
	public String getMobileNum() {
		return mobileNum;
	}
	public void setMobileNum(String mobileNum) {
		this.mobileNum = mobileNum;
	}
	public String getSmsCode() {
		return smsCode;
	}
	public void setSmsCode(String smsCode) {
		this.smsCode = smsCode;
	}
	public String getNewPaymentCodeDES() {
		return newPaymentCodeDES;
	}
	public void setNewPaymentCodeDES(String newPaymentCodeDES) {
		this.newPaymentCodeDES = newPaymentCodeDES;
	}
	/**
	 * md5 登录密码 
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * md5 登录密码 
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
}
