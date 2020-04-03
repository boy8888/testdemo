package com.hummingbird.maaccount.service;

import java.util.List;

import com.hummingbird.common.exception.DataInvalidException;
import com.hummingbird.common.face.Pagingnation;
import com.hummingbird.maaccount.entity.AccountPayAllow;
import com.hummingbird.maaccount.vo.CardSelecter;

/**
 * 根据用户查询有效期记录
 * */
public interface AccountPayAllowService {

	public List<AccountPayAllow> selectAllowListByAccountId(String accountId,Pagingnation page);

	/**
	 * 添加加油卡允许支付的产品
	 * @param accountId
	 * @param groupId
	 * @param zjproducts
	 * @throws DataInvalidException 
	 */
	public void addPayProducts(String accountId, String groupId, String[] zjproducts) throws DataInvalidException;

	/**
	 * 添加加油卡允许支付的终端
	 * @param accountId
	 * @param groupId
	 * @param terminalIds
	 * @param description
	 * @throws DataInvalidException 
	 */
	public void addPayTerminals(String accountId, String groupId, String[] terminalIds, String description) throws DataInvalidException;

	/**
	 * 添加加油卡允许支付的门店
	 * @param accountId
	 * @param groupId
	 * @param terminalIds
	 * @param description
	 * @throws DataInvalidException 
	 */
	public void addPayStores(String accountId, String groupId, String[] storeIds, String description) throws DataInvalidException;

	/**
	 * 添加加油卡允许支付的时间
	 * @param accountId
	 * @param groupId
	 * @param periodTime
	 * @param cycleType
	 * @param cycleDate
	 * @param description
	 * @throws DataInvalidException 
	 */
	public void addPayTimes(String accountId, String groupId, String[] periodTime, String cycleType, String cycleDate,
			String description) throws DataInvalidException;

	/**
	 * 查询可以支付的账户
	 * @param cs
	 * @return 账户列表
	 */
	public List<String> getAccountCanPay(CardSelecter cs);
	
	

	
}
