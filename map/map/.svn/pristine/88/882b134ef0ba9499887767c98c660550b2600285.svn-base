/**
 * 
 * Off.java
 * 版本所有 深圳市蜂鸟娱乐有限公司 2013-2014
 */
package com.hummingbird.maaccount.controller;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hummingbird.common.event.EventListenerContainer;
import com.hummingbird.common.exception.DataInvalidException;
import com.hummingbird.common.exception.ToolsException;
import com.hummingbird.common.exception.ValidateException;
import com.hummingbird.common.ext.AccessRequered;
import com.hummingbird.common.util.DateUtil;
import com.hummingbird.common.util.Md5Util;
import com.hummingbird.common.util.MoneyUtil;
import com.hummingbird.common.util.PropertiesUtil;
import com.hummingbird.common.util.RequestUtil;
import com.hummingbird.common.util.SmsSenderUtil;
import com.hummingbird.common.util.SpringBeanUtil;
import com.hummingbird.common.util.StrUtil;
import com.hummingbird.common.util.ValidateUtil;
import com.hummingbird.common.util.json.JSONObject;
import com.hummingbird.common.vo.ResultModel;
import com.hummingbird.common.vo.ValidateResult;
import com.hummingbird.maaccount.constant.OrderConst;
import com.hummingbird.maaccount.entity.AppLog;
import com.hummingbird.maaccount.entity.CashAccount;
import com.hummingbird.maaccount.entity.CashAccountOrder;
import com.hummingbird.maaccount.entity.DefaultOfflinePayOrder;
import com.hummingbird.maaccount.entity.DiscountCardAccount;
import com.hummingbird.maaccount.entity.DiscountCardAccountOrder;
import com.hummingbird.maaccount.entity.DiscountCardProduct;
import com.hummingbird.maaccount.entity.InvestmentAccount;
import com.hummingbird.maaccount.entity.InvestmentAccountRemainingOrder;
import com.hummingbird.maaccount.entity.OilcardAccount;
import com.hummingbird.maaccount.entity.OilcardAccountProduct;
import com.hummingbird.maaccount.entity.Product;
import com.hummingbird.maaccount.entity.ProductTerminalList;
import com.hummingbird.maaccount.entity.TerminalList;
import com.hummingbird.maaccount.entity.User;
import com.hummingbird.maaccount.entity.VolumecardAccount;
import com.hummingbird.maaccount.entity.VolumecardAccountOrder;
import com.hummingbird.maaccount.entity.VolumecardAccountProduct;
import com.hummingbird.maaccount.entity.ZJProduct;
import com.hummingbird.maaccount.event.OfflinePayCancelEvent;
import com.hummingbird.maaccount.event.OfflinePayEvent;
import com.hummingbird.maaccount.event.OfflinePayUndoEvent;
import com.hummingbird.maaccount.exception.InsufficientAccountBalanceException;
import com.hummingbird.maaccount.exception.MaAccountException;
import com.hummingbird.maaccount.face.Account;
import com.hummingbird.maaccount.face.Consumer;
import com.hummingbird.maaccount.face.Order;
import com.hummingbird.maaccount.face.Receipt;
import com.hummingbird.maaccount.mapper.CashAccountMapper;
import com.hummingbird.maaccount.mapper.CashAccountOrderMapper;
import com.hummingbird.maaccount.mapper.DiscountCardAccountMapper;
import com.hummingbird.maaccount.mapper.DiscountCardAccountOrderMapper;
import com.hummingbird.maaccount.mapper.DiscountCardProductMapper;
import com.hummingbird.maaccount.mapper.InvestmentAccountMapper;
import com.hummingbird.maaccount.mapper.OilcardAccountMapper;
import com.hummingbird.maaccount.mapper.OilcardAccountProductMapper;
import com.hummingbird.maaccount.mapper.PrioritySettingMapper;
import com.hummingbird.maaccount.mapper.ProductMapper;
import com.hummingbird.maaccount.mapper.ProductOiltypeMapper;
import com.hummingbird.maaccount.mapper.ProductTerminalListMapper;
import com.hummingbird.maaccount.mapper.TerminalListMapper;
import com.hummingbird.maaccount.mapper.VolumecardAccountMapper;
import com.hummingbird.maaccount.mapper.VolumecardAccountOrderMapper;
import com.hummingbird.maaccount.mapper.VolumecardAccountProductMapper;
import com.hummingbird.maaccount.mapper.ZJProductMapper;
import com.hummingbird.maaccount.service.AccountPayAllowService;
import com.hummingbird.maaccount.service.DiscountCardAccountService;
import com.hummingbird.maaccount.service.OilcardAccountService;
import com.hummingbird.maaccount.service.VolumecardAccountService;
import com.hummingbird.maaccount.service.impl.AccountIdServiceImpl;
import com.hummingbird.maaccount.service.impl.SmsSendService;
import com.hummingbird.maaccount.service.impl.UserService;
import com.hummingbird.maaccount.util.AccountFactory;
import com.hummingbird.maaccount.util.AccountValidateUtil;
import com.hummingbird.maaccount.util.OilcardReturnUtil;
import com.hummingbird.maaccount.util.OrderValidateUtil;
import com.hummingbird.maaccount.util.PospEncyptUtil;
import com.hummingbird.maaccount.util.ProductPriorityUtil;
import com.hummingbird.maaccount.vo.AccountUserVO;
import com.hummingbird.maaccount.vo.AntiPayoffline;
import com.hummingbird.maaccount.vo.BaseConsumerVO;
import com.hummingbird.maaccount.vo.CardSelecter;
import com.hummingbird.maaccount.vo.DisCardResultVO;
import com.hummingbird.maaccount.vo.OfflineOpencardOrderVO;
import com.hummingbird.maaccount.vo.OfflinePayOrderConsumerVO;
import com.hummingbird.maaccount.vo.OilcardQueryVO;
import com.hummingbird.maaccount.vo.OilcardResultVO;
import com.hummingbird.maaccount.vo.ProductDownloadVO;
import com.hummingbird.maaccount.vo.ProductPriority;
import com.hummingbird.maaccount.vo.ProductVO;
import com.hummingbird.maaccount.vo.TransOrderVO2;
import com.hummingbird.maaccount.vo.TransOrderWithConsumerVO;
import com.hummingbird.maaccount.vo.VolumecardResultVO;

/**
 * @author john huang
 * 2015年2月13日 下午9:16:04
 * 本类主要做为 线下支付的处理器
 */
@Controller
@RequestMapping("/offline")
public class OffLinePayController extends AccountBaseController{

	@Autowired(required = true)
	private SmsSendService smsSender;
	@Autowired
	ProductMapper productDao;
	@Autowired
	VolumecardAccountProductMapper volumeProductDao;
	@Autowired
	DiscountCardAccountMapper discountProductDao;
	
	@Autowired
	UserService userSrv;
	@Autowired
	AccountIdServiceImpl accountIdSrv;
	
	@Autowired
	OilcardAccountMapper oaDao;
	@Autowired
	OilcardAccountProductMapper ocproDao;

	@Autowired
	TerminalListMapper terminalListDao;
	
	@Autowired
	CashAccountMapper caDao;
	@Autowired
	DiscountCardProductMapper dcproDao;
	
	@Autowired
	VolumecardAccountProductMapper vcproDao;
	@Autowired
	DiscountCardAccountMapper daDao;
	@Autowired
	VolumecardAccountMapper vaDao;
	@Autowired
	PrioritySettingMapper psDao;
	@Autowired
	ProductTerminalListMapper productTerminalListDao;
	
	@Autowired
	ProductMapper proDao;
	@Autowired
	ProductOiltypeMapper proOiltypeDao;
	
