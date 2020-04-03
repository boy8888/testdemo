package com.hummingbird.maaccount.mapper;

import java.util.List;

import com.hummingbird.maaccount.entity.SmsMTDelay;

public interface SmsMTDelayMapper {
    /**
     * 根据主键删除记录
     */
    int deleteByPrimaryKey(Integer idsms);

    /**
     * 保存记录,不管记录里面的属性是否为空
     */
    int insert(SmsMTDelay record);

    /**
     * 保存属性不为空的记录
     */
    int insertSelective(SmsMTDelay record);

    /**
     * 根据主键查询记录
     */
    SmsMTDelay selectByPrimaryKey(Integer idsms);

    /**
     * 根据主键更新属性不为空的记录
     */
    int updateByPrimaryKeySelective(SmsMTDelay record);

    /**
     * 根据主键更新记录
     */
    int updateByPrimaryKey(SmsMTDelay record);

	/**
	 * 查询延时短信
	 * @return
	 */
	List<SmsMTDelay> selectDelaySmses();
}