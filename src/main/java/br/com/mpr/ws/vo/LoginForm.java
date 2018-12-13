package br.com.mpr.ws.vo;

import br.com.mpr.ws.constants.LoginType;

/**
 *
 */
public class LoginForm {

    private LoginType loginType;
    private String email;
    private String password;
    private String socialKey;

    public LoginType getLoginType() {
        return loginType;
    }

    public void setLoginType(LoginType loginType) {
        this.loginType = loginType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSocialKey() {
        return socialKey;
    }

    public void setSocialKey(String socialKey) {
        this.socialKey = socialKey;
    }
}
