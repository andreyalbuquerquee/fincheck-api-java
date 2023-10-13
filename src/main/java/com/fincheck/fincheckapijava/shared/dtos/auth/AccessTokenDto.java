package com.fincheck.fincheckapijava.shared.dtos.auth;

public class AccessTokenDto {
    private String accessToken;

    public AccessTokenDto(String accessToken) {
        this.accessToken = accessToken;
    }
    
    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
