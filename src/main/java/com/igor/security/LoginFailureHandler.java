package com.igor.security;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {

        String URL_CONTEXT = request.getContextPath();

        request.getServletContext().setAttribute("mensagemerro", "Login or password invalid");

        response.sendRedirect(URL_CONTEXT + "/signIn");

        //request.getRequestDispatcher(URL_CONTEXT + "/signIn").forward(request, response);

    }
}
