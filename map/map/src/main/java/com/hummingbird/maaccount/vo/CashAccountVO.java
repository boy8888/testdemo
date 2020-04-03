package com.hummingbird.maaccount.vo;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.hummingbird.common.util.DateUtil;
import com.hummingbird.maaccount.entity.CashAccount;
import com.hummingbird.maaccount.entity.RealNameAuth;
import com.hummingbird.maaccount.entity.RedPaperAccount;
import com.hummingbird.maaccount.entity.User;

//查询现金账户，开通现金账户返回数据
public class CashAccountVO {

	/*{
	    "account":{
	        "mobileNum":"13912345678",
	        "accountId":"9512123412341234",
	        "createTime":"20150505",
	        "status":"OK#",
	        "disableReason":"",
	        "balance":
	        "customer":{
	            "name":"张三",
	            "idType":"ID#",
	            "idCode":"441233343143432141413"
	        }
	    }
	}*/
	
	
	
	private String mobileNum;
	private String accountId;
	private String createTime;
	private String status;
	private String disableReason;
	private CustomerVO customer;
	private Long balance;
	public String toString() {
		return "CashAccountVO [mobileNum=" + mobileNum + ", accountId=" + accountId
				+ ", createTime=" + createTime + ", status=" + status
				+ ", disableReason=" + disableReason + ", customer=" + customer
				+ ", balance=" + balance+ "]";
	}
	
	public CashAccountVO(String mobileNum,CashAccount account,RealNameAuth realNameuser,User user){
		this.mobileNum = mobileNum;
		accountId=account.getAccountId();
		createTime=DateUtil.formatCommonDateorNull(user.getInsertTime());
		
		balance=account.getBalance();
		if(StringUtils.isBlank(user.getPaymentCodeDES())){
			status="NPC";
			disableReason="未设置支付密码，卡不能正常使用";
		}
		else{
			status=realNameuser.getStatus();
			switch(status){
				case "NRN":disableReason="未实名认证，但可以消费，不可以二次充值";break;
				case "OK#":disableReason="正常";break;
				case "CHK":disableReason="实名审核中，但可以消费，不可以二次充值";break;
				case "FRZ":disableReason="冻结账户";break;
				case "NON":disableReason="不存在/注销";break;
				case "FLS":disableReason="实名认证失败";break;
				default :disableReason=account.getRemark();break;
			}
		}
		customer=new CustomerVO(realNameuser);
	}
	public CashAccountVO(String mobileNum,CashAccount account,User user){
		this.mobileNum = mobileNum;
		accountId=account.getAccountId();
		createTime=DateUtil.formatCommonDateorNull(user.getInsertTime());
		if(StringUtils.isBlank(user.getPaymentCodeDES())){
			status="NPC";
			disableReason="未设置支付密码，卡不能正常使用";
		}
		else{
			status="NRN";
			disableReason="未实名认证，但可以消费，不可以二次充值";
		}
		balance=account.getBalance();
		customer=new CustomerVO();
	}
	
	public String getMobileNum() {
		return mobileNum;
	}
	public void setMobileNum(String mobileNum) {
		this.mobileNum = mobileNum;
	}
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDisableReason() {
		return disableReason;
	}
	public void setDisableReason(String disableReason) {
		this.disableReason = disableReason;
	}
	public CustomerVO getCustomer() {
		return customer;
	}
	public void setCustomer(CustomerVO customer) {
		this.customer = customer;
	}

	public Long getBalance() {
		return balance;
	}

	public void setBalance(Long balance) {
		this.balance = balance;
	}
	
}
