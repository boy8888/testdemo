package com.hummingbird.maaccount.entity;

/**
 * 应用短信对应的businessName,这个由中经指定,不同的app不同的动作对应的businessName也不同
 */
public class AppSmsActionSetting {
    /**
     * id
     */
    private Integer id;

    /**
     * 应用id
     */
    private String appId;

    /**
     * 动作
     */
    private String actionName;

    /**
     * 对应的业务名称
     */
    private String businessName;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id 
	 *            id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return 应用id
     */
    public String getAppId() {
        return appId;
    }

    /**
     * @param appid 
	 *            应用id
     */
    public void setAppId(String appId) {
        this.appId = appId == null ? null : appId.trim();
    }

    /**
     * @return 动作
     */
    public String getActionName() {
        return actionName;
    }

    /**
     * @param actionname 
	 *            动作
     */
    public void setActionName(String actionName) {
        this.actionName = actionName == null ? null : actionName.trim();
    }

    /**
     * @return 对应的业务名称
     */
    public String getBusinessName() {
        return businessName;
    }

    /**
     * @param businessname 
	 *            对应的业务名称
     */
    public void setBusinessName(String businessName) {
        this.businessName = businessName == null ? null : businessName.trim();
    }
}