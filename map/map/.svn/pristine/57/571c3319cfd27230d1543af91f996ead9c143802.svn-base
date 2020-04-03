package com.hummingbird.maaccount.vo;

import com.hummingbird.common.exception.DataInvalidException;
import com.hummingbird.common.util.ValidateUtil;

public class SpendJifenOrderVO implements IOrderVO{
	/* "order":{
    "mobileNum":"13912345678",
    "jifenProductId":"1234123412341234",
    "remark":"使用积分兑换", 
    "appOrderId":"AO201412122344888444",
    "associatedOrderId":"AO201412122344888444",
    "accountCode":"231435",
    "paymentCodeMD5":"w344dioeorreeoocWRT"
    },*/
	protected String mobileNum;
	protected String jifenProductId;
	protected Long sum;
	protected String remark;
	protected String appOrderId;
	protected String associatedOrderId;
	protected String accountCode;
	protected String paymentCodeMD5;
	@Override
	public String toString() {
		return "SpendRedPaperOrderVO [mobileNum=" + mobileNum + ", jifenProductId="
				+ jifenProductId + ", sum="+ sum 
				+ ", associatedOrderId=" + associatedOrderId + 
				", accountCode=" + accountCode + ", "
				+", paymentCodeMD5=" + paymentCodeMD5 + ", "
				+"appOrderId=" + appOrderId + " remark=" + remark +"]";
	}
	@Override
	public String getPaintText() {
		String mingwen=ValidateUtil.sortbyValues(mobileNum,jifenProductId,sum.toString(),remark,appOrderId,associatedOrderId,accountCode,paymentCodeMD5);
		return mingwen;
	}
	@Override
	public void selfvalidate() throws DataInvalidException {
		//ValidateUtil.assertNull(this.paymentCodeMD5, "支付密码");
		ValidateUtil.assertNull(this.remark, "备注");
		ValidateUtil.assertNull(this.sum, "积分额度");
		ValidateUtil.assertNull(this.jifenProductId, "红包编号");
		ValidateUtil.assertNull(this.mobileNum, "电话号码");
		ValidateUtil.assertNull(this.associatedOrderId, "关联订单号");
		ValidateUtil.assertNull(this.appOrderId, "app自定义订单号");
		
	}
	public String getMobileNum() {
		return mobileNum;
	}
	public void setMobileNum(String mobileNum) {
		this.mobileNum = mobileNum;
	}
	
	public String getJifenProductId() {
		return jifenProductId;
	}
	public void setJifenProductId(String jifenProductId) {
		this.jifenProductId = jifenProductId;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getAppOrderId() {
		return appOrderId;
	}
	public void setAppOrderId(String appOrderId) {
		this.appOrderId = appOrderId;
	}
	public String getAssociatedOrderId() {
		return associatedOrderId;
	}
	public void setAssociatedOrderId(String associatedOrderId) {
		this.associatedOrderId = associatedOrderId;
	}
	public String getAccountCode() {
		return accountCode;
	}
	public void setAccountCode(String accountCode) {
		this.accountCode = accountCode;
	}
	public String getPaymentCodeMD5() {
		return paymentCodeMD5;
	}
	public void setPaymentCodeMD5(String paymentCodeMD5) {
		this.paymentCodeMD5 = paymentCodeMD5;
	}
	public Long getSum() {
		return sum;
	}
	public void setSum(Long sum) {
		this.sum = sum;
	}
	
	
}
