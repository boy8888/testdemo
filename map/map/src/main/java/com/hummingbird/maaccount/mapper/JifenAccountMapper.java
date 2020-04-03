package com.hummingbird.maaccount.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hummingbird.maaccount.entity.JifenAccount;
import com.hummingbird.maaccount.exception.MaAccountException;
import com.hummingbird.maaccount.face.Pagingnation;

public interface JifenAccountMapper {
    /**
     * 根据主键删除记录
     */
    int deleteByPrimaryKey(String accountId);

    /**
     * 保存记录,不管记录里面的属性是否为空
     */
    int insert(JifenAccount record);

    /**
     * 保存属性不为空的记录
     */
    int insertSelective(JifenAccount record);

    /**
     * 根据主键查询记录
     */
    JifenAccount selectByPrimaryKey(String accountId);

    /**
     * 根据主键更新属性不为空的记录
     */
    int updateByPrimaryKeySelective(JifenAccount record);

    /**
     * 根据主键更新记录
     */
    int updateByPrimaryKey(JifenAccount record);
    JifenAccount selectByProductId(@Param("userId")Integer userId,@Param("productId")String productId);
    
    public List<JifenAccount> queryJifenAccount(@Param("mobileNum")String mobileNum,@Param("list")List<String> redPaperProductId,@Param("page")Pagingnation page);
    public Integer queryJifenAccountByTotal(@Param("mobileNum")String mobileNum, @Param("list")List<String> jifenProductId);
    /**
	 * 根据产品Id查询
	 * @param appOrderId
	 * @return 
	 * @throws MaAccountException 
	 */
	public JifenAccount selectAccountByProductId(@Param("mobileNum")String mobileNum,@Param("productId")String productId,@Param("appId")String appId);
}