	@Autowired
	InvestmentAccountMapper iaDao;
	@Autowired
	ZJProductMapper zjproDao;
	@Autowired
	protected AccountPayAllowService accountPayAllowService;
	
	
	/**
	 * 线下支付
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/pay")
	@AccessRequered(methodName="线下支付")
	public @ResponseBody Object payOffine(HttpServletRequest request) {
//		{
//	    "app":
//	        {"appId":"zjhtwallet","timeStamp":"TIMESTAMP", "nonce":"NONCE","signature":"SIGNATURE"},  
//	    "order":
//	        {"mobileNum":"13912345678","userToken":"qwerqerewtuuuu123wer","sum":500000,"productName":"","remark":"从投资账户转账5000元到现金账户", "appOrderId":"AO201412122344888444"},
//	    "TSIG":
//	        {"orderMD5":"ORDERMD5","signature":"SIGNATURE", "timeStamp":"TIMESTAMP","nonce":"NONCE"}
//	} 
	TransOrderWithConsumerVO<OfflinePayOrderConsumerVO> transorder;
	ResultModel rm = new ResultModel();
	try {
		String jsonstr = RequestUtil.getRequestPostData(request);
		request.setAttribute("rawjson", jsonstr);
		transorder = RequestUtil.convertJson2Obj(jsonstr, TransOrderWithConsumerVO.class,OfflinePayOrderConsumerVO.class);
	} catch (Exception e) {
		log.error(String.format("获取订单参数出错"),e);
		rm.mergeException(ValidateException.ERROR_PARAM_FORMAT_ERROR.cloneAndAppend(null, "订单参数"));
		return rm;
	}
	
	String messagebase = "线下支付";
	
	int baseerrcode=25400;
	rm.setBaseErrorCode(baseerrcode);
	rm.setErrmsg(messagebase+"成功");
	transorder.setOperationType(OrderConst.ORDER_OPERATION_PAY_OFFILNE);
	try {
		prepare(transorder,request);
		TerminalList terminal=terminalListDao.selectByTransOrderVO(transorder.getOrder());
		if(terminal==null){
			if (log.isDebugEnabled()) {
				log.debug(String.format("无法从数据库中获取此终端%s",transorder.getOrder().getTerminalId()));
			}
			throw new MaAccountException(MaAccountException.ERR_POS_EXCEPTION,"此终端不能开展该业务");
		}
		//添加处理,如果订单中storeid为空或为null,而终端表中storeid不为空,则用终端表的storeid进行替换
		if((StringUtils.isBlank(transorder.getOrder().getStoreId())||StringUtils.equalsIgnoreCase("null", transorder.getOrder().getStoreId()))&&StringUtils.isNotBlank(terminal.getStoreId())){
			transorder.getOrder().setStoreId(terminal.getStoreId());
		}
		Consumer consumer = transorder.getOrder().getConsumer();
		ValidateUtil.assertNull(consumer, "消费号码");
		User user;
		Boolean isPay=false;
		ZJProduct zjProduct=null;
		//user = getUserFromConsumer(consumer);
		AccountUserVO au = validateFromConsumer(consumer, transorder.getOrder().getPaymentCodeDES());
		Account fromConsumer = au.getAccount();
		user = au.getUser();
		String mobile = user.getMobilenum();
		if(consumer.getConsumerType().equals(Consumer.CONSUMER_TYPE_MOBILE))
		{
			//加载消费优先级
			String consumerTypeString=psDao.selectByConsumerType(Consumer.MOBILE_PAY_OFFLINE);
			List<ProductPriority> productPriorities = ProductPriorityUtil.getProductPriorities(consumerTypeString);
			for (Iterator iterator = productPriorities.iterator(); iterator	.hasNext()&&!isPay;) {
				ProductPriority pp = (ProductPriority) iterator	.next();
				
				CardSelecter cs = new CardSelecter( transorder.getOrder().getProductName(),transorder.getOrder().getProductQuantity(),transorder.getOrder().getSum(),pp,user.getUserId(),transorder.getOrder().getTerminalId());
				List<? extends Account> bas= getConsumers(cs);
				if(bas==null){
					continue;
				}
				else{
					
					for (Iterator iterator2 = bas.iterator(); iterator2	.hasNext();) {
						Account sourceaccount = (Account) iterator2	.next();
						try{
							if(paybyAccount(sourceaccount,transorder,rm,consumer,baseerrcode,mobile,transorder.getApp().getAppId())){
								if (log.isDebugEnabled()) {
									log.debug(String.format("使用帐户"+sourceaccount.getAccountId()+"支付成功"));
								}
								isPay=true;
								break;
							}
						}
						catch(MaAccountException e){
							log.error("使用帐户"+sourceaccount.getAccountId()+"支付失败",e);
							continue;
						}
					}
				}
			}
			
			
		}
		else if(consumer.getConsumerType().equals(Consumer.CONSUMER_TYPE_OILCARD)){
			//按分期卡消费,因为分期卡的钱会转入现金帐户,所以无法指定用分期卡进行消费
			if (log.isDebugEnabled()) {
				log.debug(String.format("指定帐户的分期卡无法进行消费,请使用现金帐户进行消费"));
			}
			throw new MaAccountException("不能指定按分期卡进行消费");
		}
		else if(consumer.getConsumerType().equals(Consumer.CONSUMER_TYPE_DISCOUNTCARD)){
			//按折扣卡消费
			DiscountCardAccount dca =  (DiscountCardAccount) fromConsumer;
			validateTerminal(dca.getProductId(), transorder.getOrder().getTerminalId());
			//判断折扣卡能不能消费,是否能消费指定的产品
			int countProductOiltype = proOiltypeDao.countProductOiltype(dca.getProductId(), transorder.getOrder().getProductName());
			if(countProductOiltype!=1){
				if (log.isDebugEnabled()) {
					log.debug(String.format("帐户%s不能消费此类产品%s",dca.getAccountId(),transorder.getOrder().getProductName()));
				}
				throw new MaAccountException(MaAccountException.ERR_ACCOUNT_EXCEPTION,"此帐户不能用于消费该产品");
			}
			//查询有足够金额的卡
			if(dca.getBalance()<transorder.getOrder().getSum()){
				if (log.isDebugEnabled()) {
					log.debug(String.format("折扣卡%s的可用余额不足",dca.getAccountId()));
				}
				throw new InsufficientAccountBalanceException("卡的可用余额不足");
			}
			Account descacc=(Account) AccountFactory.getAccount(Account.ACCOUNT_APP,consumer.getConsumerId());
			ValidateUtil.assertNull(descacc, "应用帐户");
			if(log.isDebugEnabled()){
				log.debug("检验通过，获取请求");
			}
			Receipt processOrder = orderSrv.payOffineByDiscard(transorder,dca,descacc);
			DefaultOfflinePayOrder exorder = (DefaultOfflinePayOrder) processOrder.getExtValue("exorder");
		//			DiscountCardAccountOrder exorder = (DiscountCardAccountOrder) processOrder.getExtValue("exorder");
			Map order = setReturnMsg(rm, consumer, dca, processOrder,
					exorder);
			zjProduct = sendSmsByMoney(rm, baseerrcode, dca, mobile,
					exorder, order,StringUtils.right(consumer.getConsumerId(), 4),transorder.getApp().getAppId(),SmsSendService.ACTION_NAME_PAY);
			
		}
		else if(consumer.getConsumerType().equals(Consumer.CONSUMER_TYPE_VOLUMECARD)){
			//按折扣卡消费
			VolumecardAccount vca =  (VolumecardAccount) fromConsumer;
			ValidateUtil.assertNull(vca, "容量卡帐户");
			AccountValidateUtil.validateAccount(vca);
			validateTerminal(vca.getProductId(), transorder.getOrder().getTerminalId());
			int countProductOiltype = proOiltypeDao.countProductOiltype(vca.getProductId(), transorder.getOrder().getProductName());
			if(countProductOiltype!=1){
				if (log.isDebugEnabled()) {
					log.debug(String.format("帐户%s不能消费此类产品%s",vca.getAccountId(),transorder.getOrder().getProductName()));
				}
				throw new MaAccountException(MaAccountException.ERR_ACCOUNT_EXCEPTION,"此帐户不能用于消费该产品");
			}
			//查询有足够金额的卡
			if(vca.getBalance()<transorder.getOrder().getSum()){
				if (log.isDebugEnabled()) {
					log.debug(String.format("容量卡%s的可用余额不足",vca.getAccountId()));
				}
				throw new InsufficientAccountBalanceException("卡的可用余额不足");
			}
			Account descacc=(Account) AccountFactory.getAccount(Account.ACCOUNT_APP,consumer.getConsumerId());
			ValidateUtil.assertNull(descacc, "应用帐户");
			if(log.isDebugEnabled()){
				log.debug("检验通过，获取请求");
			}
			Receipt processOrder = orderSrv.payOffineByVolcard(transorder,vca,descacc);
			DefaultOfflinePayOrder exorder = (DefaultOfflinePayOrder) processOrder.getExtValue("exorder");
		//			DiscountCardAccountOrder exorder = (DiscountCardAccountOrder) processOrder.getExtValue("exorder");
			Map order = setReturnMsg(rm, consumer, vca, processOrder,
					exorder);
			zjProduct = sendSmsByVolume(rm, baseerrcode, vca, mobile,
					exorder, order,StringUtils.right(consumer.getConsumerId(), 4),transorder.getApp().getAppId(),SmsSendService.ACTION_NAME_PAY);
		}
		
		if(isPay){
			if (log.isDebugEnabled()) {
				log.debug(String.format("发送消费的信息"));
			}
			//添加消费通知
			OfflinePayEvent oce = new OfflinePayEvent(transorder,zjProduct,rm,mobile);
			EventListenerContainer.getInstance().fireEvent(oce);
		}
		else{
			if (log.isDebugEnabled()) {
				log.debug(String.format("%s的所有账户都不满足支付条件",mobile));
			}
			throw new MaAccountException(MaAccountException.ERR_ORDER_EXCEPTION,"没有合适的电子卡进行消费");	
		}

		
	} catch (Exception e1) {
		log.error(String.format(messagebase+"失败"),e1);
		rm.mergeException(e1);
		rm.setErrmsg(messagebase+"失败，"+rm.getErrmsg());
	}
	finally{
		//记录日志
		post(rm);
	}
	
	return rm;
	
	}
	/**
	 * @param sourceaccount
	 * @param transorder
	 * @param rm 
	 * @param consumer 
	 * @param baseerrcode 
	 * @param mobile 
	 * @param appId 
	 * @throws MaAccountException 
	 */
	private boolean paybyAccount(Account sourceaccount,
			TransOrderWithConsumerVO<OfflinePayOrderConsumerVO> transorder, ResultModel rm, Consumer consumer, int baseerrcode, String mobile, String appId) throws MaAccountException {
		Account descacc=(Account) AccountFactory.getAccount(Account.ACCOUNT_APP,sourceaccount.getAccountId());
		if (sourceaccount instanceof CashAccount) {
			CashAccount ca = (CashAccount) sourceaccount;
			AccountValidateUtil.validateAccount(ca);
			if(log.isDebugEnabled()){
				log.debug("检验通过，获取请求");
			}
			
			Receipt processOrder;
			if (log.isDebugEnabled()) {
				log.debug(String.format("使用现金账户%s进行支付!",sourceaccount.getAccountId()));
			}
			processOrder = orderSrv.payOffineByCas(transorder,ca,descacc);							
			DefaultOfflinePayOrder exorder = (DefaultOfflinePayOrder) processOrder.getExtValue("exorder");
			Map order = setReturnMsg(rm, consumer, ca, processOrder,exorder);
			sendSmsByMoney(rm, baseerrcode, ca, mobile,exorder, order,StringUtils.right(consumer.getConsumerId(), 4),appId,SmsSendService.ACTION_NAME_PAY);
			return true;
		}
		else if (sourceaccount instanceof DiscountCardAccount) {
			DiscountCardAccount dca = (DiscountCardAccount) sourceaccount;
			AccountValidateUtil.validateAccount(dca);
			if (log.isDebugEnabled()) {
				log.debug(String.format("使用折扣卡账户%s进行支付!",sourceaccount.getAccountId()));
			}
			Receipt processOrder = orderSrv.payOffineByDiscard(transorder,dca,descacc);
			DefaultOfflinePayOrder exorder = (DefaultOfflinePayOrder) processOrder.getExtValue("exorder");
		//			DiscountCardAccountOrder exorder = (DiscountCardAccountOrder) processOrder.getExtValue("exorder");
			Map order = setReturnMsg(rm, consumer, dca, processOrder,exorder);
			sendSmsByMoney(rm, baseerrcode, dca, mobile,
					exorder, order,StringUtils.right(consumer.getConsumerId(), 4),appId,SmsSendService.ACTION_NAME_PAY);
			return true;
		}
		else if (sourceaccount instanceof VolumecardAccount) {
			VolumecardAccount vca = (VolumecardAccount) sourceaccount;
			AccountValidateUtil.validateAccount(vca);
			if (log.isDebugEnabled()) {
				log.debug(String.format("使用容量卡账户%s进行支付!",sourceaccount.getAccountId()));
			}
			Receipt processOrder = orderSrv.payOffineByVolcard(transorder,vca,descacc);
			DefaultOfflinePayOrder exorder = (DefaultOfflinePayOrder) processOrder.getExtValue("exorder");
		//			DiscountCardAccountOrder exorder = (DiscountCardAccountOrder) processOrder.getExtValue("exorder");
			Map order = setReturnMsg(rm, consumer, vca, processOrder,
					exorder);
			sendSmsByVolume(rm, baseerrcode, vca, mobile,
					exorder, order,StringUtils.right(consumer.getConsumerId(), 4),appId,SmsSendService.ACTION_NAME_PAY);
			return true;
		}
		return false;
	}

	
	/**
	 * 查询合适的卡
	 * @param cs
	 * @return
	 * @throws MaAccountException 
	 */
	private List<? extends Account> getConsumers(CardSelecter cs) throws MaAccountException {
		
		List<Account> baseconsumer=new ArrayList<Account>();
		//从账户中查出满足消费的数据
		List<String> accountIdlist = accountPayAllowService.getAccountCanPay(cs);
		if(accountIdlist!=null&&!accountIdlist.isEmpty()){
			for (Iterator iterator = accountIdlist.iterator(); iterator.hasNext();) {
				String accid = (String) iterator.next();
				switch (cs.getConsumerType()) {
				case Consumer.CONSUMER_TYPE_DISCOUNTCARD:
					baseconsumer.add(daDao.getAccountByAccountId(accid));
					break;
				case Consumer.CONSUMER_TYPE_VOLUMECARD:
					baseconsumer.add(vaDao.getAccountByAccountId(accid));
					break;
				case Consumer.CONSUMER_TYPE_CASHACCOUNT:
					baseconsumer.add(caDao.getAccountByAccountId(accid));
					break;
				case Consumer.CONSUMER_TYPE_INVESTMENTACCOUNT:
					if (log.isDebugEnabled()) {
						log.debug(String.format("投资帐户不参与消费"));
					}
					break;
				case Consumer.CONSUMER_TYPE_OILCARD:
					if (log.isDebugEnabled()) {
						log.debug(String.format("分期卡帐户转为现金帐户消费"));
					}
					baseconsumer.add(caDao.getAccountByAccountId(accid));
					break;
				default:
					break;
				}
			}
			if(!baseconsumer.isEmpty()){
				return baseconsumer;
			}
		}
		//根据产品找到符合的条件
		switch (cs.getConsumerType()) {
		case Consumer.CONSUMER_TYPE_DISCOUNTCARD:
			List<DiscountCardAccount> dcas=new ArrayList<DiscountCardAccount>();
			dcas=daDao.getAccountByCardSelecter(cs);
			return dcas;
		case Consumer.CONSUMER_TYPE_VOLUMECARD:
			List<VolumecardAccount> vcas;
			vcas=vaDao.getAccountByCardSelecter(cs);
			return vcas;
		case Consumer.CONSUMER_TYPE_CASHACCOUNT:
			CashAccount ca=new CashAccount();
			ca=caDao.selectByUserId(cs.getUserId(),cs.getSum());
			if(ca==null){
				//throw new MaAccountException(MaAccountException.ERR_ACCOUNT_EXCEPTION,"现金帐户不存在或余额不足");
				return null;
			}
			List<Account> list = new ArrayList();
			list.add(ca);
			return list;
		case Consumer.CONSUMER_TYPE_INVESTMENTACCOUNT:
			if (log.isDebugEnabled()) {
				log.debug(String.format("投资帐户不参与消费"));
			}
			return null;
		case Consumer.CONSUMER_TYPE_OILCARD:
			if (log.isDebugEnabled()) {
				log.debug(String.format("分期卡帐户转为现金帐户消费"));
			}
			CashAccount oca=caDao.selectByUserId(cs.getUserId(),cs.getSum());
			if(oca==null){
				//throw new MaAccountException(MaAccountException.ERR_ACCOUNT_EXCEPTION,"现金帐户不存在或余额不足");
				return null;
			}
			List<Account> list1 = new ArrayList();
			list1.add(oca);
			return list1;
		default:
			break;
		}
		return null;
	}
	/**
	 * @param rm
	 * @param baseerrcode
	 * @param payaccount
	 * @param mobile
	 * @param exorder
	 * @param order
	 * @param action  动作
	 * @param appId  应用
	 * @return
	 */
	public ZJProduct sendSmsByMoney(ResultModel rm, int baseerrcode,
			Account payaccount, String mobile,
			DefaultOfflinePayOrder exorder, Map order,String accountno, String appId, String action) {
		ZJProduct zjProduct;
		//发送支付成功的短信通知
		Map smsparam = new HashMap();
		smsparam.putAll(order);
		smsparam.put("sum", MoneyUtil.getMoneyStringDecimal2fen((long)order.get("sum")));
		smsparam.put("unit", "元");
		zjProduct = zjproDao.selectByPrimaryKey(exorder.getProductName());
		smsparam.put("productName", zjProduct==null?"产品":zjProduct.getZjproductname());
		smsparam.put("payTime", DateUtil.format(exorder.getInsertTime(), "M月d日HH:mm"));
		smsparam.put("accountno",accountno);
		smsparam.put("balance",new DecimalFormat("0.00").format((double)payaccount.getBalance()/100));
		String content = new PropertiesUtil().getProperty("sms.offline_pay.success");
		content = StrUtil.replaceAll(content,smsparam);
		// 调用webservice 发送模板
		if (log.isDebugEnabled()) {
			log.debug("发送手机验证码至"+mobile+"请求:" + content);
		}
		try {
			smsSender.send(mobile,content, appId, action);
//			SmsSenderUtil.sendSms(mobile, content);
			if (log.isDebugEnabled()) {
				log.debug("向用户发送支付成功的短信通知完成");
			}
		} catch (Exception e) {
			log.error("向用户发送支付成功的短信通知出错:", e);
//			rm.setErr(baseerrcode, "向用户发送支付成功的短信通知失败，其它原因");
		}
		return zjProduct;
	}
	/**
	 * @param rm
	 * @param baseerrcode
	 * @param payaccount
	 * @param mobile
	 * @param exorder
	 * @param order
	 * @param action 动作
	 * @param appId 应用 
	 * @return
	 */
	public ZJProduct sendSmsByVolume(ResultModel rm, int baseerrcode,
			Account payaccount, String mobile,
			DefaultOfflinePayOrder exorder, Map order,String accountno, String appId, String action) {
		ZJProduct zjProduct;
		//发送支付成功的短信通知
		Map smsparam = new HashMap();
		smsparam.putAll(order);
		smsparam.put("sum", new DecimalFormat("0.00").format((long)order.get("sum")/1000));
		smsparam.put("unit", "升");
		zjProduct = zjproDao.selectByPrimaryKey(exorder.getProductName());
		smsparam.put("productName", zjProduct==null?"产品":zjProduct.getZjproductname());
		smsparam.put("payTime", DateUtil.format(exorder.getInsertTime(), "M月d日HH:mm"));
		smsparam.put("accountno",accountno);
		smsparam.put("balance",new DecimalFormat("0.00").format((double)(payaccount.getBalance()/1000)));
		String content = new PropertiesUtil().getProperty("sms.offline_pay_byvolumecard.success");
		content = StrUtil.replaceAll(content,smsparam);
		// 调用webservice 发送模板
		if (log.isDebugEnabled()) {
			log.debug("发送手机验证码至"+mobile+"请求:" + content);
		}
		
		try {
			smsSender.send(mobile,content, appId, action);
//			SmsSenderUtil.sendSms(mobile, content);
			if (log.isDebugEnabled()) {
				log.debug("向用户发送支付成功的短信通知完成");
			}
			
		} catch (Exception e) {
			log.error("向用户发送支付成功的短信通知出错:", e);
//			rm.setErr(baseerrcode, "向用户发送支付成功的短信通知失败，其它原因");
		}
		return zjProduct;
		
	}
	/**
	 * 
	 * @param rm
	 * @param consumer
	 * @param sourceAccount
	 * @param processOrder
	 * @param exorder
	 * @return
	 */
	public Map setReturnMsg(ResultModel rm, Consumer consumer,
			Account sourceAccount, Receipt processOrder,
			DefaultOfflinePayOrder exorder) {
		Map order = new HashMap();
		order.put("orderId", processOrder.getOrderNo());
		order.put("consumerId", consumer.getConsumerId());
		order.put("sum", Math.abs(exorder.getSum()));
		order.put("payTime",DateUtil.formatCommonDateorNull(exorder.getInsertTime()));
		order.put("sellerId", exorder.getSellerId());
		order.put("storeId", exorder.getStoreId());
		order.put("terminalId", exorder.getTerminalId());
		order.put("operatorId", exorder.getOperatorId());
		order.put("batchNo", exorder.getBatchNo());
		order.put("terminalOrderId", exorder.getTerminalOrderId());
		order.put("productName", exorder.getProductName());
		order.put("productPrice", exorder.getProductPrice());
		order.put("productQuantity", exorder.getProductQuantity());
		order.put("remark", exorder.getRemark());
		order.put("status", "支付成功");
		//输出现金帐户消息
		Map accountmap = new HashMap();
		accountmap.put("accountId", sourceAccount.getAccountId());
		accountmap.put("balance", sourceAccount.getBalance());
		accountmap.put("status","正常");
		rm.put("account",accountmap);
		rm.put("order",order);
		return order;
	}
	/**
	 * 从consumer中校验内容
	 * @param consumer
	 * @return
	 * @throws MaAccountException 
	 * @throws DataInvalidException 
	 */
	private AccountUserVO validateFromConsumer(Consumer consumer,String paymentCodeDES) throws MaAccountException, DataInvalidException {
		User user=null;
		Account accountByConsumer = null;
		if(consumer.getConsumerType().equals(Consumer.CONSUMER_TYPE_MOBILE))
		{
			user = userdao.selectByMobile(consumer.getConsumerId());
			if(user==null)
			{
				if (log.isDebugEnabled()) {
					log.debug(String.format("用户[手机号%s]不存在",consumer.getConsumerId()));
				}
				throw new MaAccountException(ValidateException.ERROR_EXISTING_USER_NOT_EXISTS.getErrcode(),"用户不存在");
				
			}
		}
		else {
			accountByConsumer = AccountFactory.getAccountByConsumer(consumer);
			if(accountByConsumer==null){
				if (log.isDebugEnabled()) {
					log.debug(String.format("账户[消费号码%s]不存在",consumer.getConsumerId()));
				}
				throw new MaAccountException(ValidateException.ERROR_EXISTING_ACCOUNT_NOT_EXISTS.getErrcode(),"消费号码不存在");
				
			}
			Integer userId = accountByConsumer.getUserId();
			user = userdao.selectByPrimaryKey(userId);
			if(user==null)
			{
				if (log.isDebugEnabled()) {
					log.debug(String.format("用户[消费号码%s]不存在",consumer.getConsumerId()));
				}
				throw new MaAccountException(ValidateException.ERROR_EXISTING_USER_NOT_EXISTS.getErrcode(),"用户不存在");
				
			}
		}
		//检查支付密码
		String decypt;
		try {
		    decypt = PospEncyptUtil.decypt(paymentCodeDES, user.getMobilenum());
		} catch (ToolsException e) {
		    log.error(String.format("支付密码解密出错"),e);
		    throw ValidateException.ERROR_MATCH_PAYMENT_CODE;
		}
		String encryptdes = Md5Util.Encrypt(decypt);
		if(!StringUtils.equals(encryptdes,user.getPaymentCodeDES())){//des和md5分别对应 有油网的支付和有油贷投资的支付密码
		if (log.isDebugEnabled()) {
		    log.debug(String.format("用户【消费号码%s】支付密码错,支付md5=%s",consumer.getConsumerId(),encryptdes));
		}
		throw ValidateException.ERROR_MATCH_PAYMENT_CODE;
		}
		return new AccountUserVO(accountByConsumer,user);
	}
	/**
	 * 线下开卡
	 * 线下开卡接口，pos机不调用
	 * @return
	 */
	@RequestMapping("/open_card")
	@AccessRequered(methodName="线下开卡")
	@Deprecated
	public @ResponseBody Object offineOpenOilcard(HttpServletRequest request) {
//		{
//		    "app":
//		        {"appId":"zjhtwallet","timeStamp":"TIMESTAMP", "nonce":"NONCE","signature":"SIGNATURE"},  
//		    "order":
//		        {"mobileNum":"13912345678","userToken":"qwerqerewtuuuu123wer","sum":500000,"productName":"","remark":"从投资账户转账5000元到现金账户", "appOrderId":"AO201412122344888444"},
//		    "TSIG":
//		        {"orderMD5":"ORDERMD5","signature":"SIGNATURE", "timeStamp":"TIMESTAMP","nonce":"NONCE"}
//		} 
		TransOrderVO2<OfflineOpencardOrderVO> transorder;
		ResultModel rm = new ResultModel();
		try {
			String jsonstr = RequestUtil.getRequestPostData(request);
			request.setAttribute("rawjson", jsonstr);
			transorder = RequestUtil.convertJson2Obj(jsonstr, TransOrderVO2.class,OfflineOpencardOrderVO.class);
		} catch (Exception e) {
			log.error(String.format("获取订单参数出错"),e);
			rm.mergeException(ValidateException.ERROR_PARAM_FORMAT_ERROR.cloneAndAppend(null, "订单参数"));
			return rm;
		}
		String messagebase = "开卡";
		int baseerrcode = 26700;
		rm.setBaseErrorCode(baseerrcode);
		rm.setErrmsg(messagebase+"成功");
		String accountId=null;
		transorder.setOperationType(OrderConst.ORDER_OPERATION_OPEN_CARD_OFFILNE);
		try {
			prepare(transorder,request,false);
			//3des 解密
			//检查手机号码是否已经注册过，如果未注册，则注册该用户，并用新用户交易密码短息模板下行短信；
			User user= userdao.selectByMobile(transorder.getOrder().getMobileNum());
			//必填判断
			//根据cardtype查询卡类型
			String cardType = transorder.getOrder().getProductId();
			Product pro = proDao.selectByPrimaryKey(cardType);
			//判断产品是否存在
			if(pro!=null){
				//分期卡类型业务
				if(pro.getAccountType().equals("OCA")){
					OilcardAccountProduct product = ocproDao.selectByPrimaryKey(cardType);
					if(product==null)
					{
						if (log.isDebugEnabled()) {
							log.debug(String.format("根据类型%s查询分期卡，结果为空",cardType));
						}
						throw ValidateException.ERROR_EXISTING_PRODUCT_NOT_EXISTS.clone(null, "找不到对应的产品");
					}
					if(!"OK#".equals(product.getStatus()))
					{
						if (log.isDebugEnabled()) {
							log.debug(String.format("分期卡%s已下线",cardType));
						}
						throw ValidateException.ERROR_PRODUCT_OFFLINE.clone(null, "分期卡产品已下线");
					}
					Product commonproduct= productDao.selectByPrimaryKey(product.getProductId());
					//modify by 3.6 如果是拆单，则返回报错
					Long paySum = transorder.getOrder().getPaySum();
					if(paySum!=null){
						if(paySum.longValue()!=commonproduct.getProductPrice()){
							if (log.isDebugEnabled()) {
								log.debug(String.format("开卡数据[%s]传递支付金额与产品购买金额%s不一致，可能进行拆单，系统返回失败。",transorder.getOrder(),commonproduct.getProductPrice()));
							}
							throw new MaAccountException(MaAccountException.ERR_PRODUCT_EXCEPTION,"支付金额无法匹配到产品价格");
						}
					}
					//创建加油卡
					
					OilcardAccount descacc=createOilcardAccount(transorder,product,commonproduct);
					accountId = descacc.getAccountId();
//					OilcardAccount descacc=(OilcardAccount) AccountFactory.getAccount(Account.ACCOUNT_OIL_CARD,transorder.getOrder().getMobileNum());
					Account sourceacc= AccountFactory.getAccount(Account.ACCOUNT_APP,transorder.getOrder().getMobileNum());
					ValidateUtil.assertNull(descacc, "分期卡帐户");
					ValidateUtil.assertNull(sourceacc, "应用帐户");
					if(log.isDebugEnabled()){
						log.debug("检验通过，获取请求");
					}
					Receipt processOrder = orderSrv.processOfflineOpenOilcard(transorder,sourceacc,descacc,product);
					Map<String,Object> ordermap = new HashMap<String,Object>();
					
			        OfflineOpencardOrderVO order = transorder.getOrder();
					ordermap.put("remark",order.getRemark());
					ordermap.put("batchNo", order.getBatchNo());
					ordermap.put("operatorId", order.getOperatorId());
					ordermap.put("terminalOrderId", order.getTerminalOrderId());
					ordermap.put("terminalId", order.getTerminalId());
					ordermap.put("storeId", order.getStoreId());
					ordermap.put("sellerId", order.getSellerId());
					ordermap.put("mobileNum", order.getMobileNum());
					rm.put("order", ordermap);
					//返回分期卡信息
					Map<String,Object> cardmap = new HashMap<String, Object>();
					OilcardAccount returnaccount = descacc;
					cardmap.put("accountId", returnaccount.getAccountId());
					cardmap.put("amount", returnaccount.getAmount());
					cardmap.put("startTime", DateUtil.formatCommonDateorNull(returnaccount.getStartTime()));
					cardmap.put("endTime", DateUtil.formatCommonDateorNull(returnaccount.getEndTime()));
		        	String status2 = returnaccount.getStatus();
					switch(status2){
					case OilcardAccount.STATUS_FRZ:cardmap.put("status","冻结");break;
					case OilcardAccount.STATUS_NOP:cardmap.put("status","未开通");break;
					case OilcardAccount.STATUS_OK:cardmap.put("status","正常");break;
					case OilcardAccount.STATUS_OFF:cardmap.put("status","注销");break;
					}
					OilcardResultVO oilcardResultVO = new OilcardResultVO(descacc, product);
					rm.put("card",cardmap );
					//发送开卡成功的短信通知
					Map smsparam = BeanUtils.describe(oilcardResultVO);
					smsparam.put("productName", product.getProductName());
					smsparam.put("amount", MoneyUtil.getMoneyStringDecimal2(returnaccount.getAmount()));
					String content = new PropertiesUtil().getProperty("sms.offline_openoilcard.success");
					content = StrUtil.replaceAll(content,smsparam);
					String mobileNum = transorder.getOrder().getMobileNum();
					// 调用webservice 发送模板
					if (log.isDebugEnabled()) {
						log.debug("发送手机验证码至"+mobileNum+"请求:" + content);
					}
					
					try {
						smsSender.send(mobileNum,content, transorder.getApp().getAppId(),SmsSendService.ACTION_NAME_REGISTER);
//						SmsSenderUtil.sendSms(mobileNum, content);
						if (log.isDebugEnabled()) {
							log.debug("向用户发送新用户创建成功的短信通知完成");
						}
						
					} catch (Exception e) {
						log.error("向用户发送新用户创建成功的短信通知出错:", e);
						rm.setErr(baseerrcode, "向用户发送新用户创建成功的短信通知失败，其它原因");
					}
				}
				//折扣卡业务类型
				else if(pro.getAccountType().equals("DCA")){
					DiscountCardProduct product = dcproDao.selectByPrimaryKey(cardType);
					if(product==null)
					{
						if (log.isDebugEnabled()) {
							log.debug(String.format("根据类型%s查询折扣卡，结果为空",cardType));
						}
						throw ValidateException.ERROR_EXISTING_PRODUCT_NOT_EXISTS.clone(null, "找不到对应的产品");
					}
					if(!"OK#".equals(product.getStatus()))
					{
						if (log.isDebugEnabled()) {
							log.debug(String.format("折扣卡%s已下线",cardType));
						}
						throw ValidateException.ERROR_PRODUCT_OFFLINE.clone(null, "折扣卡产品已下线");
					}
					Product commonproduct= productDao.selectByPrimaryKey(product.getProductId());
					//modify by 3.6 如果是拆单，则返回报错
					Long paySum = transorder.getOrder().getPaySum();
					if(paySum!=null){
						if(paySum.longValue()!=commonproduct.getProductPrice()){
							if (log.isDebugEnabled()) {
								log.debug(String.format("开卡数据[%s]传递支付金额与产品金额%s不一致，可能进行拆单，系统返回失败。",transorder.getOrder(),commonproduct.getProductPrice()));
							}
							throw new MaAccountException(MaAccountException.ERR_PRODUCT_EXCEPTION,"支付金额无法匹配到产品价格");
						}
					}
					//创建折扣卡
					
					DiscountCardAccount descacc=createDiscountcardAccount(transorder,product,commonproduct);
					accountId = descacc.getAccountId();
//					OilcardAccount descacc=(OilcardAccount) AccountFactory.getAccount(Account.ACCOUNT_OIL_CARD,transorder.getOrder().getMobileNum());
					Account sourceacc= AccountFactory.getAccount(Account.ACCOUNT_APP,transorder.getOrder().getMobileNum());
					ValidateUtil.assertNull(descacc, "折扣卡帐户");
					ValidateUtil.assertNull(sourceacc, "应用帐户");
					if(log.isDebugEnabled()){
						log.debug("检验通过，获取请求");
					}
					Receipt processOrder = orderSrv.processOfflineOpenDiscard(transorder,sourceacc,descacc,product);
					Map<String,Object> ordermap = new HashMap<String,Object>();
					
			        OfflineOpencardOrderVO order = transorder.getOrder();
					ordermap.put("remark",order.getRemark());
					ordermap.put("batchNo", order.getBatchNo());
					ordermap.put("operatorId", order.getOperatorId());
					ordermap.put("terminalOrderId", order.getTerminalOrderId());
					ordermap.put("terminalId", order.getTerminalId());
					ordermap.put("storeId", order.getStoreId());
					ordermap.put("sellerId", order.getSellerId());
					ordermap.put("mobileNum", order.getMobileNum());
					rm.put("order", ordermap);
					//返回折扣卡信息
					Map<String,Object> cardmap = new HashMap<String, Object>();
					DiscountCardAccount returnaccount = descacc;
					cardmap.put("accountId", returnaccount.getAccountId());
					cardmap.put("amount", returnaccount.getAmount());
					cardmap.put("startTime", DateUtil.formatCommonDateorNull(returnaccount.getStartTime()));
					cardmap.put("endTime", DateUtil.formatCommonDateorNull(returnaccount.getEndTime()));
		        	String status2 = returnaccount.getStatus();
					switch(status2){
					case OilcardAccount.STATUS_FRZ:cardmap.put("status","冻结");break;
					case OilcardAccount.STATUS_NOP:cardmap.put("status","未开通");break;
					case OilcardAccount.STATUS_OK:cardmap.put("status","正常");break;
					case OilcardAccount.STATUS_OFF:cardmap.put("status","注销");break;
					}
					DisCardResultVO discardResultVO = new DisCardResultVO(descacc, product);
					rm.put("card",cardmap );
					//发送开卡成功的短信通知
					Map smsparam = BeanUtils.describe(discardResultVO);
					smsparam.put("productName", product.getProductName());
					smsparam.put("amount", MoneyUtil.getMoneyStringDecimal2fen(returnaccount.getAmount()));
					String content = new PropertiesUtil().getProperty("sms.offline_opendiscountcard.success");
					content = StrUtil.replaceAll(content,smsparam);
					String mobileNum = transorder.getOrder().getMobileNum();
					// 调用webservice 发送模板
					if (log.isDebugEnabled()) {
						log.debug("发送手机验证码至"+mobileNum+"请求:" + content);
					}
					
					try {
						smsSender.send(mobileNum,content, transorder.getApp().getAppId(),SmsSendService.ACTION_NAME_OPENCARD);
//						SmsSenderUtil.sendSms(mobileNum, content);
						if (log.isDebugEnabled()) {
							log.debug("向用户发送新用户创建成功的短信通知完成");
						}
						
					} catch (Exception e) {
						log.error("向用户发送新用户创建成功的短信通知出错:", e);
						rm.setErr(baseerrcode, "向用户发送新用户创建成功的短信通知失败，其它原因");
					}
				}
				//容量卡业务类型
				else if(pro.getAccountType().equals("VCA")){
					VolumecardAccountProduct product = vcproDao.selectByPrimaryKey(cardType);
					if(product==null)
					{
						if (log.isDebugEnabled()) {
							log.debug(String.format("根据类型%s查询容量卡，结果为空",cardType));
						}
						throw ValidateException.ERROR_EXISTING_PRODUCT_NOT_EXISTS.clone(null, "找不到对应的产品");
					}
					if(!"OK#".equals(product.getStatus()))
					{
						if (log.isDebugEnabled()) {
							log.debug(String.format("容量卡%s已下线",cardType));
						}
						throw ValidateException.ERROR_PRODUCT_OFFLINE.clone(null, "容量卡产品已下线");
					}
					Product commonproduct= productDao.selectByPrimaryKey(product.getProductId());
					//modify by 3.6 如果是拆单，则返回报错
					Long paySum = transorder.getOrder().getPaySum();
					if(paySum!=null){
						if(paySum.longValue()!=commonproduct.getProductPrice()){
							if (log.isDebugEnabled()) {
								log.debug(String.format("开卡数据[%s]传递支付金额与产品金额%s不一致，可能进行拆单，系统返回失败。",transorder.getOrder(),commonproduct.getProductPrice()));
							}
							throw new MaAccountException(MaAccountException.ERR_PRODUCT_EXCEPTION,"支付金额无法匹配到产品价格");
						}
					}
					//创建容量卡
					
					VolumecardAccount descacc=createVolumecardAccount(transorder,product,commonproduct);
					accountId = descacc.getAccountId();
//					OilcardAccount descacc=(OilcardAccount) AccountFactory.getAccount(Account.ACCOUNT_OIL_CARD,transorder.getOrder().getMobileNum());
					Account sourceacc= AccountFactory.getAccount(Account.ACCOUNT_APP,transorder.getOrder().getMobileNum());
					ValidateUtil.assertNull(descacc, "折扣卡帐户");
					ValidateUtil.assertNull(sourceacc, "应用帐户");
					if(log.isDebugEnabled()){
						log.debug("检验通过，获取请求");
					}
					Receipt processOrder = orderSrv.processOfflineOpenVolcard(transorder,sourceacc,descacc,product);
					Map<String,Object> ordermap = new HashMap<String,Object>();
					
			        OfflineOpencardOrderVO order = transorder.getOrder();
					ordermap.put("remark",order.getRemark());
					ordermap.put("batchNo", order.getBatchNo());
					ordermap.put("operatorId", order.getOperatorId());
					ordermap.put("terminalOrderId", order.getTerminalOrderId());
					ordermap.put("terminalId", order.getTerminalId());
					ordermap.put("storeId", order.getStoreId());
					ordermap.put("sellerId", order.getSellerId());
					ordermap.put("mobileNum", order.getMobileNum());
					rm.put("order", ordermap);
					//返回容量卡信息
					Map<String,Object> cardmap = new HashMap<String, Object>();
					VolumecardAccount returnaccount = descacc;
					cardmap.put("accountId", returnaccount.getAccountId());
					cardmap.put("amount", returnaccount.getAmount());
					cardmap.put("startTime", DateUtil.formatCommonDateorNull(returnaccount.getStartTime()));
					cardmap.put("endTime", DateUtil.formatCommonDateorNull(returnaccount.getEndTime()));
		        	String status2 = returnaccount.getStatus();
					switch(status2){
					case OilcardAccount.STATUS_FRZ:cardmap.put("status","冻结");break;
					case OilcardAccount.STATUS_NOP:cardmap.put("status","未开通");break;
					case OilcardAccount.STATUS_OK:cardmap.put("status","正常");break;
					case OilcardAccount.STATUS_OFF:cardmap.put("status","注销");break;
					}
					//DisCardResultVO discardResultVO = new DisCardResultVO(descacc, product);
					VolumecardResultVO volumecardResultVO = new VolumecardResultVO(descacc);
					rm.put("card",cardmap );
					//发送开卡成功的短信通知
					Map smsparam = BeanUtils.describe(volumecardResultVO);
					smsparam.put("productName", product.getProductName());
					smsparam.put("amount",new DecimalFormat("0.00").format((double)(descacc.getAmount()/1000)));
					String content = new PropertiesUtil().getProperty("sms.offline_openvolumecard.success");
					content = StrUtil.replaceAll(content,smsparam);
					String mobileNum = transorder.getOrder().getMobileNum();
					// 调用webservice 发送模板
					if (log.isDebugEnabled()) {
						log.debug("发送手机验证码至"+mobileNum+"请求:" + content);
					}
					
					try {
						smsSender.send(mobileNum,content, transorder.getApp().getAppId(),SmsSendService.ACTION_NAME_REGISTER);
//						SmsSenderUtil.sendSms(mobileNum, content);
						if (log.isDebugEnabled()) {
							log.debug("向用户发送新用户创建成功的短信通知完成");
						}
						
					} catch (Exception e) {
						log.error("向用户发送新用户创建成功的短信通知出错:", e);
						rm.setErr(baseerrcode, "向用户发送新用户创建成功的短信通知失败，其它原因");
					}
				}
				
			}
			if(user==null){
				if (log.isDebugEnabled()) {
					log.debug(String.format("手机号码%s并没有注册，这里为其创建用户",transorder.getOrder().getMobileNum()));
				}
				user = new User();
				user.setInsertTime(new Date());
				String mobileNum = transorder.getOrder().getMobileNum();
				user.setMobilenum(mobileNum);
				user.setPassword(Md5Util.Encrypt(mobileNum.substring(mobileNum.length()-6)));
				//随机支付密码
				String paymentcode = StrUtil.genRandomCode(6);
				//user.setPaymentcodemd5(Md5Util.Encrypt(paymentcode));
				user.setPaymentCodeDES(Md5Util.Encrypt(paymentcode));				
				userSrv.createUser(user,transorder.getApp().getAppId());
				//下发通知信息
				String content = new PropertiesUtil().getProperty("sms.newuser");
				content = StrUtil.replaceAllWithToken(content, "paymentCode", paymentcode);
				// 调用webservice 发送模板
				if (log.isDebugEnabled()) {
					log.debug("发送手机验证码至"+mobileNum+"请求:" + content);
				}
				
				try {
					smsSender.send(mobileNum,content, transorder.getApp().getAppId(),SmsSendService.ACTION_NAME_REGISTER);
//					SmsSenderUtil.sendSms(mobileNum, content);
					if (log.isDebugEnabled()) {
						log.debug("向用户发送新用户创建成功的短信通知完成");
					}
					
				} catch (Exception e) {
					log.error("向用户发送新用户创建成功的短信通知出错:", e);
					rm.setErr(baseerrcode, "向用户发送新用户创建成功的短信通知失败，其它原因");
				}
				
			}
			
		} catch (Exception e1) {
			log.error(String.format(messagebase+"失败"),e1);
			rm.mergeException(e1);
			rm.setErrmsg(messagebase+"失败，"+rm.getErrmsg());
//			if(accountId!=null){
//				try {
//					accountIdSrv.returnAccountId(accountId);
//				} catch (MaAccountException e) {
//					log.error(String.format("帐号归还失败"),e);
//				}
//				
//			}
		}
		finally{
			//记录日志
			post(rm);
		}
		
		return rm;
		
	}
	
	
	/**
	 * @param transorder
	 * @param product 
	 * @param commonproduct 
	 * @return
	 * @throws DataInvalidException 
	 * @throws MaAccountException 
	 */
	private OilcardAccount createOilcardAccount(
			TransOrderVO2<OfflineOpencardOrderVO> transorder, OilcardAccountProduct product, Product commonproduct) throws DataInvalidException, MaAccountException {
		OfflineOpencardOrderVO order = transorder.getOrder();
		OilcardAccount account = new OilcardAccount();
		
		User user = userSrv.getUserByMobile(order.getMobileNum());
		if(user==null){
			if (log.isDebugEnabled()) {
				log.debug(String.format("用户[手机号%s]不存在",order.getMobileNum()));
			}
			throw ValidateException.ERROR_EXISTING_USER_NOT_EXISTS;
		}
		account = OilcardReturnUtil.openOilCard(user.getUserId(), "", product, commonproduct, order.getRemark(), false);
//		//开卡时，还要经过审核操作，在此之前不能使用，也不会进行分期操作。
////		account.setAccountId(NoGenerationUtil.genNO("oc_"));//卡号
//		String accountid = accountIdSrv.prepareGetAccountId(order.getProductId());
//		account.setAccountId(accountid);
//		account.setRemark(order.getRemark());
//		account.setUserId(user.getUserId());
//		account.setProductId(order.getProductId());
//		account.setRestAmount(commonproduct.getProductAmount());
//		account.setAmount(commonproduct.getProductAmount());
//		account.setRestStages(product.getTotalStages());
//		account.setBalance(0L);
//		account.setInsertTime(new Date());
//		account.setUpdateTime(new Date());
////		account.setChannelNo(order.getChannelNo()); //线下支付，无渠道
//		account.setChannelNo(""); //线下支付，无渠道
////		account.setStartTime(new Date());
//		//开始时间默认为第二天开通
//		Date startdate = new Date();
//		Date dayStart = DateUtil.toDayStart(startdate);
//		dayStart = DateUtils.addDays(dayStart, 1);
//		Date dayend = DateUtils.add(dayStart, "MON".equals(product.getExpiresType())?Calendar.MONTH:Calendar.DAY_OF_MONTH, product.getExpiresLength());
//		account.setStartTime(dayStart);
//		account.setEndTime(dayend);
//		account.setStatus(OilcardAccount.STATUS_NOP);
//		AccountValidateUtil.updateAccountSignature(account);
		return account;
	}
	
