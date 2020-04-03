package com.hummingbird.maaccount.entity;

public class RedPaperProductRelation {
    /**
     * 
     */
    private Integer id;

    /**
     */
    private String appId;

    /**
     */
    private String productId;

    /**
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id 
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     */
    public String getAppId() {
        return appId;
    }

    /**
     * @param appid 
     */
    public void setAppId(String appId) {
        this.appId = appId == null ? null : appId.trim();
    }

    /**
     */
    public String getProductId() {
        return productId;
    }

    /**
     * @param productid 
     */
    public void setProductId(String productId) {
        this.productId = productId == null ? null : productId.trim();
    }
}