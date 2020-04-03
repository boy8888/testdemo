package com.hummingbird.maaccount.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hummingbird.maaccount.entity.RedPaperAccount;
import com.hummingbird.maaccount.exception.MaAccountException;
import com.hummingbird.maaccount.face.Account;
import com.hummingbird.maaccount.face.Pagingnation;

public interface RedPaperAccountMapper extends DefaultAccountDao{
    /**
     * 根据主键删除记录
     */
    int deleteByPrimaryKey(String accountId);

    /**
     * 保存记录,不管记录里面的属性是否为空
     */
    int insert(RedPaperAccount record);

    /**
     * 保存属性不为空的记录
     */
    int insertSelective(RedPaperAccount record);

    /**
     * 根据主键查询记录
     */
    RedPaperAccount selectByPrimaryKey(String accountId);

    /**
     * 根据主键更新属性不为空的记录
     */
    int updateByPrimaryKeySelective(RedPaperAccount record);

    /**
     * 根据主键更新记录
     */
    int updateByPrimaryKey(RedPaperAccount record);
    
    /**
	 * 创建帐户
	 * @param ca
	 */
	int createAccount(RedPaperAccount redPaperAccount);
	
	/**
	 * 获取帐户信息
	 * @param mobile
	 * @return
	 */
	RedPaperAccount getAccountByAccountId(String accountId );
	/**
	 * 查询红包数据
	 * @param appId
	 * @param appOrderId
	 * @return 
	 * @throws MaAccountException 
	 */
	public List<RedPaperAccount> queryRedPaperAccount(@Param("mobileNum")String mobileNum,@Param("list")List<String> redPaperProductId,@Param("expireIn")String expireIn,@Param("status")String status,@Param("page")Pagingnation page,@Param("startTime")Date startTime,@Param("endTime")Date endTime,@Param("accountIds")List<String> accountIds,@Param("appId")String appId);
	
	/**
	 * 查询红包数据记录数
	 * @param appId
	 * @param appOrderId
	 * @return 
	 * @throws MaAccountException 
	 */
	public Integer queryRedPaperAccountByTotal(@Param("mobileNum")String mobileNum,@Param("list")List<String> redPaperProductId,@Param("expireIn")String expireIn,@Param("status")String status,@Param("startTime")Date startTime,@Param("endTime")Date endTime,@Param("accountIds")List<String> accountId,@Param("appId")String appId);
	
	
	/**
	 * 根据红包Id查询红包
	 * @param appOrderId
	 * @return 
	 * @throws MaAccountException 
	 */
	public List<RedPaperAccount> selectAccountByAccountId(@Param("mobileNum")String mobileNum,@Param("list")List<String> redPaperIds,@Param("appId")String appId);
}