	/**
	 * 线下支付撤消，这个是人通过pos机操作
	 * @return
	 */
	@RequestMapping("/pay_cancel")
	@AccessRequered(methodName="线下交易撤销")
	public @ResponseBody Object payOffineCancel(HttpServletRequest request) {
//		{
//		    "app":,              
//		        {"appId":"zjhtwallet","timeStamp":"TIMESTAMP", "nonce":"NONCE","signature":"SIGNATURE"},  
//		    "order":
//		        {"mobileNum":"13912345678","userToken":"qwerqerewtuuuu123wer","sum":500000,"productName":"","remark":"从投资账户转账5000元到现金账户", "appOrderId":"AO201412122344888444"},
//		    "TSIG":
//		        {"orderMD5":"ORDERMD5","signature":"SIGNATURE", "timeStamp":"TIMESTAMP","nonce":"NONCE"}
//		} 
		TransOrderVO2<AntiPayoffline> transorder;
		ResultModel rm = new ResultModel();
		try {
			String jsonstr = RequestUtil.getRequestPostData(request);
			request.setAttribute("rawjson", jsonstr);
			transorder = RequestUtil.convertJson2Obj(jsonstr, TransOrderVO2.class,AntiPayoffline.class);
		} catch (Exception e) {
			log.error(String.format("获取订单参数出错"),e);
			rm.mergeException(ValidateException.ERROR_PARAM_FORMAT_ERROR.cloneAndAppend(null, "订单参数"));
			return rm;
		}
		int basecode=25500;
		String messagebase = "线下交易撤销";
		rm.setBaseErrorCode(basecode);
		rm.setErrmsg(messagebase+"成功");
		transorder.setOperationType(OrderConst.ORDER_OPERATION_CANCEL);
		ZJProduct zjProduct =null;
		Map ext = new HashMap();
		try {
			logWithBegin(transorder,request);
			transorder.selfvalidate();
			ValidateUtil.assertNull(transorder.getOrder().getOrderId(), "交易订单号");
			//获取url以作为method的内容
			String requestURI = request.getRequestURI();
			requestURI=requestURI.replace(request.getContextPath(), "");
			transorder.setMethod(requestURI);
			//检查appid是否在数据中存在
			ValidateResult validate = appService.validate(transorder.getApp());
			//验证order签名
			validateOrderSign(transorder);
			//验证transOrderVO签名
			validateTransOrderSign(transorder);
			//校验订单是否正常
			OrderValidateUtil.validateOrder(transorder);
			//检查订单是否已被撤销或已被冲正,orderid为pos机的ternimalOrderId
			CashAccountOrderMapper cashmapper = SpringBeanUtil.getInstance().getBean(CashAccountOrderMapper.class);
			CashAccountOrder posorder = cashmapper.selectPosOrder(transorder.getOrder().getSellerId(), transorder.getOrder().getTerminalId(), transorder.getOrder().getBatchNo(), transorder.getOrder().getOrderId());
			
			//线下交易，需要有手机号码，并进行校验
			String mobileNum = transorder.getOrder().getMobileNum();
			ValidateUtil.assertNull(mobileNum, "手机号码");
			User user = userSrv.getUserByMobile(mobileNum);
			if(user==null){
				if (log.isDebugEnabled()) {
					log.debug(String.format("根据手机号码%s查询不到用户",mobileNum));
				}
				throw new MaAccountException(MaAccountException.ERR_USER_EXCEPTION,"手机号码非消费号码");
			}
			String smscontent;
			//判断订单类型，现金账号
			if(posorder!=null){
				String orderId= posorder.getOrderId();
				transorder.getOrder().setOrderId(orderId);
				List<Order> relaOrder = orderSrv.getRelaOrder(OrderConst.ORDER_TABLE_CASH,orderId);
				if(relaOrder!=null&&!relaOrder.isEmpty())
				{
					if (log.isDebugEnabled()) {
						log.debug(String.format("线下交易订单[%s]已被冲正或撤销",orderId));
					}
					throw new MaAccountException(MaAccountException.ERR_ORDER_EXCEPTION, "订单已被冲正或撤销");
				}
				CashAccountOrder caOrder = posorder;
				CashAccount descacc=(CashAccount) AccountFactory.getAccountByAccountId(Account.ACCOUNT_CASH,caOrder.getAccountId());
				Account sourceacc= (Account) AccountFactory.getAccount(Account.ACCOUNT_APP,transorder.getOrder().getMobileNum());
				ValidateUtil.assertNull(sourceacc, "应用帐户");
				ValidateUtil.assertNull(descacc, "现金帐户");
				//撤销需要对比手机号
				ValidateUtil.assertNotEqual(user.getUserId(), descacc.getUserId(), "手机号码非消费号码", basecode+MaAccountException.ERR_ACCOUNT_EXCEPTION);
				AccountValidateUtil.validateAccount(descacc);
				if(log.isDebugEnabled()){
					log.debug("检验通过，获取请求");
				}
				OilcardAccountService oilSrv = SpringBeanUtil.getInstance().getBean(OilcardAccountService.class);
				Receipt processOrder = oilSrv.antiPayOffineByOilcard(transorder,sourceacc,descacc);
				CashAccountOrder exorder = (CashAccountOrder) processOrder.getExtValue("inorder");
				ext.put("productQuantity", exorder.getProductQuantity());
				ext.put("productPrice", exorder.getProductPrice());
				Map order = new HashMap();
				order.put("orderId", processOrder.getOrderNo());
				order.put("consumerId", exorder.getAccountId());
				order.put("sum", exorder.getSum());
				order.put("payTime",DateUtil.formatCommonDateorNull(exorder.getInsertTime()));
				order.put("sellerId", exorder.getSellerId());
				order.put("storeId", exorder.getStoreId());
				order.put("terminalId", exorder.getTerminalId());
				order.put("operatorId", exorder.getOperatorId());
				order.put("batchNo", exorder.getBatchNo());
				order.put("terminalOrderId", exorder.getTerminalOrderId());
				order.put("appOrderId", exorder.getAppOrderId());
				order.put("remark", exorder.getRemark());
				order.put("status", "撤销成功");
				//输出现金帐户消息
				Map accountmap = new HashMap();
				accountmap.put("accountId", descacc.getAccountId());
				accountmap.put("balance", descacc.getBalance());
				accountmap.put("status","正常");
				rm.put("account",accountmap);
				rm.put("order",order);
				//准备撤销的短信信息
				Map smsparam = new HashMap();
				smsparam.put("sum", MoneyUtil.getMoneyStringDecimal2fen((long)order.get("sum")));
				smsparam.put("unit", "元");
				zjProduct = zjproDao.selectByPrimaryKey(posorder.getProductName());
				smsparam.put("productName", zjProduct==null?"产品":zjProduct.getZjproductname());
				smsparam.put("payTime", DateUtil.format(exorder.getInsertTime(), "M月d日HH:mm"));
				smsparam.put("accountno",StringUtils.right(mobileNum, 4));
				smsparam.put("balance",new DecimalFormat("0.00").format((double)descacc.getBalance()/100));
				String offline_pay_cancle = new PropertiesUtil().getProperty("sms.offline_pay_cancel.success");
				smscontent=StrUtil.replaceAll(offline_pay_cancle,smsparam);
			}
			//判断订单类型，折扣卡
			else {
				DiscountCardAccountOrderMapper discountCardAccountOrderMapper = SpringBeanUtil.getInstance().getBean(DiscountCardAccountOrderMapper.class);
				DiscountCardAccountOrder disorder = discountCardAccountOrderMapper.selectPosOrder(transorder.getOrder().getSellerId(), transorder.getOrder().getTerminalId(), transorder.getOrder().getBatchNo(), transorder.getOrder().getOrderId());
				if(disorder!=null){
					String orderId= disorder.getOrderId();
					transorder.getOrder().setOrderId(orderId);
					List<Order> relaOrder = orderSrv.getRelaOrder(OrderConst.ORDER_TABLE_DIS_CARD,orderId);
					if(relaOrder!=null&&!relaOrder.isEmpty())
					{
						if (log.isDebugEnabled()) {
							log.debug(String.format("线下交易订单[%s]已被冲正或撤销",orderId));
						}
						throw new MaAccountException(MaAccountException.ERR_ORDER_EXCEPTION, "订单已被冲正或撤销");
					}
					DiscountCardAccountOrder caOrder = disorder;
					DiscountCardAccount descacc=(DiscountCardAccount) AccountFactory.getAccountByAccountId(Account.ACCOUNT_DISCOUNT_CARD,caOrder.getAccountId());
					Account sourceacc= (Account) AccountFactory.getAccount(Account.ACCOUNT_APP,transorder.getOrder().getMobileNum());
					ValidateUtil.assertNull(sourceacc, "应用帐户");
					ValidateUtil.assertNull(descacc, "折扣卡帐户");
					//撤销需要对比手机号
					ValidateUtil.assertNotEqual(user.getUserId(), descacc.getUserId(), "手机号码非消费号码", basecode+MaAccountException.ERR_ACCOUNT_EXCEPTION);
					AccountValidateUtil.validateAccount(descacc);
					if(log.isDebugEnabled()){
						log.debug("检验通过，获取请求");
					}
					DiscountCardAccountService disSrv = SpringBeanUtil.getInstance().getBean(DiscountCardAccountService.class);
					Receipt processOrder = disSrv.antiPayOffineByDiscard(transorder,sourceacc,descacc);
					DiscountCardAccountOrder exorder = (DiscountCardAccountOrder) processOrder.getExtValue("inorder");
					ext.put("productQuantity", exorder.getProductQuantity());
					ext.put("productPrice", exorder.getProductPrice());
					Map order = new HashMap();
					order.put("orderId", processOrder.getOrderNo());
					order.put("consumerId", exorder.getAccountId());
					order.put("sum", exorder.getSum());
					order.put("payTime",DateUtil.formatCommonDateorNull(exorder.getInsertTime()));
					order.put("sellerId", exorder.getSellerId());
					order.put("storeId", exorder.getStoreId());
					order.put("terminalId", exorder.getTerminalId());
					order.put("operatorId", exorder.getOperatorId());
					order.put("batchNo", exorder.getBatchNo());
					order.put("terminalOrderId", exorder.getTerminalOrderId());
					order.put("appOrderId", exorder.getAppOrderId());
					order.put("remark", exorder.getRemark());
					order.put("status", "撤销成功");
					//输出现金帐户消息
					Map accountmap = new HashMap();
					accountmap.put("accountId", descacc.getAccountId());
					accountmap.put("balance", descacc.getBalance());
					accountmap.put("status","正常");
					rm.put("account",accountmap);
					rm.put("order",order);
					//准备撤销的短信信息
					Map smsparam = new HashMap();
					smsparam.put("sum", MoneyUtil.getMoneyStringDecimal2fen((long)order.get("sum")));
					smsparam.put("unit", "元");
					zjProduct = zjproDao.selectByPrimaryKey(disorder.getProductName());
					smsparam.put("productName", zjProduct==null?"产品":zjProduct.getZjproductname());
					smsparam.put("payTime", DateUtil.format(exorder.getInsertTime(), "M月d日HH:mm"));
					smsparam.put("accountno",StringUtils.right(mobileNum, 4));
					smsparam.put("balance",new DecimalFormat("0.00").format((double)descacc.getBalance()/100));
					String offline_pay_cancle = new PropertiesUtil().getProperty("sms.offline_pay_cancel.success");
					smscontent=StrUtil.replaceAll(offline_pay_cancle,smsparam);
				}
				else{
					//容量卡
					VolumecardAccountOrderMapper volumecardAccountOrderMapper = SpringBeanUtil.getInstance().getBean(VolumecardAccountOrderMapper.class);
					VolumecardAccountOrder volumeOrder = volumecardAccountOrderMapper.selectPosOrder(transorder.getOrder().getSellerId(), transorder.getOrder().getTerminalId(), transorder.getOrder().getBatchNo(), transorder.getOrder().getOrderId());
					
					if(volumeOrder != null) {
						String orderId = volumeOrder.getOrderId();
						transorder.getOrder().setOrderId(orderId);
						List<Order> relaOrder = orderSrv.getRelaOrder(OrderConst.ORDER_TABLE_VOLUME_CARD, orderId);
						if (relaOrder != null && !relaOrder.isEmpty()) {
							if (log.isDebugEnabled()) {
								log.debug(String.format("线下交易订单[%s]已被冲正或撤销", orderId));
							}
							throw new MaAccountException(MaAccountException.ERR_ORDER_EXCEPTION, "订单已被冲正或撤销");
						}
						VolumecardAccount vaccount=(VolumecardAccount) AccountFactory.getAccountByAccountId(Account.ACCOUNT_VOLUME_CARD,volumeOrder.getAccountId());
						Account sourceacc= (Account) AccountFactory.getAccount(Account.ACCOUNT_APP,transorder.getOrder().getMobileNum());
						ValidateUtil.assertNull(sourceacc, "应用帐户");
						ValidateUtil.assertNull(vaccount, "容量卡帐户");
						//撤销需要对比手机号
						ValidateUtil.assertNotEqual(user.getUserId(), vaccount.getUserId(), "手机号码非消费号码", basecode+MaAccountException.ERR_ACCOUNT_EXCEPTION);
						AccountValidateUtil.validateAccount(vaccount);
						if(log.isDebugEnabled()){
							log.debug("检验通过，获取请求");
						}
						VolumecardAccountService vaSrv = SpringBeanUtil.getInstance().getBean(VolumecardAccountService.class);
						Receipt processOrder = vaSrv.antiPayOffine(transorder,sourceacc,vaccount);
						VolumecardAccountOrder exorder = (VolumecardAccountOrder) processOrder.getExtValue("inorder");
						ext.put("productQuantity", exorder.getProductQuantity());
						ext.put("productPrice", exorder.getProductPrice());
						Map order = new HashMap();
						order.put("orderId", processOrder.getOrderNo());
						order.put("consumerId", exorder.getAccountId());
						order.put("sum", exorder.getSum());
						order.put("payTime",DateUtil.formatCommonDateorNull(exorder.getInsertTime()));
						order.put("sellerId", exorder.getSellerId());
						order.put("storeId", exorder.getStoreId());
						order.put("terminalId", exorder.getTerminalId());
						order.put("operatorId", exorder.getOperatorId());
						order.put("batchNo", exorder.getBatchNo());
						order.put("terminalOrderId", exorder.getTerminalOrderId());
						order.put("appOrderId", exorder.getAppOrderId());
						order.put("remark", exorder.getRemark());
						order.put("status", "撤销成功");
						//输出现金帐户消息
						Map accountmap = new HashMap();
						accountmap.put("accountId", vaccount.getAccountId());
						accountmap.put("balance", vaccount.getBalance());
						accountmap.put("status","正常");
						rm.put("account",accountmap);
						rm.put("order",order);
						//准备撤销的短信信息
						Map smsparam = new HashMap();
						smsparam.put("unit", "升");
						zjProduct = zjproDao.selectByPrimaryKey(volumeOrder.getProductName());
						smsparam.put("productName", zjProduct==null?"产品":zjProduct.getZjproductname());
						smsparam.put("payTime", DateUtil.format(exorder.getInsertTime(), "M月d日HH:mm"));
						smsparam.put("accountno",StringUtils.right(mobileNum, 4));
						smsparam.put("balance",vaccount.getBalance());
						smsparam.put("sum", new DecimalFormat("0.00").format((double)((long)order.get("sum")/1000)));
						String offline_pay_cancle = new PropertiesUtil().getProperty("sms.offline_pay_byvolumecard_cancle.success");
						smscontent=StrUtil.replaceAll(offline_pay_cancle,smsparam);
						
					}
					else{
						throw new MaAccountException(MaAccountException.ERR_ORDER_EXCEPTION,"未能找到支付订单");
					}
			}}
			sendbysms(transorder.getOrder().getMobileNum(), smscontent,transorder.getApp().getAppId(), "支付撤销");
			//添加消费冲正通知
			OfflinePayCancelEvent oce = new OfflinePayCancelEvent(transorder,zjProduct,rm,user.getMobilenum(),ext);
			EventListenerContainer.getInstance().fireEvent(oce);
			
		} catch (Exception e1) {
			log.error(String.format(messagebase+"失败"),e1);
			rm.mergeException(e1);
			rm.setErrmsg(messagebase+"失败，"+rm.getErrmsg());
		}
		finally{
			//记录日志
			post(rm);
		}
		return rm;
	}
	
