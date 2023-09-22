package org.com.jsfspring.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AllAttendance {

    Long attendanceId;

    String userName;

    LocalDate dateAttendance;

    Integer delayInMin;

    Integer streak;

    Long money;
}
