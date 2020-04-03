package com.hummingbird.maaccount.vo;

import java.util.ArrayList;
import java.util.List;

import com.hummingbird.common.exception.DataInvalidException;
import com.hummingbird.common.util.ValidateUtil;

public class UndoRedPaperTransOrderVO extends BaseTransOrderVO{
	 
	protected ArrayList<UndoRedPaperListVO> order;
	/**
	 * 访问地址，用于鉴权和查询
	 */
	protected String method;

	/**
	 * 业务处理类型,ZZ#-转账，FK#-付款，TOB-投标，FEB-废标， FB#-付本，SX#-收息，SB#-收本，TX#-提现，CZ#-充值，CZH-冲正
	 */
	protected String operationType;
	/**
	 * 构造函数
	 */
	public UndoRedPaperTransOrderVO() {
		super();
	}
	@Override
	public String toString() {
		return "UndoRedPaperTransOrderVO [order=" + order + ", app=" + app
				+ ", tsig=" + tsig + "]";
	}

	
	public ArrayList<UndoRedPaperListVO> getOrder() {
		return order;
	}


	public void setOrder(ArrayList<UndoRedPaperListVO> order) {
		this.order = order;
	}


	public void selfvalidate() throws DataInvalidException {
		// TODO Auto-generated method stub
		
	}
	
	public String getPaintText() {
		String text="";
		for(UndoRedPaperListVO rp:order){
			text+=rp.getOrderId();
		}
		String mingwen=ValidateUtil.sortbyValues(text);
		return mingwen;
	}

	public String getMethod() {
		return method;
	}
	
	public void setMethod(String method) {
		this.method = method;
	}

	public String getOperationType() {
		return operationType;
	}

	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}
	
}
