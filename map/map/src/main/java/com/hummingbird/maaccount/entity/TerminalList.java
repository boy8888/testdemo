package com.hummingbird.maaccount.entity;

import java.util.List;
import java.util.Date;

/**
 * 终端清单
 */

public class TerminalList {
    /**
     * 终端编码
     */
    private String terminalId;

    /**
     * 门户编码
     */
    private String storeId;

    /**
     * 商户编码
     */
    private String sellerId;

    /**
     * 门店名称
     */
    private String storeName;
    
    /**
     * 商户名称
     */
    private String sellerName;
    
    
    private String district;

    private String province;

    /**
     * 商户简称
     */
    private String sellerShortName;

    /**
     * 商户类型
     */
    private String sellerType;

    /**
     * 经度
     */
    private String longitude;

    /**
     * 纬度
     */
    private String latitude;

    /**
     * 门店简称
     */
    private String storeShortName;

    /**
     * 状态
     */
    private String status;
    
    /**
     * 城市使用中文 
     */
    private String city;

    private Date insertTime;

    /**
     * @return 终端编码
     */
    public String getTerminalId() {
        return terminalId;
    }

    /**
     * @param terminalid 
	 *            终端编码
     */
    public void setTerminalId(String terminalId) {
        this.terminalId = terminalId == null ? null : terminalId.trim();
    }

    /**
     * @return 门户编码
     */
    public String getStoreId() {
        return storeId;
    }

    /**
     * @param storeid 
	 *            门户编码
     */
    public void setStoreId(String storeId) {
        this.storeId = storeId == null ? null : storeId.trim();
    }

    /**
     * @return 商户编码
     */
    public String getSellerId() {
        return sellerId;
    }

    /**
     * @param sellerid 
	 *            商户编码
     */
    public void setSellerId(String sellerId) {
        this.sellerId = sellerId == null ? null : sellerId.trim();
    }

    /**
     * @return 门店名称
     */
    public String getStoreName() {
        return storeName;
    }

    /**
     * @param storename 
	 *            门店名称
     */
    public void setStoreName(String storeName) {
        this.storeName = storeName == null ? null : storeName.trim();
    }

    /**
     * @return 商户名称
     */
    public String getSellerName() {
        return sellerName;
    }

    /**
     * @param sellername 
	 *            商户名称
     */
    public void setSellerName(String sellerName) {
        this.sellerName = sellerName == null ? null : sellerName.trim();
    }

	/**
	 * 区域使用中文 
	 */
	public String getDistrict() {
		return district;
	}

	/**
	 * 区域使用中文 
	 */
	public void setDistrict(String district) {
		this.district = district;
	}

	/**
	 * 城市使用中文 
	 */
	public String getCity() {
		return city;
	}

	/**
	 * 城市使用中文 
	 */
	public void setCity(String city) {
		this.city = city;
	}


    public Date getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(Date insertTime) {
        this.insertTime = insertTime;
    }

	/**
	 * #{bare_field_comment} 
	 */
	public String getProvince() {
		return province;
	}

	/**
	 * #{bare_field_comment} 
	 */
	public void setProvince(String province) {
		this.province = province;
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

	/**
	 * 商户类型 
	 */
	public String getSellerType() {
		return sellerType;
	}

	/**
	 * 商户类型 
	 */
	public void setSellerType(String sellerType) {
		this.sellerType = sellerType;
	}

	/**
	 * 经度 
	 */
	public String getLongitude() {
		return longitude;
	}

	/**
	 * 经度 
	 */
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	/**
	 * 纬度 
	 */
	public String getLatitude() {
		return latitude;
	}

	/**
	 * 纬度 
	 */
	public void setLatitude(String latitude) {
		this.latitude = latitude;
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
	 * 状态 
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * 状态 
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * 设置商户简称
	 * @param shortName
	 */
	public void setShortName(String shortName) {
		sellerShortName = shortName;
		
	}
}