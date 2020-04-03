package com.hummingbird.maaccount.service;



import com.hummingbird.common.exception.DataInvalidException;
import com.hummingbird.common.vo.ResultModel;
import com.hummingbird.maaccount.entity.AppInfo;
import com.hummingbird.maaccount.entity.VolumecardAccountOrder;
import com.hummingbird.maaccount.entity.VolumecardAccountProduct;
import com.hummingbird.maaccount.entity.VolumecardProduct;
import com.hummingbird.maaccount.exception.MaAccountException;
import com.hummingbird.maaccount.entity.VolumecardAccount;
import com.hummingbird.maaccount.face.Account;
import com.hummingbird.maaccount.face.Receipt;
import com.hummingbird.maaccount.vo.AntiPayoffline;
import com.hummingbird.maaccount.vo.BatchOpenOnlineDetailVO;
import com.hummingbird.maaccount.vo.BatchOpenOnlineListVO;
import com.hummingbird.maaccount.vo.TransOrderVO2;

public interface VolumecardAccountService extends AccountService{
	/**
	 * 根据第三方应用id查询开卡情况
	 * @param appId
	 * @param appOrderId
	 * @return 
	 * @throws MaAccountException 
	 */
	public VolumecardAccountOrder queryVolumecardAccountByapporderId(String appId, String appOrderId) throws MaAccountException;
	/**
	 * 冲正/撤销 线下支付
	 * @param transorder
	 * @param sourceacc
	 * @param vaccount
	 * @return
	 * @throws MaAccountException 
	 */
	public Receipt antiPayOffine(
			TransOrderVO2<AntiPayoffline> transorder, Account sourceacc,
			VolumecardAccount vaccount)throws MaAccountException;

	/**
	 * 批量线上开卡
	 * @author liudou
	 * @param body
	 * @param app
	 * @return
	 */
	public ResultModel createUserBatch(TransOrderVO2<BatchOpenOnlineDetailVO> transorder, AppInfo app) ;
	
	public VolumecardAccount createVolumecardAccount(String channleNo,BatchOpenOnlineListVO order, VolumecardAccountProduct product) throws DataInvalidException, MaAccountException ;
}
