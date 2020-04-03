package com.hummingbird.maaccount.mapper;

import org.apache.ibatis.annotations.Param;

import com.hummingbird.maaccount.entity.InvestmentAccount;
import com.hummingbird.maaccount.face.Account;
import com.hummingbird.maaccount.face.Order;

public interface InvestmentAccountMapper extends DefaultAccountDao{
    int deleteByPrimaryKey(Integer userid);

    int insert(InvestmentAccount record);

    int insertSelective(InvestmentAccount record);

    InvestmentAccount selectByPrimaryKey(Integer userid);

    int updateByPrimaryKeySelective(InvestmentAccount record);

    int updateByPrimaryKey(InvestmentAccount record);
    
    void updateSignature(String accountId);
    
    /**
	 * 剩余金额帐户支出，支付时先尝试acout减总额，如果不够减将不会更新成功
	 * @param account
	 * @param order
	 * @return 1-更新成功，0-更新失败，不够减
	 */
	int expenseWithRemain(@Param("account") Account account,@Param("order") Order order);
    
	/**
	 * 标的帐户支出，支付时先尝试acout减总额，如果不够减将不会更新成功
	 * @param account
	 * @param order
	 * @return 1-更新成功，0-更新失败，不够减
	 */
	int expenseWithObject(@Param("account") Account account,@Param("order") Order order);
	/**
	 * 剩余金额帐户收入
	 * @param account
	 * @param order
	 * @return 1-更新成功，0-更新失败
	 */
	int incomeWithRemain(@Param("account") Account account,@Param("order") Order order);
	/**
	 * 冻结帐户支出，支付时先尝试acout减总额，如果不够减将不会更新成功
	 * @param account
	 * @param order
	 * @return 1-更新成功，0-更新失败，不够减
	 */
	int expenseWithFrozen(@Param("account") Account account,@Param("order") Order order);
	/**
	 * 冻结金额帐户收入
	 * @param account
	 * @param order
	 * @return 1-更新成功，0-更新失败
	 */
	int incomeWithFrozen(@Param("account") Account account,@Param("order") Order order);
	
	/**
	 * 标的帐户收入
	 * @param account
	 * @param order
	 * @return 1-更新成功，0-更新失败
	 */
	int incomeWithObject(@Param("account") Account account,@Param("order") Order order);
	InvestmentAccount selectByUserId(@Param("userId")Integer userId,@Param("sum")Long sum);
}