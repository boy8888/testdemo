package com.hummingbird.maaccount.mapper;

import java.util.List;

import com.hummingbird.maaccount.entity.Terminal;

public interface TerminalMapper {

    int deleteByPrimaryKey(Long id);

    int insert(Terminal record);

    int insertSelective(Terminal record);

    Terminal selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Terminal record);

    int updateByPrimaryKey(Terminal record);

    List<String> findTerminalCodeListByShop(String shopCode);

    int terminalExistFlag(String code);

    /**
     * 根据code查询并且不理会是否已经逻辑删除
     * @param code
     * @return
     */
    Terminal selectByCode(String code);

    /**
     * 查询所有终端号
     */
    List<Terminal> getAllTerminalList();
}