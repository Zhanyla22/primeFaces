package org.xtremebiker.jsfspring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.xtremebiker.jsfspring.dto.request.LoginRequest;
import org.xtremebiker.jsfspring.service.UserService;

import javax.annotation.PostConstruct;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

@Component
@Scope("view")
public class LoginController {

    @Autowired
    private UserService userService;

    private LoginRequest loginRequest;

    @PostConstruct
    public void init() {
        loginRequest = new LoginRequest();
    }

    public LoginRequest getLoginRequest() {
        return loginRequest;
    }

    public String auth() {
        UserDetails userDetails = userService.auth(loginRequest.getUserName(), loginRequest.getPassword());
        if (loginRequest.getPassword() !=null && userDetails != null) {
            for (GrantedAuthority authority : userDetails.getAuthorities()) {
                if ("ADMIN".equals(authority.getAuthority())) {
                    return "/ui/test.xhtml";
                }
                if ("USER".equals(authority.getAuthority())) {
                    return "/ui/user.xhtml";
                }
            }
        }
        return null;
    }


    public String doLogin() throws ServletException, IOException {
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        RequestDispatcher dispatcher = ((ServletRequest) context.getRequest()).getRequestDispatcher("/login");
        dispatcher.forward((ServletRequest) context.getRequest(), (ServletResponse) context.getResponse());
        FacesContext.getCurrentInstance().responseComplete();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && !(auth instanceof AnonymousAuthenticationToken) && auth.isAuthenticated()) {
            UserDetails userSecurity = (UserDetails) ((Authentication) auth).getPrincipal();
            if (userSecurity.getAuthorities().equals("ADMIN")) {
                return "/ui/test.xhtml";
            } else if (userSecurity.getAuthorities().equals("USER")) {
                return "/ui/user.xhtml";
            }
        }
        return "";
    }
}
