package org.com.jsfspring.mapper;

import org.com.jsfspring.entity.AttendRecord;
import org.springframework.stereotype.Service;
import org.com.jsfspring.dto.response.AddAttendResponse;
import org.com.jsfspring.dto.response.AllAttendance;
import org.com.jsfspring.dto.response.LoanHistory;
import org.com.jsfspring.service.JoinDtoService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AttendRecordMapper {

    public static LoanHistory entityToLoanDto(JoinDtoService attendRecord) {
        return LoanHistory.builder()
                .userId(attendRecord.getUserId())
                .userName(attendRecord.getFirstName())
                .loanSum(attendRecord.getMoney())
                .balanceSum(attendRecord.getBalance())
                .left(attendRecord.getMoney() - attendRecord.getBalance())
                .build();
    }

    public static List<LoanHistory> entityToListLoan(List<JoinDtoService> joinDtos) {
        return joinDtos.stream().map(AttendRecordMapper::entityToLoanDto).collect(Collectors.toList());
    }

    public static AddAttendResponse entityToAttendResponse(AttendRecord attendRecord) {
        return AddAttendResponse.builder()
                .userId(attendRecord.getUserEntity().getId())
                .delayInMin(attendRecord.getDelayInMin())
                .attendDate(attendRecord.getAttendDate())
                .money(attendRecord.getMoney())
                .streak(attendRecord.getStreak())
                .uuid(attendRecord.getUuid().toString())
                .build();
    }

    public static AllAttendance entityToDto(AttendRecord attendRecord) {
        return AllAttendance.builder()
                .attendanceId(attendRecord.getId())
                .dateAttendance(attendRecord.getAttendDate())
                .userName(attendRecord.getUserEntity().getFirstName())
                .delayInMin(attendRecord.getDelayInMin())
                .money(attendRecord.getMoney())
                .streak(attendRecord.getStreak())
                .build();
    }

    public static List<AllAttendance> entityListToDtoList(List<AttendRecord> attendRecords) {
        return attendRecords.stream().map(AttendRecordMapper::entityToDto).collect(Collectors.toList());
    }
}
