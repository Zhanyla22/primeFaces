package org.xtremebiker.jsfspring.service;

import org.xtremebiker.jsfspring.dto.request.AddDelayDto;
import org.xtremebiker.jsfspring.dto.request.UpdateAttendance;
import org.xtremebiker.jsfspring.dto.response.AddAttendResponse;
import org.xtremebiker.jsfspring.dto.response.AllAttendance;
import org.xtremebiker.jsfspring.dto.response.DelayUserDto;
import org.xtremebiker.jsfspring.dto.response.LoanHistory;
import org.xtremebiker.jsfspring.enums.Status;

import java.util.List;
import java.util.UUID;


public interface AttendRecordService {

    AddAttendResponse addDelayMin(AddDelayDto addDelayDto);

    List<DelayUserDto> getAllByUser(String userName);

    List<AllAttendance> getAllAttendance(Status status);

    String deleteById(UUID uuid);

    UpdateAttendance updateAttendanceById(UpdateAttendance updateAttendance);

    List<AllAttendance> getAllAttendanceByUser();

    Long getAllSumByUser();

    Long getLoanByUserId(Long userId);

    Long getAllSum();

    Long getAllLoan();

    Long allBalanceSum();

    Long getBalanceByUser();

    List<LoanHistory> getAllLoanHistory();

    Long getSumRestLeft();

    Long getCurrentUsersLeft();
}
