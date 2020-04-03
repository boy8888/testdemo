package com.hummingbird.maaccount.vo;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.hummingbird.commonbiz.vo.AppMobileDecidable;


/**
 * 获取短信的输入参数
 * @author huangjiej_2
 * 2014年10月18日 上午9:36:53
 */
@JsonIgnoreProperties(value = {"appId", "type","authed","businessKeys"})
public class GetSmsVo extends AppBaseVO implements AppMobileDecidable,AccountCodeRequest {
	
	private String mobileNum;
	
	/**
	 * @return the mobileNum
	 */
	@Override
	public String getMobileNum() {
		return mobileNum;
	}
	/**
	 * @param mobileNum the mobileNum to set
	 */
	public void setMobileNum(String mobileNum) {
		this.mobileNum = mobileNum;
	}
	
	
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "GetSmsVo [mobileNum=" + mobileNum + ", app=" + app + "]";
	}
	/* (non-Javadoc)
	 * @see com.hummingbird.commonbiz.vo.AppMobileDecidable#getAppId()
	 */
	@Override
	@JsonIgnore
	public String getAppId() {
		return app.getAppId();
	}
}
