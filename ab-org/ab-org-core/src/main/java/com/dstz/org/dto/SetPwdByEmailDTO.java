package com.dstz.org.dto;

import javax.validation.constraints.NotBlank;

public class SetPwdByEmailDTO {
    /**
     * 账户
     */
    @NotBlank(message = "账户不能为空！")
    private String account;

    /**
     * 新密码
     */
    @NotBlank(message = "密码不能为空！")
    private String newPassword;

    /**
     * 验证码
     */
    @NotBlank(message = "验证码不能为空！")
    private String captcha;

    /**
     * 确认密码
     */
    @NotBlank(message = "确认密码不能为空！")
    private String confirmPassword;


    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    @Override
    public String toString() {
        return "SetPwdByEmailDTO{" +
                "account='" + account + '\'' +
                ", newPassword='" + newPassword + '\'' +
                ", captcha='" + captcha + '\'' +
                ", confirmPassword='" + confirmPassword + '\'' +
                '}';
    }
}
