package com.fincheck.fincheckapijava.view.model.user;

import com.fincheck.fincheckapijava.model.User;

public class LoginResponse {
    String token;
    User user;

    public LoginResponse() {}

    public LoginResponse(String token, User user) {
        this.token = token;
        this.user = user;
    }
    
    //#region Getters and Setters
    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    //#endregion
}
