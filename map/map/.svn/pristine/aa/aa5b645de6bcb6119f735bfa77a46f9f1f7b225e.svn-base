/**
 * 
 * OilcardResultVO.java
 * 版本所有 深圳市蜂鸟娱乐有限公司 2013-2014
 */
package com.hummingbird.maaccount.vo;

import com.hummingbird.common.util.DateUtil;
import com.hummingbird.common.util.SpringBeanUtil;
import com.hummingbird.maaccount.entity.OilcardAccount;
import com.hummingbird.maaccount.entity.OilcardAccountProduct;
import com.hummingbird.maaccount.mapper.OilcardAccountProductMapper;

/**
 * @author john huang
 * 2015年2月6日 下午2:55:45
 * 本类主要做为controller输出
 */
public class OilcardResultVO {

	/**
	 * 卡号
	 */
	private String accountId;
	
	/**
	 * 油卡购买总额
	 */
	private Long amount;
	/**
	 * 油卡剩余总额
	 */
	private Long restAmount;
	/**
	 * 油卡当前可用金额
	 */
	private Long balance;
	/**
	 * 分期返还金额
	 */
	private Long returnSum;
	/**
	 * 分期总数
	 */
	private int totalStages;
	/**
	 * 剩余分期数
	 */
	private int restStages;
	/**
	 * 油卡有效期-开始日期
	 */
	private String startTime;		
	/**
	 * 油卡有效期-结束日期
	 */
	private String endTime;		
	/**
	 * 状态，正常、冻结、锁定
	 */
	private String status;		
			
	/**
	 * @return 卡号
	 */
	public String getAccountId() {
		return accountId;
	}
	/**
	 * @param 卡号
	 */
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	/**
	 * @return 油卡购买总额
	 */
	public Long getAmount() {
		return amount;
	}
	/**
	 * @param 油卡购买总额
	 */
	public void setAmount(Long amount) {
		this.amount = amount;
	}
	/**
	 * @return the restAmount
	 */
	public Long getRestAmount() {
		return restAmount;
	}
	/**
	 * @param restAmount the restAmount to set
	 */
	public void setRestAmount(Long restAmount) {
		this.restAmount = restAmount;
	}
	/**
	 * @return the balance
	 */
	public Long getBalance() {
		return balance;
	}
	/**
	 * @param balance the balance to set
	 */
	public void setBalance(Long balance) {
		this.balance = balance;
	}
	/**
	 * @return the returnSum
	 */
	public Long getReturnSum() {
		return returnSum;
	}
	/**
	 * @param returnSum the returnSum to set
	 */
	public void setReturnSum(Long returnSum) {
		this.returnSum = returnSum;
	}
	/**
	 * @return the totalStages
	 */
	public int getTotalStages() {
		return totalStages;
	}
	/**
	 * @param totalStages the totalStages to set
	 */
	public void setTotalStages(int totalStages) {
		this.totalStages = totalStages;
	}
	/**
	 * @return the restStage
	 */
	public int getRestStages() {
		return restStages;
	}
	/**
	 * @param restStage the restStage to set
	 */
	public void setRestStages(int restStage) {
		this.restStages = restStage;
	}
	/**
	 * @return the startTime
	 */
	public String getStartTime() {
		return startTime;
	}
	/**
	 * @param startTime the startTime to set
	 */
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	/**
	 * @return the endTime
	 */
	public String getEndTime() {
		return endTime;
	}
	/**
	 * @param endTime the endTime to set
	 */
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "OilcardResultVO [accountId=" + accountId + ", amount=" + amount
				+ ", restAmount=" + restAmount + ", balance=" + balance
				+ ", returnSum=" + returnSum + ", totalStages=" + totalStages
				+ ", restStages=" + restStages + ", startTime=" + startTime
				+ ", endTime=" + endTime + ", status=" + status + "]";
	}
	
	public OilcardResultVO(OilcardAccount account,OilcardAccountProduct product){
		accountId = account.getAccountId();
		amount = account.getAmount();
		restAmount = account.getRestAmount();
		balance = account.getBalance();
		returnSum = product.getReturnSum();
		totalStages = product.getTotalStages();
		restStages=account.getRestStages();
		startTime=DateUtil.formatCommonDateorNull(account.getStartTime());
		endTime=DateUtil.formatCommonDateorNull(account.getEndTime());
		String status2 = account.getStatus();
		switch(status2){
		case OilcardAccount.STATUS_FRZ:status="冻结";break;
		case OilcardAccount.STATUS_NOP:status="未开通";break;
		case OilcardAccount.STATUS_OK:status="正常";break;
		case OilcardAccount.STATUS_OFF:status="注销";break;
		}
	}
	/**
	 * 构造函数
	 */
	public OilcardResultVO(OilcardAccount account) {
		OilcardAccountProductMapper oaprodao = SpringBeanUtil.getInstance().getBean(OilcardAccountProductMapper.class);
		OilcardAccountProduct product = oaprodao.selectByPrimaryKey(account.getProductId());
		accountId = account.getAccountId();
		amount = account.getAmount();
		restAmount = account.getRestAmount();
		balance = account.getBalance();
		returnSum = product.getReturnSum();
		totalStages = product.getTotalStages();
		restStages=account.getRestStages();
		startTime=DateUtil.formatCommonDateorNull(account.getStartTime());
		endTime=DateUtil.formatCommonDateorNull(account.getEndTime());
		String status2 = account.getStatus();
		switch(status2){
		case OilcardAccount.STATUS_FRZ:status="冻结";break;
		case OilcardAccount.STATUS_NOP:status="未开通";break;
		case OilcardAccount.STATUS_OK:status="正常";break;
		case OilcardAccount.STATUS_OFF:status="注销";break;
		}
		
	}
	
	
}
