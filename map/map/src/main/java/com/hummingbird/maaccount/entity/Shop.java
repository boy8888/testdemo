package com.hummingbird.maaccount.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author
 *         <html>
 *         <table border="1" width="100%">
 *         <tr>
 *         <td>description:门店信息</td>
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
public class Shop implements Serializable {

    private static final long serialVersionUID = -6749018303623252400L;

    private Long              id;

    private String            code;                                    //门店编号

    private String            name;                                    //门店全称

    private String            shortName;                               //门店简称

    private String            status;                                  //状态

    private String            createdIp;                               //创建者Ip

    private String            creater;                                 //创建者

    private Date              createdDate;                             //创建日期

    private String            updatedIp;                               //更新者Ip

    private String            updater;                                 //更新者

    private Date              updatedDate;                             //更新日期

    private Integer           deleted          = 0;                    //删除标志

    private Supplier          supplier;                                //商户

    private Address           address;                                 //联系地址

    private String            terminalCodes;                           //终端号编码，以逗号分隔


    public Shop() {
        super();
    }

    public Shop(Long id, String code, String name, String shortName, String status, String createdIp, String creater, Date createdDate, String updatedIp, String updater, Date updatedDate,
            Integer deleted, Supplier supplier, Address address) {
        super();
        this.id = id;
        this.code = code;
        this.name = name;
        this.shortName = shortName;
        this.status = status;
        this.createdIp = createdIp;
        this.creater = creater;
        this.createdDate = createdDate;
        this.updatedIp = updatedIp;
        this.updater = updater;
        this.updatedDate = updatedDate;
        this.deleted = deleted;
        this.supplier = supplier;
        this.address = address;
    }

    public String getCreatedIp() {
        return createdIp;
    }

    public void setCreatedIp(String createdIp) {
        this.createdIp = createdIp;
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

    public String getUpdatedIp() {
        return updatedIp;
    }

    public void setUpdatedIp(String updatedIp) {
        this.updatedIp = updatedIp;
    }

    public String getUpdater() {
        return updater;
    }

    public void setUpdater(String updater) {
        this.updater = updater;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

    public String getTerminalCodes() {
        return terminalCodes;
    }

    public void setTerminalCodes(String terminalCodes) {
        this.terminalCodes = terminalCodes;
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
        Shop other = (Shop) obj;
        if (code == null) {
            if (other.code != null) return false;
        } else if (!code.equals(other.code)) return false;
        return true;
    }

}
