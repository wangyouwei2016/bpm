package com.dstz.org.vo;

import com.dstz.base.common.valuemap.AbValueMap;
import com.dstz.base.common.valuemap.AbValueMapType;
import com.dstz.org.enums.OrgStatus;

import java.util.List;

public class OrgUserVO implements java.io.Serializable {

    /**
     * id
     */
    private String id;

    /**
     * 姓名
     */
    private String fullname;

    /**
     * 账户
     */
    private String account;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 微信
     */
    private String weixin;

    /**
     * 性别
     */
    private String sex;

    /**
     * 状态
     */
    @AbValueMap(type = AbValueMapType.ENUM, fixClass = OrgStatus.class, matchField = "status",
            attrMap = {@AbValueMap.AttrMap(originName = "desc", targetName = "statusDesc"),
                    @AbValueMap.AttrMap(originName = "labelCss", targetName = "statusCss")
            })
    private Integer status;

    /**
     * 地址
     */
    private String address;

    /**
     * 用户关系
     */
    private List<OrgRelationUserVO> orgRelationList;


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

    public String getWeixin() {
        return weixin;
    }

    public void setWeixin(String weixin) {
        this.weixin = weixin;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<OrgRelationUserVO> getOrgRelationList() {
        return orgRelationList;
    }

    public void setOrgRelationList(List<OrgRelationUserVO> orgRelationList) {
        this.orgRelationList = orgRelationList;
    }

    @Override
    public String toString() {
        return "OrgUserVO{" +
                "id='" + id + '\'' +
                ", fullname='" + fullname + '\'' +
                ", account='" + account + '\'' +
                ", email='" + email + '\'' +
                ", mobile='" + mobile + '\'' +
                ", weixin='" + weixin + '\'' +
                ", sex='" + sex + '\'' +
                ", status=" + status +
                ", orgRelationList=" + orgRelationList +
                ", address='" + address + '\'' +
                '}';
    }
}
