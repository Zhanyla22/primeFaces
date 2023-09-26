package org.com.jsfspring.service;

import org.com.jsfspring.dto.request.AddDelayRequest;
import org.com.jsfspring.dto.request.UpdateAttendanceRequest;
import org.com.jsfspring.dto.response.AddAttendResponse;
import org.com.jsfspring.dto.response.AllAttendanceResponse;
import org.com.jsfspring.dto.response.LoanHistoryResponse;
import org.com.jsfspring.enums.Status;

import java.util.List;
import java.util.UUID;


public interface AttendRecordService {

    AddAttendResponse addDelayMin(AddDelayRequest addDelayDto);

    List<AllAttendanceResponse> getAllAttendance(Status status);

    String deleteById(UUID uuid); //TODO: уточнить что возвращать

    UpdateAttendanceRequest updateAttendanceById(UpdateAttendanceRequest updateAttendanceRequest);

    List<AllAttendanceResponse> getAllAttendanceByUser(String token);

    Long getAllSumByUser(String token); //TODO: можно ли возвращать примю тип данных одних

    Long getAllSum(); //TODO: ?

    Long allBalanceSum(); //TODO: ?

    Long getBalanceByUser(String token); //TODO: ?

    List<LoanHistoryResponse> getAllLoanHistory();

    Long getSumRestLeft(); //TODO: ?

    Long getCurrentUsersLeft(String token); //TODO: ?
}
