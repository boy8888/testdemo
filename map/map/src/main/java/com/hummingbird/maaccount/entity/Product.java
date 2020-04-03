package com.hummingbird.maaccount.entity;

import java.util.Date;

public class Product {
    /**
     * 产品编码
     */
    private String productId;

    /**
     * 产品购买价格,单位：分
	 * 
     */
    private Integer productPrice;

    /**
     * 产品名称
     */
    private String productName;

    /**
     * 产品链接
     */
    private String productUrl;

    /**
     * 创建时间
     */
    private Date insertTime;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 账户类型，CA#-现金账户，IA#-投资账户，OCA-分期卡账户
     */
    private String accountType;

    /**
     * OK#-正常，OFF-下线，TBP-to be published 待发布
     */
    private String status;
    
    /**
     * 产品公开面额
     */
    private Long productAmount;

    /**
     * @return 产品编码
     */
    public String getProductId() {
        return productId;
    }
    
    private String productShortName;

    /* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Product [productId=" + productId + ", productPrice="
				+ productPrice + ", productName=" + productName
				+ ", productUrl=" + productUrl + ", insertTime=" + insertTime
				+ ", updateTime=" + updateTime + ", accountType=" + accountType
				+ ", status=" + status + ", productAmount=" + productAmount
				+ ", productShortName=" + productShortName + "]";
	}

	public String getProductShortName() {
		return productShortName;
	}

	public void setProductShortName(String productShortName) {
		this.productShortName = productShortName;
	}


    /**
     * @param productid 
	 *            产品编码
     */
    public void setProductId(String productId) {
        this.productId = productId == null ? null : productId.trim();
    }

    /**
     * 产品购买价格,单位：分
	 * 
     */
    public Integer getProductPrice() {
        return productPrice;
    }

    /**
     * 产品购买价格,单位：分
	 * 
     */
    public void setProductPrice(Integer productPrice) {
        this.productPrice = productPrice;
    }

    /**
     * @return 产品名称
     */
    public String getProductName() {
        return productName;
    }

    /**
     * @param productname 
	 *            产品名称
     */
    public void setProductName(String productName) {
        this.productName = productName == null ? null : productName.trim();
    }

    /**
     * @return 产品链接
     */
    public String getProductUrl() {
        return productUrl;
    }

    /**
     * @param producturl 
	 *            产品链接
     */
    public void setProductUrl(String productUrl) {
        this.productUrl = productUrl == null ? null : productUrl.trim();
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
     * @return 账户类型，CA#-现金账户，IA#-投资账户，OCA-分期卡账户
     */
    public String getAccountType() {
        return accountType;
    }

    /**
     * @param accounttype 
	 *            账户类型，CA#-现金账户，IA#-投资账户，OCA-分期卡账户
     */
    public void setAccountType(String accountType) {
        this.accountType = accountType == null ? null : accountType.trim();
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
     * 产品公开面额
     */
	public Long getProductAmount() {
		return productAmount;
	}

	 /**
     * 产品公开面额
     */
	public void setProductAmount(Long productAmount) {
		this.productAmount = productAmount;
	}
}