	/**
	 * 线下支付冲正，这个是系统对账处理的
	 * @return
	 */
	@RequestMapping("/pay_undo")
	@AccessRequered(methodName="线下交易冲正")
	public @ResponseBody Object payOffineUndo(HttpServletRequest request) {
//		{
//		    "app":
//		        {"appId":"zjhtwallet","timeStamp":"TIMESTAMP", "nonce":"NONCE","signature":"SIGNATURE"},  
//		    "order":
//		        {"mobileNum":"13912345678","userToken":"qwerqerewtuuuu123wer","sum":500000,"productName":"","remark":"从投资账户转账5000元到现金账户", "appOrderId":"AO201412122344888444"},
//		    "TSIG":
//		        {"orderMD5":"ORDERMD5","signature":"SIGNATURE", "timeStamp":"TIMESTAMP","nonce":"NONCE"}
//		} 
		TransOrderVO2<AntiPayoffline> transorder;
		ResultModel rm = new ResultModel();
		try {
			String jsonstr = RequestUtil.getRequestPostData(request);
			request.setAttribute("rawjson", jsonstr);
			transorder = RequestUtil.convertJson2Obj(jsonstr, TransOrderVO2.class,AntiPayoffline.class);
		} catch (Exception e) {
			log.error(String.format("获取订单参数出错"),e);
			rm.mergeException(ValidateException.ERROR_PARAM_FORMAT_ERROR.cloneAndAppend(null, "订单参数"));
			return rm;
		}
		String messagebase = "线下交易冲正";
		rm.setBaseErrorCode(26300);
		rm.setErrmsg(messagebase+"成功");
		transorder.setOperationType(OrderConst.ORDER_OPERATION_CZH);
		ZJProduct zjProduct =null;
		Map ext=new HashMap();
		try {
			logWithBegin(transorder,request);
			transorder.selfvalidate();
			//获取url以作为method的内容
			String requestURI = request.getRequestURI();
			requestURI=requestURI.replace(request.getContextPath(), "");
			transorder.setMethod(requestURI);
			//检查appid是否在数据中存在
			ValidateResult validate = appService.validate(transorder.getApp());
			//验证order签名
			validateOrderSign(transorder);
			//验证transOrderVO签名
			validateTransOrderSign(transorder);
			//校验订单是否正常
			OrderValidateUtil.validateOrder(transorder);
			//检查订单是否已被撤销或已被冲正
			CashAccountOrderMapper cashmapper = SpringBeanUtil.getInstance().getBean(CashAccountOrderMapper.class);
			CashAccountOrder posorder = cashmapper.selectPosOrder(transorder.getOrder().getSellerId(), transorder.getOrder().getTerminalId(), transorder.getOrder().getBatchNo(), transorder.getOrder().getTerminalOrderId());
			
			String smscontent;
			//判断订单类型，现金账号
			User user;
			if(posorder!=null){
				String orderId= posorder.getOrderId();
				transorder.getOrder().setOrderId(orderId);
				List<Order> relaOrder = orderSrv.getRelaOrder(OrderConst.ORDER_TABLE_CASH,orderId);
				if(relaOrder!=null&&!relaOrder.isEmpty())
				{
					if (log.isDebugEnabled()) {
						log.debug(String.format("线下交易订单[%s]已被冲正或撤销",orderId));
					}
					throw new MaAccountException(MaAccountException.ERR_ORDER_EXCEPTION, "订单已被冲正或撤销");
				}
				CashAccountOrder caOrder = posorder;
				CashAccount descacc=(CashAccount) AccountFactory.getAccountByAccountId(Account.ACCOUNT_CASH,caOrder.getAccountId());
				Account sourceacc= (Account) AccountFactory.getAccount(Account.ACCOUNT_APP,transorder.getOrder().getMobileNum());
				ValidateUtil.assertNull(sourceacc, "应用帐户");
				ValidateUtil.assertNull(descacc, "现金帐户");
				AccountValidateUtil.validateAccount(descacc);
				
				if(log.isDebugEnabled()){
					log.debug("检验通过，获取请求");
				}
				OilcardAccountService oilSrv = SpringBeanUtil.getInstance().getBean(OilcardAccountService.class);
				Receipt processOrder = oilSrv.antiPayOffineByOilcard(transorder,sourceacc,descacc);
				CashAccountOrder exorder = (CashAccountOrder) processOrder.getExtValue("inorder");
				ext.put("productQuantity", exorder.getProductQuantity());
				ext.put("productPrice", exorder.getProductPrice());
				Map order = new HashMap();
				order.put("orderId", processOrder.getOrderNo());
				order.put("consumerId", exorder.getAccountId());
				order.put("sum", exorder.getSum());
				order.put("payTime",DateUtil.formatCommonDateorNull(exorder.getInsertTime()));
				order.put("sellerId", exorder.getSellerId());
				order.put("storeId", exorder.getStoreId());
				order.put("terminalId", exorder.getTerminalId());
				order.put("operatorId", exorder.getOperatorId());
				order.put("batchNo", exorder.getBatchNo());
				order.put("terminalOrderId", exorder.getTerminalOrderId());
				order.put("appOrderId", exorder.getAppOrderId());
				order.put("remark", exorder.getRemark());
				order.put("status", "冲正成功");
				//输出现金帐户消息
				Map accountmap = new HashMap();
				accountmap.put("accountId", descacc.getAccountId());
				accountmap.put("balance", descacc.getBalance());
				accountmap.put("status","正常");
				rm.put("account",accountmap);
				rm.put("order",order);
				//准备撤销的短信信息
				Map smsparam = new HashMap();
				smsparam.put("sum", MoneyUtil.getMoneyStringDecimal2fen((long)order.get("sum")));
				smsparam.put("unit", "元");
				zjProduct = zjproDao.selectByPrimaryKey(exorder.getProductName());
				smsparam.put("productName", zjProduct==null?"产品":zjProduct.getZjproductname());
				smsparam.put("payTime", DateUtil.format(exorder.getInsertTime(), "M月d日HH:mm"));
				user = userdao.selectByPrimaryKey(descacc.getUserId());
				smsparam.put("accountno",StringUtils.right(user.getMobilenum(), 4));
				smsparam.put("balance",new DecimalFormat("0.00").format((double)descacc.getBalance()/100));
				String offline_pay_cancle = new PropertiesUtil().getProperty("sms.offline_pay_undo.success");
				smscontent=StrUtil.replaceAll(offline_pay_cancle,smsparam);
			}
			//折扣卡冲正
			else {
				DiscountCardAccountOrderMapper discountCardAccountOrderMapper = SpringBeanUtil.getInstance().getBean(DiscountCardAccountOrderMapper.class);
				DiscountCardAccountOrder disorder = discountCardAccountOrderMapper.selectPosOrder(transorder.getOrder().getSellerId(), transorder.getOrder().getTerminalId(), transorder.getOrder().getBatchNo(), transorder.getOrder().getTerminalOrderId());
				if(disorder!=null){
					String orderId= disorder.getOrderId();
					transorder.getOrder().setOrderId(orderId);
					List<Order> relaOrder = orderSrv.getRelaOrder(OrderConst.ORDER_TABLE_DIS_CARD,orderId);
					if(relaOrder!=null&&!relaOrder.isEmpty())
					{
						if (log.isDebugEnabled()) {
							log.debug(String.format("线下交易订单[%s]已被冲正或撤销",orderId));
						}
						throw new MaAccountException(MaAccountException.ERR_ORDER_EXCEPTION, "订单已被冲正或撤销");
					}
					DiscountCardAccountOrder caOrder = disorder;
					DiscountCardAccount descacc=(DiscountCardAccount) AccountFactory.getAccountByAccountId(Account.ACCOUNT_DISCOUNT_CARD,caOrder.getAccountId());
					Account sourceacc= (Account) AccountFactory.getAccount(Account.ACCOUNT_APP,transorder.getOrder().getMobileNum());
					ValidateUtil.assertNull(sourceacc, "应用帐户");
					ValidateUtil.assertNull(descacc, "折扣卡帐户");
					AccountValidateUtil.validateAccount(descacc);
					if(log.isDebugEnabled()){
						log.debug("检验通过，获取请求");
					}
					DiscountCardAccountService disSrv = SpringBeanUtil.getInstance().getBean(DiscountCardAccountService.class);
					Receipt processOrder = disSrv.antiPayOffineByDiscard(transorder,sourceacc,descacc);
					DiscountCardAccountOrder exorder = (DiscountCardAccountOrder) processOrder.getExtValue("inorder");
					ext.put("productQuantity", exorder.getProductQuantity());
					ext.put("productPrice", exorder.getProductPrice());
					Map order = new HashMap();
					order.put("orderId", processOrder.getOrderNo());
					order.put("consumerId", exorder.getAccountId());
					order.put("sum", exorder.getSum());
					order.put("payTime",DateUtil.formatCommonDateorNull(exorder.getInsertTime()));
					order.put("sellerId", exorder.getSellerId());
					order.put("storeId", exorder.getStoreId());
					order.put("terminalId", exorder.getTerminalId());
					order.put("operatorId", exorder.getOperatorId());
					order.put("batchNo", exorder.getBatchNo());
					order.put("terminalOrderId", exorder.getTerminalOrderId());
					order.put("appOrderId", exorder.getAppOrderId());
					order.put("remark", exorder.getRemark());
					order.put("status", "冲正成功");
					//输出现金帐户消息
					Map accountmap = new HashMap();
					accountmap.put("accountId", descacc.getAccountId());
					accountmap.put("balance", descacc.getBalance());
					accountmap.put("status","正常");
					rm.put("account",accountmap);
					rm.put("order",order);
					//准备撤销的短信信息
					Map smsparam = new HashMap();
					smsparam.put("sum", MoneyUtil.getMoneyStringDecimal2fen((long)order.get("sum")));
					smsparam.put("unit", "元");
					zjProduct = zjproDao.selectByPrimaryKey(exorder.getProductName());
					smsparam.put("productName", zjProduct==null?"产品":zjProduct.getZjproductname());
					smsparam.put("payTime", DateUtil.format(exorder.getInsertTime(), "M月d日HH:mm"));
					user = userdao.selectByPrimaryKey(descacc.getUserId());
					smsparam.put("accountno",StringUtils.right(user.getMobilenum(), 4));
					smsparam.put("balance",new DecimalFormat("0.00").format((double)descacc.getBalance()/100));
					String offline_pay_cancle = new PropertiesUtil().getProperty("sms.offline_pay_undo.success");
					smscontent=StrUtil.replaceAll(offline_pay_cancle,smsparam);
				}
				else{
					//容量卡冲正
					VolumecardAccountOrderMapper volumecardAccountOrderMapper = SpringBeanUtil.getInstance().getBean(VolumecardAccountOrderMapper.class);
					VolumecardAccountOrder volumeOrder = volumecardAccountOrderMapper.selectPosOrder(transorder.getOrder().getSellerId(), transorder.getOrder().getTerminalId(), transorder.getOrder().getBatchNo(), transorder.getOrder().getTerminalOrderId());
					if(volumeOrder!=null){
						String orderId= volumeOrder.getOrderId();
						transorder.getOrder().setOrderId(orderId);
						List<Order> relaOrder = orderSrv.getRelaOrder(OrderConst.ORDER_TABLE_VOLUME_CARD,orderId);
						if(relaOrder!=null&&!relaOrder.isEmpty())
						{
							if (log.isDebugEnabled()) {
								log.debug(String.format("线下交易订单[%s]已被冲正或撤销",orderId));
							}
							throw new MaAccountException(MaAccountException.ERR_ORDER_EXCEPTION, "订单已被冲正或撤销");
						}
						VolumecardAccount vaccount=(VolumecardAccount) AccountFactory.getAccountByAccountId(Account.ACCOUNT_VOLUME_CARD,volumeOrder.getAccountId());
						Account sourceacc= (Account) AccountFactory.getAccount(Account.ACCOUNT_APP,transorder.getOrder().getMobileNum());
						ValidateUtil.assertNull(sourceacc, "应用帐户");
						ValidateUtil.assertNull(vaccount, "容量卡帐户");
						AccountValidateUtil.validateAccount(vaccount);
						if(log.isDebugEnabled()){
							log.debug("检验通过，获取请求");
						}
						VolumecardAccountService vaSrv = SpringBeanUtil.getInstance().getBean(VolumecardAccountService.class);
						Receipt processOrder = vaSrv.antiPayOffine(transorder,sourceacc,vaccount);
						VolumecardAccountOrder exorder = (VolumecardAccountOrder) processOrder.getExtValue("inorder");
						ext.put("productQuantity", exorder.getProductQuantity());
						ext.put("productPrice", exorder.getProductPrice());
						Map order = new HashMap();
						order.put("orderId", processOrder.getOrderNo());
						order.put("consumerId", exorder.getAccountId());
						order.put("sum", exorder.getSum());
						order.put("payTime",DateUtil.formatCommonDateorNull(exorder.getInsertTime()));
						order.put("sellerId", exorder.getSellerId());
						order.put("storeId", exorder.getStoreId());
						order.put("terminalId", exorder.getTerminalId());
						order.put("operatorId", exorder.getOperatorId());
						order.put("batchNo", exorder.getBatchNo());
						order.put("terminalOrderId", exorder.getTerminalOrderId());
						order.put("appOrderId", exorder.getAppOrderId());
						order.put("remark", exorder.getRemark());
						order.put("status", "冲正成功");
						//输出现金帐户消息
						Map accountmap = new HashMap();
						accountmap.put("accountId", vaccount.getAccountId());
						accountmap.put("balance", vaccount.getBalance());
						accountmap.put("status","正常");
						rm.put("account",accountmap);
						rm.put("order",order);
						//准备撤销的短信信息
						Map smsparam = new HashMap();
						smsparam.put("sum", MoneyUtil.getMoneyStringDecimal2fen((long)order.get("sum")));
						smsparam.put("unit", "升");
						zjProduct = zjproDao.selectByPrimaryKey(exorder.getProductName());
						smsparam.put("productName", zjProduct==null?"产品":zjProduct.getZjproductname());
						smsparam.put("payTime", DateUtil.format(exorder.getInsertTime(), "M月d日HH:mm"));
						user = userdao.selectByPrimaryKey(vaccount.getUserId());
						smsparam.put("accountno",StringUtils.right(user.getMobilenum(), 4));
						smsparam.put("balance",new DecimalFormat("0.00").format((double)vaccount.getBalance()/1000));
						String offline_pay_cancle = new PropertiesUtil().getProperty("sms.offline_pay_undo.success");
						smscontent=StrUtil.replaceAll(offline_pay_cancle,smsparam);

					}
					else{
						throw new MaAccountException(MaAccountException.ERR_ORDER_EXCEPTION,"未能找到支付订单");
					}
				}
			}
			sendbysms(transorder.getOrder().getMobileNum(), smscontent,transorder.getApp().getAppId(), "支付冲正");
			//添加消费冲正通知
			OfflinePayUndoEvent oce = new OfflinePayUndoEvent(transorder,zjProduct,rm,user.getMobilenum(),ext);
			EventListenerContainer.getInstance().fireEvent(oce);
		} catch (Exception e1) {
			log.error(String.format(messagebase+"失败"),e1);
			rm.mergeException(e1);
			rm.setErrmsg(messagebase+"失败，"+rm.getErrmsg());
		}
		finally{
			//记录日志
			post(rm);
		}
		return rm;
	}
	
