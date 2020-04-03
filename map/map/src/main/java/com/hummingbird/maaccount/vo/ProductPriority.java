/**
 * 
 * ProductPriority.java
 * 版本所有 深圳市蜂鸟娱乐有限公司 2013-2014
 */
package com.hummingbird.maaccount.vo;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

/**
 * @author john huang
 * 2015年8月19日 上午12:07:55
 * 本类主要做为
 */
public class ProductPriority {

	private String consumerType;
	
	private String allow;
	
	private List<String> denies =new ArrayList<String>();

	/**
	 * 构造函数
	 */
	public ProductPriority(String consumerType) {
		this.consumerType = consumerType;
	}

	/**
	 * 构造函数
	 */
	public ProductPriority(String accountType, String productId) {
		this.consumerType = accountType;
		allow=(productId);
	}
	
	/**
	 * 根据产品id删除已确定的产品
	 * @param pp
	 */
	public void removeProductId(ProductPriority pp){
		if(StringUtils.equals(consumerType, pp.consumerType)){
			if(pp.allow!=null&&this.allow==null){
				this.denies.add(pp.allow);
			}
			else if(pp.allow==null&&this.allow!=null){
				pp.denies.add(this.allow);
			}
		}
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((allow == null) ? 0 : allow.hashCode());
		result = prime * result
				+ ((consumerType == null) ? 0 : consumerType.hashCode());
		result = prime * result + ((denies == null) ? 0 : denies.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof ProductPriority))
			return false;
		ProductPriority other = (ProductPriority) obj;
		if (allow == null) {
			if (other.allow != null)
				return false;
		} else if (!allow.equals(other.allow))
			return false;
		if (consumerType == null) {
			if (other.consumerType != null)
				return false;
		} else if (!consumerType.equals(other.consumerType))
			return false;
		if (denies == null) {
			if (other.denies != null)
				return false;
		} else if (!denies.equals(other.denies))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ProductPriority [consumerType=" + consumerType + ", allow="
				+ allow + ", denies=" + denies + "]";
	}

	/**
	 * @return the consumerType
	 */
	public String getConsumerType() {
		return consumerType;
	}

	/**
	 * @param consumerType the consumerType to set
	 */
	public void setConsumerType(String consumerType) {
		this.consumerType = consumerType;
	}

	/**
	 * @return the allow
	 */
	public String getAllow() {
		return allow;
	}

	/**
	 * @param allow the allow to set
	 */
	public void setAllow(String allow) {
		this.allow = allow;
	}

	/**
	 * @return the denies
	 */
	public List<String> getDenies() {
		return denies;
	}

	/**
	 * @param denies the denies to set
	 */
	public void setDenies(List<String> denies) {
		this.denies = denies;
	}
	
	

}
