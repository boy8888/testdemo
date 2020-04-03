package com.hummingbird.maaccount.mapper;

import com.hummingbird.maaccount.entity.AppSmsSetting;

public interface AppSmsSettingMapper {
    /**
     * 根据主键删除记录
     */
    int deleteByPrimaryKey(String appId);

    /**
     * 保存记录,不管记录里面的属性是否为空
     */
    int insert(AppSmsSetting record);

    /**
     * 保存属性不为空的记录
     */
    int insertSelective(AppSmsSetting record);

    /**
     * 根据主键查询记录
     */
    AppSmsSetting selectByPrimaryKey(String appId);

    /**
     * 根据主键更新属性不为空的记录
     */
    int updateByPrimaryKeySelective(AppSmsSetting record);

    /**
     * 根据主键更新记录
     */
    int updateByPrimaryKey(AppSmsSetting record);
}