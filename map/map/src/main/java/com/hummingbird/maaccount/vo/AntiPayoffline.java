/**
 * 
 * AntiPayoffline.java
 * 版本所有 深圳市蜂鸟娱乐有限公司 2013-2014
 */
package com.hummingbird.maaccount.vo;

import org.apache.commons.lang.StringUtils;

import com.hummingbird.common.exception.DataInvalidException;
import com.hummingbird.common.util.ValidateUtil;


/**
 * @author john huang
 * 2015年2月8日 下午10:37:42
 * 本类主要做为线下支付的撤销或冲正
 */
public class AntiPayoffline extends OfflinePayOrderVO {

	protected String orderId;
	
	/**
	 * @return the orderId
	 */
	public String getOrderId() {
		return orderId;
	}
	/**
	 * @param orderId the orderId to set
	 */
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "AntiPayoffline [orderId=" + orderId + ", mobileNum="
				+ mobileNum + ", paymentCodeDES=" + paymentCodeDES + ", sum="
				+ sum + ", sellerId=" + sellerId + ", storeId=" + storeId
				+ ", terminalId=" + terminalId + ", operatorId=" + operatorId
				+ ", remark=" + remark + ", appOrderId=" + appOrderId
				+ ", productName=" + productName + ", terminalOrderId="
				+ terminalOrderId + "]";
	}
	/* (non-Javadoc)
	 * @see com.hummingbird.maaccount.vo.OfflinePayOrderVO#selfvalidate()
	 */
	@Override
	public void selfvalidate() throws DataInvalidException {
//		ValidateUtil.assertNull(this.orderId, "交易订单号");
		ValidateUtil.assertNull(this.terminalId, "终端编码");
		ValidateUtil.assertNull(this.operatorId, "操作员编码");
//		ValidateUtil.assertNull(this.storeId, "门店编码");
		ValidateUtil.assertNull(this.sellerId, "商户编码");
		ValidateUtil.assertNull(this.terminalOrderId, "订单流水号");
	}
	/* (non-Javadoc)
	 * @see com.hummingbird.maaccount.vo.OfflinePayOrderVO#getPaintText()
	 */
	@Override
	public String getPaintText() {
		return ValidateUtil.sortbyValues(StringUtils.defaultIfEmpty(orderId,""),terminalOrderId,sellerId,storeId,terminalId,operatorId,remark);
	}
	
	
	
}
