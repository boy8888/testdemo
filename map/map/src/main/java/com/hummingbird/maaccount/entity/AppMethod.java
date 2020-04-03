package com.hummingbird.maaccount.entity;

import java.util.Date;

public class AppMethod {
    private Integer idtAppMethod;

    private String appid;

    private String method;

    private String remark;

    private Date inserttime;

    public Integer getIdtAppMethod() {
        return idtAppMethod;
    }

    public void setIdtAppMethod(Integer idtAppMethod) {
        this.idtAppMethod = idtAppMethod;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid == null ? null : appid.trim();
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method == null ? null : method.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Date getInserttime() {
        return inserttime;
    }

    public void setInserttime(Date inserttime) {
        this.inserttime = inserttime;
    }
}