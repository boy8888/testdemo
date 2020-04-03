package com.hummingbird.maaccount.mapper;

import org.apache.ibatis.annotations.Param;

import com.hummingbird.maaccount.entity.CashAccount;

public interface CashAccountMapper  extends DefaultAccountDao  {
    int deleteByPrimaryKey(Integer userid);

    int insert(CashAccount record);

    int insertSelective(CashAccount record);

    CashAccount selectByPrimaryKey(Integer userid);

    int updateByPrimaryKeySelective(CashAccount record);

    int updateByPrimaryKey(CashAccount record);
    
    void updateSignature(String accountId);
    CashAccount selectByUserId(@Param("userId")Integer userId,@Param("sum")Long sum);
}