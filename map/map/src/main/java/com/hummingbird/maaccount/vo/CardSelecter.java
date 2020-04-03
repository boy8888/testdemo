/**
 * 
 * CardSelecter.java
 * 版本所有 深圳市蜂鸟娱乐有限公司 2013-2014
 */
package com.hummingbird.maaccount.vo;

import java.util.List;

/**
 * @author john huang
 * 2015年8月28日 下午10:35:52
 * 本类主要做为
 */
public class CardSelecter {

	/**
	 * 中经的产品编号
	 */
	private String zjproductId;
	/**
	 * 产品数量
	 */
	private String productQuantity;
	/**
	 * 产品金额
	 */
	private Long sum;
	/**
	 * 消费的产品id
	 */
	private String productId;
	/**
	 * 不消费的产品id
	 */
	private List<String> denyProducts;
	/**
	 * 产品类型,OCA,DCA,VCA
	 */
	private String consumerType;
	private Integer userId;
	private String terminalId;

	/**
	 * 构造函数
	 * @param userid 
	 * @param terminalId 
	 */
	public CardSelecter(String zjproductName, String productQuantity, Long sum,
			ProductPriority pp, Integer userid, String terminalId) {
			this.zjproductId = zjproductName;
			this.productQuantity = productQuantity;
			this.sum = sum;
			this.userId = userid;
			this.terminalId = terminalId;
			this.productId = pp.getAllow();
			List<String> denies = pp.getDenies();
			if(denies!=null&&!denies.isEmpty()){
				this.denyProducts = denies;
			}
			this.consumerType = pp.getConsumerType();
		
	}

	/**
	 * 中经的产品编号
	 */
	public String getZjproductId() {
		return zjproductId;
	}

	/**
	 * 中经的产品编号
	 */
	public void setZjproductId(String zjproductId) {
		this.zjproductId = zjproductId;
	}

	/**
	 * 产品数量
	 */
	public String getProductQuantity() {
		return productQuantity;
	}

	/**
	 * 产品数量
	 */
	public void setProductQuantity(String productQuantity) {
		this.productQuantity = productQuantity;
	}

	/**
	 * 产品金额
	 */
	public Long getSum() {
		return sum;
	}

	/**
	 * 产品金额
	 */
	public void setSum(Long sum) {
		this.sum = sum;
	}

	/**
	 * 消费的产品id
	 */
	public String getProductId() {
		return productId;
	}

	/**
	 * 消费的产品id
	 */
	public void setProductId(String productId) {
		this.productId = productId;
	}

	/**
	 * 不消费的产品id
	 */
	public List<String> getDenyProducts() {
		return denyProducts;
	}

	/**
	 * 不消费的产品id
	 */
	public void setDenyProducts(List<String> denyProducts) {
		this.denyProducts = denyProducts;
	}

	/**
	 * 产品类型,OCA,DCA,VCA
	 */
	public String getConsumerType() {
		return consumerType;
	}

	/**
	 * 产品类型,OCA,DCA,VCA
	 */
	public void setConsumerType(String consumerType) {
		this.consumerType = consumerType;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CardSelecter [zjproductId=" + zjproductId
				+ ", productQuantity=" + productQuantity + ", sum=" + sum
				+ ", productId=" + productId + ", denyProducts=" + denyProducts
				+ ", consumerType=" + consumerType + ", userId=" + userId
				+ ", terminalId=" + terminalId + "]";
	}

	/**
	 * @return the userid
	 */
	public Integer getUserId() {
		return userId;
	}

	/**
	 * @param userid the userid to set
	 */
	public void setUserId(Integer userid) {
		this.userId = userid;
	}

	/**
	 * @return the terminalId
	 */
	public String getTerminalId() {
		return terminalId;
	}

	/**
	 * @param terminalId the terminalId to set
	 */
	public void setTerminalId(String terminalId) {
		this.terminalId = terminalId;
	}

}
