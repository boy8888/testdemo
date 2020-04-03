package com.hummingbird.maaccount.vo;

import com.hummingbird.common.util.DateUtil;
import com.hummingbird.maaccount.entity.JifenAccount;
import com.hummingbird.maaccount.entity.RedPaperAccount;

public class JifenResultVO {
	/*"order":{
    "orderId":"12345678901234567890",
    "jifenProductId":"HONGBAO_YYD",
    "sum":1000,
    "restAmount":3000,
    "status":"正常"
}*/
	private String orderId;
	private String jifenProductId;
	private Long sum;
	private Long restAmount;
	private String status;
	
	@Override
	public String toString() {
		return "JifenResultVO [orderId=" + orderId + ", jifenProductId="
				+ jifenProductId+  ", sum=" + sum
				+ ", restAmount=" + restAmount 
				+ ", status=" + status + "]";
	}
	
	public JifenResultVO(JifenAccount account,String orderNo,Long s){
		orderId = orderNo.toString();
		jifenProductId = account.getProductId();
		sum = s;
		restAmount=account.getBalance();
		
		String status2 = account.getStatus();
		switch(status2){
		case JifenAccount.STATUS_END:status="未开通";break;
		case JifenAccount.STATUS_OK:status="正常";break;
		
		}
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getJifenProductId() {
		return jifenProductId;
	}
	public void setJifenProductId(String jifenProductId) {
		this.jifenProductId = jifenProductId;
	}
	public Long getSum() {
		return sum;
	}
	public void setSum(Long sum) {
		this.sum = sum;
	}
	public Long getRestAmount() {
		return restAmount;
	}
	public void setRestAmount(Long restAmount) {
		this.restAmount = restAmount;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}
