package com.hummingbird.maaccount.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hummingbird.maaccount.entity.FactoryAccountId;
import com.hummingbird.maaccount.entity.FactoryProcess;
import com.hummingbird.maaccount.entity.Product;

public interface FactoryAccountIdMapper {
    int deleteByPrimaryKey(String accountId);

    int insert(FactoryAccountId record);

    int insertSelective(FactoryAccountId record);

    FactoryAccountId selectByPrimaryKey(String accountId);

    int updateByPrimaryKeySelective(FactoryAccountId record);

    int updateByPrimaryKey(FactoryAccountId record);

	/**
	 * 创建帐户
	 * @param process
	 * @param product
	 * @param insertlist
	 */
	void createAccounts(@Param("process") FactoryProcess process,@Param("product") Product product,
			@Param("insertlist") List<String> insertlist);

	/**
	 * 获取可用的帐户
	 * @param productId
	 * @return
	 */
	List<FactoryAccountId> selectUseableAccount(String productId);
	
	/**
	 * 获取可用的帐户
	 * @param accountType
	 * @return
	 */
	String selectUseableAccountIdWithProc(java.util.Map map);
	/**
	 * 获取可用的帐户
	 * @param accountType
	 * @return
	 */
	String selectUseableAccountId(String productId);
	/**
	 * 获取可用的帐户
	 * @param accountType
	 * @return
	 */
	String selectUseableAccountIdByAccountType(String accountType);

	/**
	 * 尝试使用帐户
	 * @param factoryAccountId
	 * @return
	 */
	int useThisAccount(FactoryAccountId factoryAccountId);
	
	long selectCountByProductIdAndStatus(@Param("productId")String productId,@Param("status")String status);

}
