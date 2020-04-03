package com.hummingbird.maaccount.vo;

import java.util.Date;

import com.hummingbird.common.util.DateUtil;
import com.hummingbird.maaccount.entity.CashAccount;
import com.hummingbird.maaccount.entity.CashAccountOrder;

public class CashAccountOrderVO {
	/*{
	    "errcode":0,"errmsg":"查询交易订单成功",
	    "order":{
	            "orderId":"123434590003333444",
	            "appId":"WXFRONT",
	            "appName":"微信加油卡前置",
	            "method":"/cashAccount/recharge",
	            "methodName":"现金账户/充值",
	            "sum":500000,
	            "flowDirection":"IN#",
	            "balance":400000,
	            "peerAccountId":"1111222233334444",
	            "peerAccountUnit":"微信支付",
	            "peerAccountType":"WXP",
	            "type":"CZ#",
	            "remark":"从微信支付充值5000元到现金账户",
	            "status":"OK#",
	            "insertTime":"2015-01-01 12:12:12",
	            "appOrderId":"223223232323233",
	            "productName":"有油贷5000元套餐",
	            "externalOrderId":"223223232323233"
	    }
	}*/
	private String orderId;
	private String appId;
	private String appName;
	private String method;
	private String methodName;
	private Long sum;
	private String flowDirection;
	private Long balance;
	private String peerAccountId;
	private String peerAccountUnit;
	private String peerAccountType;
	private String type;
	private String remark;
	private String status;
	private String appOrderId;
	private String insertTime;
	private String productName;
	private String externalOrderId;
	public String toString() {
		return "CashAccountOrderVO [orderId=" + orderId + ", appId=" + appId
				+ ", appName=" + appName + ", method=" + method
				+ ", methodName=" + methodName + ", sum=" + sum
				+ ", flowDirection=" + flowDirection+ ", balance=" + balance
				+ ", peerAccountId=" + peerAccountId+ ", peerAccountUnit=" + peerAccountUnit
				+ ", peerAccountType=" + peerAccountType+ ", type=" + type
				+ ", remark=" + remark+ ", status=" + status+ ", appOrderId=" + appOrderId
				+ ", insertTime=" + insertTime+ ", productName=" + productName
				+ ", externalOrderId=" + externalOrderId+ "]";
	}
	
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getAppName() {
		return appName==null?"":appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getMethodName() {
		return methodName==null?"":methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	public Long getSum() {
		return sum;
	}
	public void setSum(Long sum) {
		this.sum = sum;
	}
	public String getFlowDirection() {
		return flowDirection;
	}
	public void setFlowDirection(String flowDirection) {
		this.flowDirection = flowDirection;
	}
	public Long getBalance() {
		return balance;
	}
	public void setBalance(Long balance) {
		this.balance = balance;
	}
	public String getPeerAccountId() {
		return peerAccountId==null?"":peerAccountId;
	}
	public void setPeerAccountId(String peerAccountId) {
		this.peerAccountId = peerAccountId;
	}
	public String getPeerAccountUnit() {
		return peerAccountUnit==null?"":peerAccountUnit;
	}
	public void setPeerAccountUnit(String peerAccountUnit) {
		this.peerAccountUnit = peerAccountUnit;
	}
	public String getPeerAccountType() {
		return peerAccountType==null?"":peerAccountType;
	}
	public void setPeerAccountType(String peerAccountType) {
		this.peerAccountType = peerAccountType;
	}
	public String getType() {
		return type==null?"":type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getRemark() {
		return remark==null?"":remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getAppOrderId() {
		return appOrderId==null?"":appOrderId;
	}
	public void setAppOrderId(String appOrderId) {
		this.appOrderId = appOrderId;
	}
	public String getInsertTime() {
		return insertTime==null?"":insertTime;
	}
	public void setInsertTime(String insertTime) {
		this.insertTime = insertTime;
	}
	public String getProductName() {
		return productName==null?"":productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getExternalOrderId() {
		return externalOrderId==null?"":externalOrderId;
	}
	public void setExternalOrderId(String externalOrderId) {
		this.externalOrderId = externalOrderId;
	}
	
}
