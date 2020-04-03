package com.hummingbird.maaccount.entity;

import java.io.Serializable;

public class ZJProduct implements Serializable{
    /**
     * 中经产品id
     */
    private String zjid;

    /**
     * 中经产品的名称
     */
    private String zjproductname;

    /**
     * @return 中经产品id
     */
    public String getZjid() {
        return zjid;
    }

    /**
     * @param zjid 
	 *            中经产品id
     */
    public void setZjid(String zjid) {
        this.zjid = zjid == null ? null : zjid.trim();
    }

    /**
     * @return 中经产品的名称
     */
    public String getZjproductname() {
        return zjproductname;
    }

    /**
     * @param zjproductname 
	 *            中经产品的名称
     */
    public void setZjproductname(String zjproductname) {
        this.zjproductname = zjproductname == null ? null : zjproductname.trim();
    }
}