	/**
	 * 线下支付查询帐户余额
	 * @return
	 */
	@RequestMapping("/get_account_info")
	@AccessRequered(methodName="线下支付查询帐户余额")
	public @ResponseBody Object queryAccountInfo4Oilcard(HttpServletRequest request) {
//		{
//		    "app":
//		        {"appId":"zjhtwallet","timeStamp":"TIMESTAMP", "nonce":"NONCE","signature":"SIGNATURE"},  
//		    "order":
//		        {"mobileNum":"13912345678","userToken":"qwerqerewtuuuu123wer","sum":500000,"productName":"","remark":"从投资账户转账5000元到现金账户", "appOrderId":"AO201412122344888444"},
//		    "TSIG":
//		        {"orderMD5":"ORDERMD5","signature":"SIGNATURE", "timeStamp":"TIMESTAMP","nonce":"NONCE"}
//		} 
		
		OilcardQueryVO transorder;
		ResultModel rm = new ResultModel();
		try {
			String jsonstr = RequestUtil.getRequestPostData(request);
			request.setAttribute("rawjson", jsonstr);
			transorder = RequestUtil.convertJson2Obj(jsonstr,OilcardQueryVO.class);
		} catch (Exception e) {
			log.error(String.format("获取订单参数出错"),e);
			rm.mergeException(ValidateException.ERROR_PARAM_FORMAT_ERROR.cloneAndAppend(null, "订单参数"));
			return rm;
		}
		String messagebase = "查询帐户余额";
		rm.setBaseErrorCode(25700);
		rm.setErrmsg(messagebase+"成功");
		AppLog al= new AppLog();;
		try {
			al.setAppid(transorder.getApp().getAppId());
			String requestURI = request.getRequestURI();
			requestURI=requestURI.replace(request.getContextPath(), "");
			al.setMethod(requestURI);
			al.setInserttime(new Date());
			String requestjson = ObjectUtils.toString(request.getAttribute("rawjson"));
			if(StringUtils.isBlank(requestjson))
			{
				requestjson = new JSONObject(transorder).toString();
			}
			al.setRequest(requestjson);
		
			logdao.insert(al);
			//检查appid是否在数据中存在
			ValidateResult validate = appService.validate(transorder.getApp());
			//验证order签名
//			validateOrderSign(transorder);
//			//验证transOrderVO签名
//			validateTransOrderSign(transorder);
			//查询当前用户的现金帐户余额
			Consumer consumer = transorder.getQuery().getConsumer();
			ValidateUtil.assertNull(consumer, "消费号码");
			User user;
			
			AccountUserVO au = validateFromConsumer(consumer, transorder.getQuery().getPaymentCodeDES());
			Account fromConsumer = au.getAccount();
			user = au.getUser();
			List cardlist = new ArrayList();
			long sum = 0 ;
			if(consumer.getConsumerType().equals(Consumer.CONSUMER_TYPE_MOBILE)){
				//加载分期卡
//				sum+= oaDao.statAccountBalance(consumer.getConsumerId());
//				List<OilcardAccount> accountByMobile = oaDao.statAccountBalance(consumer.getConsumerId());
//				for (Iterator iterator = accountByMobile.iterator(); iterator
//						.hasNext();) {
//					OilcardAccount oilcardAccount = (OilcardAccount) iterator
//							.next();
//					cardlist.add(convertCardResult(oilcardAccount));
//					sum+=oilcardAccount.getBalance();
//				}
				//加载折扣卡
//				sum+= daDao.statAccountBalance(consumer.getConsumerId());
//				List<DiscountCardAccount> accountByMobile2 = daDao.getAccountsByMobile(consumer.getConsumerId());
//				for (Iterator iterator = accountByMobile2.iterator(); iterator
//						.hasNext();) {
//					DiscountCardAccount dca = (DiscountCardAccount) iterator
//							.next();
//					cardlist.add(convertCardResult(dca));
//					sum+=dca.getBalance();
//				}
				//加载容量卡
//				List<VolumecardAccount> accountsByMobile = vaDao.getAccountsByMobile(consumer.getConsumerId());
//				for (Iterator iterator = accountsByMobile.iterator(); iterator
//						.hasNext();) {
//					VolumecardAccount vca = (VolumecardAccount) iterator
//							.next();
//					cardlist.add(convertCardResult(vca));
//					sum+=vca.getBalance();
//				}
				//现金帐户
				Account descacc=(Account) AccountFactory.getAccountByConsumer(consumer);
				ValidateUtil.assertNull(descacc, "帐户");
				Long balance = descacc.getBalance();
				sum+=balance;
			}
			else{
				Account descacc=(Account) AccountFactory.getAccountByConsumer(consumer);
				ValidateUtil.assertNull(descacc, "帐户");
				Long balance = descacc.getBalance();
				cardlist.add(convertCardResult(descacc));
				sum+=balance;
			}
			if(log.isDebugEnabled()){
				log.debug("检验通过，获取请求");
			}
			//输出现金帐户消息
//			rm.put("card",cardlist);
			Map accountmap = new HashMap();
			accountmap.put("accountId",consumer.getConsumerId());
			accountmap.put("balance",sum);
			accountmap.put("remark",String.format("电子卡号：%s\n剩余金额：%s元\n",consumer.getConsumerId(),MoneyUtil.getMoneyStringDecimal2fen(sum)));
			accountmap.put("status","正常");
			rm.put("card", accountmap);
			
		} catch (Exception e1) {
			log.error(String.format(messagebase+"失败"),e1);
			rm.mergeException(e1);
			rm.setErrmsg(messagebase+"失败，"+rm.getErrmsg());
		}
		finally{
			//记录日志
			al.setRespone(new JSONObject(rm).toString());
			logdao.updateByPrimaryKey(al);
		}
		return rm;
	}
	/**
	 * @param oilcardAccount
	 * @return 
	 */
	public Map convertCardResult(Account acc) {
		Map accountmap = new HashMap();
		String status2=null;
		if (accountmap instanceof OilcardAccount) {
			OilcardAccount oilcardAccount = (OilcardAccount) accountmap;
			accountmap.put("accountId", oilcardAccount.getAccountId());
			accountmap.put("balance", oilcardAccount.getBalance());
			accountmap.put("remark",String.format("电子卡号：%s\n剩余金额：%s元\n",oilcardAccount.getAccountId(),MoneyUtil.getMoneyStringDecimal2fen(oilcardAccount.getBalance())));
			status2 = oilcardAccount.getStatus();
		}
		else if (accountmap instanceof DiscountCardAccount) {
			DiscountCardAccount daaccount = (DiscountCardAccount) accountmap;
			accountmap.put("accountId", daaccount.getAccountId());
			accountmap.put("balance", daaccount.getBalance());
			accountmap.put("remark",String.format("电子卡号：%s\n剩余金额：%s元\n",daaccount.getAccountId(),MoneyUtil.getMoneyStringDecimal2fen(daaccount.getBalance())));
			status2 = daaccount.getStatus();
		}
		else if (accountmap instanceof VolumecardAccount) {
			VolumecardAccount vcaccount = (VolumecardAccount) accountmap;
			accountmap.put("accountId", vcaccount.getAccountId());
			accountmap.put("balance", vcaccount.getBalance());
			accountmap.put("remark",String.format("电子卡号：%s\n剩余金额：%s升\n",vcaccount.getAccountId(),MoneyUtil.getMoneyStringDecimal2fen(vcaccount.getBalance()/10)));
			status2 = vcaccount.getStatus();
		}
		else if(accountmap instanceof CashAccount){
			CashAccount caccount = (CashAccount) accountmap;
			accountmap.put("accountId", caccount.getAccountId());
			accountmap.put("balance", caccount.getBalance());
			accountmap.put("remark",String.format("电子卡号：%s\n剩余金额：%s元\n",caccount.getAccountId(),MoneyUtil.getMoneyStringDecimal2fen(caccount.getBalance())));
			status2 = caccount.getStatus();
			
		}
		
		String status="未知状态";
		if(StringUtils.isNotBlank(status2)){
			switch(status2){
			case OilcardAccount.STATUS_FRZ:status="冻结";break;
			case OilcardAccount.STATUS_NOP:status="未开通";break;
			case OilcardAccount.STATUS_OK:status="正常";break;
			case OilcardAccount.STATUS_OFF:status="注销";break;
			}
			
		}
		accountmap.put("status",status);
		return accountmap;
	}
	/**
	 * 线下产品下载接口
	 * @return
	 */
	@RequestMapping("/download_product")
	@AccessRequered(methodName="线下产品下载接口")
	public @ResponseBody Object productDownload(HttpServletRequest request) {
//		{
//		    "app":
//		        {"appId":"zjhtwallet","timeStamp":"TIMESTAMP", "nonce":"NONCE","signature":"SIGNATURE"},  
//		    "order":
//		        {"mobileNum":"13912345678","userToken":"qwerqerewtuuuu123wer","sum":500000,"productName":"","remark":"从投资账户转账5000元到现金账户", "appOrderId":"AO201412122344888444"},
//		    "TSIG":
//		        {"orderMD5":"ORDERMD5","signature":"SIGNATURE", "timeStamp":"TIMESTAMP","nonce":"NONCE"}
//		} 
		
		ProductDownloadVO transorder;
		ResultModel rm = new ResultModel();
		try {
			String jsonstr = RequestUtil.getRequestPostData(request);
			request.setAttribute("rawjson", jsonstr);
			transorder = RequestUtil.convertJson2Obj(jsonstr,ProductDownloadVO.class);
		} catch (Exception e) {
			log.error(String.format("获取线下产品下载参数出错"),e);
			rm.mergeException(ValidateException.ERROR_PARAM_FORMAT_ERROR.cloneAndAppend(null, "线下产品下载参数"));
			return rm;
		}
		String messagebase = "线下产品下载";
		rm.setBaseErrorCode(25800);
		rm.setErrmsg(messagebase+"成功");
		AppLog al= new AppLog();;
		try {
			al.setAppid(transorder.getApp().getAppId());
			String requestURI = request.getRequestURI();
			requestURI=requestURI.replace(request.getContextPath(), "");
			al.setMethod(requestURI);
			al.setInserttime(new Date());
			String requestjson = ObjectUtils.toString(request.getAttribute("rawjson"));
			if(StringUtils.isBlank(requestjson))
			{
				requestjson = new JSONObject(transorder).toString();
			}
			al.setRequest(requestjson);
			
			logdao.insert(al);
			//检查appid是否在数据中存在
			ValidateResult validate = appService.validate(transorder.getApp());
//			//验证此终端是否在终端列表中
//			ProductTerminalList productTerminal=new ProductTerminalList();
//			ProductTerminalList productTerminal2=new ProductTerminalList();
//			TerminalList terminal=terminalListDao.selectByPosVO(transorder.getDownload());
//			if(terminal==null){
//				if (log.isDebugEnabled()) {
//					log.debug(String.format("无法从数据库中获取此终端"));
//				}
//				throw ValidateException.ERROR_EXISTING_SELLER_NOT_EXISTS;
//			}
			//查询所有产品信息
			List<Product> selectUseableProducts = productDao.selectUseableProducts();
			List<ProductVO> vos = new ArrayList<ProductVO>();
			for (Iterator iterator = selectUseableProducts.iterator(); iterator
					.hasNext();) {
				Product product = (Product) iterator.next();
				//去掉产品限制
//				productTerminal=productTerminalListDao.selectByProduct(product.getProductId(), transorder.getDownload().getTerminalId());
//				productTerminal2=productTerminalListDao.selectByProduct(product.getProductId(), "ALL_TERMINAL_CAN");
//
//				if(productTerminal!=null||productTerminal2!=null){
					ProductVO provo = new ProductVO(product);
					vos.add(provo);
//				}
			}
//			if(vos==null){
//				if (log.isDebugEnabled()) {
//					log.debug(String.format("该端口没有可下载的产品"));
//				}
//				throw ValidateException.ERROR_EXISTING_PRODUCT_NOT_EXISTS;
//			}
			rm.put("product",vos);
			
		} catch (Exception e1) {
			log.error(String.format(messagebase+"失败"),e1);
			rm.mergeException(e1);
			rm.setErrmsg(messagebase+"失败，"+rm.getErrmsg());
		}
		finally{
			//记录日志
			al.setRespone(new JSONObject(rm).toString());
			logdao.updateByPrimaryKey(al);
		}
		return rm;
	}
	
	
	
