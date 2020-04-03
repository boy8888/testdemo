package com.hummingbird.maaccount.vo;

import com.hummingbird.common.exception.DataInvalidException;
import com.hummingbird.common.util.DateUtil;
import com.hummingbird.common.util.ValidateUtil;
import com.hummingbird.maaccount.entity.OilcardAccount;
import com.hummingbird.maaccount.entity.OilcardAccountProduct;
import com.hummingbird.maaccount.entity.RedPaperAccount;
import com.hummingbird.maaccount.entity.RedPaperProduct;
import com.hummingbird.maaccount.exception.MaAccountException;
import com.hummingbird.maaccount.face.Consumer;

public class RedPaperOrderVO implements IOrderVO{
	/* "order":
     {
         "mobileNum":"13912345678",
         "sum":100000, 
         "redPaperProductId":"HONGBAO_YYD", 
         "appOrderId":"123456789",
         "remark":"某某为用户赠送有油贷1000元红包"},*/
	protected String mobileNum;
	protected Long sum;
	protected String redPaperProductId;
	protected String appOrderId;
	protected String remark;
	protected String activity;
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "RedPaperOrderVO [mobileNum=" + mobileNum + ", sum="
				+ sum + ", redPaperProductId=" + redPaperProductId + ", "
				+ "appOrderId=" + appOrderId + " remark=" + remark +"]";
	}
	
	@Override
	public String getPaintText() {
		String mingwen=ValidateUtil.sortbyValues(mobileNum,sum.toString(),remark,appOrderId,redPaperProductId,activity);
		return mingwen;
	}
	public String getMobileNum() {
		return mobileNum;
	}
	public void setMobileNum(String mobileNum) {
		this.mobileNum = mobileNum;
	}
	
	public Long getSum() {
		return sum;
	}

	public void setSum(Long sum) {
		this.sum = sum;
	}

	public String getRedPaperProductId() {
		return redPaperProductId;
	}
	public void setRedPaperProductId(String redPaperProductId) {
		this.redPaperProductId = redPaperProductId;
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
	public String getActivity() {
		return activity;
	}

	public void setActivity(String activity) {
		this.activity = activity;
	}

	@Override
	public void selfvalidate() throws DataInvalidException {
		ValidateUtil.assertNull(this.sum, "红包金额");
		ValidateUtil.assertNull(this.remark, "备注");
		ValidateUtil.assertNull(this.redPaperProductId, "红包产品编号");
		ValidateUtil.assertNull(this.mobileNum, "电话号码");
		ValidateUtil.assertNull(this.appOrderId, "app自定义订单号");
		
	}
	
	
}
