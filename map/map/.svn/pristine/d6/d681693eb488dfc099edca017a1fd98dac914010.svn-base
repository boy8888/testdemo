/**
 * 
 * BaseConsumer.java
 * 版本所有 深圳市蜂鸟娱乐有限公司 2013-2014
 */
package com.hummingbird.maaccount.vo;

import com.hummingbird.maaccount.face.Consumer;

/**
 * @author john huang
 * 2015年2月13日 下午4:42:09
 * 本类主要做为消费者的基类
 */
public class BaseConsumer implements Consumer {

	public BaseConsumer() {
		super();
	}

	public BaseConsumer(String consumerId, String consumerType) {
		super();
		this.consumerId = consumerId;
		this.consumerType = consumerType;
	}

	protected String consumerId;
	
	protected String consumerType;

	/* (non-Javadoc)
	 * @see com.hummingbird.maaccount.face.Consumer#getConsumerType()
	 */
	@Override
	public String getConsumerType() {
		return consumerType;
	}

	/* (non-Javadoc)
	 * @see com.hummingbird.maaccount.face.Consumer#getConsumerId()
	 */
	@Override
	public String getConsumerId() {
		return this.consumerId;
	}

	/**
	 * @param consumerId the consumerId to set
	 */
	public void setConsumerId(String consumerId) {
		this.consumerId = consumerId;
	}

	/**
	 * @param consumerType the consumerType to set
	 */
	public void setConsumerType(String consumerType) {
		this.consumerType = consumerType;
	}

}
