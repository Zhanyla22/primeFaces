package org.xtremebiker.jsfspring.controllerJsf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.xtremebiker.jsfspring.dto.request.AddDelayDto;
import org.xtremebiker.jsfspring.dto.request.UpdateAttendance;
import org.xtremebiker.jsfspring.dto.response.AllAttendance;
import org.xtremebiker.jsfspring.dto.response.LoanHistory;
import org.xtremebiker.jsfspring.dto.response.SaveDto;
import org.xtremebiker.jsfspring.entity.UserEntity;
import org.xtremebiker.jsfspring.enums.Status;
import org.xtremebiker.jsfspring.service.AttendRecordService;
import org.xtremebiker.jsfspring.service.impl.UserDetailServiceImpl;

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

    @Autowired
    private  AttendRecordService attendRecordService;

    @Autowired
    private  UserDetailServiceImpl userDetailService;


    private SaveDto saveDto;

    private AllAttendance allAttendance;

    private Integer min;

    private Long changeId;

    private LoanHistory loanHistory;


    public Long getChangeId() {
        return changeId;
    }


    public void changeIdSet(Long id) {
        this.changeId = id;
    }

    public Integer getMin() {
        return min;
    }

    public void setMin(Integer min) {
        this.min = min;
    }


    @PostConstruct
    public void init() {
        allAttendance = new AllAttendance();
        saveDto = new SaveDto();
        loanHistory = new LoanHistory();
    }

    public SaveDto getSaveDto() {
        return saveDto;
    }

    public AllAttendance getAllAttendance() {
        return allAttendance;
    }

    public LoanHistory getLoanHistory() {
        return loanHistory;
    }

    public List<AllAttendance> getAllAttendanceByUserId() {
        UserEntity userEntity = userDetailService.getCurrentUser();
        return attendRecordService.getAllAttendanceByUserId(userEntity.getId());
    }

    public List<AllAttendance> getAllAttendances() {
        return attendRecordService.getAllAttendance(Status.ACTIVE);
    }

    public List<LoanHistory> getAllLoanHistory() {
        return attendRecordService.getAllLoanHistory();
    }

    public String getCurrentName() {
        String name = userDetailService.getCurrentUser().getFirstName();
        return name;
    }

    public Long getCurrentSumOfLoan() {
        Long userId = userDetailService.getCurrentUser().getId();
        return attendRecordService.getAllSumByUserId(userId);
    }

    public Long getCurrentBalanceOfUser() {
        Long userId = userDetailService.getCurrentUser().getId();
        return attendRecordService.getBalanceByUserId(userId);
    }

    public Long getCurrentUsersLeft() {
        if (getCurrentSumOfLoan() !=null) {
            return getCurrentSumOfLoan() - getCurrentBalanceOfUser();
        }
        else return 0L;
    }

    public void delete(Long id) {

        attendRecordService.deleteById(id);
    }

    public void save() {
        attendRecordService.addDelayMin(
                AddDelayDto.builder()
                        .userId(saveDto.getUserId())
                        .date(saveDto.getDate())
                        .delayInMin(saveDto.getMin())
                        .build()
        );
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Инфо", "Добавлено"));
    }

    public void update() {
        attendRecordService.updateAttendanceById(UpdateAttendance.builder()
                .attendanceId(changeId)
                .min(min)
                .build());
    }

    public void reload() throws IOException {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
    }

    public Long getAllSum() {
        return attendRecordService.getAllSum();
    }

    public Long getAllBalanceSum() {
        return attendRecordService.allBalanceSum();
    }

    public Long getLeftSum() {
        return attendRecordService.getAllSum() - attendRecordService.allBalanceSum();
    }
}
