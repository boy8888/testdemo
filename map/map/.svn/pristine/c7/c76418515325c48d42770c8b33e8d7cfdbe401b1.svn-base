package com.hummingbird.maaccount.mapper;

import com.hummingbird.maaccount.entity.RedPaperProduct;

public interface RedPaperProductMapper {
    /**
     * 根据主键删除记录
     */
    int deleteByPrimaryKey(String productId);

    /**
     * 保存记录,不管记录里面的属性是否为空
     */
    int insert(RedPaperProduct record);

    /**
     * 保存属性不为空的记录
     */
    int insertSelective(RedPaperProduct record);

    /**
     * 根据主键查询记录
     */
    RedPaperProduct selectByPrimaryKey(String productId);

    /**
     * 根据主键更新属性不为空的记录
     */
    int updateByPrimaryKeySelective(RedPaperProduct record);

    /**
     * 根据主键更新记录
     */
    int updateByPrimaryKey(RedPaperProduct record);
}