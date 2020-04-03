package com.hummingbird.maaccount.mapper;

import org.apache.ibatis.annotations.Param;

import com.hummingbird.maaccount.entity.FactoryProcess;

public interface FactoryProcessMapper {
    int deleteByPrimaryKey(Integer idt_factory_process);

    int insert(FactoryProcess record);

    int insertSelective(FactoryProcess record);

    FactoryProcess selectByPrimaryKey(Integer idt_factory_process);

    int updateByPrimaryKeySelective(FactoryProcess record);

    int updateByPrimaryKey(FactoryProcess record);

	/**
	 * 查询创建进程
	 * @param productId
	 * @param unitId
	 * @return
	 */
	FactoryProcess selectProcess(@Param("productId") String productId,@Param("unitId") String unitId);
}