/**
 * 
 * AccountOrderDao.java
 * 版本所有 深圳市蜂鸟娱乐有限公司 2013-2014
 */
package com.hummingbird.maaccount.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.hummingbird.maaccount.face.Order;
import com.hummingbird.maaccount.face.Pagingnation;

/**
 * @author huangjiej_2
 * 2014年12月27日 下午9:56:56
 * 本类主要做为 帐户订单dao
 */
public interface AccountOrderDao {

	/**
	 * 创建订单
	 * @param order
	 * @return
	 */
	int createOrder(Order order);
	
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
	 * 更新订单
	 * @param order
	 * @return
	 */
	int updateOrder(Order order);
	
	/**
	 * 查询以orderid为oriorderid的id
	 * @param oriorderid
	 * @return
	 */
	List<Order> selectByOriginalOrderId(String oriorderid);
	
	
	
}
