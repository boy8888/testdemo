package com.hummingbird.maaccount.vo;

import java.util.List;

import com.hummingbird.maaccount.entity.RedPaperAccountOrder;

public class SpentRedPaperResultVO {
	protected String orderId;
	protected String redPaperId;

	

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getRedPaperId() {
		return redPaperId;
	}

	public void setRedPaperId(String redPaperId) {
		this.redPaperId = redPaperId;
	}

	@Override
	public String toString() {
		return "QueryRedPaperResultVO [orderId=" + orderId + 
				", redPaperId"+redPaperId+"]";
	}
	
	/**
	 * 构造函数
	 */
	public SpentRedPaperResultVO(RedPaperAccountOrder order) {
		orderId=order.getOrderId();
		redPaperId=order.getAccountId();
	}
}
