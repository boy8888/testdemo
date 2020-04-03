package com.hummingbird.maaccount.vo;

import com.hummingbird.maaccount.entity.JifenAccount;
public class JifenListResultVO {
	/* {
        "jifenProductId":"HONGBAO_YYD",
        "restAmount":100000,
        "status":"正常"            
    },       */
	protected String JifenProductId;
	protected String restAmount;
	protected String status;
	@Override
	public String toString() {
		return "QueryJifenResultVO [JifenProductId=" + JifenProductId + 
				", restAmount"+restAmount+",status"+status+"]";
	}
	
	/**
	 * 构造函数
	 */
	public JifenListResultVO(JifenAccount account) {
		
		JifenProductId=account.getProductId();
		
		restAmount=account.getBalance().toString();
	
		if(account.getStatus().equals("OK#")){
			status="正常";
		}
		else{
			status="无效";
		}
		
	}

	public String getJifenProductId() {
		return JifenProductId;
	}

	public void setJifenProductId(String jifenProductId) {
		JifenProductId = jifenProductId;
	}

	public String getRestAmount() {
		return restAmount;
	}

	public void setRestAmount(String restAmount) {
		this.restAmount = restAmount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
