package com.hummingbird.maaccount.service;

import java.util.List;

import com.hummingbird.common.exception.DataInvalidException;
import com.hummingbird.maaccount.exception.MaAccountException;
import com.hummingbird.maaccount.face.Pagingnation;
import com.hummingbird.maaccount.vo.QueryUserCardBodyVO;
import com.hummingbird.maaccount.vo.QueryUserCardListDetailVO;
import com.hummingbird.maaccount.vo.QueryUserCardListResultVO;

/**
 * @author john huang
 * 2016年1月9日 下午10:51:24
 * 本类主要做为 用户卡的service
 */
public interface QueryUserCardService {
	
	/**
	 * 查询我的卡列表
	 * @param query
	 * @param pagingnation
	 * @return
	 * @throws MaAccountException
	 */
	public List<QueryUserCardListResultVO> queryUserCard(QueryUserCardListDetailVO query,Pagingnation pagingnation)throws MaAccountException;

	/**
	 * 查询我的卡详情
	 * @param query
	 * @return
	 * @throws DataInvalidException 
	 */
	public QueryUserCardListResultVO queryUserCardDetail(QueryUserCardBodyVO query) throws DataInvalidException;
}
