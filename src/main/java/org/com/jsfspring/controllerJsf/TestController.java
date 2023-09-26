package org.com.jsfspring.controllerJsf;

import org.com.jsfspring.dto.request.AddDelayRequest;
import org.com.jsfspring.dto.request.UpdateAttendanceRequest;
import org.com.jsfspring.dto.response.AllAttendanceResponse;
import org.com.jsfspring.dto.response.LoanHistoryResponse;
import org.com.jsfspring.dto.request.SaveDto;
import org.com.jsfspring.enums.Status;
import org.com.jsfspring.service.AttendRecordService;
import org.com.jsfspring.service.impl.UserDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Component
@Scope("view")
public class TestController {

    @Autowired
    private AttendRecordService attendRecordService;

    @Autowired
    private UserDetailServiceImpl userDetailService;

    private SaveDto saveDto;

    private AllAttendanceResponse allAttendanceResponse;

    private Integer min;

    private Long changeId;

    private LoanHistoryResponse loanHistoryResponse;

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
        allAttendanceResponse = new AllAttendanceResponse();
        saveDto = new SaveDto();
        loanHistoryResponse = new LoanHistoryResponse();
    }

    public SaveDto getSaveDto() {
        return saveDto;
    }

    public AllAttendanceResponse getAllAttendance() {
        return allAttendanceResponse;
    }

    public LoanHistoryResponse getLoanHistory() {
        return loanHistoryResponse;
    }

//    public List<AllAttendance> getAllAttendanceByUserId() {
//        UserEntity userEntity = userDetailService.getCurrentUser();
//        return attendRecordService.getAllAttendanceByUser();
//    }

    public List<AllAttendanceResponse> getAllAttendances() {
        return attendRecordService.getAllAttendance(Status.ACTIVE);
    }

    public List<LoanHistoryResponse> getAllLoanHistory() {
        return attendRecordService.getAllLoanHistory();
    }

//    public String getCurrentName() {
//        String name = userDetailService.getCurrentUser().getFirstName();
//        return name;
//    }

//    public Long getCurrentSumOfLoan() {
//        Long userId = userDetailService.getCurrentUser().getId();
//        return attendRecordService.getAllSumByUser();
//    }
//
//    public Long getCurrentBalanceOfUser() {
//        Long userId = userDetailService.getCurrentUser().getId();
//        return attendRecordService.getBalanceByUser();
//    }

//    public Long getCurrentUsersLeft() {
//        if (getCurrentSumOfLoan() != null) {
//            return getCurrentSumOfLoan() - getCurrentBalanceOfUser();
//        } else return 0L;
//    }

    public void delete(UUID uuid) {
        attendRecordService.deleteById(uuid);
    }

    public void save() {
        attendRecordService.addDelayMin(
                AddDelayRequest.builder()
                        .userId(saveDto.getUserId())
                        .date(saveDto.getDate())
                        .delayInMin(saveDto.getMin())
                        .build()
        );
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Инфо", "Добавлено"));
    }

    public void update() {
        attendRecordService.updateAttendanceById(UpdateAttendanceRequest.builder()
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
