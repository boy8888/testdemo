package com.hummingbird.maaccount.vo;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.codehaus.jackson.annotate.JsonIgnore;

import com.hummingbird.common.exception.DataInvalidException;
import com.hummingbird.common.exception.ValidateException;
import com.hummingbird.common.util.ValidateUtil;

/**
 * 折扣卡线上开油我发起加油卡VO
 */
public class YouMeDiscountCardOrderVO extends DiscountCardOrderVO {

	
    /**
	 * 可以消费的油站pos机id
	 */
	public List<String> terminalIds;
	/**
	 * 可以消费的油站
	 */
	public List<String> storeIds;
	/**
	 * 可以消费的产品，它使用中经的产品id
	 */
	public List<String> zjProducts;
	 /**
	  * 卡片购买金额
	  */
	public Long productPrice;
	/**
	 * 卡片面额
	 */
	public Long amount;
	 
	 /**
	 * 卡片有效期开始
	 */
	public String startTime;
	
	/**
	 * 卡片有效期结束
	 */
	public String endTime;
	/**
	 * 卡片有效消费开始时间，在此时间段内才能使用此卡消费
	 */
	public String customStartTime;
	/**
	 * 卡片有效消费结束时间，在此时间段内才能使用此卡消费
	 */
	public String customEndTime;
	/**
	 * 有效期开始
	 */
	private Date startTimeDate;
	/**
	 * 有效期结束
	 */
	private Date endTimeDate;
	/**
	 * 消费时间开始
	 */
	private Date customStartTimeDate;
	/**
	 * 消费时间结束
	 */
	private Date customEndTimeDate;
	
	 
	/* (non-Javadoc)
	 * @see com.hummingbird.maaccount.vo.IOrderVO#getPaintText()
	 */
	@JsonIgnore
	@Override
	public String getPaintText() {
		
		List<String> forsign = new ArrayList<String>();
		forsign.add(ID             );
		forsign.add(name           );
		forsign.add(mobileNum      );
		forsign.add(smsCode        );
		forsign.add(channelOrderId );
		forsign.add(channelNo      );
		forsign.add(appOrderId     );
		forsign.add(productId      );
		forsign.add(remark         );
		forsign.add(String.valueOf(productPrice)   );
		forsign.add(String.valueOf(amount)   );
		forsign.add(startTime      );
		forsign.add(endTime        );
		forsign.add(customStartTime);
		forsign.add(customEndTime  );
		forsign.add(ValidateUtil.sortbyValues(zjProducts));
		if(terminalIds!=null){
			forsign.add(ValidateUtil.sortbyValues(terminalIds));
		}
		forsign.add(ValidateUtil.sortbyValues(storeIds));
		
		return ValidateUtil.sortbyValues(forsign);
	}

	

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "YouMeDiscountCardOrderVO [terminalIds=" + terminalIds + ", storeIds=" + storeIds + ", zjProducts="
				+ zjProducts + ", productPrice=" + productPrice + ", amount=" + amount + ", startTime=" + startTime
				+ ", endTime=" + endTime + ", customStartTime=" + customStartTime + ", customEndTime=" + customEndTime
				+ ", startTimeDate=" + startTimeDate + ", endTimeDate=" + endTimeDate + ", customStartTimeDate="
				+ customStartTimeDate + ", customEndTimeDate=" + customEndTimeDate + ", ID=" + ID + ", name=" + name
				+ ", mobileNum=" + mobileNum + ", smsCode=" + smsCode + ", channelOrderId=" + channelOrderId
				+ ", channelNo=" + channelNo + ", appOrderId=" + appOrderId + ", productId=" + productId + ", remark="
				+ remark + "]";
	}

	/* (non-Javadoc)
	 * @see com.hummingbird.maaccount.vo.IOrderVO#selfvalidate()
	 */
