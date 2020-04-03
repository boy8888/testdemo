package com.hummingbird.maaccount.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hummingbird.maaccount.entity.DiscountCardAccount;
import com.hummingbird.maaccount.entity.OilcardAccount;
import com.hummingbird.maaccount.face.Account;
import com.hummingbird.maaccount.vo.QueryUserCardListOilResultVO;
import com.hummingbird.maaccount.vo.QueryUserCardListResultVO;

public interface OilcardAccountMapper  {
    int deleteByPrimaryKey(String accountId);

    OilcardAccount selectByPrimaryKey(String accountId);

    int updateByPrimaryKey(OilcardAccount record);
    
    /**
     * 统计帐户可用余额
     * @param mobile
     * @return
     */
    Long statAccountBalance(String mobile );
    
    
	/**
	 * 获取帐户信息
	 * @param mobile
	 * @return
	 */
	List<OilcardAccount> getAccountByMobile(String mobile );
	/**
	 * 获取帐户信息
	 * @param mobile
	 * @return
	 */
	Account getAccountByAccountId(String accountId );


	/**
	 * 创建帐户
	 * @param ca
	 */
	int createAccount(OilcardAccount ca);

	/**
	 * 根据userid获取帐户
	 * @param userId
	 * @return
	 */
	List<OilcardAccount> getAccountByUserId(Integer userId);

	/**
	 * 更新帐户信息
	 * @param account
	 */
	int updateAccount(OilcardAccount account);

	/**
	 * 查询可用的分期卡，可用即是状态为OK的
	 * @param mobile
	 * @return
	 */
	List<OilcardAccount> selectUseableOilcard(String mobile);
	
	/**
	 * 查询所有的可用的分期卡，可用即是状态为OK的，数量最多是3000条
	 * @return
	 */
	List<OilcardAccount> selectAllUseableOilcard();
	List<OilcardAccount> getAccountByUserId4list(@Param("userId")Integer userId,@Param("terminalId")String terminalId);

	/**
	 * 查询到期可以返还钱的分期卡
	 * @return
	 */
	List<OilcardAccount> selectTimeToReturn();
	/**
	 * 获取帐户列表
	 * @param mobile
	 * @return
	 */
	List<QueryUserCardListOilResultVO> getUserAccounts(@Param("mobileNum")String mobile,@Param("startTime")Date startTime,@Param("endTime")Date endTime,@Param("status")List<String> status,@Param("channelNo")String  channelNo);
    
	
}