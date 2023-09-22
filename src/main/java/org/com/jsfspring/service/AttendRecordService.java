package org.com.jsfspring.service;

import org.com.jsfspring.dto.request.AddDelayDto;
import org.com.jsfspring.dto.request.UpdateAttendance;
import org.com.jsfspring.dto.response.AddAttendResponse;
import org.com.jsfspring.dto.response.AllAttendance;
import org.com.jsfspring.dto.response.LoanHistory;
import org.com.jsfspring.enums.Status;

import java.util.List;
import java.util.UUID;


public interface AttendRecordService {

    AddAttendResponse addDelayMin(AddDelayDto addDelayDto);

    List<AllAttendance> getAllAttendance(Status status);

    String deleteById(UUID uuid); //TODO: уточнить что возвращать

    UpdateAttendance updateAttendanceById(UpdateAttendance updateAttendance);

    List<AllAttendance> getAllAttendanceByUser(String token);

    Long getAllSumByUser(String token); //TODO: можно ли возвращать примю тип данных одних

    Long getAllSum(); //TODO: ?

    Long allBalanceSum(); //TODO: ?

    Long getBalanceByUser(String token); //TODO: ?

    List<LoanHistory> getAllLoanHistory();

    Long getSumRestLeft(); //TODO: ?

    Long getCurrentUsersLeft(String token); //TODO: ?
}
