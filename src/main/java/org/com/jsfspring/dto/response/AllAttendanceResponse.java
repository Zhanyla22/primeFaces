package org.com.jsfspring.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * ответ при получении записи опоздания
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AllAttendanceResponse {

    @NotNull
    @JsonProperty("attendance_id")
    Long attendanceId;

    @NotNull
    @JsonProperty("user_name")
    String userName;

    @NotNull
    @JsonProperty("date_attendance")
    LocalDate dateAttendance;

    @NotNull
    @JsonProperty("delay_in_min")
    Integer delayInMin;

    @NotNull
    Integer streak;

    @NotNull
    Long money;
}
