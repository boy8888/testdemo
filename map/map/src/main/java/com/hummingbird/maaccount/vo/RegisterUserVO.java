/**
 * 
 * RegisterUserVO.java
 * 版本所有 深圳市蜂鸟娱乐有限公司 2013-2014
 */
package com.hummingbird.maaccount.vo;

import java.util.Arrays;
import java.util.Date;

import org.codehaus.jackson.annotate.JsonProperty;

import com.hummingbird.maaccount.entity.User;

/**
 * @author john huang
 * 2015年7月23日 下午10:34:59
 * 本类主要做为 用户注册的具体内容
 */
public class RegisterUserVO {

	/**
	 * 手机号
	 */
	protected String mobileNum;
	/**
	 * 身份证
	 */
	protected String ID;
	/**
	 * 用户名
	 */
	protected String name;
	/**
	 * 用户属性
	 */
	protected String[] attrs;
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
	 * @return the iD
	 */
	public String getID() {
		return ID;
	}
	/**
	 * @param iD the iD to set
	 */
	@JsonProperty("ID")
	public void setID(String iD) {
		ID = iD;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the attrs
	 */
	public String[] getAttrs() {
		return attrs;
	}
	/**
	 * @param attrs the attrs to set
	 */
	public void setAttrs(String[] attrs) {
		this.attrs = attrs;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "RegisterUserVO [mobileNum=" + mobileNum + ", ID=" + ID
				+ ", name=" + name + ", attrs=" + Arrays.toString(attrs) + "]";
	}
	
	/**
	 * 转成用户
	 * @return
	 */
	public User toUser(){
		User user = new User();
		user.setId(ID);
		user.setInsertTime(new Date());
		user.setName(name);
		user.setMobilenum(mobileNum);
		return user;
	}
	
	
	
}
