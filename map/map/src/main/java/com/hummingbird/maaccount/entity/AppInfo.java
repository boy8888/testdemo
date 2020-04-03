package com.hummingbird.maaccount.entity;

import java.util.Date;

import com.hummingbird.maaccount.face.AppObj;


/**
 * 应用的标识
 * @author huangjiej_2
 * 2014年11月11日 下午11:01:24
 */
public class AppInfo implements AppObj{

    private String appId;
    
    private String appname;
    
    private String appKey;
    
    private String appPublicKey;

    private String appcert;

    private Date insertTime;

    private Date updateTime;

    private String status;
    
    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId == null ? null : appId.trim();
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey == null ? null : appKey.trim();
    }

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "AppInfo [appId=" + appId + ", appname=" + appname + ", appKey="
				+ appKey +  ", appcert="
				+ appcert + ", insertTime=" + insertTime + ", updateTime="
				+ updateTime + ", status=" + status + "]";
	}
    public String getAppname() {
        return appname;
    }

    public void setAppname(String appname) {
        this.appname = appname == null ? null : appname.trim();
    }
	
	

    public void setAppcert(String appcert) {
        this.appcert = appcert == null ? null : appcert.trim();
    }

    public Date getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(Date insertTime) {
        this.insertTime = insertTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

	/**
	 * @return the appcert
	 */
	public String getAppcert() {
		return appcert;
	}

	/**
	 * @return the appPublicKey
	 */
	public String getAppPublicKey() {
		return appPublicKey;
	}

	/**
	 * @param appPublicKey the appPublicKey to set
	 */
	public void setAppPublicKey(String appPublicKey) {
		this.appPublicKey = appPublicKey;
	}
}