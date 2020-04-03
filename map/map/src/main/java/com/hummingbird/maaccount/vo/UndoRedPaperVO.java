package com.hummingbird.maaccount.vo;


import java.util.ArrayList;
import java.util.List;

import com.hummingbird.common.exception.DataInvalidException;
import com.hummingbird.common.util.ValidateUtil;
import com.hummingbird.maaccount.entity.RedPaperAccount;

public class UndoRedPaperVO implements IOrderVO{
	protected List<UndoRedPaperListVO> order;
	
	/**
	 * 构造函数
	 */
	public UndoRedPaperVO(List<String> accounts) {
		List<UndoRedPaperListVO> r=new ArrayList<UndoRedPaperListVO>();
		for(String orderId:accounts){
			r.add(new UndoRedPaperListVO(orderId));
		}
		order=r;
	}
	
	public List<UndoRedPaperListVO> getOrder() {
		return order;
	}
	public void setOrder(List<UndoRedPaperListVO> order) {
		this.order = order;
	}
	public String toString() {
		return "UndoRedPaperVO [order=" + order + "]";
	}
	@Override
	public String getPaintText() {
		String text="";
		for(UndoRedPaperListVO rp:order){
			text+=rp.getOrderId();
		}
		String mingwen=ValidateUtil.sortbyValues(text);
		return mingwen;
	}

	@Override
	public String getMobileNum() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void selfvalidate() throws DataInvalidException {
		// TODO Auto-generated method stub
		
	}

}
