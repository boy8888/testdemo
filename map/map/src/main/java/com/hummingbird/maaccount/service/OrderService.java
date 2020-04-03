/**
 * 
 * OrderService.java
 * 版本所有 深圳市蜂鸟娱乐有限公司 2013-2014
 */
package com.hummingbird.maaccount.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.hummingbird.maaccount.entity.CashAccount;
import com.hummingbird.maaccount.entity.DiscountCardAccount;
import com.hummingbird.maaccount.entity.DiscountCardProduct;
import com.hummingbird.maaccount.entity.InvestmentAccount;
import com.hummingbird.maaccount.entity.InvestmentAccountObjectOrder;
import com.hummingbird.maaccount.entity.JifenAccount;
import com.hummingbird.maaccount.entity.JifenAccountOrder;
import com.hummingbird.maaccount.entity.JifenProduct;
import com.hummingbird.maaccount.entity.OfflineRecharge;
import com.hummingbird.maaccount.entity.OilcardAccount;
import com.hummingbird.maaccount.entity.OilcardAccountProduct;
import com.hummingbird.maaccount.entity.RedPaperAccount;
import com.hummingbird.maaccount.entity.RedPaperAccountOrder;
import com.hummingbird.maaccount.entity.RedPaperProduct;
import com.hummingbird.maaccount.entity.User;
import com.hummingbird.maaccount.entity.VolumecardAccount;
import com.hummingbird.maaccount.entity.VolumecardAccountProduct;
import com.hummingbird.maaccount.exception.InsufficientAccountBalanceException;
import com.hummingbird.maaccount.exception.MaAccountException;
import com.hummingbird.maaccount.face.Account;
import com.hummingbird.maaccount.face.Order;
import com.hummingbird.maaccount.face.Pagingnation;
import com.hummingbird.maaccount.face.Receipt;
import com.hummingbird.maaccount.vo.BatchOpenOnlineDetailVO;
import com.hummingbird.maaccount.vo.BatchOpenOnlineListVO;
import com.hummingbird.maaccount.vo.CashAccountOrderVO;
import com.hummingbird.maaccount.vo.DiscountCardOrderVO;
import com.hummingbird.maaccount.vo.JifenOrderVO;
import com.hummingbird.maaccount.vo.OfflineOpencardOrderVO;
import com.hummingbird.maaccount.vo.OfflinePayOrderConsumerVO;
import com.hummingbird.maaccount.vo.OfflinePayOrderVO2;
import com.hummingbird.maaccount.vo.OilcardOrderVO;
import com.hummingbird.maaccount.vo.OrderQueryTransVO;
import com.hummingbird.maaccount.vo.OrderWithdrawDetailOutputVO;
import com.hummingbird.maaccount.vo.OrderdetailOutputBaseVO;
import com.hummingbird.maaccount.vo.RechargeOutputVO;
import com.hummingbird.maaccount.vo.RedPaperOrderVO;
import com.hummingbird.maaccount.vo.SpendJifenOrderVO;
import com.hummingbird.maaccount.vo.SpendOrderOutputVO;
import com.hummingbird.maaccount.vo.SpendOrderQueryPagingVO;
import com.hummingbird.maaccount.vo.SpendRedPaperOrderVO;
import com.hummingbird.maaccount.vo.JournalOrderOutputVO;
import com.hummingbird.maaccount.vo.TokenCheckResultVO;
import com.hummingbird.maaccount.vo.TransOrderVO;
import com.hummingbird.maaccount.vo.TransOrderVO2;
import com.hummingbird.maaccount.vo.TransOrderWithConsumerVO;
import com.hummingbird.maaccount.vo.UndoRedPaperTransOrderVO;
import com.hummingbird.maaccount.vo.VolumecardOrderVO;
import com.hummingbird.maaccount.vo.YouMeDiscountCardOrderVO;

/**
 * @author huangjiej_2
 * 2014年12月27日 下午5:17:53
 * 本类主要做为订单相关的service
 */
public interface OrderService {


	/**
	 * 订单处理
	 * @param transorder 原订单信息
	 * @param fromaccount  金额流出帐户
	 * @param toaccount    金额注入帐户
	 * @return 票据信息，如果是多表操作，返回第一步的操作票据
	 * @throws MaAccountException 
	 */
	Receipt processOrder(TransOrderVO transorder, Account fromaccount,
			Account toaccount) throws MaAccountException;

