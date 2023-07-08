package com.dstz.org.dto;

import javax.validation.constraints.NotBlank;

public class SendCaptchaEmailDTO implements java.io.Serializable {

    /**
     * 账户
     */
    @NotBlank(message = "账户不能为空！")
    private String account;


    /**
     * 邮箱
     */
    @NotBlank(message = "邮箱不能为空！")
    private String email;


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

    @Override
    public String toString() {
        return "SendCaptchaEmailDTO{" +
                "account='" + account + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
