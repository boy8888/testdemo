/**
 * 
 * QueryOilcardOpenResultVO.java
 * 版本所有 深圳市蜂鸟娱乐有限公司 2013-2014
 */
package com.hummingbird.maaccount.vo;

/**
 * @author john huang
 * 2015年2月21日 上午12:02:03
 * 本类主要做为查询开卡结果接口的参数
 */
public class QueryOilcardOpenResultVO extends BaseTransOrderVO {

	protected String appOrderId;

	/**
	 * @return the appOrderId
	 */
	public String getAppOrderId() {
		return appOrderId;
	}

	/**
	 * @param appOrderId the appOrderId to set
	 */
	public void setAppOrderId(String appOrderId) {
		this.appOrderId = appOrderId;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "QueryOilcardOpenResultVO [appOrderId=" + appOrderId + ", app="
				+ app + "]";
	}
	
	
	
}
