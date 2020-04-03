package com.hummingbird.maaccount.entity;

import java.util.Date;

public class SMSMessage {
	private Integer id;

	private String mobileNum;

	private String smscode;

	private Integer expirein;

	private Date sendtime;

	private String appId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMobileNum() {
		return mobileNum;
	}

	public void setMobileNum(String mobileNum) {
		this.mobileNum = mobileNum;
	}

	public String getSmscode() {
		return smscode;
	}

	public void setSmscode(String smscode) {
		this.smscode = smscode;
	}

	public Integer getExpirein() {
		return expirein;
	}

	public void setExpirein(Integer expirein) {
		this.expirein = expirein;
	}

	public Date getSendtime() {
		return sendtime;
	}

	public void setSendtime(Date sendtime) {
		this.sendtime = sendtime;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

}
