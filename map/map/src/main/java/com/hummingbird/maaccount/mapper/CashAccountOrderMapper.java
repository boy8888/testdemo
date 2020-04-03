package com.hummingbird.maaccount.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.hummingbird.maaccount.entity.CashAccountOrder;
import com.hummingbird.maaccount.entity.OilcardAccountOrder;
import com.hummingbird.maaccount.face.Order;
import com.hummingbird.maaccount.face.Pagingnation;
import com.hummingbird.maaccount.vo.CashAccountOrderVO;

public interface CashAccountOrderMapper extends AccountOrderDao{

    CashAccountOrder selectByPrimaryKey(String orderid);
    CashAccountOrderVO selectByExternalOrderId(String externalOrderId);

    /**
     * 查询pos机的交易信息
     * @param sellerId 商户
     * @param terminalId 终端
     * @param batchNo 批次
     * @param terminalOrderId 流水
     * @return
     */
    CashAccountOrder selectPosOrder(@Param("sellerId") String sellerId,@Param("terminalId")String terminalId,@Param("batchNo")String batchNo,@Param("terminalOrderId")String terminalOrderId);
    /**
	 * 统计充值次数
	 * @param accountid
	 */
	public int countCashAccountOrder(String mobileNum);
	/**
	 * 查询限定时间内充值交易笔数
	 * @param filter
	 * @return
	 */
	int  queryCheckTokenTotal(@Param("filter") Map filter);
	/**
	 * 查询限定时间内充值交易金额
	 * @param filter
	 * @return
	 */
	Long  queryCheckTokenSum(@Param("filter") Map filter);
	/**
	 * 查询限定时间内微信端充值交易金额
	 * @param filter
	 * @return
	 */
	Long  queryRechargeTotal(@Param("filter") Map filter,@Param("appId")String appId);
	
	/**
	 * 查询限定时间内撤销交易笔数
	 * @param filter
	 * @return
	 */
	int  queryCancelCount(@Param("filter") Map filter);
	/**
	 * 查询限定时间内撤销交易金额
	 * @param filter
	 * @return
	 */
	Long  queryCancelSum(@Param("filter") Map filter);

	/**
	 * 分页查询订单记录
	 * @param paging
	 * @param filter
	 * @return
	 */
	List queryCashAccountOrder(@Param("page") Pagingnation paging,@Param("filter")Map filter);
	/**
	 * 分页查询订单总记录数
	 * @param paging
	 * @param filter
	 * @return
	 */
	int  queryCashAccountTotal(@Param("page") Pagingnation paging,@Param("filter") Map filter);
    
}