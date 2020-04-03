package com.hummingbird.maaccount.mapper;

import org.apache.ibatis.annotations.Param;

import com.hummingbird.maaccount.entity.AppMethod;

public interface AppMethodMapper {
    int deleteByPrimaryKey(Integer idtAppMethod);

    int insert(AppMethod record);

    int insertSelective(AppMethod record);

    AppMethod selectByPrimaryKey(Integer idtAppMethod);

    int updateByPrimaryKeySelective(AppMethod record);

    int updateByPrimaryKey(AppMethod record);
    
    AppMethod selectAppMethod(@Param("appId") String appId,@Param("method")  String method);
}