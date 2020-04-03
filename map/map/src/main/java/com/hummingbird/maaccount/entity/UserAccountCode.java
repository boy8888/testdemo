package com.hummingbird.maaccount.entity;

import java.util.Date;

import com.hummingbird.commonbiz.vo.AppMobile;
import com.hummingbird.commonbiz.vo.UserToken;

/**
 * 帐户验证码
 * @author huangjiej_2
 * 2015年1月18日 下午5:57:54
 * 本类主要做为
 */
public class UserAccountCode implements AppMobile,UserToken{
	
    private Integer idtUserAccountcode;

    private String mobileNum;

    private String smscode;

    private Integer expirein;

    private Date sendtime;

    private String appId;

    public Integer getIdtUserAccountcode() {
        return idtUserAccountcode;
    }

    public void setIdtUserAccountcode(Integer idtUserAccountcode) {
        this.idtUserAccountcode = idtUserAccountcode;
    }

    public String getMobileNum() {
        return mobileNum;
    }

    public void setMobileNum(String mobilenum) {
        this.mobileNum = mobilenum == null ? null : mobilenum.trim();
    }


    public String getSmscode() {
        return smscode;
    }

    public void setSmscode(String smscode) {
        this.smscode = smscode == null ? null : smscode.trim();
    }

    public int getExpirein() {
        return expirein==null?-1:expirein;
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

    public void setAppId(String appid) {
        this.appId = appid == null ? null : appid.trim();
    }

	/* (non-Javadoc)
	 * @see com.hummingbird.commonbiz.vo.UserToken#getToken()
	 */
	@Override
	public String getToken() {
		return smscode;
	}
}