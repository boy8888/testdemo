/**
 * 
 * ProductDownloadVO.java
 * 版本所有 深圳市蜂鸟娱乐有限公司 2013-2014
 */
package com.hummingbird.maaccount.vo;

/**
 * @author john huang
 * 2015年2月23日 下午9:01:48
 * 本类主要做为
 */
public class ProductDownloadVO extends AppBaseVO{

	private BasePosVO download;

	/**
	 * @return the download
	 */
	public BasePosVO getDownload() {
		return download;
	}

	/**
	 * @param download the download to set
	 */
	public void setDownload(BasePosVO download) {
		this.download = download;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ProductDownloadVO [download=" + download + ", app=" + app + "]";
	}
	
}
