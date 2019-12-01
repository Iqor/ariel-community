/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Layout
 * and open the template in the editor.
 */
package com.igor.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 *
 * @author Igor
 */
public class SessionFilter implements Filter {

    private FilterConfig fc;


    public void doFilter(ServletRequest sr, ServletResponse sr1, FilterChain fc) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) sr;
        HttpServletResponse res = (HttpServletResponse) sr1;
        HttpSession session = req.getSession(false);
        
        if (session != null && session.getAttribute("userId") != null) {
            System.out.println("Sessão com usuário existente");
            fc.doFilter(sr, sr1);
            return;
        } else {

            System.out.println("Sessão ou usuário nulos");
            res.sendRedirect(sr.getServletContext().getContextPath() + "/index.jsp");
            return;
        }
    }


    public void destroy() {

    }

    public void init(FilterConfig config) throws ServletException {
        this.fc = config;
    }

}
