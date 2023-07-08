package com.dstz.org.dto;

import javax.validation.constraints.NotBlank;

public class UpdateUserPassWorldDTO implements java.io.Serializable {

    /**
     * 姓名
     */
    private String fullname;

    /**
     * 账户
     */
    @NotBlank(message = "账户不能为空！")
    private String account;

    /**
     * 原密码
     */
    @NotBlank(message = "原密码不能为空！")
    private String oldPassword;

    /**
     * 新密码
     */
    @NotBlank(message = "新密码不能为空！")
    private String newPassword;

    /**
     * 确认密码
     */
    @NotBlank(message = "确认密码不能为空！")
    private String confirmPassword;


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

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    @Override
    public String toString() {
        return "UpdateUserPassWorldDTO{" +
                "fullname='" + fullname + '\'' +
                ", account='" + account + '\'' +
                ", oldPassword='" + oldPassword + '\'' +
                ", newPassword='" + newPassword + '\'' +
                ", confirmPassword='" + confirmPassword + '\'' +
                '}';
    }
}
