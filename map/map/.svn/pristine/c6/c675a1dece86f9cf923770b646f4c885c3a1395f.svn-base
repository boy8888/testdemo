/**
 * 
 * TransOrderVO.java
 * 版本所有 深圳市蜂鸟娱乐有限公司 2013-2014
 */
package com.hummingbird.maaccount.vo;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.hummingbird.common.exception.DataInvalidException;
import com.hummingbird.common.util.ValidateUtil;


/**
 * @author huangjiej_2
 * 2014年12月25日 下午6:03:37
 * 本类主要做为资金转移的对象
 */
public class TransOrderVO2<T extends IOrderVO> extends BaseTransOrderVO  {
	/**
	 * 订单信息
	 */
	protected T order;
	
	
	/**
	 * 访问地址，用于鉴权和查询
	 */
	protected String method;
	
	/**
	 * 原订单的所在表
	 */
	protected String originalTable;
	
	/**
	 * 原订单号
	 */
	protected String originalOrderId;
	
	/**
	 * 业务处理类型,ZZ#-转账，FK#-付款，TOB-投标，FEB-废标， FB#-付本，SX#-收息，SB#-收本，TX#-提现，CZ#-充值，CZH-冲正
	 */
	protected String operationType;
	/**
	 * 是否验证帐户验证码和支付码，默认false
	 */
	protected boolean strictCheck=false;

	protected String status;

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

	/**
	 * @return the order
	 */
	public T getOrder() {
		return order;
	}

	/**
	 * @param order the order to set
	 */
	public void setOrder(T order) {
		this.order = order;
	}


	/**
	 * @return the method
	 */
	public String getMethod() {
		return method;
	}

	/**
	 * @param method the method to set
	 */
	@JsonIgnore
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
	@JsonIgnore
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
	@JsonIgnore
	public void setOriginalOrderId(String originalOrderId) {
		this.originalOrderId = originalOrderId;
	}
	
	public void selfvalidate() throws DataInvalidException{
		ValidateUtil.assertNull(this.app, "app相关信息");
		ValidateUtil.assertNull(this.app.getAppId(), "appId");
		ValidateUtil.assertNull(this.order, "订单相关信息");
		this.order.selfvalidate();
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
	@JsonIgnore
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
	@JsonIgnore
	public void setStrictCheck(boolean strictCheck) {
		this.strictCheck = strictCheck;
	}

	/**
	 * 设置订单状态
	 */
	@JsonIgnore
	public void setOrderStatus(String status) {
		this.status = status;
		
	}

	/**
	 * 获取订单状态
	 */
	public String getOrderStatus() {
		return status;
	}

	/**
	 * 订单信息 (存放在order中,实现以body存放内容)
	 */
	public T getBody() {
		return order;
	}

	/**
	 * 订单信息 (存放在order中,实现以body存放内容)
	 */
	public void setBody(T body) {
		this.order = body;
	}

	
}
