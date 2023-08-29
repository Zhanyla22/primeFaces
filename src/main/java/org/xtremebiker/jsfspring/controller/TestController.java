package org.xtremebiker.jsfspring.controller;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.xtremebiker.jsfspring.dto.response.AllAttendance;
import org.xtremebiker.jsfspring.dto.response.DelayUserDto;
import org.xtremebiker.jsfspring.service.AttendRecordService;
import org.xtremebiker.jsfspring.service.impl.UserServiceImpl;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
@Scope("view")
public class TestController {

    private final AttendRecordService attendRecordService;

    private final UserServiceImpl userService;

    public TestController(AttendRecordService attendRecordService, UserServiceImpl userService) {
        this.attendRecordService = attendRecordService;
        this.userService = userService;
    }

    private AllAttendance allAttendance;

    @PostConstruct
    public void init() {
        allAttendance = new AllAttendance();
    }

    private AllAttendance getAllAttendance() {
        return allAttendance;
    }

    public List<AllAttendance> getAllUsers() {
        return attendRecordService.getAllAttendance();
    }

    public List<DelayUserDto> getAllAttendanceByUser() {
        return attendRecordService.getAllByUser(userService.getCurrentUser().getUsername());
    }

    public void delete(Long id) {
        attendRecordService.deleteById(id);
    }


}
