package com.hummingbird.maaccount.vo;

import java.util.List;

import com.hummingbird.maaccount.entity.User;

/**
 * @author
 *         <html>
 *         <table border="1" width="100%">
 *         <tr>
 *         <td>description:用户组织机构VO值对象</td>
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
public class QueryUserOrgVO {//一个用户只能绑定一个组织机构类型下面的多个组织机构对象；不能绑定多个组织机构类型下面的多个组织机构对象

    private User         user;    //用户

    private String       orgType; //组织机构类型

    private List<String> orgCodes;//组织机构代码


    public QueryUserOrgVO() {
        super();
    }

    public QueryUserOrgVO(User user, String orgType, List<String> orgCodes) {
        super();
        this.user = user;
        this.orgType = orgType;
        this.orgCodes = orgCodes;
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

    public List<String> getOrgCodes() {
        return orgCodes;
    }

    public void setOrgCodes(List<String> orgCodes) {
        this.orgCodes = orgCodes;
    }

}
