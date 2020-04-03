package com.hummingbird.maaccount.mapper;

import java.util.List;

import com.hummingbird.maaccount.entity.TerminalList;
import com.hummingbird.maaccount.vo.BasePosVO;
import com.hummingbird.maaccount.vo.OfflinePayOrderConsumerVO;

public interface TerminalListMapper {
    /**
     * 根据主键删除记录
     */
    int deleteByPrimaryKey(String terminalId);

    /**
     * 保存记录,不管记录里面的属性是否为空
     */
    int insert(TerminalList record);

    /**
     * 保存属性不为空的记录
     */
    int insertSelective(TerminalList record);

    /**
     * 根据主键查询记录
     */
    TerminalList selectByPrimaryKey(String terminalId);

    /**
     * 根据主键更新属性不为空的记录
     */
    int updateByPrimaryKeySelective(TerminalList record);

    /**
     * 根据主键更新记录
     */
    int updateByPrimaryKey(TerminalList record);

    
    TerminalList selectByPosVO(BasePosVO posvo);
    
    TerminalList selectByTransOrderVO(OfflinePayOrderConsumerVO posvo);

	/**
	 * 按门店id查询终端
	 * @param storeId
	 * @return
	 */
	List<TerminalList> selectByStoreId(String storeId);


}