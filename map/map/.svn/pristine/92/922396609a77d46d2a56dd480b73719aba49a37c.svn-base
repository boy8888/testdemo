/**
 * 
 * DefaultReceipt.java
 * 版本所有 深圳市蜂鸟娱乐有限公司 2013-2014
 */
package com.hummingbird.maaccount.vo;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hummingbird.maaccount.face.Account;
import com.hummingbird.maaccount.face.Receipt;

/**
 * @author huangjiej_2
 * 2014年12月28日 下午4:50:39
 * 本类主要做为
 */
public class DefaultReceipt implements Receipt{

	/**
	 * 订单号
	 */
	private String orderNo;
	/**
	 * 处理时间
	 */
	private Date dealtime;
	
	private Map ext=new HashMap();
	
	private List<Account> inAccounts=new ArrayList<Account>();
	
	private List<Account> outAccounts=new ArrayList<Account>();

	@Override
	public String getOrderNo(){
		return orderNo;
	}

	/**
	 * @param orderNo the orderNo to set
	 */
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	/* (non-Javadoc)
	 * @see com.hummingbird.maaccount.face.Receipt#getDealTime()
	 */
	@Override
	public Date getDealTime() {
		return dealtime;
	}
	
	/**
	 * @param string
	 * @return
	 */
	public  Object getExtValue(String key){
		return ext.get(key);
	}

	/**
	 * @param dealtime the dealtime to set
	 */
	public void setDealtime(Date dealtime) {
		this.dealtime = dealtime;
	}

	/**
	 * @return the ext
	 */
	public Map getExt() {
		return ext;
	}

	/**
	 * @param ext the ext to set
	 */
	public void setExt(Map ext) {
		this.ext = ext;
	}

	/**
	 * @return the inAccounts
	 */
	public List<Account> getInAccounts() {
		return inAccounts;
	}

	/**
	 * @param inAccounts the inAccounts to set
	 */
	public void setInAccounts(List<Account> inAccounts) {
		this.inAccounts = inAccounts;
	}

	/**
	 * @return the outAccounts
	 */
	public List<Account> getOutAccounts() {
		return outAccounts;
	}

	/**
	 * @param outAccounts the outAccounts to set
	 */
	public void setOutAccounts(List<Account> outAccounts) {
		this.outAccounts = outAccounts;
	}
	
	public void addInAccount(Account acc)
	{
		this.inAccounts.add(acc);
	}
	
	public void addOutAccount(Account acc)
	{
		this.outAccounts.add(acc);
	}
	
	public void addExt(Object key,Object value)
	{
		ext.put(key, value);
	}
	
}
