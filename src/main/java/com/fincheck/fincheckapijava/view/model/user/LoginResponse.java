package com.fincheck.fincheckapijava.view.model.user;


public class LoginResponse {
    String accessToken;

    public LoginResponse() {}

    public LoginResponse(String token) {
        this.accessToken = token;
    }
    
    //#region Getters and Setters
    public String getAccessToken() {
        return accessToken;
    }
    public void setAccessToken(String token) {
        this.accessToken = token;
    }
    //#endregion
}
