package com.hummingbird.maaccount.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hummingbird.maaccount.entity.AccountPayTime;

public interface AccountPayTimeMapper {
    /**
     * 根据主键删除记录
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * 保存记录,不管记录里面的属性是否为空
     */
    int insert(AccountPayTime record);

    /**
     * 保存属性不为空的记录
     */
    int insertSelective(AccountPayTime record);

    /**
     * 根据主键查询记录
     */
    AccountPayTime selectByPrimaryKey(Integer id);

    /**
     * 根据主键更新属性不为空的记录
     */
    int updateByPrimaryKeySelective(AccountPayTime record);

    /**
     * 根据主键更新记录
     */
    int updateByPrimaryKey(AccountPayTime record);
    
    /**
	 * 查询所有的支付时间
	 * @param accountId
	 * @param groupId
	 * @return
	 */
	List<AccountPayTime> selectAllTime(@Param("accountId") String accountId,@Param("groupId") String groupId);
	
}