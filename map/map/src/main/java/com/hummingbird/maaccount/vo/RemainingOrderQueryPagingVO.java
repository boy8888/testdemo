/**
 * 
 * OrderQueryPagingVO.java
 * 版本所有 深圳市蜂鸟娱乐有限公司 2013-2014
 */
package com.hummingbird.maaccount.vo;

/**
 * @author huangjiej_2
 * 2015年1月8日 下午10:15:48
 * 本类主要做为订单查询条件
 */
public class RemainingOrderQueryPagingVO extends OrderQueryPagingVO{

	/**
	 * 构造函数
	 */
	public RemainingOrderQueryPagingVO() {
		// TODO Auto-generated constructor stub
	}
	
	protected String flowDirection;
	protected String type;
	protected String peerAccountType;
	public String getFlowDirection() {
		return flowDirection;
	}
	public void setFlowDirection(String flowDirection) {
		this.flowDirection = flowDirection;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getPeerAccountType() {
		return peerAccountType;
	}
	public void setPeerAccountType(String peerAccountType) {
		this.peerAccountType = peerAccountType;
	}
	
	
	

}
