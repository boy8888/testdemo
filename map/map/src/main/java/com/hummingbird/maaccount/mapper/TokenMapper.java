package com.hummingbird.maaccount.mapper;

import com.hummingbird.commonbiz.vo.UserToken;
import com.hummingbird.maaccount.entity.Token;

public interface TokenMapper {
    int deleteByPrimaryKey(String token);

    int insert(Token record);

    int insertSelective(Token record);

    Token selectByPrimaryKey(String token);

    int updateByPrimaryKeySelective(Token record);

    int updateByPrimaryKey(Token record);
    
    
    /**
     * 根据条件获取token
     * @param record
     * @return
     */
    Token selectByToken(UserToken record);
    
    /**
     * 根据条件获取token
     * @param record
     * @return
     */
    Token selectByTokenStr(String token);

    
}