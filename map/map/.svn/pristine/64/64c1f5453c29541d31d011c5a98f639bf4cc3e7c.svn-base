package com.hummingbird.maaccount.vo;

import com.hummingbird.common.util.DateUtil;
import com.hummingbird.common.util.SpringBeanUtil;
import com.hummingbird.maaccount.entity.RedPaperAccount;
import com.hummingbird.maaccount.entity.RedPaperAccountOrder;
import com.hummingbird.maaccount.entity.RedPaperProduct;
import com.hummingbird.maaccount.mapper.RedPaperProductMapper;

public class RedPaperResultVO {
	/* "order":{
    "orderId":"12345678901234567890",
    "redPaperProductId":"HONGBAO_YYD",
    "redPaperId":"RP20150404123456",
    "sum":100000,
    "startTime":"",
    "endTime":"2015-05-31 24:00:00",
    "status":"正常"
	}*/
	private String orderId;
	private String redPaperProductId;
	private String redPaperId;
	private Long sum;
	private String startTime;
	private String endTime;
	private String status;
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "RedPaperResultVO [orderId=" + orderId + ", redPaperProductId=" + redPaperProductId
				+ ", redPaperId=" + redPaperId + ", sum=" + sum
				+ ", startTime=" + startTime + ", endTime=" + endTime
				+ ", status=" + status + "]";
	}
	
	public RedPaperResultVO(RedPaperAccount account,String orderNo){
		orderId = orderNo.toString();
		redPaperProductId = account.getProductId();
		redPaperId = account.getAccountId();
		sum = account.getAmount();
		startTime=DateUtil.formatCommonDateorNull(account.getStartTime());
		endTime=DateUtil.formatCommonDateorNull(account.getEndTime());
		String status2 = account.getStatus();
		switch(status2){
		case RedPaperAccount.STATUS_NOP:status="未开通";break;
		case RedPaperAccount.STATUS_OK:status="正常";break;
		case RedPaperAccount.STATUS_OFF:status="注销";break;
		}
	}
	
	
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getRedPaperProductId() {
		return redPaperProductId;
	}
	public void setRedPaperProductId(String redPaperProductId) {
		this.redPaperProductId = redPaperProductId;
	}
	public String getRedPaperId() {
		return redPaperId;
	}
	public void setRedPaperId(String redPaperId) {
		this.redPaperId = redPaperId;
	}
	public Long getSum() {
		return sum;
	}
	public void setSum(Long sum) {
		this.sum = sum;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
