package com.hummingbird.maaccount.service;

import java.util.ArrayList;
import java.util.List;

import com.hummingbird.maaccount.entity.RedPaperAccount;
import com.hummingbird.maaccount.exception.MaAccountException;
import com.hummingbird.maaccount.face.Account;
import com.hummingbird.maaccount.face.Pagingnation;

public interface RedPaperAccountService {
	/**
	 * 查询红包帐户情况
	 * @param appId
	 * @param appOrderId
	 * @return 
	 * @throws MaAccountException 
	 */
	public List<RedPaperAccount> queryRedPaperAccount(String mobileNum,String redPaperProductId,String expireIn,Pagingnation page,String status,String startTime,String endTime,String accountId,String appId) throws MaAccountException;
	/**
	 * 根据红包Id查询红包
	 * @param appOrderId
	 * @return 
	 * @throws MaAccountException 
	 */
	public List<RedPaperAccount> selectAccountByAccountId(String mobileNum,List<String> redPaperIds,String appId) throws MaAccountException;
	/**
     * 根据主键更新记录
     */
	boolean updateAccount(RedPaperAccount record);
}
