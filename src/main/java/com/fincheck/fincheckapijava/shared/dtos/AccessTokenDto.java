package com.fincheck.fincheckapijava.shared.dtos;

public class AccessTokenDto {
    private String accessToken;

    public AccessTokenDto(String accessToken) {
        this.accessToken = accessToken;
    }
    
    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String acessToken) {
        this.accessToken = acessToken;
    }
}
