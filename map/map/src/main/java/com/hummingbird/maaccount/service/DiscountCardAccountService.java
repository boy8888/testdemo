package com.hummingbird.maaccount.service;

import java.util.Date;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hummingbird.common.exception.DataInvalidException;
import com.hummingbird.common.util.SpringBeanUtil;
import com.hummingbird.common.vo.ResultModel;
import com.hummingbird.commonbiz.util.NoGenerationUtil;
import com.hummingbird.maaccount.constant.OrderConst;
import com.hummingbird.maaccount.entity.AppInfo;
import com.hummingbird.maaccount.entity.CashAccount;
import com.hummingbird.maaccount.entity.CashAccountOrder;
import com.hummingbird.maaccount.entity.DiscountCardAccount;
import com.hummingbird.maaccount.entity.DiscountCardAccountOrder;
import com.hummingbird.maaccount.entity.DiscountCardProduct;
import com.hummingbird.maaccount.entity.Product;
import com.hummingbird.maaccount.exception.MaAccountException;
import com.hummingbird.maaccount.face.Account;
import com.hummingbird.maaccount.face.Receipt;
import com.hummingbird.maaccount.mapper.CashAccountOrderMapper;
import com.hummingbird.maaccount.util.BatchProcessReporter;
import com.hummingbird.maaccount.vo.AntiPayoffline;
import com.hummingbird.maaccount.vo.AppVO;
import com.hummingbird.maaccount.vo.BatchAddUserVO;
import com.hummingbird.maaccount.vo.BatchOpenOnlineDetailVO;
import com.hummingbird.maaccount.vo.BatchOpenOnlineListVO;
import com.hummingbird.maaccount.vo.DefaultReceipt;
import com.hummingbird.maaccount.vo.DiscountCardOrderVO;
import com.hummingbird.maaccount.vo.TransOrderVO2;

/**
 * 折扣卡业务类
 * @author Administrator
 *
 */
public interface DiscountCardAccountService extends AccountService{

	/**
	 * 根据APPID及APPORDERID查询线上开折扣卡订单
	 * @param appId
	 * @param appOrderId
	 * @return
	 */
	public DiscountCardAccountOrder queryDiscountCardAccountByApporderId(String appId, String appOrderId) throws MaAccountException;

	


	
	/**
	 * 冲正/撤销 线下支付
	 * @param transorder
	 * @param sourceacc
	 * @param descacc
	 * @return
	 * @throws MaAccountException 
	 */
	public Receipt antiPayOffineByDiscard(
			TransOrderVO2<AntiPayoffline> transorder, Account sourceacc,
			DiscountCardAccount descacc)throws MaAccountException;
	/**
	 * 批量线上开卡
	 * @author liudou
	 * @param body
	 * @param app
	 * @return
	 */
	public ResultModel createUserBatch(TransOrderVO2<BatchOpenOnlineDetailVO> transorder, AppInfo app) ;
	
	/**
	 * 创建折扣卡帐户
	 * @param channleNo
	 * @param order
	 * @param product
	 * @param commonproduct
	 * @return
	 * @throws DataInvalidException
	 * @throws MaAccountException
	 */
	public DiscountCardAccount createDiscountCardAccount(String channleNo,BatchOpenOnlineListVO order, DiscountCardProduct product,Product commonproduct) throws DataInvalidException, MaAccountException ;
}