	/**
	 * 充值到现金账户
	 
	 * @throws MaAccountException 
	 */
	Receipt rechargeCashAccount(TransOrderVO transorder, CashAccount sourceacc) throws MaAccountException;

	
	/**
	 * 投资帐户的购买有油贷订单处理
	 * @param transorder 原订单信息
	 * @param fromaccount  金额流出帐户-冻结帐户
	 * @param toaccount    金额注入帐户-标的帐户
	 * @param oriorder  原来的标的订单
	 * @return 票据信息，如果是多表操作，返回第一步的操作票据
	 * @throws MaAccountException 
	 */
	Receipt processOrderIaYYD(TransOrderVO transorder, Account fromaccount,
			Account toaccount, InvestmentAccountObjectOrder oriorder) throws MaAccountException;
	
	/**
	 * 根据表名查询订单
	 * @param table
	 * @param orderId
	 * @return
	 */
	public abstract Order getOrder(String table, String orderId);
	
	/**
	 * 分页查询订单
	 * @param pagingnation
	 * @param filter
	 * @return
	 * @throws MaAccountException 
	 */
	public List<OrderdetailOutputBaseVO> queryOrder(Pagingnation pagingnation,Map filter) throws MaAccountException;

	/**
	 * 分页查询现金交易记录
	 * @param pagingnation
	 * @param filter
	 * @return
	 * @throws MaAccountException 
	 */
	List<CashAccountOrderVO> queryCashAccountOrder(Pagingnation pagingnation,Map filter) throws MaAccountException;

	
	/**
	 * 获取以指定的orderid为订单的订单，需要指定在哪个订单表中查询
	 * @param ordertable
	 * @param oriorderid
	 * @return
	 */
	List<Order> getRelaOrder(String table,String oriorderid);

	public abstract Receipt processOrder4withdraw_to_freeze(TransOrderVO transorder,
			Account fromaccount, Account toaccount) throws MaAccountException;

	/**
	 * 提现成功接口
	 * @param transorder
	 * @param sourceacc
	 * @param descacc
	 * @return
	 */
	Receipt processOrder4withdraw_success(TransOrderVO transorder,
			InvestmentAccount sourceacc, Account descacc) throws MaAccountException;

	/**
	 * @param transorder
	 * @param fromaccount
	 * @param toaccount
	 * @return
	 * @throws MaAccountException
	 */
	Receipt processOrder4withdraw_to_unfreeze(TransOrderVO transorder,
			Account fromaccount, Account toaccount) throws MaAccountException;

	/**
	 * 从余额订单中查询冻结的标的订单
	 * @param oriorderid
	 * @return
	 */
	InvestmentAccountObjectOrder getFreezeObjectOrder(String remainOrderId);

	/**
	 * 查询提现申请
	 * @param pagingnation
	 * @param filter
	 * @return
	 */
	List<OrderWithdrawDetailOutputVO> queryWithdrawOrder(
			Pagingnation pagingnation, Map filter);

	/**
	 * 提交线下充值凭据
	 * @param transorder
	 * @param sourceacc
	 * @param descacc
	 * @return
	 */
	Receipt savingByUser(TransOrderVO transorder, Account sourceacc,
			InvestmentAccount descacc) throws MaAccountException;

	/**
	 * 分页查询充值
	 * @param pagingnation
	 * @param filter
	 * @return
	 */
	List<com.hummingbird.maaccount.vo.OfflineRechargeOutputVO> queryOfflineRecharge(
			Pagingnation pagingnation, Map filter);

	/**
	 * 线下充值成功
	 * @param transorder
	 * @param sourceacc
	 * @param descacc
	 * @return
	 */
	Receipt savingSuccess(TransOrderVO transorder, Account sourceacc,
			InvestmentAccount descacc,OfflineRecharge oriorder) throws MaAccountException;
	/**
	 * 线下充值撤销
	 * @param transorder
	 * @param sourceacc
	 * @param descacc
	 * @return
	 */
	Receipt savingCancle(TransOrderVO transorder, Account sourceacc,
			InvestmentAccount descacc,OfflineRecharge oriorder) throws MaAccountException;

	/**
	 * 订单处理
	 * @param transorder 原订单信息
	 * @param fromaccount  金额流出帐户
	 * @param toaccount    金额注入帐户
	 * @return 票据信息，如果是多表操作，返回第一步的操作票据
	 * @throws MaAccountException 
	 */
	Receipt processOrder(TransOrderVO2<OilcardOrderVO> transorder,
			Account sourceacc, OilcardAccount descacc);

