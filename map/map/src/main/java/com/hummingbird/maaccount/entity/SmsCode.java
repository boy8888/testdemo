package com.hummingbird.maaccount.entity;

import java.util.Date;

public class SmsCode {
    private Integer idtUserSmscode;

    private String mobilenum;

    private Integer userId;

    private String smscode;

    private Integer expirein;

    private Date sendTime;

    private String appId;

    public Integer getIdtUserSmscode() {
        return idtUserSmscode;
    }

    public void setIdtUserSmscode(Integer IdtUserSmscode) {
        this.idtUserSmscode = IdtUserSmscode;
    }

    public String getMobilenum() {
        return mobilenum;
    }

    public void setMobilenum(String mobilenum) {
        this.mobilenum = mobilenum == null ? null : mobilenum.trim();
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getSmscode() {
        return smscode;
    }

    public void setSmscode(String smscode) {
        this.smscode = smscode == null ? null : smscode.trim();
    }

    public Integer getExpirein() {
        return expirein;
    }

    public void setExpirein(Integer expirein) {
        this.expirein = expirein;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId == null ? null : appId.trim();
    }
}