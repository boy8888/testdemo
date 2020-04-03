package com.hummingbird.maaccount.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hummingbird.common.face.Pagingnation;
import com.hummingbird.maaccount.entity.AccountPayAllow;
import com.hummingbird.maaccount.vo.CardSelecter;

public interface AccountPayAllowMapper {
    /**
     * 根据主键删除记录
     */
    int deleteByPrimaryKey(Integer id);
    
    /**
     * 根据accountId +　groupId　删除记录
     */
    int deleteByAccountIdAndGroupId(String accountId, String groupId);
    /**
     * 根据accountId查询记录
     */
    int selectTotalByAccountId(String accountId);
    /**
     * 根据accountId查询记录
     */
    List<AccountPayAllow> selectByAccountId(@Param("accountId") String accountId,@Param("page") Pagingnation page);

    /**
     * 保存记录,不管记录里面的属性是否为空
     */
    int insert(AccountPayAllow record);

    /**
     * 保存属性不为空的记录
     */
    int insertSelective(AccountPayAllow record);

    /**
     * 根据主键查询记录
     */
    AccountPayAllow selectByPrimaryKey(Integer id);
   

    /**
     * 根据主键更新属性不为空的记录
     */
    int updateByPrimaryKeySelective(AccountPayAllow record);

    /**
     * 根据主键更新记录
     */
    int updateByPrimaryKey(AccountPayAllow record);

    /**
	 * 查询可以支付的账户
	 * @param cs
	 * @return 账户列表
	 */
	List<String> selectCanPayAccount(CardSelecter cs);
}