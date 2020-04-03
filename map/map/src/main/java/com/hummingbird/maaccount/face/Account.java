/**
 * 
 * Account.java
 * 版本所有 深圳市蜂鸟娱乐有限公司 2013-2014
 */
package com.hummingbird.maaccount.face;

import java.util.HashMap;
import java.util.Map;



/**
 * @author huangjiej_2
 * 2014年12月18日 下午10:28:57
 * 本类主要做为帐户接口
 */
public interface Account {

    /**
     * 未开通
     */
    public static final String STATUS_NOP = "NOP";
    /**
     * 正常
     */
    public static final String STATUS_OK = "OK#";
    /**
     * 注销
     */
    public static final String STATUS_OFF = "OFF";
    /**
     * 冻结
     */
    public static final String STATUS_FRZ = "FRZ";

	
	/**
	 * 虚拟-app帐户,它将不做任何事
	 */
	public static String ACCOUNT_APP="appAccount";
	/**
	 * 现金帐户
	 */
	public static String ACCOUNT_CASH="cashAccount";
	/**
	 * 投资帐户
	 */
	public static String ACCOUNT_INVESTMENT = "investmentAccount";
	/**
	 * 银行帐户
	 */
	public static String ACCOUNT_BANK =  "bankAccount";
	/**
	 * 汇通卡帐户
	 */
	public static String ACCOUNT_HUITONGCARD =  "huitongcardAccount";
	/**
	 * 分期卡帐户
	 */
	public static String ACCOUNT_OIL_CARD =  "OilcardAccount";
	/**
	 * 折扣卡
	 */
	public static String ACCOUNT_DISCOUNT_CARD = "DiscountcardAccount";
	/**
	 * 容量卡
	 */
	public static String ACCOUNT_VOLUME_CARD = "VolumecardAccount";
	/**
	 * 红包
	 */
	public static String ACCOUNT_REDPAPAER = "RedPaperAccount";
	/**
	 * 积分
	 */
	public static String ACCOUNT_JIFEN = "JifenAccount";
	
	

	/**
	 * 帐户代号
	 * @return
	 */
	String getAccountCode();
	
	/**
	 * 帐户名称
	 * @return
	 */
	String getAccountName();
	
	/**
	 * 是否启用
	 * @return
	 */
	boolean isEnabled();
	
	/**
	 * 取帐户余额
	 * @return
	 */
	Long getBalance();
	
	/**
	 * 取帐户ID
	 * @return
	 */
	String getAccountId();
	
	/**
	 * 检查帐户限额，如果不足则返回false
	 * @param limit
	 * @return
	 */
	boolean checkBalanceLimit(long limit);
	
	/**
	 * 虚拟帐户，帐户不由营销系统管理
	 * @return
	 */
	boolean isVirtualAccount();
	
	/**
	 * 获取对应的用户
	 * @return
	 */
	Integer getUserId();
	/**
	 * 获取账户明文
	 * @return
	 */
	String getBalanceValidateString();
	/**
	 * 获取对应的密文
	 * @return
	 */
	String getSignature();
	/**
	 * 更新密文
	 * @return
	 */
	void setSignature(String signature);
}
