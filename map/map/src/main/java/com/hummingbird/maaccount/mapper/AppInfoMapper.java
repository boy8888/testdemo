package com.hummingbird.maaccount.mapper;

import com.hummingbird.maaccount.entity.AppInfo;

public interface AppInfoMapper {
    int deleteByPrimaryKey(String appid);

    int insert(AppInfo record);

    int insertSelective(AppInfo record);

    AppInfo selectByPrimaryKey(String appid);

    int updateByPrimaryKeySelective(AppInfo record);

    int updateByPrimaryKey(AppInfo record);
}