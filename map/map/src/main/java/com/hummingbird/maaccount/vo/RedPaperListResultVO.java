package com.hummingbird.maaccount.vo;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;

import com.hummingbird.common.constant.CommonStatusConst;
import com.hummingbird.common.util.DateUtil;
import com.hummingbird.common.util.SpringBeanUtil;
import com.hummingbird.maaccount.entity.RedPaperAccount;
import com.hummingbird.maaccount.entity.RedPaperProduct;
import com.hummingbird.maaccount.exception.MaAccountException;
import com.hummingbird.maaccount.mapper.RedPaperProductMapper;

public class RedPaperListResultVO {
	/* "redPaperProductId":"HONGBAO_YYD",
     "redPaperProductName":"有油贷红包",
     "redPaperId":"RP20150404123456",
     "sum":100000,
     "startTime":"",
     "endTime":"2015-05-31 24:00:00",
     "status":"正常"        */
	protected String redPaperProductId;
	protected String redPaperProductName;
	protected String redPaperId;
	protected String sum;
	protected String startTime;
	protected String endTime;
	protected String status;
	private String remark;
	@Override
	public String toString() {
		return "QueryRedPaperResultVO [redPaperProductId=" + redPaperProductId + 
				", redPaperProductName"+redPaperProductName+
				",redPaperId"+redPaperId+",sum"+sum+",startTime"+startTime+
				",endTime"+endTime+",status"+status+",remark"+remark+"]";
	}
	/**
	 * 构造函数
	 */
	public RedPaperListResultVO() {
		super();
	}
	/**
	 * 构造函数
	 */
	public RedPaperListResultVO(RedPaperAccount account) throws MaAccountException{
		RedPaperProductMapper rcDao=SpringBeanUtil.getInstance().getBean(RedPaperProductMapper.class);
		
		RedPaperProduct product=rcDao.selectByPrimaryKey(account.getProductId());
		if(product==null){
			throw new MaAccountException(MaAccountException.ERR_PRODUCT_EXCEPTION,account.getProductId()+"红包产品不存在");
		}
		redPaperProductId=account.getProductId();
		redPaperProductName=product.getProductName();
		redPaperId=account.getAccountId();
		sum=account.getAmount().toString();
		remark=account.getRemark();
		startTime=DateUtil.formatCommonDateorNull(account.getStartTime());
		endTime=DateUtil.formatCommonDateorNull(account.getEndTime());
		if(account.getEndTime()==null||new Date().compareTo(account.getEndTime())<=0){
			status="正常";
		}
		else{
			status="无效";
		}
		if("USD".equals(account.getStatus())){
			status="已消费";
		}
		
	}
	
	public String getRedPaperProductId() {
		return redPaperProductId;
	}
	public void setRedPaperProductId(String redPaperProductId) {
		this.redPaperProductId = redPaperProductId;
	}
	public String getRedPaperProductName() {
		return redPaperProductName;
	}
	public void setRedPaperProductName(String redPaperProductName) {
		this.redPaperProductName = redPaperProductName;
	}
	public String getRedPaperId() {
		return redPaperId;
	}
	public void setRedPaperId(String redPaperId) {
		this.redPaperId = redPaperId;
	}
	public String getSum() {
		return sum;
	}
	public void setSum(String sum) {
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
