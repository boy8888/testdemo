/**
 * 
 * Receipt.java
 * 版本所有 深圳市蜂鸟娱乐有限公司 2013-2014
 */
package com.hummingbird.maaccount.face;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author huangjiej_2
 * 2014年12月18日 下午10:56:48
 * 本类主要做为 回执接口，为帐户收入或支出时返回值
 */
public interface Receipt {

	public abstract String getOrderNo();

	/**
	 * @return
	 */
	public abstract Date getDealTime();

	
	/**
	 * 获取收入帐户
	 * @return
	 */
	public List<Account> getInAccounts();
	
	/**
	 * 获取支出帐户，一般 收入-支出 是1对多或多对1或1对1的关系
	 * @return
	 */
	public List<Account> getOutAccounts();
	
	/**
	 * 获取扩展信息
	 * @return
	 */
	public Map getExt();

	/**
	 * @param string
	 * @return
	 */
	public abstract Object getExtValue(String key);
	
}
