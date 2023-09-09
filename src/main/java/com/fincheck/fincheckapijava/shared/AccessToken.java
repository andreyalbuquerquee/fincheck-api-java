package com.fincheck.fincheckapijava.shared;

public class AccessToken {
    private String accessToken;

    public AccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
    
    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String acessToken) {
        this.accessToken = acessToken;
    }
}
