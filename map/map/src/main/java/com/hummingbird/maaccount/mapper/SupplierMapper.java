package com.hummingbird.maaccount.mapper;

import java.util.List;

import com.hummingbird.maaccount.entity.Supplier;

public interface SupplierMapper {

    int deleteByPrimaryKey(Long id);

    int insert(Supplier record);

    int insertSelective(Supplier record);

    Supplier selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Supplier record);

    int updateByPrimaryKey(Supplier record);

    int supplierExistFlag(String code);

    /**
     * 根据code查询并且不理会是否已经逻辑删除
     * @param code
     * @return
     */
    Supplier selectByCode(String code);

    /**
     * 查询所有商户
     */
    List<Supplier> getAllSupplierList();

}