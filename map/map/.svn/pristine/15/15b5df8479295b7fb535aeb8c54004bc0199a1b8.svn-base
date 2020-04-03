package com.hummingbird.maaccount.entity;

import java.io.Serializable;

/**
 * @author
 *         <html>
 *         <table border="1" width="100%">
 *         <tr>
 *         <td>description:终端号</td>
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
public class Terminal implements Serializable {

    private static final long serialVersionUID = -2915798657289958635L;

    private Long              id;

    private String            code;                                    //终端编号

    private Shop              shop;                                    //门店

    private String            status;                                  //状态

    private Integer           deleted          = 0;                    //删除状态


    public Terminal() {
        super();
    }

    public Terminal(Long id, String code, Shop shop, String status, Integer deleted) {
        super();
        this.id = id;
        this.code = code;
        this.shop = shop;
        this.status = status;
        this.deleted = deleted;
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

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
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
        Terminal other = (Terminal) obj;
        if (code == null) {
            if (other.code != null) return false;
        } else if (!code.equals(other.code)) return false;
        return true;
    }

}
