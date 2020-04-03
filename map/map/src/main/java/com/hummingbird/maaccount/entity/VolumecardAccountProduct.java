package com.hummingbird.maaccount.entity;

import java.util.Date;

/**
 * 容量卡产品表
 */
public class VolumecardAccountProduct {
    /**
     * 产品编号
     */
    private String productId;

    /**
     * 备注
     */
    private String remark;

    /**
     * OK#-正常，OFF-下线，TBP-to be published 待发布
     */
    private String status;


    /**
     * 创建时间
     */
    private Date insertTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 有效期长度类型，DAY-自然日，MON-自然月
     */
    private String expiresType;

    /**
     * 有效期长度
     */
    private Integer expiresLength;

    private String productName;


    /**
     * @return 产品编号
     */
    public String getProductId() {
        return productId;
    }

    /**
     * @param productid 
	 *            产品编号
     */
    public void setProductId(String productId) {
        this.productId = productId == null ? null : productId.trim();
    }

    /**
     * @return 备注
     */
    public String getRemark() {
        return remark;
    }

    /**
     * @param remark 
	 *            备注
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
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
     * @return 更新时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * @param updatetime 
	 *            更新时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * @return 有效期长度类型，DAY-自然日，MON-自然月
     */
    public String getExpiresType() {
        return expiresType;
    }

    /**
     * @param expirestype 
	 *            有效期长度类型，DAY-自然日，MON-自然月
     */
    public void setExpiresType(String expiresType) {
        this.expiresType = expiresType == null ? null : expiresType.trim();
    }

    /**
     * @return 有效期长度
     */
    public Integer getExpiresLength() {
        return expiresLength;
    }

    /**
     * @param expireslength 
	 *            有效期长度
     */
    public void setExpiresLength(Integer expiresLength) {
        this.expiresLength = expiresLength;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName == null ? null : productName.trim();
    }

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "VolumecardAccountProduct [productId=" + productId + ", remark="
				+ remark + ", status=" + status + ", insertTime=" + insertTime
				+ ", updateTime=" + updateTime + ", expiresType=" + expiresType
				+ ", expiresLength=" + expiresLength + ", productName="
				+ productName + "]";
	}

}