package com.hummingbird.maaccount.vo;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import org.apache.commons.lang.ObjectUtils;

import com.hummingbird.common.util.ValidateUtil;
import com.hummingbird.common.vo.PainttextAble;

/**
 * 油站通知接口 在body VO
 */
public class AddStoreBodyVO
implements PainttextAble {

    	 /**
	     * 商户id
	     */
	    protected String sellerId;
	    	/**
	     * 商户名称
	     */
	    protected String sellerName;
	    	/**
	     * 门店id
	     */
	    protected String storeId;
	    	/**
	     * 门店名称
	     */
	    protected String storeName;
	    	/**
	     * 新增终端id ,多个以逗号分开
	     */
	    protected List<String> addTerminalIds;
	    	/**
	     * 移除终端id ,多个以逗号分开
	     */
	    protected List<String> delTerminalIds;
	    	/**
	     * 区域,使用中文
	     */
	    protected String district;
	    	/**
	     * 城市,使用中文
	     */
	    protected String city;
	    	/**
	     * 
	     */
	    protected String status;
	    	/**
	     * 省份,使用中文
	     */
	    protected String province;
	    	/**
	     * 商户类型
	     */
	    protected String sellerType;
	    	/**
	     * 经度
	     */
	    protected String longitude;
	    	/**
	     * 纬度
	     */
	    protected String latitude;
	    /**
	     * 门店简称
	     */
	    private String storeShortName;
	    /**
	     * 商户简称
	     */
	    private String sellerShortName;
	
	    	/**
	     * @return 
	     */
	    public String getSellerId() {
	        return sellerId;
	    }
	
	    /**
	     * @param 
	     */
	    public void setSellerId(String sellerId) {
	        this.sellerId = sellerId;
	    }
	    	/**
	     * @return 商户名称
	     */
	    public String getSellerName() {
	        return sellerName;
	    }
	
	    /**
	     * @param 商户名称
	     */
	    public void setSellerName(String sellerName) {
	        this.sellerName = sellerName;
	    }
	    	/**
	     * @return 门店id
	     */
	    public String getStoreId() {
	        return storeId;
	    }
	
	    /**
	     * @param 门店id
	     */
	    public void setStoreId(String storeId) {
	        this.storeId = storeId;
	    }
	    	/**
	     * @return 门店名称
	     */
	    public String getStoreName() {
	        return storeName;
	    }
	
	    /**
	     * @param 门店名称
	     */
	    public void setStoreName(String storeName) {
	        this.storeName = storeName;
	    }
	    	/**
	     * @return 新增终端id ,多个以逗号分开
	     */
	    public List<String> getAddTerminalIds() {
	        return addTerminalIds;
	    }
	
	    /**
	     * @param 新增终端id ,多个以逗号分开
	     */
	    public void setAddTerminalIds(List<String> addTerminalIds) {
	        this.addTerminalIds = addTerminalIds;
	    }
	    	/**
	     * @return 移除终端id ,多个以逗号分开
	     */
	    public List<String> getDelTerminalIds() {
	        return delTerminalIds;
	    }
	
	    /**
	     * @param 移除终端id ,多个以逗号分开
	     */
	    public void setDelTerminalIds(List<String> delTerminalIds) {
	        this.delTerminalIds = delTerminalIds;
	    }
	    	/**
	     * @return 区域,使用中文
	     */
	    public String getDistrict() {
	        return district;
	    }
	
	    /**
	     * @param 区域,使用中文
	     */
	    public void setDistrict(String district) {
	        this.district = district;
	    }
	    	/**
	     * @return 城市,使用中文
	     */
	    public String getCity() {
	        return city;
	    }
	
	    /**
	     * @param 城市,使用中文
	     */
	    public void setCity(String city) {
	        this.city = city;
	    }
	    	/**
	     * @return 
	     */
	    public String getStatus() {
	        return status;
	    }
	
	    /**
	     * @param 
	     */
	    public void setStatus(String status) {
	        this.status = status;
	    }
	    	/**
	     * @return 省份,使用中文
	     */
	    public String getProvince() {
	        return province;
	    }
	
	    /**
	     * @param 省份,使用中文
	     */
	    public void setProvince(String province) {
	        this.province = province;
	    }
	    	/**
	     * @return 商户简称
	     */
	    public String getShortName() {
	        return sellerShortName;
	    }
	
	    /**
	     * @param 商户简称
	     */
	    public void setShortName(String shortName) {
	        this.sellerShortName = shortName;
	    }
	    	/**
	     * @return 商户类型
	     */
	    public String getSellerType() {
	        return sellerType;
	    }
	
	    /**
	     * @param 商户类型
	     */
	    public void setSellerType(String sellerType) {
	        this.sellerType = sellerType;
	    }
	    	/**
	     * @return 经度
	     */
	    public String getLongitude() {
	        return longitude;
	    }
	
	    /**
	     * @param 经度
	     */
	    public void setLongitude(String longitude) {
	        this.longitude = longitude;
	    }
	    	/**
	     * @return 纬度
	     */
	    public String getLatitude() {
	        return latitude;
	    }
	
	    /**
	     * @param 纬度
	     */
	    public void setLatitude(String latitude) {
	        this.latitude = latitude;
	    }
		
	/**
	 * 生成文本组装内容
	 * @return
	 */
	public String getPaintText(){
		String pt = ValidateUtil.sortbyValues(
				   
							ObjectUtils.toString(sellerId) ,
							   
							ObjectUtils.toString(sellerName) ,
							   
							ObjectUtils.toString(storeId) ,
							   
							ObjectUtils.toString(storeName) ,
							   
							ObjectUtils.toString(addTerminalIds) ,
							   
							ObjectUtils.toString(delTerminalIds) ,
							   
							ObjectUtils.toString(district) ,
							   
							ObjectUtils.toString(city) ,
							   
							ObjectUtils.toString(status) ,
							   
							ObjectUtils.toString(province) ,
							   
							ObjectUtils.toString(sellerShortName) ,
							   
							ObjectUtils.toString(sellerType) ,
							   
							ObjectUtils.toString(longitude) ,
							   
							ObjectUtils.toString(latitude) 
							);
		return pt;
	}
	

    public String toString() {
		return org.apache.commons.lang.builder.ToStringBuilder.reflectionToString(this);
	}

	/**
	 * 门店简称 
	 */
	public String getStoreShortName() {
		return storeShortName;
	}

	/**
	 * 门店简称 
	 */
	public void setStoreShortName(String storeShortName) {
		this.storeShortName = storeShortName;
	}

	/**
	 * 商户简称 
	 */
	public String getSellerShortName() {
		return sellerShortName;
	}

	/**
	 * 商户简称 
	 */
	public void setSellerShortName(String sellerShortName) {
		this.sellerShortName = sellerShortName;
	}

    

}