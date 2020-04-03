/**
 * 
 * CashAccountService.java
 * 版本所有 深圳市蜂鸟娱乐有限公司 2013-2014
 */
package com.hummingbird.maaccount.service;

import java.util.Date;
import java.util.Map;

import com.hummingbird.common.exception.DataInvalidException;
import com.hummingbird.common.util.SpringBeanUtil;
import com.hummingbird.common.vo.ResultModel;
import com.hummingbird.commonbiz.util.NoGenerationUtil;
import com.hummingbird.maaccount.constant.OrderConst;
import com.hummingbird.maaccount.entity.AppInfo;
import com.hummingbird.maaccount.entity.CashAccount;
import com.hummingbird.maaccount.entity.CashAccountOrder;
import com.hummingbird.maaccount.entity.OilcardAccount;
import com.hummingbird.maaccount.entity.OilcardAccountOrder;
import com.hummingbird.maaccount.entity.OilcardAccountProduct;
import com.hummingbird.maaccount.entity.VolumecardAccount;
import com.hummingbird.maaccount.entity.VolumecardAccountProduct;
import com.hummingbird.maaccount.exception.InsufficientAccountBalanceException;
import com.hummingbird.maaccount.exception.MaAccountException;
import com.hummingbird.maaccount.face.Account;
import com.hummingbird.maaccount.face.Receipt;
import com.hummingbird.maaccount.mapper.CashAccountOrderMapper;
import com.hummingbird.maaccount.vo.AntiPayoffline;
import com.hummingbird.maaccount.vo.AppVO;
import com.hummingbird.maaccount.vo.BatchOpenOnlineDetailVO;
import com.hummingbird.maaccount.vo.BatchOpenOnlineListVO;
import com.hummingbird.maaccount.vo.DefaultReceipt;
import com.hummingbird.maaccount.vo.IOrderVO;
import com.hummingbird.maaccount.vo.OfflinePayOrderVO;
import com.hummingbird.maaccount.vo.TransOrderVO;
import com.hummingbird.maaccount.vo.TransOrderVO2;

/**
 * @author huangjiej_2
 * 2014年12月29日 下午10:55:31
 * 本类主要做为分期卡
 */
public interface OilcardAccountService extends AccountService{

	/**
	 * 计算分期卡转钱
	 * @param mobile
	 * @param requireAmount 还需要转多少钱
	 * @return 
	 */
	public Map<OilcardAccount, Long> transferOutFromOilcard(String mobile,long requireAmount, boolean notenoughContinue,String accountId) throws MaAccountException;

	/**
	 * 更新帐户
	 * @param oc
	 */
	public void updateAccount(OilcardAccount oc);

	/**
	 * 从分期卡转钱到现金帐户，会实现帐户金额的变更和订单的生成
	 * @param mobile
	 * @param requireAmount 需要转多少钱
	 * @param notenoughContinue 当不足时是否继续转，如为否，钱不足时会抛出异常
	 * @return 
	 * @throws InsufficientAccountBalanceException 分期卡可用余额不足指定的金额时，如果notenoughContinue=false，会抛出异常
	 * @throws MaAccountException 
	 */
	public Receipt transferOilcard2cashaccount(TransOrderVO2<? extends IOrderVO> transorder,
			CashAccount descacc, Long requireSum,boolean notenoughContinue) throws  MaAccountException;
	
	
	/**
	 * 冲正/撤销 线下支付
	 * @param transorder
	 * @param sourceacc
	 * @param descacc
	 * @return
	 * @throws MaAccountException 
	 */
	public abstract Receipt antiPayOffineByOilcard(TransOrderVO2<AntiPayoffline> transorder, Account sourceacc,
			CashAccount descacc) throws MaAccountException;

	/**
	 * 转换分期卡可用余额至现金帐户
	 * @param oilcardAccount
	 * @return
	 */
	public Receipt transferOilcard2cashaccount(OilcardAccount oilcardAccount)throws MaAccountException;

	/**
	 * 根据第三方应用id查询开卡情况
	 * @param appId
	 * @param appOrderId
	 * @return 
	 * @throws MaAccountException 
	 */
	public OilcardAccountOrder queryOilcardAccountByapporderId(String appId, String appOrderId) throws MaAccountException;
	
	/**
	 * 按期数把分期卡的钱转至可用余额
	 */
	public void transOilcard2UseableByDay();
	
	
	/**
	 * 交割
	 * @param orderId
	 * @return 
	 * @throws DataInvalidException 
	 * @throws MaAccountException 
	 */
	public OilcardAccount deliveryOilcard(String orderId) throws DataInvalidException, MaAccountException;
	/**
	 * 批量线上开卡
	 * @author liudou
	 * @param body
	 * @param app
	 * @return
	 */
	public ResultModel createUserBatch(TransOrderVO2<BatchOpenOnlineDetailVO> transorder, AppInfo app) ;
	
	public OilcardAccount createOilcardAccount(String channleNo,BatchOpenOnlineListVO order, OilcardAccountProduct product) throws DataInvalidException, MaAccountException ;

}
