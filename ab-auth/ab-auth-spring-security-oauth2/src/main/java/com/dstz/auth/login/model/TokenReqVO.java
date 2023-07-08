package com.dstz.auth.login.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author lightning
 */
public class TokenReqVO implements Serializable {

    @NotBlank(message = "用户名不能为空！")
    private String userName;

    @NotBlank(message = "密码不能为空！")
    private String password;

    private String grantType;

    @NotBlank(message = "应用id不能为空！")
    private String clientId;

    @NotBlank(message = "应用密钥不能为空！")
    private String clientSecret;

    //验证码
    private String captcha;

    public TokenReqVO() {
    }

    public TokenReqVO(@NotBlank(message = "用户名不能为空！") String userName, @NotBlank(message = "密码不能为空！") String password, @NotBlank(message = "验证类型不能为空！") String grantType, @NotBlank(message = "应用id不能为空！") String clientId, @NotBlank(message = "应用密钥不能为空！") String clientSecret) {
        this.userName = userName;
        this.password = password;
        this.grantType = grantType;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGrantType() {
        return grantType;
    }

    public void setGrantType(String grantType) {
        this.grantType = grantType;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }
}
