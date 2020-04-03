package com.hummingbird.maaccount.mapper;

import org.apache.ibatis.annotations.Param;

import com.hummingbird.maaccount.entity.AppSmsActionSetting;

public interface AppSmsActionSettingMapper {
    /**
     * 根据主键删除记录
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * 保存记录,不管记录里面的属性是否为空
     */
    int insert(AppSmsActionSetting record);

    /**
     * 保存属性不为空的记录
     */
    int insertSelective(AppSmsActionSetting record);

    /**
     * 根据主键查询记录
     */
    AppSmsActionSetting selectByPrimaryKey(Integer id);

    /**
     * 根据主键更新属性不为空的记录
     */
    int updateByPrimaryKeySelective(AppSmsActionSetting record);

    /**
     * 根据主键更新记录
     */
    int updateByPrimaryKey(AppSmsActionSetting record);

	/**
	 * @param appId
	 * @param actionName
	 * @return
	 */
	AppSmsActionSetting selectByAppAction(@Param("appId") String appId,@Param("actionName") String actionName);
}