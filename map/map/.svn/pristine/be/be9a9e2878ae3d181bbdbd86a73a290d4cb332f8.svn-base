/**
 * 
 * TransOrderVO.java
 * 版本所有 深圳市蜂鸟娱乐有限公司 2013-2014
 */
package com.hummingbird.maaccount.vo;

import com.hummingbird.common.exception.DataInvalidException;
import com.hummingbird.common.util.ValidateUtil;


/**
 * @author huangjiej_2
 * 2014年12月25日 下午6:03:37
 * 本类主要做为资金转移的对象
 */
public class TransOrderVO extends TransOrderVO2<OrderVO>  {
//	/**
//	 * 订单信息
//	 */
//	private OrderVO order;
	
	/**
	 * 访问地址，用于鉴权和查询
	 */
	private String method;
	
	/**
	 * 原订单的所在表
	 */
	private String originalTable;
	
	/**
	 * 原订单号
	 */
	private String originalOrderId;
	
	/**
	 * 业务处理类型,ZZ#-转账，FK#-付款，TOB-投标，FEB-废标， FB#-付本，SX#-收息，SB#-收本，TX#-提现，CZ#-充值，CZH-冲正
	 */
	private String operationType;
	/**
	 * 是否验证帐户验证码和支付码，默认false
	 */
	private boolean strictCheck=false;

	private String status;

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "TransOrderVO [order=" + order + ", app=" + app + ", tsig="
				+ tsig + ", method=" + method + ", originalTable="
				+ originalTable + ", originalOrderId=" + originalOrderId
				+ ", operationType=" + operationType + "]";
	}

//	/**
//	 * @return the order
//	 */
//	public OrderVO getOrder() {
//		return order;
//	}
//
//	/**
//	 * @param order the order to set
//	 */
//	public void setOrder(OrderVO order) {
//		this.order = order;
//	}

	/**
	 * @return the method
	 */
	public String getMethod() {
		return method;
	}

	/**
	 * @param method the method to set
	 */
	public void setMethod(String method) {
		this.method = method;
	}

	/**
	 * @return the originalTable
	 */
	public String getOriginalTable() {
		return originalTable;
	}

	/**
	 * @param originalTable the originalTable to set
	 */
	public void setOriginalTable(String originalTable) {
		this.originalTable = originalTable;
	}

	/**
	 * @return the originalOrderId
	 */
	public String getOriginalOrderId() {
		return originalOrderId;
	}

	/**
	 * @param originalOrderId the originalOrderId to set
	 */
	public void setOriginalOrderId(String originalOrderId) {
		this.originalOrderId = originalOrderId;
	}
	
	public void selfvalidate() throws DataInvalidException{
		ValidateUtil.assertNull(this.app, "app相关信息");
		ValidateUtil.assertNull(this.app.getAppId(), "appId");
		ValidateUtil.assertNull(this.order, "订单相关信息");
		//ValidateUtil.assertNull(this.order.getMobileNum(), "手机号码");
		
	}

	/**
	 * @return the operationType
	 */
	public String getOperationType() {
		return operationType;
	}

	/**
	 * @param operationType the operationType to set
	 */
	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}

	/**
	 * 是否验证帐户验证码和支付码，默认false
	 * @return
	 */
	public boolean isStrictCheck() {
		return strictCheck;
	}

	/**
	 * 是否验证帐户验证码和支付码，默认false
	 */
	public void setStrictCheck(boolean strictCheck) {
		this.strictCheck = strictCheck;
	}

	/**
	 * 设置订单状态
	 */
	public void setOrderStatus(String status) {
		this.status = status;
		
	}

	/**
	 * 获取订单状态
	 */
	public String getOrderStatus() {
		return status;
	}

	
}
