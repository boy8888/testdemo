package com.hummingbird.maaccount.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hummingbird.maaccount.entity.VolumecardAccountOrder;
import com.hummingbird.maaccount.face.Order;

public interface VolumecardAccountOrderMapper  extends AccountOrderDao{
    /**
     * 根据主键删除记录
     */
    int deleteByPrimaryKey(String orderId);

    /**
     * 保存记录,不管记录里面的属性是否为空
     */
    int insert(VolumecardAccountOrder record);

    /**
     * 保存属性不为空的记录
     */
    int insertSelective(VolumecardAccountOrder record);

    /**
     * 根据主键查询记录
     */
    VolumecardAccountOrder selectByPrimaryKey(String orderId);

    /**
     * 根据主键更新属性不为空的记录
     */
    int updateByPrimaryKeySelective(VolumecardAccountOrder record);

    /**
     * 根据主键更新记录
     */
    int updateByPrimaryKey(VolumecardAccountOrder record);
	/**
	 * 根据第三方的应用查询分期卡帐户情况
	 * @param appId
	 * @param appOrderId
	 * @return
	 */
	VolumecardAccountOrder selectByChannelOrderId(@Param("appId") String appId,@Param("channelOrderId")  String channelOrderId);
	/**
     * 查询pos机的交易信息
     * @param sellerId 商户
     * @param terminalId 终端
     * @param batchNo 批次
     * @param terminalOrderId 流水
     * @return
     */
    VolumecardAccountOrder selectPosOrder(@Param("sellerId") String sellerId,@Param("terminalId")String terminalId,@Param("batchNo")String batchNo,@Param("terminalOrderId")String terminalOrderId);
    
	/**
	 * 查询以orderid为oriorderid的id
	 * @param oriorderid
	 * @return
	 */
	List<Order> selectByOriginalOrderId(String oriorderid);

}