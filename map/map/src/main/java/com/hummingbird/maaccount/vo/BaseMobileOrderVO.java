/**
 * 
 * BaseMobileOrderVO.java
 * 版本所有 深圳市蜂鸟娱乐有限公司 2013-2014
 */
package com.hummingbird.maaccount.vo;


/**
 * @author john huang
 * 2015年5月20日 下午4:12:27
 * 本类主要做为 以手机号为基类的订单vo
 */
public class BaseMobileOrderVO  {


	/**
	 * 手机号
	 */
	private String mobileNum;

	public String getMobileNum() {
		return this.mobileNum;
	}

	/**
	 * 手机号
	 */
	public void setMobileNum(String mobileNum) {
		this.mobileNum = mobileNum;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "BaseMobileOrderVO [mobileNum=" + mobileNum + "]";
	}


}
