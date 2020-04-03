/**
 * 
 * OilcardTrans2CashOrderVO.java
 * 版本所有 深圳市蜂鸟娱乐有限公司 2013-2014
 */
package com.hummingbird.maaccount.vo;

import com.hummingbird.common.util.ValidateUtil;

/**
 * @author john huang
 * 2015年4月23日 下午5:28:25
 * 本类主要做为
 */
public class OilcardTrans2CashOrderVO extends OrderVO implements PaymentCodeMD5able,AccountCodeable{

	protected String accountId;

	/**
	 * @return the accountId
	 */
	public String getAccountId() {
		return accountId;
	}

	/**
	 * @param accountId the accountId to set
	 */
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "OilcardTrans2CashOrderVO [accountId=" + accountId
				+ ", getPayOrderId()=" + getPayOrderId() + ", getMobileNum()="
				+ getMobileNum() + ", getUserToken()=" + getUserToken()
				+ ", getSum()=" + getSum() + ", getProductName()="
				+ getProductName() + ", getRemark()=" + getRemark()
				+ ", getAppOrderId()=" + getAppOrderId() + ", toString()="
				+ super.toString() + ", getOrderId()=" + getOrderId()
				+ ", getAccountCode()=" + getAccountCode()
				+ ", getPaymentCodeMD5()=" + getPaymentCodeMD5()
				+ ", getPeerAccountId()=" + getPeerAccountId()
				+ ", getPeerAccountUnit()=" + getPeerAccountUnit()
				+ ", getPeerAccountType()=" + getPeerAccountType()
				+ ", getExternalOrderId()=" + getExternalOrderId()
				+ ", getExternalOrderTime()=" + getExternalOrderTime()
				+ ", getPaintText()=" + getPaintText() + ", getClass()="
				+ getClass() + ", hashCode()=" + hashCode() + "]";
	}

	/* (non-Javadoc)
	 * @see com.hummingbird.maaccount.vo.OrderVO#getPaintText()
	 */
	@Override
	public String getPaintText() {
		return ValidateUtil.sortbyValues(getMobileNum(),getAccountId(),getAccountCode(),getAppOrderId(),getRemark(),String.valueOf(getSum()),getPaymentCodeMD5()); 
	}
	
	
	
	
}
