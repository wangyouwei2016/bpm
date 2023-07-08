package com.dstz.auth.login.model;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author lightning
 */
public class RefreshTokenReqVO implements Serializable {

    @NotBlank(message = "应用id不能为空！")
    private String clientId;

    @NotBlank(message = "应用密钥不能为空！")
    private String clientSecret;


    @NotBlank(message = "刷新token不能为空！")
    private String refreshToken;


    public RefreshTokenReqVO() {
    }

    public RefreshTokenReqVO(@NotBlank(message = "应用id不能为空！") String clientId, @NotBlank(message = "应用密钥不能为空！") String clientSecret, @NotBlank(message = "刷新token不能为空！") String refreshToken) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.refreshToken = refreshToken;
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


    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

}