	/* (non-Javadoc)
	 * @see com.hummingbird.maaccount.controller.AccountBaseController#getAccountType()
	 */
	@Override
	protected String getAccountType() {
		//不处理
		return null;
	}
	
	/**
	 * @param transorder
	 * @param product 
	 * @param commonproduct 
	 * @return
	 * @throws DataInvalidException 
	 * @throws MaAccountException 
	 */
	private DiscountCardAccount createDiscountcardAccount(
			TransOrderVO2<OfflineOpencardOrderVO> transorder, DiscountCardProduct product, Product commonproduct) throws DataInvalidException, MaAccountException {
		OfflineOpencardOrderVO order = transorder.getOrder();
		DiscountCardAccount account = new DiscountCardAccount();
		
		User user = userSrv.getUserByMobile(order.getMobileNum());
		if(user==null){
			if (log.isDebugEnabled()) {
				log.debug(String.format("用户[手机号%s]不存在",order.getMobileNum()));
			}
			throw ValidateException.ERROR_EXISTING_USER_NOT_EXISTS;
		}
		//开卡时，还要经过审核操作，在此之前不能使用，也不会进行分期操作。
//		account.setAccountId(NoGenerationUtil.genNO("oc_"));//卡号
		String accountid = accountIdSrv.prepareGetAccountId(order.getProductId());
		account.setAccountId(accountid);
		account.setRemark(order.getRemark());
		account.setUserId(user.getUserId());
		account.setProductId(order.getProductId());
		account.setRestAmount(commonproduct.getProductAmount());
		account.setAmount(commonproduct.getProductAmount().longValue());
		account.setRestStages(0);
		account.setBalance(0L);
		account.setInsertTime(new Date());
		account.setUpdateTime(new Date());
//		account.setChannelNo(order.getChannelNo()); //线下支付，无渠道
		account.setChannelNo(""); //线下支付，无渠道
//		account.setStartTime(new Date());
		//开始时间默认为第二天开通
		Date startdate = new Date();
		Date dayStart = DateUtil.toDayStart(startdate);
		dayStart = DateUtils.addDays(dayStart, 1);
		Date dayend = DateUtils.add(dayStart, "MON".equals(product.getExpiresType())?Calendar.MONTH:Calendar.DAY_OF_MONTH, product.getExpiresLength());
		account.setStartTime(dayStart);
		account.setEndTime(dayend);
		account.setStatus(OilcardAccount.STATUS_NOP);
		AccountValidateUtil.updateAccountSignature(account);
		return account;
	}
	
