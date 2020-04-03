package com.hummingbird.maaccount.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hummingbird.maaccount.entity.AccountPayProduct;

public interface AccountPayProductMapper {
    /**
     * 根据主键删除记录
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * 保存记录,不管记录里面的属性是否为空
     */
    int insert(AccountPayProduct record);

    /**
     * 保存属性不为空的记录
     */
    int insertSelective(AccountPayProduct record);

    /**
     * 根据主键查询记录
     */
    AccountPayProduct selectByPrimaryKey(Integer id);

    /**
     * 根据主键更新属性不为空的记录
     */
    int updateByPrimaryKeySelective(AccountPayProduct record);

    /**
     * 根据主键更新记录
     */
    int updateByPrimaryKey(AccountPayProduct record);

	/**
	 * 查询产品
	 * @param accountId
	 * @param groupId
	 * @param zjproducts
	 * @return
	 */
	List<AccountPayProduct> selectProduct(@Param("accountId") String accountId,@Param("groupId") String groupId,@Param("zjproducts") String[] zjproducts);
}