package org.com.jsfspring.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * dto для добавления опоздания(request - запрос от фронта)
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AddDelayRequest {

    @NotNull
    @JsonProperty("user_id")
    Long userId;

    @NotNull
    LocalDate date;

    @NotNull
    @JsonProperty("delay_in_min")
    Integer delayInMin;
}
