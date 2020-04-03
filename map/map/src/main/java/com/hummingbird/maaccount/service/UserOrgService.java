package com.hummingbird.maaccount.service;

import java.io.Serializable;
import java.util.List;

import com.hummingbird.maaccount.vo.QueryUserOrgVO;

/**
 * @author
 *         <html>
 *         <table border="1" width="100%">
 *         <tr>
 *         <td>description:用户组织机构接口</td>
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
public interface UserOrgService {

    /**
     * 绑定用户和组织
     * 一个用户只能绑定一个组织机构类型下面的多个组织机构对象；不能绑定多个组织机构类型下面的多个组织机构对象
     * @param userId 用户主键ID
     * @param orgType 组织类型；商户、门店、企业客户等
     * @param orgCodes 组织编码；赌赢组织类型的所在对象编码
     * @return
     */
    public boolean bindUserOrg(Serializable userId, String orgType, List<String> orgCodes);

    /**
     * 根据用户主键ID查询用户可访问的组织机构代码
     * @param userId 用户主键ID
     * @return
     */
    public QueryUserOrgVO queryUserOrgByUserId(Serializable userId);

}
