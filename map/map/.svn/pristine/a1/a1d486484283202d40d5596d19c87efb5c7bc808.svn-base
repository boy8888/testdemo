package com.hummingbird.maaccount.entity;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.hummingbird.common.face.AbstractAppLog;

public class AppLog {
	
	/**
	 * 构造函数
	 * @param applog
	 */
	public AppLog(AbstractAppLog applog) {
		this.appid = applog.getAppid();
		this.inserttime = applog.getInserttime();
		this.method=applog.getMethod();
		this.request= StringUtils.substring(applog.getRequest(),0,2000);
		this.respone = StringUtils.substring(applog.getRespone(),0,2000);
	}

	public AppLog() {
		super();
	}
	
    protected Integer idtAppLog;

    protected String appid;

    protected String method;

    protected String request;

    protected String respone;

    protected Date inserttime;

    public Integer getIdtAppLog() {
        return idtAppLog;
    }

    public void setIdtAppLog(Integer idtAppLog) {
        this.idtAppLog = idtAppLog;
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

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request == null ? null : request.trim();
    }

    public String getRespone() {
        return respone;
    }

    public void setRespone(String respone) {
        this.respone = respone == null ? null : StringUtils.substring(respone.trim(),0,2000);
    }

    public Date getInserttime() {
        return inserttime;
    }

    public void setInserttime(Date inserttime) {
        this.inserttime = inserttime;
    }
}