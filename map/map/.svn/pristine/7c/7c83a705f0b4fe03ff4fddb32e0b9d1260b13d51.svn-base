package com.hummingbird.maaccount.entity;

import java.io.Serializable;

/**
 * @author
 *         <html>
 *         <table border="1" width="100%">
 *         <tr>
 *         <td>description:用户组织机构(组织机构包含：商户、门店、企业客户等)</td>
 *         <td></td>
 *         </tr>
 *         <tr>
 *         <td>mender: <a href='mailto:zyyceo@gmail.com'>Ecol</a></td>
 *         <td>updated: 2016年4月1日</td>
 *         </tr>
 *         </table>
 *         </html>
 * @version 1.0
 */
public class UserOrg implements Serializable {

    private static final long serialVersionUID = -6409827099600644478L;

    private Long              id;

    private User              user;                                    //用户

    private String            orgType;                                 //组织机构类型；supplier=商户；shop=门店；coporation=企业客户

    private String            orgCode;                                 //组织机构编码


    public UserOrg() {
        super();
    }

    public UserOrg(Long id, User user, String orgType, String orgCode) {
        super();
        this.id = id;
        this.user = user;
        this.orgType = orgType;
        this.orgCode = orgCode;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getOrgType() {
        return orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

}
