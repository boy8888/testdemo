package com.hummingbird.maaccount.entity;

/**
 * 产品消费表,记录产品允许支付什么产品,如果油类型为ALL_OIL则表示能支付所有的油品
 */
public class ProductOiltype {
    /**
     * 主键
     */
    private Integer customId;

    /**
     * 产品id
     */
    private String productId;

    /**
     * 油品类型,它使用中经的产品表中的产品,
     */
    private String oilType;

    /**
     * @return 主键
     */
    public Integer getCustomId() {
        return customId;
    }

    /**
     * @param customId 
	 *            主键
     */
    public void setCustomId(Integer customId) {
        this.customId = customId;
    }

    /**
     * @return 产品id
     */
    public String getProductId() {
        return productId;
    }

    /**
     * @param productId 
	 *            产品id
     */
    public void setProductId(String productId) {
        this.productId = productId == null ? null : productId.trim();
    }

    /**
     * @return 油品类型,它使用中经的产品表中的产品,
     */
    public String getOilType() {
        return oilType;
    }

    /**
     * @param oilType 
	 *            油品类型,它使用中经的产品表中的产品,
     */
    public void setOilType(String oilType) {
        this.oilType = oilType == null ? null : oilType.trim();
    }
}