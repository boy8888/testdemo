package com.hummingbird.maaccount.mapper;

import java.util.List;

import com.hummingbird.maaccount.entity.FactoryTask;

public interface FactoryTaskMapper {
    int deleteByPrimaryKey(Integer idt_factory_task);

    int insert(FactoryTask record);

    int insertSelective(FactoryTask record);

    FactoryTask selectByPrimaryKey(Integer idt_factory_task);

    int updateByPrimaryKeySelective(FactoryTask record);

    int updateByPrimaryKey(FactoryTask record);

	/**
	 * 查询待运行的任务
	 * @return
	 */
	List<FactoryTask> selectTask4Gen();
	
	/**
	 * 根据产品ID查询待运行的任务数量
	 * @param productId
	 * @return
	 */
	int selectCRTTaskByProductId(String productId);
}