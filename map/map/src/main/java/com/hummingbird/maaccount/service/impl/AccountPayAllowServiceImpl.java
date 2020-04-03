package com.hummingbird.maaccount.service.impl;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hummingbird.common.exception.DataInvalidException;
import com.hummingbird.common.exception.ValidateException;
import com.hummingbird.common.face.Pagingnation;
import com.hummingbird.common.util.DateUtil;
import com.hummingbird.common.util.ValidateUtil;
import com.hummingbird.common.util.matcher.IntegerMatcher;
import com.hummingbird.common.util.matcher.IntegerRangeMatcher;
import com.hummingbird.common.util.matcher.TimeRangeMatcher;
import com.hummingbird.maaccount.entity.AccountPayAllow;
import com.hummingbird.maaccount.entity.AccountPayProduct;
import com.hummingbird.maaccount.entity.AccountPayTerminal;
import com.hummingbird.maaccount.entity.AccountPayTime;
import com.hummingbird.maaccount.entity.TerminalList;
import com.hummingbird.maaccount.entity.ZJProduct;
import com.hummingbird.maaccount.mapper.AccountPayAllowMapper;
import com.hummingbird.maaccount.mapper.AccountPayProductMapper;
import com.hummingbird.maaccount.mapper.AccountPayTerminalMapper;
import com.hummingbird.maaccount.mapper.AccountPayTimeMapper;
import com.hummingbird.maaccount.mapper.TerminalListMapper;
import com.hummingbird.maaccount.mapper.UserMapper;
import com.hummingbird.maaccount.mapper.ZJProductMapper;
import com.hummingbird.maaccount.service.AccountPayAllowService;
import com.hummingbird.maaccount.vo.CardSelecter;
@Service
public class AccountPayAllowServiceImpl implements AccountPayAllowService {
	org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory.getLog(this.getClass());
	@Autowired
	private UserMapper userDao;
	@Autowired
	private AccountPayAllowMapper  accountPayAllowListDao;
	
	@Autowired
	protected AccountPayProductMapper accountPayProductDao;//油品
	@Autowired
	protected ZJProductMapper zjProductDao;//油品
	@Autowired
	protected AccountPayTerminalMapper accountPayTerminalDao;//终端
	@Autowired
	protected AccountPayTimeMapper accountPayTimeDao;//时间
	@Autowired
	protected TerminalListMapper tlDao;//终端列表



	@Override
	public List<AccountPayAllow> selectAllowListByAccountId(String accountId, Pagingnation page) {
		
		if(page!=null&&page.isCountsize()){
			int totalcount = accountPayAllowListDao.selectTotalByAccountId(accountId);
			page.setTotalCount(totalcount);
			page.calculatePageCount();
		}
		List<AccountPayAllow> aps = accountPayAllowListDao.selectByAccountId(accountId, page); 
		
		return aps;

	}


