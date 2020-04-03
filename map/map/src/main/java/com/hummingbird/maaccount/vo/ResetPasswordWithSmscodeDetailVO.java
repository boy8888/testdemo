/**
 * 
 * ResetPasswordDetailVO.java
 * 版本所有 深圳市蜂鸟娱乐有限公司 2013-2014
 */
package com.hummingbird.maaccount.vo;

import java.util.Map;

import com.hummingbird.common.exception.SignatureException;
import com.hummingbird.common.util.SignatureUtil;
import com.hummingbird.common.vo.Signaturable;
import com.hummingbird.commonbiz.vo.Decidable;

/**
 * @author john huang
 * 2015年2月5日 下午3:22:02
 * 本类主要做为重置密码的对象
 */
public class ResetPasswordWithSmscodeDetailVO extends Signaturable implements Decidable {

	
	protected String mobileNum;
	protected String smsCode;
	protected String newpassword;
	protected String appKey;
	/* (non-Javadoc)
	 * @see com.hummingbird.commonbiz.vo.Decidable#getType()
	 */
	@Override
	public int getType() {
		// TODO Auto-generated method stub
		return 0;
	}
	/* (non-Javadoc)
	 * @see com.hummingbird.commonbiz.vo.Decidable#setOtherParam(java.util.Map)
	 */
	@Override
	public void setOtherParam(Map map) {
		// TODO Auto-generated method stub
		
	}
	/* (non-Javadoc)
	 * @see com.hummingbird.commonbiz.vo.Decidable#isAuthed()
	 */
	@Override
	public boolean isAuthed() throws SignatureException {
		return SignatureUtil.validateSignature(signature,SignatureUtil.SIGNATURE_TYPE_MD5 , mobileNum,nonce,timeStamp,appKey,smsCode,newpassword);
	}
	/**
	 * @return the mobileNum
	 */
	public String getMobileNum() {
		return mobileNum;
	}
	/**
	 * @param mobileNum the mobileNum to set
	 */
	public void setMobileNum(String mobileNum) {
		this.mobileNum = mobileNum;
	}
	/**
	 * @return the newpassword
	 */
	public String getNewpassword() {
		return newpassword;
	}
	/**
	 * @param newpassword the newpassword to set
	 */
	public void setNewpassword(String newpassword) {
		this.newpassword = newpassword;
	}
	/**
	 * @return the appKey
	 */
	public String getAppKey() {
		return appKey;
	}
	/**
	 * @param appKey the appKey to set
	 */
	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ResetPasswordWithSmscodeDetailVO [mobileNum=" + mobileNum
				+ ", smsCode=" + smsCode + ", newpassword=" + newpassword
				+ ", appKey=" + appKey + "]";
	}
	/**
	 * @return the smsCode
	 */
	public String getSmsCode() {
		return smsCode;
	}
	/**
	 * @param smsCode the smsCode to set
	 */
	public void setSmsCode(String smsCode) {
		this.smsCode = smsCode;
	}
	
	
	
	
}
