package org.xtremebiker.jsfspring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.xtremebiker.jsfspring.dto.request.AddDelayDto;
import org.xtremebiker.jsfspring.dto.response.SaveDto;
import org.xtremebiker.jsfspring.dto.response.UserDto;
import org.xtremebiker.jsfspring.service.AttendRecordService;
import org.xtremebiker.jsfspring.service.UserService;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.util.List;


@Component
@Scope("view")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AttendRecordService attendRecordService;

    private SaveDto saveDto;

    public List<UserDto> getAllUsers() {

        return userService.getAllUsers();
    }

    @PostConstruct
    public void init() {
        saveDto = new SaveDto();
    }

    public SaveDto getSaveDto() {
        return saveDto;
    }

    public void save() {
        attendRecordService.addDelayMin(
                AddDelayDto.builder()
                        .userId(saveDto.getUserId())
                        .date(saveDto.getDate())
                        .delayInMin(saveDto.getMin())
                        .build()
        );
        FacesMessage msg = new FacesMessage("опоздание добавлено");
        FacesContext.getCurrentInstance().addMessage(null, msg);
//        user.setUserId(null);
//        date = null;
//        min=null;
    }
}
