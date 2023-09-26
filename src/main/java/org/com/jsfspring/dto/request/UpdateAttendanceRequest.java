package org.com.jsfspring.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotNull;

/**
 * dto для обновления записи  опоздания
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateAttendanceRequest {

    @NotNull
    @JsonProperty("attendance_id")
    Long attendanceId;

    @NotNull
    Integer min;
}
