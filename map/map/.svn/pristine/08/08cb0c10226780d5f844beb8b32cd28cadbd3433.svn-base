package com.hummingbird.maaccount.service.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hummingbird.common.exception.DataInvalidException;
import com.hummingbird.common.util.ValidateUtil;
import com.hummingbird.maaccount.constant.AccountConst;
import com.hummingbird.maaccount.entity.CashAccount;
import com.hummingbird.maaccount.entity.DiscountCardAccount;
import com.hummingbird.maaccount.entity.InvestmentAccount;
import com.hummingbird.maaccount.entity.OilcardAccount;
import com.hummingbird.maaccount.entity.OilcardAccountProduct;
import com.hummingbird.maaccount.entity.Product;
import com.hummingbird.maaccount.entity.VolumecardAccount;
import com.hummingbird.maaccount.exception.MaAccountException;
import com.hummingbird.maaccount.face.Account;
import com.hummingbird.maaccount.face.Pagingnation;
import com.hummingbird.maaccount.mapper.CashAccountMapper;
import com.hummingbird.maaccount.mapper.DiscountCardAccountMapper;
import com.hummingbird.maaccount.mapper.InvestmentAccountMapper;
import com.hummingbird.maaccount.mapper.OilcardAccountMapper;
import com.hummingbird.maaccount.mapper.OilcardAccountProductMapper;
import com.hummingbird.maaccount.mapper.ProductMapper;
import com.hummingbird.maaccount.mapper.VolumecardAccountMapper;
import com.hummingbird.maaccount.service.QueryUserCardService;
import com.hummingbird.maaccount.util.AccountFactory;
import com.hummingbird.maaccount.vo.QueryUserCardBodyVO;
import com.hummingbird.maaccount.vo.QueryUserCardListBaseResultVO;
import com.hummingbird.maaccount.vo.QueryUserCardListDetailVO;
import com.hummingbird.maaccount.vo.QueryUserCardListOilResultVO;
import com.hummingbird.maaccount.vo.QueryUserCardListResultVO;

@Service
public class QueryUserCardServiceImpl implements QueryUserCardService{
	org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory
			.getLog(this.getClass());
	
	@Autowired
	DiscountCardAccountMapper discountCardAccountDao;
	@Autowired
	VolumecardAccountMapper volumecardAccountDao;
	@Autowired
	OilcardAccountMapper oilcardAccountDao;
	@Autowired
	CashAccountMapper cashAccountDao;
	@Autowired
	InvestmentAccountMapper investAccountDao;
	@Autowired
	ProductMapper productDao;
	@Autowired
	OilcardAccountProductMapper oilproductDao;
	@Override
	public List<QueryUserCardListResultVO> queryUserCard (
			QueryUserCardListDetailVO query,Pagingnation pagingnation) throws MaAccountException{
//		List<QueryUserCardListBaseResultVO> queryResult=new ArrayList<QueryUserCardListBaseResultVO>();
		List<QueryUserCardListResultVO> result=new ArrayList<QueryUserCardListResultVO>();
		if(StringUtils.isBlank(query.getChannelNo())||!query.getQueryCardSource()){
			query.setChannelNo(null);
		}
		
			for(String type:query.getTypes()){
				if(type.equals("DCA")){
					try {
						List<QueryUserCardListResultVO> dcaquery=discountCardAccountDao.getUserAccounts(query.getMobileNum(),query.getSearchStart(),query.getSearchEnd(),query.getStatus(),query.getChannelNo());
						for(QueryUserCardListBaseResultVO quer:dcaquery){
							result.add(new QueryUserCardListResultVO(quer));
						}
					} catch (ParseException e) {
						if(log.isErrorEnabled()){
							log.error("日期转换失败！");
						}throw new MaAccountException(MaAccountException.ERRCODE_CONVERSION,"日期转换失败");
					}
				}else if(type.equals("VCA")){
					try {
						List<QueryUserCardListResultVO> vcaquery=volumecardAccountDao.getUserAccounts(query.getMobileNum(),query.getSearchStart(),query.getSearchEnd(),query.getStatus(),query.getChannelNo());
						for(QueryUserCardListBaseResultVO quer:vcaquery){
							result.add(new QueryUserCardListResultVO(quer));
						}
					} catch (ParseException e) {
						if(log.isErrorEnabled()){
							log.error("日期转换失败！");
						}throw new MaAccountException(MaAccountException.ERRCODE_CONVERSION,"日期转换失败");
					}
				}else if(type.equals("OCA")){
					try {
						List<QueryUserCardListOilResultVO> oilquery=oilcardAccountDao.getUserAccounts(query.getMobileNum(),query.getSearchStart(),query.getSearchEnd(),query.getStatus(),query.getChannelNo());
						for(QueryUserCardListOilResultVO quer:oilquery){
							result.add(new QueryUserCardListResultVO(quer));
						}
					} catch (ParseException e) {
						if(log.isErrorEnabled()){
							log.error("日期转换失败！");
						}throw new MaAccountException(MaAccountException.ERRCODE_CONVERSION,"日期转换失败");
					}
				}
			}
		
		
		pagingnation.setTotalCount(result.size());
		return result;
	}

