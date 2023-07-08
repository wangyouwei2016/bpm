package com.dstz.org.dto;

import javax.validation.constraints.NotBlank;


/**
 * requestDto 保存用户信息
 */
public class SaveOrgUserInfoDTO implements java.io.Serializable {

    private String id;

    /**
     * 头像
     */
    private String photo;

    /**
     * 签名
     */
    private String signature;

    /**
     * 姓名
     */
    @NotBlank(message = "姓名不能为空！")
    private String fullname;

    /**
     * 账号
     */
    @NotBlank(message = "账号不能为空！")
    private String account;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 手机
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


    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

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

    @Override
    public String toString() {
        return "SaveOrgUserDTO{" +
                "id='" + id + '\'' +
                ", photo='" + photo + '\'' +
                ", signature='" + signature + '\'' +
                ", fullname='" + fullname + '\'' +
                ", account='" + account + '\'' +
                ", email='" + email + '\'' +
                ", mobile='" + mobile + '\'' +
                ", weixin='" + weixin + '\'' +
                ", sex='" + sex + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
