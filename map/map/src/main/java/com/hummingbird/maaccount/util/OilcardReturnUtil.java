/**
 * 
 * OilcardReturnUtil.java
 * 版本所有 深圳市蜂鸟娱乐有限公司 2013-2014
 */
package com.hummingbird.maaccount.util;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.time.DateUtils;

import com.hummingbird.common.constant.CommonStatusConst;
import com.hummingbird.common.exception.DataInvalidException;
import com.hummingbird.common.util.DateUtil;
import com.hummingbird.common.util.SpringBeanUtil;
import com.hummingbird.common.util.ValidateUtil;
import com.hummingbird.maaccount.entity.OilcardAccount;
import com.hummingbird.maaccount.entity.OilcardAccountProduct;
import com.hummingbird.maaccount.entity.Product;
import com.hummingbird.maaccount.exception.MaAccountException;
import com.hummingbird.maaccount.mapper.OilcardAccountProductMapper;
import com.hummingbird.maaccount.service.impl.AccountIdServiceImpl;

/**
 * @author john huang
 * 2015年7月30日 下午7:15:41
 * 本类主要做为 分期卡返还处理工具类
 */
public class OilcardReturnUtil {

	static org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory
			.getLog(OilcardReturnUtil.class);
	
	/**
	 * 返还一期钱
	 * @param account
	 * @return 
	 * @throws DataInvalidException 
	 */
	static public boolean doReturn(OilcardAccount account) throws DataInvalidException{
		ValidateUtil.assertNull(account, "分期卡帐户");
		OilcardAccountProductMapper oilcardAccountProductMapper = SpringBeanUtil.getInstance().getBean(OilcardAccountProductMapper.class);
		OilcardAccountProduct product = oilcardAccountProductMapper.selectByPrimaryKey(account.getProductId());
		return doReturn(account,product);
	}
	
	/**
	 * 返还一期
	 * @param oilcardAccount
	 * @param product
	 * @return 执行了返还返回true
	 * @throws DataInvalidException 
	 */
	static public boolean doReturn(OilcardAccount oilcardAccount,OilcardAccountProduct product) throws DataInvalidException{
		ValidateUtil.assertNull(oilcardAccount, "分期卡帐户");
		ValidateUtil.assertNull(product, "分期卡产品");
		if(oilcardAccount.getInitSumReturned()==0){
			oilcardAccount.setInitSumReturned(1);
			oilcardAccount.setRestAmount(oilcardAccount.getRestAmount()-product.getInitialSum());
			oilcardAccount.setBalance(oilcardAccount.getBalance()+product.getInitialSum());
			oilcardAccount.setUpdateTime(new Date());
			AccountValidateUtil.updateAccountSignature(oilcardAccount);
			return true;
		}
		else if(oilcardAccount.getRestStages()>0){
			
			Long returnSum = product.getReturnSum();
			//暂时按固定金额反
			oilcardAccount.setBalance(oilcardAccount.getBalance()+returnSum);
			oilcardAccount.setRestStages(oilcardAccount.getRestStages()-1);
			if(oilcardAccount.getRestStages()==0){
				oilcardAccount.setStatus("END");//完结
			}
			oilcardAccount.setRestAmount(oilcardAccount.getRestAmount()-returnSum);
			oilcardAccount.setUpdateTime(new Date());
			AccountValidateUtil.updateAccountSignature(oilcardAccount);
			return true;
		}
		else{
			if(CommonStatusConst.STATUS_OK.equals(oilcardAccount.getStatus())&&oilcardAccount.getRestStages()==0){
				oilcardAccount.setStatus("END");//完结
			}
		}

		return false;
	}
	
	/**
	 * 开卡
	 * @param userId
	 * @param channelno
	 * @param product
	 * @param commonproduct
	 * @param remark
	 * @param openImme
	 * @return
	 * @throws MaAccountException 
	 * @throws DataInvalidException 
	 */
	static public OilcardAccount openOilCard(Integer userId,String channelno,OilcardAccountProduct product, Product commonproduct,String remark,boolean openImme) throws MaAccountException, DataInvalidException{
		AccountIdServiceImpl accountIdSrv= SpringBeanUtil.getInstance().getBean(AccountIdServiceImpl.class);
		String productId=commonproduct.getProductId();
		String accountid = accountIdSrv.prepareGetAccountId(productId);
		OilcardAccount account=new OilcardAccount();
		account.setAccountId(accountid);
		account.setRemark(remark);
		account.setUserId(userId);
		account.setProductId(productId);
		
		
		account.setAmount(commonproduct.getProductAmount());
		account.setRestAmount(commonproduct.getProductAmount());
		account.setRestStages(product.getTotalStages());
		account.setBalance(0L);
		//由于帐户签名会因为日期中的毫秒数造成签名不一致,所以进行处理,去毫秒去掉
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.MILLISECOND, 0);
		Date starttime = cal.getTime();
		account.setInsertTime(starttime);
		account.setUpdateTime(starttime);
		account.setChannelNo(channelno); 
//		account.setStartTime(new Date());
		//现在就可以用
		account.setStatus(OilcardAccount.STATUS_NOP);
		if(openImme){
			openACard(account,product);
		}
		AccountValidateUtil.updateAccountSignature(account);
		return account;
		
	}
	
	
	/**
	 * 开通一张卡
	 * @param account
	 * @param product 
	 * @return
	 * @throws DataInvalidException 
	 */
	public static boolean openACard(OilcardAccount account, OilcardAccountProduct product) throws DataInvalidException{
		if(OilcardAccount.STATUS_NOP.equals(account.getStatus())){
			//由于帐户签名会因为日期中的毫秒数造成签名不一致,所以进行处理,去毫秒去掉
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.MILLISECOND, 0);
			Date startdate = cal.getTime();
			Date dayStart = DateUtil.toDayStart(startdate);
			Date dayend = DateUtils.add(dayStart, "MON".equals(product.getExpiresType())?Calendar.MONTH:Calendar.DAY_OF_MONTH, product.getExpiresLength());
			account.setStartTime(dayStart);
			account.setEndTime(dayend);
			account.setStatus(OilcardAccount.STATUS_OK);
			//如果是马上开卡,就把期初数放进去
			doReturn(account, product);
			return true;
		}
		if (log.isDebugEnabled()) {
			log.debug(String.format("分期卡%s状态非\"未开通\",不执行开卡操作",account));
		}
		return false;
	}
	
}