	/**
	 * @param transorder
	 * @param product 
	 * @param commonproduct 
	 * @return
	 * @throws DataInvalidException 
	 * @throws MaAccountException 
	 */
	private VolumecardAccount createVolumecardAccount(
			TransOrderVO2<OfflineOpencardOrderVO> transorder, VolumecardAccountProduct product, Product commonproduct) throws DataInvalidException, MaAccountException {
		OfflineOpencardOrderVO order = transorder.getOrder();
		VolumecardAccount account = new VolumecardAccount();
		
		User user = userSrv.getUserByMobile(order.getMobileNum());
		if(user==null){
			if (log.isDebugEnabled()) {
				log.debug(String.format("用户[手机号%s]不存在",order.getMobileNum()));
			}
			throw ValidateException.ERROR_EXISTING_USER_NOT_EXISTS;
		}
		//开卡时，还要经过审核操作，在此之前不能使用，也不会进行分期操作。
//		account.setAccountId(NoGenerationUtil.genNO("oc_"));//卡号
		String accountid = accountIdSrv.prepareGetAccountId(order.getProductId());
		account.setAccountId(accountid);
		account.setRemark(order.getRemark());
		account.setUserId(user.getUserId());
		account.setProductId(order.getProductId());
		account.setAmount(commonproduct.getProductAmount());
		account.setBalance(0L);
		account.setInsertTime(new Date());
		account.setUpdateTime(new Date());
		account.setChannelNo(""); //线下支付，无渠道
//		account.setStartTime(new Date());
		//开始时间默认为第二天开通
		Date startdate = new Date();
		Date dayStart = DateUtil.toDayStart(startdate);
		dayStart = DateUtils.addDays(dayStart, 1);
		Date dayend = DateUtils.add(dayStart, "MON".equals(product.getExpiresType())?Calendar.MONTH:Calendar.DAY_OF_MONTH, product.getExpiresLength());
		account.setStartTime(dayStart);
		account.setEndTime(dayend);
		account.setStatus(VolumecardAccount.STATUS_NOP);
		AccountValidateUtil.updateAccountSignature(account);
		return account;
	}
	
