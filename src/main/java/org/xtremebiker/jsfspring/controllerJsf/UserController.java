package org.xtremebiker.jsfspring.controllerJsf;

import org.xtremebiker.jsfspring.dto.request.AddUserDto;
import org.xtremebiker.jsfspring.dto.response.UserDto;
import org.xtremebiker.jsfspring.enums.Position;
import org.xtremebiker.jsfspring.service.UserService;
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

    private AddUserDto addUserDto;

    private Position position;

    @PostConstruct
    public void init() {
        addUserDto = new AddUserDto();
    }

    public Position getPosition() {
        return position;
    }

    public AddUserDto getAddUserDto() {
        return addUserDto;
    }

    @Autowired
    private UserService userService;


    public static Position[] names() {
        return Position.values();
    }

    public List<UserDto> getAllUsers() {
        return userService.getAllUsers();
    }

    public void addNewUser() throws NoSuchAlgorithmException, InvalidKeySpecException {
        userService.addNewUser(addUserDto);
    }

    public void reload() throws IOException {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
    }

}
