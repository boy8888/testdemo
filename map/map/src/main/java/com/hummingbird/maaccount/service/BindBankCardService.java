package com.hummingbird.maaccount.service;

import com.hummingbird.maaccount.entity.BindBankcard;
import com.hummingbird.maaccount.exception.MaAccountException;

public interface BindBankCardService {
	
	public void creatBankCard(BindBankcard bindBankCard)throws MaAccountException;
	/**
	 * 按用户id和银行卡号查询绑定信息
	 * @param userId 
	 * @param cardNo 
	 * @return
	 */
	public BindBankcard getCard(int userId, String cardNo);
	
	/**
	 * 删除银行卡
	 * @param bindBankcard
	 */
	public void delete(BindBankcard bindBankcard );
}
