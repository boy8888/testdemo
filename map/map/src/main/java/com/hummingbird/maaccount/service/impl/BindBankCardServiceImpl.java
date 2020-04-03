package com.hummingbird.maaccount.service.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hummingbird.maaccount.entity.BindBankcard;
import com.hummingbird.maaccount.exception.MaAccountException;
import com.hummingbird.maaccount.mapper.BindBankcardMapper;
import com.hummingbird.maaccount.service.BindBankCardService;
@Service
public class BindBankCardServiceImpl implements BindBankCardService{
	org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory
			.getLog(this.getClass());
	@Autowired
	BindBankcardMapper bankcardDao;
	@Override
	public void creatBankCard(BindBankcard bindBankCard) throws MaAccountException{
		
		BindBankcard bindCard=bankcardDao.selectByUserIdAndCardNo(bindBankCard.getUserId(),bindBankCard.getCardNo());
		if(bindCard!=null){
			if (log.isDebugEnabled()) {
				log.debug(String.format("绑定银行卡失败，银行卡[%s]已被绑定",bindBankCard.getCardNo()));
			}
			throw new MaAccountException(21210,"银行卡已被绑定");
		}
//		List<BindBankcard> selectByCardNo = bankcardDao.selectByCardNo(bindBankCard.getCardNo());
//		if(CollectionUtils.isNotEmpty(selectByCardNo)){
//			throw new MaAccountException(MaAccountException.ERR_BANKCARD_EXCEPTION,"银行卡已被其他用户绑定");
//		}
		
		if(StringUtils.isNotBlank(bindBankCard.getBankName())&&StringUtils.isNotBlank(bindBankCard.getCardNo())){
			bankcardDao.insert(bindBankCard);
		}else{
			if (log.isDebugEnabled()) {
				log.debug(String.format("绑定银行卡失败,银行信息不全"));
			}
			throw new MaAccountException(MaAccountException.ERR_BANKCARD_EXCEPTION,"绑定银行卡失败,银行信息不全");
		}
	}
	
	/**
	 * 按用户id和银行卡号查询绑定信息
	 * @param userId 
	 * @param cardNo 
	 * @return
	 */
	public BindBankcard getCard(int userId, String cardNo) {
		 return bankcardDao.selectByUserIdAndCardNo(userId,cardNo);
	}
	
	@Override
	public void delete(BindBankcard bindBankcard) {
		bankcardDao.deleteByPrimaryKey(bindBankcard.getId());
		
	}

}
