/**
 * 
 * FactoryProcessResult.java
 * 版本所有 深圳市蜂鸟娱乐有限公司 2013-2014
 */
package com.hummingbird.maaccount.vo;

import com.hummingbird.maaccount.entity.FactoryProcess;

/**
 * @author john huang
 * 2015年2月28日 下午12:03:30
 * 本类主要做为制卡结果
 */
public class FactoryProcessResult extends FactoryProcess {
	/**
	 * 制卡数
	 */
	long quantity;

	/**
	 * 构造函数
	 */
	public FactoryProcessResult(FactoryProcess process, long quantity) {
		this.quantity = quantity;
		this.setProductId(process.getProductId());
		this.setUnitId(process.getUnitId());
		this.setCouter(process.getCouter());
	}

	/**
	 * @return the quantity
	 */
	public long getQuantity() {
		return quantity;
	}

	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	
	
}
