package com.hummingbird.maaccount.entity;

import java.util.Date;

/**
 * @author john huang
 * 2015年2月3日 上午11:41:39
 * 本类主要做为 分期卡产品
 */
public class OilcardAccountProduct {
    /**
     * 产品编号、产品类型
     */
    private String productId;

    private String remark;

    /**
     * 状态,OK#-正常，OFF-下线
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

    private Date insertTime;

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
    
    private Product product;
    
    /**
     * 返还周期类型,，DAY-日，MON-月
     */
    private String returnPeriodType;

    /**
     * 返还间隔,跟返还类型相匹配
     */
    private Integer returnPeriodInterval;

    /**
     * 期初返还金额,单位分
     */
    private Integer initialSum;

    /**
     * @return 产品编号、产品类型
     */
    public String getProductId() {
        return productId;
    }

    /**
     * @param 产品编号、产品类型
     */
    public void setProductId(String productId) {
        this.productId = productId == null ? null : productId.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
    /**
     * 状态,OK#-正常，OFF-下线
     */
    public String getStatus() {
        return status;
    }
    /**
     * 状态,OK#-正常，OFF-下线
     */
    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    /**
     * 总分期数
     */
    public Integer getTotalStages() {
        return totalStages;
    }

    /**
     * 总分期数
     */
    public void setTotalStages(Integer totalStages) {
        this.totalStages = totalStages;
    }

    /**
     * GDJ-固定金额分期返还，GDB-固定比例分期返还
     */
    public String getStageRule() {
        return stageRule;
    }

    /**
     * GDJ-固定金额分期返还，GDB-固定比例分期返还
     */
    public void setStageRule(String stageRule) {
        this.stageRule = stageRule == null ? null : stageRule.trim();
    }

    /**
     * 分期返还金额，单位为分
     */
    public Long getReturnSum() {
        return returnSum;
    }

    /**
     * 分期返还金额，单位为分
     */
    public void setReturnSum(Long returnSum) {
        this.returnSum = returnSum;
    }
    /**
     * 分期返还比例，单位万分比
     */
    public Integer getReturnRate() {
        return returnRate;
    }
    /**
     * 分期返还比例，单位万分比
     */
    public void setReturnRate(Integer returnRate) {
        this.returnRate = returnRate;
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



    /**
     * 有效期长度类型，DAY-自然日，MON-自然月
     */
    public String getExpiresType() {
        return expiresType;
    }

    /**
     * 有效期长度类型，DAY-自然日，MON-自然月
     */
    public void setExpiresType(String expiresType) {
        this.expiresType = expiresType == null ? null : expiresType.trim();
    }

    /**
     * 有效期长度
     */
    public Integer getExpiresLength() {
        return expiresLength;
    }

    /**
     * 有效期长度
     */
    public void setExpiresLength(Integer expiresLength) {
        this.expiresLength = expiresLength;
    }

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "OilcardAccountProduct [productId=" + productId + ", remark="
				+ remark + ", status=" + status + ", totalStages="
				+ totalStages + ", stageRule=" + stageRule + ", returnSum="
				+ returnSum + ", returnRate=" + returnRate + ", insertTime="
				+ insertTime + ", updateTime=" + updateTime + ", expiresType="
				+ expiresType + ", expiresLength=" + expiresLength
				+ ", productName=" + productName + ", product=" + product
				+ ", returnPeriodType=" + returnPeriodType
				+ ", returnPeriodInterval=" + returnPeriodInterval
				+ ", initialSum=" + initialSum + "]";
	}

	/**
	 * @return the productName
	 */
	public String getProductName() {
		return productName;
	}

	/**
	 * @param productName the productName to set
	 */
	public void setProductName(String productName) {
		this.productName = productName;
	}

	/**
	 * @return the product
	 */
	public Product getProduct() {
		return product;
	}

	/**
	 * @param product the product to set
	 */
	public void setProduct(Product product) {
		this.product = product;
	}
	
	 /**
     * @return 返还周期类型,，DAY-日，MON-月
     */
    public String getReturnPeriodType() {
        return returnPeriodType;
    }

    /**
     * @param returnperiodtype 
	 *            返还周期类型,，DAY-日，MON-月
     */
    public void setReturnPeriodType(String returnPeriodType) {
        this.returnPeriodType = returnPeriodType == null ? null : returnPeriodType.trim();
    }

    /**
     * @return 返还间隔,跟返还类型相匹配
     */
    public Integer getReturnPeriodInterval() {
        return returnPeriodInterval;
    }

    /**
     * @param returnperiodinterval 
	 *            返还间隔,跟返还类型相匹配
     */
    public void setReturnPeriodInterval(Integer returnPeriodInterval) {
        this.returnPeriodInterval = returnPeriodInterval;
    }

    /**
     * @return 期初返还金额,单位分
     */
    public Integer getInitialSum() {
        return initialSum;
    }

    /**
     * @param initialsum 
	 *            期初返还金额,单位分
     */
    public void setInitialSum(Integer initialSum) {
        this.initialSum = initialSum;
    }
}