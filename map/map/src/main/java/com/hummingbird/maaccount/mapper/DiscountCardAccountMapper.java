package com.hummingbird.maaccount.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hummingbird.maaccount.entity.DiscountCardAccount;
import com.hummingbird.maaccount.vo.CardSelecter;
import com.hummingbird.maaccount.vo.QueryUserCardListResultVO;

public interface DiscountCardAccountMapper extends DefaultAccountDao{
    int deleteByPrimaryKey(String accountId);

    int createAccount(DiscountCardAccount record);

    int insertSelective(DiscountCardAccount record);

    DiscountCardAccount selectByPrimaryKey(String accountId);

    int updateByPrimaryKeySelective(DiscountCardAccount record);

    int updateByPrimaryKey(DiscountCardAccount record);
    
    DiscountCardAccount getAccountByAccountId(String accountId);
    
    /**
	 * 获取帐户信息
	 * @param mobile
	 * @return
	 */
	List<DiscountCardAccount> getAccountsByMobile(String mobile );
    
    List<DiscountCardAccount> getAccountByUserId4list(@Param("userId")Integer userId,@Param("terminalId")String terminalId,@Param("sum")Long sum);

    /**
     * 统计帐户可用余额
     * @param mobile
     * @return
     */
	long statAccountBalance(String consumerId);
	/**
	 * 获取帐户列表
	 * @param mobile
	 * @return
	 */
	List<QueryUserCardListResultVO> getUserAccounts(@Param("mobileNum")String mobile,@Param("startTime")Date startTime,@Param("endTime")Date endTime,@Param("status")List<String> status,@Param("channelNo")String  channelNo);
    
	/**
	 * 获取帐户列表数
	 * @param mobile
	 * @return
	 */
	Integer getUserAccountsTotal(@Param("mobileNum")String mobile,@Param("startTime")Date startTime,@Param("endTime")Date endTime,@Param("status")List<String> status,@Param("channelNo")String channelNo);

	/**
	 * 查询合适的折扣卡以进行支付
	 * @param cs
	 * @return
	 */
	List<DiscountCardAccount> getAccountByCardSelecter(CardSelecter cs);
    
	
}