package org.com.jsfspring.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * ответ при добавлении опоздания
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AddAttendResponse {

    @NotNull
    @JsonProperty("user_id")
    Long userId;

    @NotNull
    @JsonProperty("attend_date")
    LocalDate attendDate;

    @NotNull
    @JsonProperty("delay_in_min")
    Integer delayInMin;

    @NotNull
    Integer streak;

    @NotNull
    Long money;

    @NotNull
    String uuid;
}
