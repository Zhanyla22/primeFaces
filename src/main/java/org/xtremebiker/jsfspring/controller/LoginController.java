package org.xtremebiker.jsfspring.controller;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.faces.bean.ManagedBean;

@ManagedBean(name = "auth")
@Scope
@Controller
public class LoginController {

    public String login(){
        return null;
    }
}
