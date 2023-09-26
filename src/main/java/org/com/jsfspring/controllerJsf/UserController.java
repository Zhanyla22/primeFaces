package org.com.jsfspring.controllerJsf;

import org.com.jsfspring.dto.request.AddUserRequest;
import org.com.jsfspring.dto.response.UserResponse;
import org.com.jsfspring.enums.Position;
import org.com.jsfspring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;


@Component
@Scope("view")
public class UserController {

    private AddUserRequest addUserRequest;

    private Position position;

    @PostConstruct
    public void init() {
        addUserRequest = new AddUserRequest();
    }

    public Position getPosition() {
        return position;
    }

    public AddUserRequest getAddUserDto() {
        return addUserRequest;
    }

    @Autowired
    private UserService userService;


    public static Position[] names() {
        return Position.values();
    }

    public List<UserResponse> getAllUsers() {
        return userService.getAllUsers();
    }

    public void addNewUser() throws NoSuchAlgorithmException, InvalidKeySpecException {
        userService.addNewUser(addUserRequest);
    }

    public void reload() throws IOException {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
    }
}
