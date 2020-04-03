package com.hummingbird.maaccount.entity;

import java.io.Serializable;
import java.util.Date;

import com.hummingbird.maaccount.util.NumRandom;

/**
 * @author
 *         <html>
 *         <table border="1" width="100%">
 *         <tr>
 *         <td>description:地址</td>
 *         <td></td>
 *         </tr>
 *         <tr>
 *         <td>mender: <a href='mailto:zyyceo@gmail.com'>Ecol</a></td>
 *         <td>updated: 2016年3月31日</td>
 *         </tr>
 *         </table>
 *         </html>
 * @version 1.0
 */
public class Address implements Serializable {

    private static final long serialVersionUID = 5196165710756520404L;

    private Long              id;

    private String            code;

    private String            province;                               //省

    private String            city;                                   //市

    private String            area;                                   //区

    private String            specifics;                              //详细地址

    private String            longitude;                              //经度

    private String            latitude;                               //纬度

    private String            contact;                                //联系人

    private String            phone;                                  //固定电话

    private String            mobile;                                 //移动电话

    private String            mailAddress;                            //邮件地址

    private String            createdIp;                              //创建者Ip

    private String            creater;                                //创建者

    private Date              createdDate;                            //创建日期


    public Address() {
        super();
        this.code = NumRandom.getSystemCode();
        this.createdDate = new Date();
    }

    public Address(String province, String city, String area, String specifics, String longitude, String latitude, String contact, String phone, String mobile, String mailAddress, String createdIp,
            String creater) {
        super();
        this.code = NumRandom.getSystemCode();
        this.province = province;
        this.city = city;
        this.area = area;
        this.specifics = specifics;
        this.longitude = longitude;
        this.latitude = latitude;
        this.contact = contact;
        this.phone = phone;
        this.mobile = mobile;
        this.mailAddress = mailAddress;
        this.createdIp = createdIp;
        this.creater = creater;
        this.createdDate = new Date();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getSpecifics() {
        return specifics;
    }

    public void setSpecifics(String specifics) {
        this.specifics = specifics;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMailAddress() {
        return mailAddress;
    }

    public void setMailAddress(String mailAddress) {
        this.mailAddress = mailAddress;
    }

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedIp() {
        return createdIp;
    }

    public void setCreatedIp(String createdIp) {
        this.createdIp = createdIp;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((code == null) ? 0 : code.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        Address other = (Address) obj;
        if (code == null) {
            if (other.code != null) return false;
        } else if (!code.equals(other.code)) return false;
        return true;
    }

}
