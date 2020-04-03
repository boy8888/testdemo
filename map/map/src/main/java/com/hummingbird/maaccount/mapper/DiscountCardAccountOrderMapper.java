package com.hummingbird.maaccount.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hummingbird.maaccount.entity.CashAccountOrder;
import com.hummingbird.maaccount.entity.DiscountCardAccountOrder;
import com.hummingbird.maaccount.entity.OilcardAccountOrder;
import com.hummingbird.maaccount.face.Order;

public interface DiscountCardAccountOrderMapper {
    int deleteByPrimaryKey(String orderId);

    int createOrder(DiscountCardAccountOrder record);

    int insertSelective(DiscountCardAccountOrder record);

    DiscountCardAccountOrder selectByPrimaryKey(String orderId);

    int updateByPrimaryKeySelective(DiscountCardAccountOrder record);

    int updateByPrimaryKey(DiscountCardAccountOrder record);

    /**
	 * 根据第三方的应用查询分期卡帐户情况
	 * @param appId
	 * @param appOrderId
	 * @return
	 */
    DiscountCardAccountOrder selectByChannelOrderId(@Param("appId") String appId,@Param("channelOrderId")  String channelOrderId);

    
	/**
	 * 根据第三方的应用查询折扣卡帐户情况
	 * @param appId
	 * @param appOrderId
	 * @return
	 */
    DiscountCardAccountOrder selectByAppOrderId(@Param("appId") String appId,@Param("appOrderId")  String appOrderId);
    
    
    /**
     * 查询pos机的交易信息
     * @param sellerId 商户
     * @param terminalId 终端
     * @param batchNo 批次
     * @param terminalOrderId 流水
     * @return
     */
    DiscountCardAccountOrder selectPosOrder(@Param("sellerId") String sellerId,@Param("terminalId")String terminalId,@Param("batchNo")String batchNo,@Param("terminalOrderId")String terminalOrderId);
    
	/**
	 * 查询以orderid为oriorderid的id
	 * @param oriorderid
	 * @return
	 */
	List<Order> selectByOriginalOrderId(String oriorderid);
}