package com.hummingbird.maaccount.vo;


import com.hummingbird.commonbiz.vo.AppMobileDecidable;


/**
 * 获取短信的输入参数
 * @author huangjiej_2
 * 2014年10月18日 上午9:36:53
 */
public class ValidateSmsMessageVo extends AppBaseVO implements AppMobileDecidable,AccountCodeRequest {
	
	private String mobileNum;
	
	private String smsCode;
	

	@Override
	public String getMobileNum() {
		return mobileNum;
	}

	public void setMobileNum(String mobileNum) {
		this.mobileNum = mobileNum;
	}

	public String getAppId() {
		return app.getAppId();
	}
	
	public String getSmsCode() {
		return smsCode;
	}
	
	public void setSmsCode(String smsCode) {
		this.smsCode = smsCode;
	}

	@Override
	public String toString() {
		return "ValidateSmsMessageVo [mobileNum=" + mobileNum + ", smsCode=" + smsCode + ", app=" + app + "]";
	}
	
	
	
	
}
