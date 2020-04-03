package com.hummingbird.maaccount.vo;

import com.hummingbird.common.util.DateUtil;
import com.hummingbird.maaccount.entity.VolumecardAccount;
import com.hummingbird.maaccount.entity.VolumecardAccountProduct;
import com.hummingbird.maaccount.mapper.VolumecardAccountProductMapper;
import com.hummingbird.common.util.SpringBeanUtil;

/**
 * @author john huang
 * 2015年2月6日 下午5:55:45
 * 本类主要做为controller输出
 */
public class VolumecardResultVO {

	/**
	 * 卡号
	 */
	private String accountId;
	
	/**
     * 面额，单位为升
     */
    private Long amount;
	
	/**
     * 可用升数，单位为升
     */
    private Long balance;
	/**
	 * 有效期-开始日期
	 */
	private String startTime;		
	/**
	 * 有效期-结束日期
	 */
	private String endTime;		
	/**
     * OK#-正常，END-生命周期结束，NOP-未开通，FRZ-冻结
     */
    private String status;
    
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	public Long getAmount() {
		return amount;
	}
	public void setAmount(Long amount) {
		this.amount = amount;
	}
	
	public Long getBalance() {
		return balance;
	}
	public void setBalance(Long balance) {
		this.balance = balance;
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
	
	@Override
	public String toString() {
		return "OilcardResultVO [accountId=" + accountId + ", amount=" + amount
				+ ",  balance=" + balance+ ",  startTime=" + startTime
				+ ", endTime=" + endTime + ", status=" + status + "]";
	}
	
	public VolumecardResultVO(VolumecardAccount account,VolumecardAccountProduct product){
		accountId = account.getAccountId();
		amount = account.getAmount();
		
		balance = account.getBalance();
		startTime=DateUtil.formatCommonDateorNull(account.getStartTime());
		endTime=DateUtil.formatCommonDateorNull(account.getEndTime());
		String status2 = account.getStatus();
		switch(status2){
		case VolumecardAccount.STATUS_FRZ:status="冻结";break;
		case VolumecardAccount.STATUS_NOP:status="未开通";break;
		case VolumecardAccount.STATUS_OK:status="正常";break;
		case VolumecardAccount.STATUS_OFF:status="注销";break;
		}
	}
	
	/**
	 * 构造函数
	 */
	public VolumecardResultVO(VolumecardAccount account) {
	//	VolumecardAccountProductMapper oaprodao = SpringBeanUtil.getInstance().getBean(VolumecardAccountProductMapper.class);
		accountId = account.getAccountId();
		amount = account.getAmount();
		balance = account.getBalance();;
		startTime=DateUtil.formatCommonDateorNull(account.getStartTime());
		endTime=DateUtil.formatCommonDateorNull(account.getEndTime());
		String status2 = account.getStatus();
		switch(status2){
		case VolumecardAccount.STATUS_FRZ:status="冻结";break;
		case VolumecardAccount.STATUS_NOP:status="未开通";break;
		case VolumecardAccount.STATUS_OK:status="正常";break;
		case VolumecardAccount.STATUS_OFF:status="注销";break;
		}
		
	}
			
}