	/**
	 * 添加加油卡允许支付的产品
	 * @param accountId
	 * @param groupId
	 * @param zjproducts
	 * @throws DataInvalidException 
	 */
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class,value="txManager")
	public void addPayProducts(String accountId, String groupId, String[] zjproducts) throws DataInvalidException{
		ValidateUtil.assertEmpty(accountId, "帐户");
		ValidateUtil.assertEmpty(groupId, "组别");
		ValidateUtil.assertNull(zjproducts, "产品");
		if(zjproducts.length==0){
			throw ValidateException.ERROR_PARAM_NULL.clone(null,"没有产品要添加");
		}
		
		List<AccountPayProduct> exists = accountPayProductDao.selectProduct(accountId,groupId,zjproducts);
		if(zjproducts!=null && zjproducts.length>0){
			for(String zip : zjproducts){
				ZJProduct product = zjProductDao.selectByPrimaryKey(zip);
				if(product==null){
					throw ValidateException.ERROR_PARAM_NULL.clone(null,"产品"+zip+"不存在"); 
				}
				boolean noneMatch = exists.stream().noneMatch(new Predicate<AccountPayProduct>() {

					@Override
					public boolean test(AccountPayProduct t) {
						
						return t.getZjproductId().equals(zip);
					}
				});
				if(noneMatch){
					
					AccountPayProduct acp = new AccountPayProduct();
					acp.setAccountId(accountId);
					acp.setGroupId(groupId);
					acp.setZjproductId(zip);
					accountPayProductDao.insert(acp);
				}
			}
		}
	}
	
	/**
	 * 添加加油卡允许支付的门店
	 * @param accountId
	 * @param groupId
	 * @param terminalIds
	 * @param description
	 * @throws DataInvalidException 
	 */
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class,value="txManager")
	public void addPayStores(String accountId, String groupId, String[] storeIds, String description) throws DataInvalidException
	{
		ValidateUtil.assertEmpty(accountId, "帐户");
		ValidateUtil.assertEmpty(groupId, "组别");
		ValidateUtil.assertNull(storeIds, "门店");
		List<String> terminalIds = new ArrayList<>();
		StringBuilder notexiststoreid = new StringBuilder();
		String spliter = "";
		//通过门店查找终端
		for (int i = 0; i < storeIds.length; i++) {
			String storeId = storeIds[i];
			List<TerminalList> tllist = tlDao.selectByStoreId(storeId);
			//增加判断门店是否存在,因为终端是主键,所以查询出来的肯定会有
			if(CollectionUtils.isEmpty(tllist)){
				notexiststoreid.append(spliter);
				notexiststoreid.append(storeId);
				spliter=",";
				continue;
			}
			for (Iterator iterator = tllist.iterator(); iterator.hasNext();) {
				TerminalList terminalList = (TerminalList) iterator.next();
				terminalIds.add(terminalList.getTerminalId());
			}
		}
		if(notexiststoreid.length()>0){
			throw ValidateException.ERROR_PARAM_NULL.clone(null,String.format("门店[%s]不存在",notexiststoreid.toString()));
		}
		addPayTerminals(accountId,groupId,terminalIds.toArray(new String[]{}),description);
	}
	/**
	 * 添加加油卡允许支付的终端
	 * @param accountId
	 * @param groupId
	 * @param terminalIds
	 * @param description
	 * @throws DataInvalidException 
	 */
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class,value="txManager")
	public void addPayTerminals(String accountId, String groupId, String[] terminalIds, String description) throws DataInvalidException{
		ValidateUtil.assertEmpty(accountId, "帐户");
		ValidateUtil.assertEmpty(groupId, "组别");
		ValidateUtil.assertNull(terminalIds, "终端");
		if(terminalIds.length==0){
			throw ValidateException.ERROR_PARAM_NULL.clone(null,"没有终端要添加");
		}
		if(terminalIds!=null && terminalIds.length>0){
			//先查询出重复的
			List<AccountPayTerminal> exists =  accountPayTerminalDao.selectTerminalIds(accountId,groupId,terminalIds);
			for(String tid : terminalIds){
				AccountPayTerminal ats = new AccountPayTerminal();
				ats.setTerminalId(tid);
				ats.setAccountId(accountId);
				ats.setGroupId(groupId);
				if(!exists.contains(ats)){
					accountPayTerminalDao.insert(ats);
				}
			}
			
		}


	}
	
	/**
	 * 添加加油卡允许支付的时间
	 * @param accountId
	 * @param groupId
	 * @param periodTime
	 * @param cycleType
	 * @param cycleDate
	 * @param description
	 * @throws DataInvalidException 
	 */
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class,value="txManager")
	public void addPayTimes(String accountId, String groupId, String[] periodTime, String cycleType, String cycleDate,
			String description) throws DataInvalidException{
		
		ValidateUtil.assertEmpty(accountId, "帐户");
		ValidateUtil.assertEmpty(groupId, "组别");
		ValidateUtil.assertEmpty(cycleType, "组别");
		ValidateUtil.assertNull(periodTime, "消费时间");
		if(periodTime.length==0){
			throw ValidateException.ERROR_PARAM_NULL.clone(null,"消费起止时间为空");
		}
		
		List<AccountPayTime> pts =  prepareAccountPayTime(cycleType,cycleDate,periodTime);
		//判读支付时间是否已经有设置
		List<AccountPayTime> exists =  accountPayTimeDao.selectAllTime(accountId,groupId);
		for (Iterator iterator = pts.iterator(); iterator.hasNext();) {
			AccountPayTime apt = (AccountPayTime) iterator.next();
			apt.setAccountId(accountId);
			apt.setGroupId(groupId);
			boolean noadd = false;
			for (Iterator iterator2 = exists.iterator(); iterator2.hasNext();) {
				AccountPayTime accountPayTime = (AccountPayTime) iterator2.next();
				if(accountPayTime.include(apt)){
					noadd = true;
					break;
				}
			}
			if(!noadd){
				accountPayTimeDao.insert(apt);
			}
		}
		
		
		
	}
	
	/**
	 * 根据时间的设置生成支付时间列表
	 * @param type
	 * @param cycleDate
	 * @param periodTimeArr
	 * @return
	 * @throws DataInvalidException
	 */
	private List<AccountPayTime> prepareAccountPayTime(String type,String cycleDate ,String[] periodTimeArr) throws  DataInvalidException{
		List<AccountPayTime> apts = new ArrayList<>();
		List<TimeRangeMatcher> timerangelist = new ArrayList<TimeRangeMatcher>();
		for (int i = 0; i < periodTimeArr.length; i++) {
			String periodTime = periodTimeArr[i];
			TimeRangeMatcher im = new TimeRangeMatcher();
			im.initPattern(periodTime);
			timerangelist.add(im);
		}
		if(StringUtils.isBlank(cycleDate)){
			if(!StringUtils.equals("DAY", type)){
				if (log.isDebugEnabled()) {
					log.debug(String.format("循环时间设置有问题，非按天循环，且循环日期为空"));
				}
				throw ValidateException.ERROR_PARAM_NULL.clone(null,"消费时间格式不对");
			}
		}
		IntegerMatcher cyclematch = new IntegerMatcher();
		switch (type) {
		case "WEK":
			//周循环
			IntegerRangeMatcher monthir = new IntegerRangeMatcher();
			monthir.initPattern("0-31");
			List<IntegerRangeMatcher> monlist = new ArrayList<>();
			monlist.add(monthir);
			cyclematch.initPattern(cycleDate);
			apts = prepareAccountPayTime(monlist,cyclematch.getMatchers(),timerangelist);
			break;
			
		case "MON":
			//月循环
			IntegerRangeMatcher weekir = new IntegerRangeMatcher();
			weekir.initPattern("0-7");
			List<IntegerRangeMatcher> weeklist = new ArrayList<>();
			weeklist.add(weekir);
			cyclematch.initPattern(cycleDate);
			apts=prepareAccountPayTime(cyclematch.getMatchers(),weeklist,timerangelist);
			break;
			
		case "DAY":
			//日循环
			IntegerRangeMatcher monthir4day = new IntegerRangeMatcher();
			monthir4day.initPattern("0-31");
			List<IntegerRangeMatcher> monthir4daylist = new ArrayList<>();
			monthir4daylist.add(monthir4day);
			IntegerRangeMatcher weekir4day = new IntegerRangeMatcher();
			weekir4day.initPattern("0-7");
			List<IntegerRangeMatcher> weekir4daylist = new ArrayList<>();
			weekir4daylist.add(weekir4day);
			apts=prepareAccountPayTime(monthir4daylist,weekir4daylist,timerangelist);

		default:
			break;
		}
		
		return apts;
		
	}


	/**
	 * 生成支付时间
	 * @param monthir4daylist
	 * @param weekir4daylist
	 * @param timerangelist
	 * @return 
	 * @throws DataInvalidException 
	 */
	private List<AccountPayTime> prepareAccountPayTime(List<IntegerRangeMatcher> monthir4daylist,
			List<IntegerRangeMatcher> weekir4daylist, List<TimeRangeMatcher> timerangelist) throws DataInvalidException {
		
		List<AccountPayTime> list = new ArrayList<>();
		for (Iterator iterator = monthir4daylist.iterator(); iterator.hasNext();) {
			IntegerRangeMatcher month = (IntegerRangeMatcher) iterator.next();
			if(month.getEndInt()>31||month.getStartInt()<0){
				if (log.isDebugEnabled()) {
					log.debug(String.format("月日期无效[最小值%s,最大值%s]",month.getStartInt(),month.getEndInt()));
				}
				throw ValidateException.ERROR_PARAM_FORMAT_ERROR.clone(null, String.format("月日期无效[最小值%s,最大值%s]",month.getStartInt(),month.getEndInt()));
			}
			for (Iterator iterator2 = weekir4daylist.iterator(); iterator2.hasNext();) {
				IntegerRangeMatcher week = (IntegerRangeMatcher) iterator2.next();
				if(week.getEndInt()>7||week.getStartInt()<0){
					
					if (log.isDebugEnabled()) {
						log.debug(String.format("周日期无效[最小值%s,最大值%s]",week.getStartInt(),week.getEndInt()));
					}
					throw ValidateException.ERROR_PARAM_FORMAT_ERROR.clone(null,String.format("周日期无效[最小值%s,最大值%s]",week.getStartInt(),week.getEndInt()));
				}

				for (Iterator iterator3 = timerangelist.iterator(); iterator3.hasNext();) {
					TimeRangeMatcher time = (TimeRangeMatcher) iterator3.next();
//					if(time.getEndInt()>24||time.getStartInt()<0){
//						
//						if (log.isDebugEnabled()) {
//							log.debug(String.format("小时日期无效[最小值%s,最大值%s]",time.getStartInt(),time.getEndInt()));
//						}
//						throw ValidateException.ERROR_PARAM_FORMAT_ERROR.clone(null,String.format("小时日期无效[最小值%s,最大值%s]",time.getStartInt(),time.getEndInt()));
//					}
					AccountPayTime apt = new AccountPayTime();
					apt.setBegintime((DateUtil.format(time.getStartTime(),"HHmm")));
					apt.setEndtime((DateUtil.format(time.getEndTime(),"HHmm")));
					apt.setMonend(month.getEndInt());
					apt.setMonstart(month.getStartInt());
					apt.setWeekend(week.getEndInt());
					apt.setWeekstart(week.getStartInt());
					if(apt.getBegintime().compareTo(apt.getEndtime())>0){
						if(apt.getEndtime().equals("0000")){
							apt.setEndtime("2400");
							list.add(apt);
						}
						else{
							apt.setEndtime("2400");
							list.add(apt);
							AccountPayTime apt1 = new AccountPayTime();
							apt1.setBegintime("0000");
							apt1.setEndtime((DateUtil.format(time.getEndTime(),"HHmm")));
							apt1.setMonend(month.getEndInt());
							apt1.setMonstart(month.getStartInt());
							apt1.setWeekend(week.getEndInt());
							apt1.setWeekstart(week.getStartInt());
							list.add(apt1);
						}
					}
					else{
						if(apt.getBegintime().compareTo(apt.getEndtime())==0&&apt.getEndtime().equals("0000")){
							apt.setEndtime("2400");
						}
						list.add(apt);
					}
				}
			}
		}
		return list;
		
	}
	
	/**
	 * 查询可以支付的账户
	 * @param cs
	 * @return 账户列表
	 */
	public List<String> getAccountCanPay(CardSelecter cs){
		List<String> accountids = accountPayAllowListDao.selectCanPayAccount(cs);
		return accountids;
	}
}


