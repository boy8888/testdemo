/**
 * 
 * BatchProcessReporter.java
 * 版本所有 深圳市蜂鸟娱乐有限公司 2013-2014
 */
package com.hummingbird.maaccount.util;

import java.util.ArrayList;
import java.util.List;

/**
 * @author john huang
 * 2015年7月23日 下午11:14:37
 * 本类主要做为 批量处理报告器
 */
public class BatchProcessReporter<T> {
	
	
	int total;
	
	int success;
	
	List<IndexObject<T>> failmsgs = new ArrayList<IndexObject<T>>();

	/**
	 * 添加成功
	 */
	public void addSuccess(){
		success++;
		total++;
	}
	
	/**
	 * 添加失败
	 */
	public void addFail(T failmsg){
		failmsgs.add(new IndexObject<T>(total++,failmsg));
	}

	/**
	 * @return the total
	 */
	public int getTotal() {
		return total;
	}

	/**
	 * @param total the total to set
	 */
	public void setTotal(int total) {
		this.total = total;
	}

	/**
	 * @return the success
	 */
	public int getSuccess() {
		return success;
	}

	/**
	 * @param success the success to set
	 */
	public void setSuccess(int success) {
		this.success = success;
	}

	/**
	 * @return the failmsgs
	 */
	public List<IndexObject<T>> getFailmsgs() {
		return failmsgs;
	}

	/**
	 * @param failmsgs the failmsgs to set
	 */
	public void setFailmsgs(List<IndexObject<T>> failmsgs) {
		this.failmsgs = failmsgs;
	}
	
	public int getFail(){
		return total-success;
	}
	
}
