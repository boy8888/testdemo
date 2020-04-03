package com.hummingbird.maaccount.entity;

import java.util.Date;

/**
 * 折扣卡产品表
 */
public class DiscountCardProduct {
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
     * 总分期数
     */
    private Integer totalStages;

    /**
     * GDJ-固定金额分期返还，GDB-固定比例分期返还
     */
    private String stageRule;

    /**
     * 分期返还金额，单位为分
     */
    private Long returnSum;

    /**
     * 分期返还比例，单位万分比
     */
    private Integer returnRate;

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

    /**
     * 产品名称
     */
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
     * @return 总分期数
     */
    public Integer getTotalStages() {
        return totalStages;
    }

    /**
     * @param totalstages 
	 *            总分期数
     */
    public void setTotalStages(Integer totalStages) {
        this.totalStages = totalStages;
    }

    /**
     * @return GDJ-固定金额分期返还，GDB-固定比例分期返还
     */
    public String getStageRule() {
        return stageRule;
    }

    /**
     * @param stagerule 
	 *            GDJ-固定金额分期返还，GDB-固定比例分期返还
     */
    public void setStageRule(String stageRule) {
        this.stageRule = stageRule == null ? null : stageRule.trim();
    }

    /**
     * @return 分期返还金额，单位为分
     */
    public Long getReturnSum() {
        return returnSum;
    }

    /**
     * @param returnsum 
	 *            分期返还金额，单位为分
     */
    public void setReturnSum(Long returnSum) {
        this.returnSum = returnSum;
    }

    /**
     * @return 分期返还比例，单位万分比
     */
    public Integer getReturnRate() {
        return returnRate;
    }

    /**
     * @param returnrate 
	 *            分期返还比例，单位万分比
     */
    public void setReturnRate(Integer returnRate) {
        this.returnRate = returnRate;
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

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "DiscountCardProduct [productId=" + productId + ", remark="
				+ remark + ", status=" + status + ", totalStages="
				+ totalStages + ", stageRule=" + stageRule + ", returnSum="
				+ returnSum + ", returnRate=" + returnRate + ", insertTime="
				+ insertTime + ", updateTime=" + updateTime + ", expiresType="
				+ expiresType + ", expiresLength=" + expiresLength
				+ ", productName=" + productName + "]";
	}

}