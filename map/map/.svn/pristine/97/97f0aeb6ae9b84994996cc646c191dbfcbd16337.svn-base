package com.hummingbird.maaccount.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hummingbird.maaccount.entity.AccountPayTerminal;
import com.hummingbird.maaccount.entity.AccountPayTime;

public interface AccountPayTerminalMapper {
    /**
     * 根据主键删除记录
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * 保存记录,不管记录里面的属性是否为空
     */
    int insert(AccountPayTerminal record);

    /**
     * 保存属性不为空的记录
     */
    int insertSelective(AccountPayTerminal record);

    /**
     * 根据主键查询记录
     */
    AccountPayTerminal selectByPrimaryKey(Integer id);

    /**
     * 根据主键更新属性不为空的记录
     */
    int updateByPrimaryKeySelective(AccountPayTerminal record);

    /**
     * 根据主键更新记录
     */
    int updateByPrimaryKey(AccountPayTerminal record);

	/**
	 * 查询终端
	 * @param accountId
	 * @param groupId
	 * @param terminalIds
	 * @return
	 */
	List<AccountPayTerminal> selectTerminalIds(@Param("accountId") String accountId,@Param("groupId") String groupId,@Param("terminalIds") String[] terminalIds);
                             
	
}