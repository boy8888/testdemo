package com.hummingbird.maaccount.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hummingbird.maaccount.entity.OilcardAccountOrder;

public interface OilcardAccountOrderMapper  extends AccountOrderDao {

    OilcardAccountOrder selectByPrimaryKey(String orderid);
    
    /**
     * 查询pos机的交易信息
     * @param sellerId 商户
     * @param terminalId 终端
     * @param batchNo 批次
     * @param terminalOrderId 流水
     * @return
     */
    OilcardAccountOrder selectPosOrder(@Param("sellerId") String sellerId,@Param("terminalId")String terminalId,@Param("batchNo")String batchNo,@Param("terminalOrderId")String terminalOrderId);

	/**
	 * 根据第三方的应用查询分期卡帐户情况
	 * @param appId
	 * @param appOrderId
	 * @return
	 */
	OilcardAccountOrder selectByChannelOrderId(@Param("appId") String appId,@Param("channelOrderId")  String channelOrderId);

	/**
	 * 根据AppOrderId来查询
	 * @param appOrderId 应该为前置的订单号
	 * @return
	 */
	List<OilcardAccountOrder> selectByAppOrderId(String appOrderId);

}