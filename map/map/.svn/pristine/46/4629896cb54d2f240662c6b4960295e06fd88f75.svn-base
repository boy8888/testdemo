package com.hummingbird.maaccount.vo;

import com.hummingbird.common.exception.DataInvalidException;
import com.hummingbird.common.util.ValidateUtil;

public class JifenOrderVO implements IOrderVO{
	/*"order":
    {
        "mobileNum":"13912345678",
        "sum":1000, 
        "jifenProductId":"JF_YYD", 
        "appOrderId":"123456789",
        "remark":"增加1000个积分"},*/
	protected String mobileNum;
	protected Long sum;
	protected String jifenProductId;
	protected String appOrderId;
	protected String remark;

	@Override
	public void selfvalidate() throws DataInvalidException {
		ValidateUtil.assertNull(this.sum, "积分个数");
		ValidateUtil.assertNull(this.remark, "备注");
		ValidateUtil.assertNull(this.jifenProductId, "积分产品编号");
		ValidateUtil.assertNull(this.mobileNum, "电话号码");
		ValidateUtil.assertNull(this.appOrderId, "app自定义订单号");
	}
	
	@Override
	public String getPaintText() {
		String mingwen=ValidateUtil.sortbyValues(mobileNum,sum.toString(),jifenProductId,appOrderId,remark);
		return mingwen;
		}
	
	public Long getSum() {
		return sum;
	}

	public void setSum(Long sum) {
		this.sum = sum;
	}

	public String getJifenProductId() {
		return jifenProductId;
	}

	public void setJifenProductId(String jifenProductId) {
		this.jifenProductId = jifenProductId;
	}

	public String getAppOrderId() {
		return appOrderId;
	}

	public void setAppOrderId(String appOrderId) {
		this.appOrderId = appOrderId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setMobileNum(String mobileNum) {
		this.mobileNum = mobileNum;
	}

	

	

	@Override
	public String getMobileNum() {
		return mobileNum;
	}
}
