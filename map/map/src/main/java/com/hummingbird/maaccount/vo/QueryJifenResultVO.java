package com.hummingbird.maaccount.vo;

import java.util.ArrayList;
import java.util.List;

import com.hummingbird.maaccount.entity.JifenAccount;

public class QueryJifenResultVO {
	
	protected List<JifenListResultVO> list;



	public List<JifenListResultVO> getList() {
		return list;
	}

	public void setList(List<JifenListResultVO> list) {
		this.list = list;
	}

	@Override
	public String toString() {
		return "QueryJifenResultVO [list=" + list + "]";
	}
	
	/**
	 * 构造函数
	 */
	public QueryJifenResultVO(List<JifenAccount> accounts) {
		List<JifenListResultVO> r=new ArrayList<JifenListResultVO>();
		for(JifenAccount rea:accounts){
			r.add(new JifenListResultVO(rea));
		}
		list=r;
	}
}
