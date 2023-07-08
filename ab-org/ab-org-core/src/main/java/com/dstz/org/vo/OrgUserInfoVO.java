package com.dstz.org.vo;

import java.util.List;

public class OrgUserInfoVO implements java.io.Serializable {

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
     * 地址
     */
    private String address;

    /**
     * 头像
     */
    private String photo;

    /**
     * 签名
     */
    private String signature;

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

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public List<OrgRelationUserVO> getOrgRelationList() {
        return orgRelationList;
    }

    public void setOrgRelationList(List<OrgRelationUserVO> orgRelationList) {
        this.orgRelationList = orgRelationList;
    }

    @Override
    public String toString() {
        return "OrgUserInfoVO{" +
                "id='" + id + '\'' +
                ", fullname='" + fullname + '\'' +
                ", account='" + account + '\'' +
                ", email='" + email + '\'' +
                ", mobile='" + mobile + '\'' +
                ", weixin='" + weixin + '\'' +
                ", sex='" + sex + '\'' +
                ", address='" + address + '\'' +
                ", photo='" + photo + '\'' +
                ", signature='" + signature + '\'' +
                '}';
    }
}
