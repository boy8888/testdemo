package com.hummingbird.maaccount.mapper;

import com.hummingbird.maaccount.entity.BatchAddUserResult;

public interface BatchAddUserResultMapper {
    /**
     * 根据主键删除记录
     */
    int deleteByPrimaryKey(Integer batchId);

    /**
     * 保存记录,不管记录里面的属性是否为空
     */
    int insert(BatchAddUserResult record);

    /**
     * 保存属性不为空的记录
     */
    int insertSelective(BatchAddUserResult record);

    /**
     * 根据主键查询记录
     */
    BatchAddUserResult selectByPrimaryKey(Integer batchId);

    /**
     * 根据主键更新属性不为空的记录
     */
    int updateByPrimaryKeySelective(BatchAddUserResult record);

    /**
     * 根据主键更新记录
     */
    int updateByPrimaryKey(BatchAddUserResult record);
}