	/**
	 * 查询我的卡详情
	 * @param query
	 * @return
	 * @throws DataInvalidException 
	 */
	public QueryUserCardListResultVO queryUserCardDetail(QueryUserCardBodyVO query) throws DataInvalidException{
		String productId = StringUtils.substring(query.getAccountId(),0,4);
		Product product = productDao.selectByPrimaryKey(productId);
		ValidateUtil.assertNullnoappend(product, "产品["+productId+"]不存在");
		QueryUserCardListResultVO result = new QueryUserCardListResultVO();
		String accountType = product.getAccountType();
		switch (accountType) {
		case AccountConst.ACCOUNT_TYPE_CASH:
			CashAccount ca = (CashAccount) cashAccountDao.getAccountByAccountId(query.getAccountId());
			ValidateUtil.assertNullnoappend(ca, "帐户不存在");
			result.setAccountId(ca.getAccountId());
			result.setAmount(ca.getBalance());
			result.setStatus(ca.getStatus());
			break;
		case AccountConst.ACCOUNT_TYPE_OILCARD:
			OilcardAccount acc = (OilcardAccount) oilcardAccountDao.getAccountByAccountId(query.getAccountId());
			ValidateUtil.assertNullnoappend(acc, "帐户不存在");
			result.setAccountId(acc.getAccountId());
			result.setAmount(acc.getBalance());
			result.setStartTime(acc.getStartTime());
			result.setEndTime(acc.getEndTime());
			result.setStatus(acc.getStatus());
			StringBuilder sb = new StringBuilder();
			OilcardAccountProduct oilproduct = oilproductDao.selectByPrimaryKey(productId);
			result.setDescription(sb.append("一共有").append(oilproduct.getTotalStages()).append("期，剩余").append(acc.getRestStages()).append("期待返还，剩余共").append(acc.getRestAmount()).append("元。").toString());
			break;
		case AccountConst.ACCOUNT_TYPE_DISCOUNTCARD:
			DiscountCardAccount dca = (DiscountCardAccount) discountCardAccountDao.getAccountByAccountId(query.getAccountId());
			ValidateUtil.assertNullnoappend(dca, "帐户不存在");
			result.setAccountId(dca.getAccountId());
			result.setAmount(dca.getBalance());
			result.setStartTime(dca.getStartTime());
			result.setEndTime(dca.getEndTime());
			result.setStatus(dca.getStatus());
//			StringBuilder sb = new StringBuilder();
//			description=sb.append("一共有").append(acc.getTotalStages()).append("期，剩余").append(query.getRestStages()).append("期待返还，剩余共").append(query.getRestAmount()).append("元。").toString();
			break;
		case AccountConst.ACCOUNT_TYPE_VOLUMECARD:
			VolumecardAccount vca = (VolumecardAccount) volumecardAccountDao.getAccountByAccountId(query.getAccountId());
			ValidateUtil.assertNullnoappend(vca, "帐户不存在");
			result.setAccountId(vca.getAccountId());
			result.setAmount(vca.getBalance());
			result.setStartTime(vca.getStartTime());
			result.setEndTime(vca.getEndTime());
			result.setStatus(vca.getStatus());
//			StringBuilder sb = new StringBuilder();
//			description=sb.append("一共有").append(acc.getTotalStages()).append("期，剩余").append(query.getRestStages()).append("期待返还，剩余共").append(query.getRestAmount()).append("元。").toString();
			break;
		case AccountConst.ACCOUNT_TYPE_INVESTMENT:
			InvestmentAccount ia = (InvestmentAccount) investAccountDao.getAccountByAccountId(query.getAccountId());
			ValidateUtil.assertNullnoappend(ia, "帐户不存在");
			result.setAccountId(ia.getAccountId());
			result.setAmount(ia.getBalance());
			result.setStatus(ia.getStatus());
			break;
		default:
			break;
		}
		
		return result;
	}
	
	
}