//	@Override
	public void selfvalidate() throws DataInvalidException {
		ValidateUtil.assertNull(this.ID, "身份证号");
		ValidateUtil.assertNull(this.remark, "备注");
		ValidateUtil.assertNull(this.name, "姓名");
//		ValidateUtil.assertNull(this.smsCode, "短信验证码");
//		ValidateUtil.assertNull(this.channelOrderId, "渠道自定义订单号");
//		ValidateUtil.assertNull(this.channelNo, "渠道编码");
		ValidateUtil.assertNull(this.appOrderId, "app自定义订单号");
		ValidateUtil.assertNull(this.productId, "折扣卡账户产品编号");
		if(StringUtils.isNotBlank(startTime)){
			try {
				startTimeDate = DateUtils.parseDate(startTime, new String[]{"yyyy-MM-dd","yyyy-MM-dd HH:mm:ss"});
			} catch (ParseException e) {
				throw ValidateException.ERROR_PARAM_FORMAT_ERROR.clone(e, "卡片有效期开始格式不符:"+startTime);
			}
		}
		if(StringUtils.isNotBlank(endTime)){
			try {
				endTimeDate = DateUtils.parseDate(endTime, new String[]{"yyyy-MM-dd","yyyy-MM-dd HH:mm:ss"});
			} catch (ParseException e) {
				throw ValidateException.ERROR_PARAM_FORMAT_ERROR.clone(e, "卡片有效期结束格式不符:"+endTime);
			}
		}
		if(StringUtils.isNotBlank(customStartTime)){
			try {
				customStartTimeDate = DateUtils.parseDate(customStartTime, new String[]{"HH","HH:mm","HH:mm:ss"});
			} catch (ParseException e) {
				throw ValidateException.ERROR_PARAM_FORMAT_ERROR.clone(e, "卡片消费开始时间格式不符:"+customStartTime);
			}
		}
		else{
			throw ValidateException.ERROR_PARAM_NULL.clone(null, "卡片消费开始时间为空");
		}
		if(StringUtils.isNotBlank(customEndTime)){
			try {
				customEndTimeDate = DateUtils.parseDate(customEndTime, new String[]{"HH","HH:mm","HH:mm:ss"});
			} catch (ParseException e) {
				throw ValidateException.ERROR_PARAM_FORMAT_ERROR.clone(e, "卡片消费结束时间格式不符:"+customEndTime);
			}
		}
		else{
			throw ValidateException.ERROR_PARAM_NULL.clone(null, "卡片消费结束时间为空");
		}
	}

	/**
	 * 可以消费的油站pos机id
	 */
	public List<String> getTerminalIds() {
		return terminalIds;
	}

	/**
	 * 可以消费的油站pos机id
	 */
	public void setTerminalIds(List<String> terminalIds) {
		this.terminalIds = terminalIds;
	}

	/**
	 * 可以消费的产品，它使用中经的产品id 
	 */
	public List<String> getZjProducts() {
		return zjProducts;
	}

	/**
	 * 可以消费的产品，它使用中经的产品id 
	 */
	public void setZjProducts(List<String> zjProducts) {
		this.zjProducts = zjProducts;
	}

	/**
	 * 卡片购买金额 
	 */
	public Long getProductPrice() {
		return productPrice;
	}

	/**
	 * 卡片购买金额 
	 */
	public void setProductPrice(Long productPrice) {
		this.productPrice = productPrice;
	}

	/**
	 * 卡片有效期开始 
	 */
	public String getStartTime() {
		return startTime;
	}

	/**
	 * 卡片有效期开始 
	 */
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	/**
	 * 卡片有效期结束 
	 */
	public String getEndTime() {
		return endTime;
	}

	/**
	 * 卡片有效期结束 
	 */
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	/**
	 * 卡片有效消费开始时间，在此时间段内才能使用此卡消费 
	 */
	public String getCustomStartTime() {
		return customStartTime;
	}

	/**
	 * 卡片有效消费开始时间，在此时间段内才能使用此卡消费 
	 */
	public void setCustomStartTime(String customStartTime) {
		this.customStartTime = customStartTime;
	}

	/**
	 * 卡片有效消费结束时间，在此时间段内才能使用此卡消费 
	 */
	public String getCustomEndTime() {
		return customEndTime;
	}

	/**
	 * 卡片有效消费结束时间，在此时间段内才能使用此卡消费 
	 */
	public void setCustomEndTime(String customEndTime) {
		this.customEndTime = customEndTime;
	}



	/**
	 * 卡片面额 
	 */
	public Long getAmount() {
		return amount;
	}



	/**
	 * 卡片面额 
	 */
	public void setAmount(Long amount) {
		this.amount = amount;
	}



	/**
	 * 有效期开始 
	 */
	public Date getStartTimeDate() {
		return startTimeDate;
	}



	/**
	 * 有效期结束 
	 */
	public Date getEndTimeDate() {
		return endTimeDate;
	}



	/**
	 * 消费时间开始 
	 */
	public Date getCustomStartTimeDate() {
		return customStartTimeDate;
	}



	/**
	 * 消费时间结束 
	 */
	public Date getCustomEndTimeDate() {
		return customEndTimeDate;
	}



	/**
	 * 可以消费的油站 
	 */
	public List<String> getStoreIds() {
		return storeIds;
	}



	/**
	 * 可以消费的油站 
	 */
	public void setStoreIds(List<String> storeIds) {
		this.storeIds = storeIds;
	}



}
