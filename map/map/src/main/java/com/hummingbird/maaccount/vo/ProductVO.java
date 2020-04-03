/**
 * 
 * ProductVO.java
 * 版本所有 深圳市蜂鸟娱乐有限公司 2013-2014
 */
package com.hummingbird.maaccount.vo;

import org.apache.commons.lang.StringUtils;

import com.hummingbird.maaccount.entity.Product;

/**
 * @author john huang
 * 2015年2月23日 下午11:41:22
 * 本类主要做为
 */
public class ProductVO {

	private String productId;

    private Integer productPrice;
    

    private String productName;
    private String remark="";
    
    private String productShortName="";

	private Long productAmount;

	private String accountType;
    
	public String getProductShortName() {
		return StringUtils.defaultString(productShortName,"");
	}
	public void setProductShortName(String productShortName) {
		this.productShortName = productShortName;
	}
	public ProductVO() {
		super();
	}
	public ProductVO(Product product) {
		super();
		productId=product.getProductId();
		productPrice = product.getProductPrice();
		productAmount = product.getProductAmount();
		productName=product.getProductName();
		productShortName = product.getProductShortName();
		accountType = product.getAccountType();
	}
	/**
	 * @return the productId
	 */
	public String getProductId() {
		return productId;
	}
	/**
	 * @param productId the productId to set
	 */
	public void setProductId(String productId) {
		this.productId = productId;
	}
	/**
	 * @return the productPrice
	 */
	public Integer getProductPrice() {
		return productPrice;
	}
	/**
	 * @param productPrice the productPrice to set
	 */
	public void setProductPrice(Integer productPrice) {
		this.productPrice = productPrice;
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
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}
	/**
	 * @param remark the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ProductVO [productId=" + productId + ", productPrice="
				+ productPrice + ", productName=" + productName + ", remark="
				+ remark + ", productShortName=" + productShortName
				+ ", productAmount=" + productAmount + ", accountType="
				+ accountType + "]";
	}
	/**
	 * @return the productAmount
	 */
	public Long getProductAmount() {
		return productAmount;
	}
	/**
	 * @param productAmount the productAmount to set
	 */
	public void setProductAmount(Long productAmount) {
		this.productAmount = productAmount;
	}
	/**
	 * @return the accountType
	 */
	public String getAccountType() {
		return accountType;
	}
	/**
	 * @param accountType the accountType to set
	 */
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
    
    
	
}
