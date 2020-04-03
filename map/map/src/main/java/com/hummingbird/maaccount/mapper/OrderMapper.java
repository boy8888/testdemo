package com.hummingbird.maaccount.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hummingbird.maaccount.face.Pagingnation;
import com.hummingbird.maaccount.vo.SpendOrderQueryPagingVO;
import com.hummingbird.maaccount.vo.JournalOrderOutputVO;

/**
 * 订单dao,主要是查询
 * @author john huang
 * 2015年7月3日 上午8:58:04
 * 本类主要做为
 */
public interface OrderMapper  {

    /**
	 * 分页查询消费订单
	 * @param paging
	 * @param filter
	 * @return
	 */
	List querySpendOrderByPage(@Param("page") Pagingnation paging,@Param("filter")SpendOrderQueryPagingVO filter);
	
	/**
	 * 分页查询消费订单总记录数
	 * @param paging
	 * @param filter
	 * @return
	 */
	int  querySpendOrderTotalByPage(@Param("filter") SpendOrderQueryPagingVO filter);

	/**
	 * 查询所有流水的总记录数
	 * @param query
	 * @return
	 */
	int queryJournalOrderTotalByPage(@Param("filter") SpendOrderQueryPagingVO query);

	/**
	 * 查询所有流水的分页记录
	 * @param pagingnation
	 * @param query
	 * @return
	 */
	List<JournalOrderOutputVO> queryJournalOrderByPage(@Param("page") Pagingnation pagingnation,@Param("filter") SpendOrderQueryPagingVO query);

	/**
	 * 查询昨日的所有流水的总记录数
	 * @param query
	 * @return
	 */
	int queryYesterdayJournalOrderTotalByPage(@Param("filter") SpendOrderQueryPagingVO query);

	/**
	 * 查询昨日所有流水的分页记录
	 * @param pagingnation
	 * @param query
	 * @return
	 */
	List<JournalOrderOutputVO> queryYesterdayJournalOrderByPage(@Param("page") Pagingnation pagingnation,@Param("filter") SpendOrderQueryPagingVO query);
    

}