	/**
	 * 开卡
	 * @param transorder
	 * @param sourceacc
	 * @param descacc
	 * @return
	 */
	Receipt processOpenOilcard(TransOrderVO2<OilcardOrderVO> transorder,
			Account sourceacc, OilcardAccount descacc,OilcardAccountProduct product)throws MaAccountException ;
	
		/**
	 * 添加红包
	 * @param transorder
	 * @param sourceacc
	 * @param descacc
	 * @return
	 */
	Receipt processRedPaper(TransOrderVO2<RedPaperOrderVO> transorder,
			Account sourceacc, RedPaperAccount descacc,RedPaperProduct product)throws MaAccountException ;
	
	/**
	 * 添加积分
	 * @param transorder
	 * @param sourceacc
	 * @param descacc
	 * @return
	 */
	Receipt processJifen(TransOrderVO2<JifenOrderVO> transorder,User user,
			 JifenProduct product)throws MaAccountException ;

	
	
	/**
	 * 容量卡线上开卡
	 * @param transorder
	 * @param sourceacc
	 * @param descacc
	 * @return
	 */
	Receipt processOpenVolumecard(TransOrderVO2<VolumecardOrderVO> transorder,
			Account sourceacc, VolumecardAccount descacc,VolumecardAccountProduct product)throws MaAccountException ;

	/**
	 * 折扣卡线上开卡
	 * @param transorder
	 * @param sourceacc
	 * @param dcacc
	 * @return
	 */
	Receipt processOpenDiscountCard(TransOrderVO2<DiscountCardOrderVO> transorder,
			Account sourceacc, DiscountCardAccount dcacc,DiscountCardProduct product)throws MaAccountException ;
	/**
	 * 折扣卡线上开卡，并且设置账户可消费参数
	 * @param transorder
	 * @param sourceacc
	 * @param dcacc
	 * @return
	 */
	Receipt processOpenDiscountCardAndSetting(TransOrderVO2<YouMeDiscountCardOrderVO> transorder,
			Account sourceacc, DiscountCardAccount dcacc,DiscountCardProduct product)throws MaAccountException ;
	
	
	
	/**
	 * 折扣卡线上开卡(批量开卡)
	 * @param transorder
	 * @param sourceacc
	 * @param dcacc
	 * @return
	 */
	Receipt processBathOpenDiscountCard(TransOrderVO2<BatchOpenOnlineDetailVO> transorder,BatchOpenOnlineListVO order,
			Account sourceacc, DiscountCardAccount dcacc,DiscountCardProduct product)throws MaAccountException ;

	/**
	 * 容量卡线上开卡(批量开卡)
	 * @param transorder
	 * @param sourceacc
	 * @param dcacc
	 * @return
	 */
	Receipt processBathOpenVolumecard(TransOrderVO2<BatchOpenOnlineDetailVO> transorder,BatchOpenOnlineListVO order,
			Account sourceacc, VolumecardAccount descacc,VolumecardAccountProduct product)throws MaAccountException ;

	/**
	 * 分期卡线上开卡(批量开卡)
	 * @param transorder
	 * @param sourceacc
	 * @param dcacc
	 * @return
	 */
	Receipt processBathOpenOilcard(TransOrderVO2<BatchOpenOnlineDetailVO> transorder,BatchOpenOnlineListVO order,
			Account sourceacc, OilcardAccount descacc,OilcardAccountProduct product)throws MaAccountException ;
	
	/**
	 * 线下支付（pos机支付),从现金帐户扣钱，如钱不足将会从分期卡扣钱
	 * @param transorder
	 * @param sourceacc
	 * @param descacc
	 * @return
	 * @throws MaAccountException 
	 */
	Receipt payOffineByOilcard(TransOrderVO2<OfflinePayOrderVO2> transorder, CashAccount sourceacc,
			Account descacc) throws MaAccountException;

	/**
	 * 线下支付
	 * @param transorder
	 * @param sourceacc
	 * @param descacc
	 * @return
	 * @throws InsufficientAccountBalanceException 
	 */
	Receipt payOffineByOilcard(
			TransOrderWithConsumerVO<OfflinePayOrderConsumerVO> transorder,
			CashAccount sourceacc, Account descacc) throws InsufficientAccountBalanceException;
	
