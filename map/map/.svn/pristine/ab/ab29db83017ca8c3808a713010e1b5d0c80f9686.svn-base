package com.hummingbird.maaccount.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hummingbird.maaccount.entity.BindBankcard;

public interface BindBankcardMapper {
    /**
     * 根据主键删除记录
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * 保存记录,不管记录里面的属性是否为空
     */
    int insert(BindBankcard record);

    /**
     * 保存属性不为空的记录
     */
    int insertSelective(BindBankcard record);

    /**
     * 根据主键查询记录
     */
    BindBankcard selectByPrimaryKey(Integer id);

    /**
     * 根据主键更新属性不为空的记录
     */
    int updateByPrimaryKeySelective(BindBankcard record);

    /**
     * 根据主键更新记录
     */
    int updateByPrimaryKey(BindBankcard record);
    
    /**
     * 按卡号查询绑定
     * @param cardNo
     * @return
     */
    List<BindBankcard> selectByCardNo(String cardNo);
    /**
     * 按用户id查询绑定
     * @param userId
     * @return
     */
    List<BindBankcard> selectByUserId(Integer userId);
    /**
     * 按用户id和银行卡号查询绑定
     * @param userId
     * @return
     */
    BindBankcard selectByUserIdAndCardNo(@Param("userId")Integer userId,@Param("cardNo")String cardNo);
}