package com.hummingbird.maaccount.mapper;

import com.hummingbird.maaccount.entity.ProductTerminalList;

public interface ProductTerminalListMapper {
    /**
     * 根据主键删除记录
     */
    int deleteByPrimaryKey(Integer idt_product_terminal_list);

    /**
     * 保存记录,不管记录里面的属性是否为空
     */
    int insert(ProductTerminalList record);

    /**
     * 保存属性不为空的记录
     */
    int insertSelective(ProductTerminalList record);

    /**
     * 根据主键查询记录
     */
    ProductTerminalList selectByPrimaryKey(Integer idt_product_terminal_list);

    /**
     * 根据主键更新属性不为空的记录
     */
    int updateByPrimaryKeySelective(ProductTerminalList record);

    /**
     * 根据主键更新记录
     */
    int updateByPrimaryKey(ProductTerminalList record);
    
    ProductTerminalList selectByProduct(String ProductId,String terminalId);
    
    /**
     * 查询指定的终端能否开展对应的产品的支付
     * @param ProductId
     * @param terminalId
     * @return
     */
    int selectPayableProduct(String ProductId,String terminalId);
    
}