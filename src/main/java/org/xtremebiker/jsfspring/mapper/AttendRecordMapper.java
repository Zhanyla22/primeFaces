package org.xtremebiker.jsfspring.mapper;

import org.springframework.stereotype.Service;
import org.xtremebiker.jsfspring.dto.response.AddAttendResponse;
import org.xtremebiker.jsfspring.dto.response.AllAttendance;
import org.xtremebiker.jsfspring.dto.response.DelayUserDto;
import org.xtremebiker.jsfspring.entity.AttendRecord;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AttendRecordMapper {

    public static DelayUserDto entityToDelayUserDto(AttendRecord attendRecord) {
        return DelayUserDto.builder()
                .delayDate(attendRecord.getAttendDate())
                .money(attendRecord.getMoney())
                .delayMin(attendRecord.getDelayInMin())
                .streak(attendRecord.getStreak())
                .build();
    }

    public static List<DelayUserDto> entityListToDelayUserDtoList(List<AttendRecord> attendRecords) {
        return attendRecords.stream().map(AttendRecordMapper::entityToDelayUserDto).collect(Collectors.toList());
    }

    public static AddAttendResponse entityToAttendResponse(AttendRecord attendRecord) {
        return AddAttendResponse.builder()
                .userId(attendRecord.getUserEntity().getId())
                .delayInMin(attendRecord.getDelayInMin())
                .attendDate(attendRecord.getAttendDate())
                .money(attendRecord.getMoney())
                .streak(attendRecord.getStreak())
                .build();
    }

    public static AllAttendance entityToDto(AttendRecord attendRecord) {
        return AllAttendance.builder()
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
