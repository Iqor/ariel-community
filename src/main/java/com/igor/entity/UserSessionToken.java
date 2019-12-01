package com.igor.entity;

import org.springframework.security.authentication.AbstractAuthenticationToken;

import java.util.List;

public class UserSessionToken extends AbstractAuthenticationToken {

    private static final long serialVersionUID = -3439156991684419811L;

    private MainUser mainUser;

    public UserSessionToken(MainUser mainUser) {
        super(null);
        this.mainUser = mainUser;
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return this.mainUser;
    }

    @Override
    public String getName() {
        return this.mainUser.getName();
    }

}
