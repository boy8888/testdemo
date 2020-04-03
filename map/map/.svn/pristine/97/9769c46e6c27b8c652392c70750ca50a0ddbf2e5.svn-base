package com.hummingbird.maaccount.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.hummingbird.maaccount.entity.OfflineRecharge;
import com.hummingbird.maaccount.face.Order;
import com.hummingbird.maaccount.face.Pagingnation;
import com.hummingbird.maaccount.vo.RechargeOutputVO;

/**
 * @author huangjiej_2
 * 2015年1月28日 下午5:14:49
 * 本类主要做为线下充值表
 */
public interface OfflineRechargeMapper {

	OfflineRecharge selectByPrimaryKey(String orderid);

    /**
	 * 添加线下充值记录
	 * @param order
	 * @return
	 */
	int createOrder(OfflineRecharge order);
	
	/**
	 * 分页查询订单记录
	 * @param paging
	 * @param filter
	 * @return
	 */
	List queryOrderByPage(@Param("page") Pagingnation paging,@Param("filter")Map filter);
	
	/**
	 * 分页查询订单总记录数
	 * @param paging
	 * @param filter
	 * @return
	 */
	int  queryOrderTotalByPage(@Param("page") Pagingnation paging,@Param("filter") Map filter);
	
	/**
	 * 更新线下充值记录
	 * @param order
	 * @return
	 */
	int updateOrder(OfflineRecharge order);

	/**
	 * 查询线上，线下订单记录
	 * @param paging
	 * @param filter
	 * @return
	 */
	List<RechargeOutputVO> queryRechargeOrderByPage(@Param("page") Pagingnation paging,@Param("filter") Map filter);

	/**
	 * 查询线上，线下订单总记录数
	 * @param paging
	 * @param filter
	 * @return
	 */
	int queryRechargeOrderTotalByPage(@Param("page") Pagingnation paging,@Param("filter") Map filter);
	

}