	Receipt payOffineByDiscard(
			TransOrderWithConsumerVO<OfflinePayOrderConsumerVO> transorder,
			DiscountCardAccount sourceacc, Account descacc) throws InsufficientAccountBalanceException;
	Receipt payOffineByVolcard(
			TransOrderWithConsumerVO<OfflinePayOrderConsumerVO> transorder,
			VolumecardAccount sourceacc, Account descacc) throws InsufficientAccountBalanceException;
	Receipt payOffineByCas(
			TransOrderWithConsumerVO<OfflinePayOrderConsumerVO> transorder,
			CashAccount sourceacc, Account descacc) throws InsufficientAccountBalanceException;
	Receipt payOffineByInv(
			TransOrderWithConsumerVO<OfflinePayOrderConsumerVO> transorder,
			InvestmentAccount sourceacc, Account descacc) throws InsufficientAccountBalanceException;
	
	/**
	 * 线下开卡 
	 * @param transorder
	 * @param sourceacc
	 * @param descacc
	 * @param product
	 * @return
	 * @throws MaAccountException 
	 */
	Receipt processOfflineOpenOilcard(
			TransOrderVO2<OfflineOpencardOrderVO> transorder,
			Account sourceacc, OilcardAccount descacc,
			OilcardAccountProduct product) throws MaAccountException;
	
	
	/**
	 * 折扣卡线下开卡 
	 * @param transorder
	 * @param sourceacc
	 * @param descacc
	 * @param product
	 * @return
	 * @throws MaAccountException 
	 */
	Receipt processOfflineOpenDiscard(
			TransOrderVO2<OfflineOpencardOrderVO> transorder,
			Account sourceacc, DiscountCardAccount descacc,
			DiscountCardProduct product) throws MaAccountException;
	
	/**
	 * 容量卡线下开卡 
	 * @param transorder
	 * @param sourceacc
	 * @param descacc
	 * @param product
	 * @return
	 * @throws MaAccountException 
	 */
	Receipt processOfflineOpenVolcard(
			TransOrderVO2<OfflineOpencardOrderVO> transorder,
			Account sourceacc, VolumecardAccount descacc,
			VolumecardAccountProduct product) throws MaAccountException;
	
	


	/**
	 * 更新现金帐户的帐户id
	 * @param accountid
	 * @throws Exception 
	 */
	void changeOldAccountIdToNewAccount4cash(String accountid) throws Exception;
	/**
	 * 更新现金帐户的帐户id
	 * @param accountid
	 * @throws Exception 
	 */
	void undoRedPaper(UndoRedPaperTransOrderVO transorder,List<String> orderId) throws Exception;
	/**
	 * 更新现金帐户的帐户id
	 * @param accountid
	 * @throws Exception 
	 */
	List<RedPaperAccountOrder> spendByRedPaper(TransOrderVO2<SpendRedPaperOrderVO> transorder,List<RedPaperAccount> accounts) throws Exception;

	/**
	 * 更新现金帐户的帐户id
	 * @param accountid
	 * @throws Exception 
	 */
	JifenAccountOrder spendByJifen(TransOrderVO2<SpendJifenOrderVO> transorder,JifenAccount account) throws Exception;
	
	/**
	 * 更新现金帐户的帐户id
	 * @param accountid
	 * @throws Exception 
	 */
	List<TokenCheckResultVO> checkTokenOrder(Date satartDate,Date endDate) throws Exception;
	
	
	/**
	 * 更新投资帐户的帐户id
	 * @param accountid
	 */
	void changeOldAccountIdToNewAccount4investment(String accountid)throws Exception;

	public abstract List<RechargeOutputVO> queryRecharge(Pagingnation pagingnation, Map filter);

	/**
	 * 查询消费订单
	 * @param orderqueryvo
	 * @param pagingnation 
	 * @return
	 */
	List<SpendOrderOutputVO> querySpendOrder(
			OrderQueryTransVO<SpendOrderQueryPagingVO> orderqueryvo, Pagingnation pagingnation);

	/**
	 * 计算当天充值的总金额
	 */
	public Long rechargeTotal();
	
	/**
	 * 查询订单,包含开卡充值消费撤销等
	 * @param orderqueryvo
	 * @param pagingnation 
	 * @param yesterdaylimit 是否限定在昨天的数据 
	 * @return
	 */
	List<JournalOrderOutputVO> queryJournalOrder(
			OrderQueryTransVO<SpendOrderQueryPagingVO> orderqueryvo, Pagingnation pagingnation,boolean yesterdaylimit);
	
}
