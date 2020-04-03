package com.hummingbird.maaccount.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.hummingbird.maaccount.entity.InvestmentAccountRemainingOrder;
import com.hummingbird.maaccount.face.Pagingnation;

public interface InvestmentAccountRemainingOrderMapper extends AccountOrderDao {

    InvestmentAccountRemainingOrder selectByPrimaryKey(String orderid);
    /**
     * 查询可疑的冻结yyd的订单
     * @return
     */
    List<InvestmentAccountRemainingOrder> selectErrorFrozenYYD(@Param("begintime") String begintime,@Param("endtime")String endtime);
    /**
     * 查询可疑的冻结yyd的订单的前一条订单
     * @return
     */
    InvestmentAccountRemainingOrder selectErrorFrozenYYDsEarlier(InvestmentAccountRemainingOrder aorder);
    
    /**
	 * 分页提现相关查询订单记录
	 * @param paging
	 * @param filter
	 * @return
	 */
	List queryFreezeOrderByPage(@Param("page") Pagingnation paging,@Param("filter")Map filter);
	
	/**
	 * 分页提现相关查询订单总记录数
	 * @param paging
	 * @param filter
	 * @return
	 */
	int  queryFreezeOrderTotalByPage(@Param("page") Pagingnation paging,@Param("filter") Map filter);
    
	/**
	 * 查询在线充值总额
	 */
	long onlineRechargeAmount(@Param("filter") Map filter);
	/**
	 * 查询线下充值总额
	 */
	long offlineRechargeAmount(@Param("filter") Map filter);
	/**
	 * 查询余额订单交易总额
	 */
	long statBillingSum(@Param("filter") Map filter);
}