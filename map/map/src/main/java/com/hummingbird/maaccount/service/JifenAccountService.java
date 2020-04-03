package com.hummingbird.maaccount.service;

import java.util.List;

import com.hummingbird.maaccount.entity.JifenAccount;
import com.hummingbird.maaccount.entity.RedPaperAccount;
import com.hummingbird.maaccount.exception.MaAccountException;
import com.hummingbird.maaccount.face.Pagingnation;

public interface JifenAccountService {
	/**
	 * 根据第三方应用id查询开卡情况
	 * @param appId
	 * @param appOrderId
	 * @return 
	 * @throws MaAccountException 
	 */
	public List<JifenAccount> queryJifenAccount(String mobileNum,String jifenProductId,Pagingnation page) throws MaAccountException;
	public JifenAccount selectAccountByProductId(String mobileNum,String ProductId,String appId) throws MaAccountException;
	/**
     * 根据主键更新记录
     */
	boolean updateAccount(JifenAccount record);
}
