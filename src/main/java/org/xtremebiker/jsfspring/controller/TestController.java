package org.xtremebiker.jsfspring.controller;

import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.xtremebiker.jsfspring.dto.request.AddDelayDto;
import org.xtremebiker.jsfspring.dto.request.UpdateAttendance;
import org.xtremebiker.jsfspring.dto.request.UpdateAttendanceFront;
import org.xtremebiker.jsfspring.dto.response.AllAttendance;
import org.xtremebiker.jsfspring.dto.response.SaveDto;
import org.xtremebiker.jsfspring.enums.Status;
import org.xtremebiker.jsfspring.service.AttendRecordService;
import org.xtremebiker.jsfspring.service.impl.UserServiceImpl;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@Component
@Scope("view")
public class TestController {

    private final AttendRecordService attendRecordService;

    private SaveDto saveDto;

    private AllAttendance allAttendance;

    private UpdateAttendanceFront updateAttendanceFront;

    private Long changeId;


    public TestController(AttendRecordService attendRecordService) {
        this.attendRecordService = attendRecordService;
    }


    @PostConstruct
    public void init() {
        allAttendance = new AllAttendance();
        saveDto = new SaveDto();
        updateAttendanceFront = new UpdateAttendanceFront();
        changeId = null;
    }

    public SaveDto getSaveDto() {
        return saveDto;
    }

    public AllAttendance getAllAttendance() {
        return allAttendance;
    }



    public UpdateAttendanceFront getUpdateAttendanceFront() {
        return updateAttendanceFront;
    }

    public List<AllAttendance> getAllAttendanceByUserId(Long userId){
        return attendRecordService.getAllAttendanceByUserId(userId);
    }

    public List<AllAttendance> getAllAttendances() {
        return attendRecordService.getAllAttendance(Status.ACTIVE);
    }


    public void delete(Long id) {

        attendRecordService.deleteById(id);
    }

    public void changeIdSet(Long id) {
        changeId = id;
    }

    public void save() {
            attendRecordService.addDelayMin(
                    AddDelayDto.builder()
                            .userId(saveDto.getUserId())
                            .date(saveDto.getDate())
                            .delayInMin(saveDto.getMin())
                            .build()
            );

        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage("Запись с id  " + saveDto.getUserId() + " добавлена"));
    }

    public void update() {
        attendRecordService.updateAttendanceById(UpdateAttendance.builder()
                        .attendanceId(changeId)
                        .min(updateAttendanceFront.getMin())
                .build());
    }

    public void reload() throws IOException {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
    }

}
