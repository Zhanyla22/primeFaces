package org.xtremebiker.jsfspring.service;

import org.xtremebiker.jsfspring.dto.request.AddDelayDto;
import org.xtremebiker.jsfspring.dto.request.UpdateAttendance;
import org.xtremebiker.jsfspring.dto.response.AddAttendResponse;
import org.xtremebiker.jsfspring.dto.response.AllAttendance;
import org.xtremebiker.jsfspring.dto.response.DelayUserDto;
import org.xtremebiker.jsfspring.dto.response.LoanHistory;
import org.xtremebiker.jsfspring.enums.Status;

import java.util.List;


public interface AttendRecordService {

    AddAttendResponse addDelayMin(AddDelayDto addDelayDto);

    List<DelayUserDto> getAllByUser(String userName);

    List<AllAttendance> getAllAttendance(Status status);

    String deleteById(Long id);

    UpdateAttendance updateAttendanceById(UpdateAttendance updateAttendance);

    List<AllAttendance> getAllAttendanceByUserId(Long userId);

    Long getAllSumByUserId(Long userId);

    Long getLoanByUserId(Long userId);

    Long getAllSum();

    Long getAllLoan();

    Long allBalanceSum();

    Long getBalanceByUserId(Long userId);

    List<LoanHistory> getAllLoanHistory();

    Long getSumRestLeft();
}
