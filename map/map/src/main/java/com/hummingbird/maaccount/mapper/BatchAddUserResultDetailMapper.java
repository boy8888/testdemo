package com.hummingbird.maaccount.mapper;

import com.hummingbird.maaccount.entity.BatchAddUserResultDetail;

public interface BatchAddUserResultDetailMapper {
    /**
     * 根据主键删除记录
     */
    int deleteByPrimaryKey(Integer detailId);

    /**
     * 保存记录,不管记录里面的属性是否为空
     */
    int insert(BatchAddUserResultDetail record);

    /**
     * 保存属性不为空的记录
     */
    int insertSelective(BatchAddUserResultDetail record);

    /**
     * 根据主键查询记录
     */
    BatchAddUserResultDetail selectByPrimaryKey(Integer detailId);

    /**
     * 根据主键更新属性不为空的记录
     */
    int updateByPrimaryKeySelective(BatchAddUserResultDetail record);

    /**
     * 根据主键更新记录
     */
    int updateByPrimaryKey(BatchAddUserResultDetail record);
}