	/**
	 * 根据卡的类型和用户id来获取同一个用户同一种可用卡的列表,卡的状态为OK#
	 * @return
	 * @throws MaAccountException 
	 */
	private List<BaseConsumerVO> RecordConsumer(String consumerType,Integer userId,String terminalId,Long productQuantity,Long sum) throws MaAccountException{
		List<BaseConsumerVO> Baseconsumer=new ArrayList<BaseConsumerVO>();
		if(Consumer.CONSUMER_TYPE_CASHACCOUNT.equals(consumerType)){
			CashAccount ca=new CashAccount();
			ca=caDao.selectByUserId(userId,sum);
			if(ca==null){
				throw new MaAccountException(MaAccountException.ERR_ACCOUNT_EXCEPTION,"现金帐户不存在或余额不足");
			}
			
			BaseConsumerVO ba=new BaseConsumerVO(ca);
			Baseconsumer.add(ba);
			
		}
		if(Consumer.CONSUMER_TYPE_INVESTMENTACCOUNT.equals(consumerType)){
			InvestmentAccount ia=new InvestmentAccount();
			ia=iaDao.selectByUserId(userId,sum);
			if(ia==null){
				throw new MaAccountException(MaAccountException.ERR_ACCOUNT_EXCEPTION,"投资帐户不存在或可用余额不足");
			}
			BaseConsumerVO ba=new BaseConsumerVO(ia);
			Baseconsumer.add(ba);
			
		}
		if(Consumer.CONSUMER_TYPE_OILCARD.equals(consumerType)){
			List<OilcardAccount> ocas=new ArrayList<OilcardAccount>();
			ocas=oaDao.getAccountByUserId4list(userId,terminalId);
			for(OilcardAccount oca:ocas){
				
				
					BaseConsumerVO ba=new BaseConsumerVO(oca);
					Baseconsumer.add(ba);
					
			}
			
		}
		if(Consumer.CONSUMER_TYPE_DISCOUNTCARD.equals(consumerType)){
			List<DiscountCardAccount> dcas=new ArrayList<DiscountCardAccount>();
			dcas=daDao.getAccountByUserId4list(userId,terminalId,sum);
			for(DiscountCardAccount dca:dcas){
				BaseConsumerVO ba=new BaseConsumerVO(dca);
				Baseconsumer.add(ba);
			}
			
		}
		if(Consumer.CONSUMER_TYPE_VOLUMECARD.equals(consumerType)){
			List<VolumecardAccount> vcas=new ArrayList<VolumecardAccount>();
			vcas=vaDao.getAccountByUserId4list(userId,terminalId,productQuantity);
			for(VolumecardAccount vca:vcas){
				
						BaseConsumerVO ba=new BaseConsumerVO(vca);
						Baseconsumer.add(ba);
					
			}
			
		}
		return Baseconsumer;
	}
	
	/**
	 * 发送短信
	 * @param mobileNum
	 * @param content
	 */
	public void sendbysms(String mobileNum,	String content,String appId,String action) {
		// 调用webservice 发送模板
		if (log.isTraceEnabled()) {
			log.trace("发送手机验证码至"+mobileNum+"请求:" + content);
		}
		
		try {
			smsSender.send(mobileNum,content, appId, action);
//			SmsSenderUtil.sendSms(mobileNum, content);
			if (log.isDebugEnabled()) {
				log.debug(action+"完成");
			}
		} catch (Exception e) {
			log.error(action+"出错", e);
		}
	}
	
	/**
	 * 校验终端是否能消费
	 * @param productId
	 * @param terminalId
	 * @return
	 * @throws MaAccountException 
	 */
	private void validateTerminal(String productId,String terminalId) throws MaAccountException{
		int count = productTerminalListDao.selectPayableProduct(productId, terminalId);
		if(count!=1){
			if (log.isDebugEnabled()) {
				log.debug(String.format("产品[编号%s]不可在终端%s使用",productId,terminalId));
			}
			throw new MaAccountException(MaAccountException.ERR_POS_EXCEPTION,"该账号不可在此终端使用");
		}
	}
	
}
