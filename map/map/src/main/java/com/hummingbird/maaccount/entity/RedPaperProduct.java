package com.hummingbird.maaccount.entity;

import java.util.Date;

public class RedPaperProduct {
    /**
     * 红包产品编码
     */
    private String productId;

    /**
     * 红包产品名称
     */
    private String productName;

    /**
     * 红包产品简称，6个汉字长度
     */
    private String productShortName;

    /**
     * 创建时间
     */
    private Date insertTime;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * OK#-正常，OFF-下线，TBP-to be published 待发布
     */
    private String status;

    /**
     * 失效计算类型，EL#-按照时间长度计算失效时间，ET#-按照决定时间计算失效时间
     */
    private String expiresType;

    /**
     * 失效时间长度，单位为天
     */
    private Integer expiresLength;

    /**
     * 失效时
     */
    private Date expireTime;

    /**
     * @return 红包产品编码
     */
    public String getProductId() {
        return productId;
    }

    /**
     * @param productid 
	 *            红包产品编码
     */
    public void setProductId(String productId) {
        this.productId = productId == null ? null : productId.trim();
    }

    /**
     * @return 红包产品名称
     */
    public String getProductName() {
        return productName;
    }

    /**
     * @param productname 
	 *            红包产品名称
     */
    public void setProductName(String productName) {
        this.productName = productName == null ? null : productName.trim();
    }

    /**
     * @return 红包产品简称，6个汉字长度
     */
    public String getProductShortName() {
        return productShortName;
    }

    /**
     * @param productshortname 
	 *            红包产品简称，6个汉字长度
     */
    public void setProductShortName(String productShortName) {
        this.productShortName = productShortName == null ? null : productShortName.trim();
    }

    /**
     * @return 创建时间
     */
    public Date getInsertTime() {
        return insertTime;
    }

    /**
     * @param inserttime 
	 *            创建时间
     */
    public void setInsertTime(Date insertTime) {
        this.insertTime = insertTime;
    }

    /**
     * @return 修改时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * @param updatetime 
	 *            修改时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * @return OK#-正常，OFF-下线，TBP-to be published 待发布
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status 
	 *            OK#-正常，OFF-下线，TBP-to be published 待发布
     */
    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    /**
     * @return 失效计算类型，EL#-按照时间长度计算失效时间，ET#-按照决定时间计算失效时间
     */
    public String getExpiresType() {
        return expiresType;
    }

    /**
     * @param expirestype 
	 *            失效计算类型，EL#-按照时间长度计算失效时间，ET#-按照决定时间计算失效时间
     */
    public void setExpiresType(String expiresType) {
        this.expiresType = expiresType == null ? null : expiresType.trim();
    }

    /**
     * @return 失效时间长度，单位为天
     */
    public Integer getExpiresLength() {
        return expiresLength;
    }

    /**
     * @param expireslength 
	 *            失效时间长度，单位为天
     */
    public void setExpiresLength(Integer expiresLength) {
        this.expiresLength = expiresLength;
    }

    /**
     * @return 失效时
     */
    public Date getExpireTime() {
        return expireTime;
    }

    /**
     * @param expiretime 
	 *            失效时
     */
    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }
}