package com.igor.security;

import com.igor.entity.MainUser;
import com.igor.entity.UserSessionToken;
import com.igor.service.UserService;
import com.igor.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class ProviderAuthentication implements AuthenticationProvider {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        final String username = authentication.getName(), password = authentication.getCredentials().toString();

        if (Utils.IsNullOrEmpty(username) || Utils.IsNullOrEmpty(password))
            throw new InsufficientAuthenticationException("");

        final MainUser mainUser = (MainUser) userService.loadMainUserByUsername(username);

        if (mainUser == null || !passwordEncoder.matches(password, mainUser.getPassword()))
            throw new CredentialsExpiredException("");

        return new UserSessionToken(mainUser);

    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
