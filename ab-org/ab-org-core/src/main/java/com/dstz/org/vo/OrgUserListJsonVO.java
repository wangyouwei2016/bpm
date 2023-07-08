package com.dstz.org.vo;

import com.dstz.base.common.valuemap.AbValueMap;
import com.dstz.base.common.valuemap.AbValueMapType;
import com.dstz.org.enums.OrgStatus;

import java.util.Date;


public class OrgUserListJsonVO implements java.io.Serializable {

    private String id;

    /**
     * 姓名
     */
    private String fullname;

    /**
     * 账号
     */
    private String account;

    /**
     * 性别：男，女，未知
     */
    private String sex;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 手机号码
     */
    private String mobile;

    /**
     * 0:禁用，1正常
     */
    @AbValueMap(type = AbValueMapType.ENUM, fixClass = OrgStatus.class, matchField = "status",
            attrMap = {@AbValueMap.AttrMap(originName = "desc", targetName = "statusDesc"),
                    @AbValueMap.AttrMap(originName = "labelCss", targetName = "statusCss")
            })
    private Integer status;

    /**
     * 创建时间
     */
    private Date createTime;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
