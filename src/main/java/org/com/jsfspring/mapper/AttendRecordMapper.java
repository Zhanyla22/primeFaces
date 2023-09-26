package org.com.jsfspring.mapper;

import org.com.jsfspring.entity.AttendRecord;
import org.springframework.stereotype.Service;
import org.com.jsfspring.dto.response.AddAttendResponse;
import org.com.jsfspring.dto.response.AllAttendanceResponse;
import org.com.jsfspring.dto.response.LoanHistoryResponse;
import org.com.jsfspring.service.JoinDtoService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * маппер для оброботки записей опоздания
 */
@Service
public class AttendRecordMapper {

    /**
     * @param attendRecord
     * @return LoanHistoryResponse
     */
    public static LoanHistoryResponse entityToLoanDto(JoinDtoService attendRecord) {
        return LoanHistoryResponse.builder()
                .userId(attendRecord.getUserId())
                .userName(attendRecord.getFirstName())
                .loanSum(attendRecord.getMoney())
                .balanceSum(attendRecord.getBalance())
                .left(attendRecord.getMoney() - attendRecord.getBalance())
                .build();
    }

    /**
     *
     * @param joinDtos
     * @return Лист из LoanHistoryResponse
     */
    public static List<LoanHistoryResponse> entityToListLoan(List<JoinDtoService> joinDtos) {
        return joinDtos.stream().map(AttendRecordMapper::entityToLoanDto).collect(Collectors.toList());
    }

    /**
     *
     * @param attendRecord
     * @return AddAttendResponse
     */
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

    /**
     * @param attendRecord
     * @return AllAttendanceResponse
     */
    public static AllAttendanceResponse entityToDto(AttendRecord attendRecord) {
        return AllAttendanceResponse.builder()
                .attendanceId(attendRecord.getId())
                .dateAttendance(attendRecord.getAttendDate())
                .userName(attendRecord.getUserEntity().getFirstName())
                .delayInMin(attendRecord.getDelayInMin())
                .money(attendRecord.getMoney())
                .streak(attendRecord.getStreak())
                .build();
    }

    /**
     * @param attendRecords
     * @return List из AllAttendanceResponse
     */
    public static List<AllAttendanceResponse> entityListToDtoList(List<AttendRecord> attendRecords) {
        return attendRecords.stream().map(AttendRecordMapper::entityToDto).collect(Collectors.toList());
    }
}
