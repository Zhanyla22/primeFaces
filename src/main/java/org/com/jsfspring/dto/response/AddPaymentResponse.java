package org.com.jsfspring.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * ответ при добавлении оплаты
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AddPaymentResponse {

    @NotNull
    Long id;

    @NotNull
    String uuid;

    @NotNull
    @JsonProperty("user_first_name")
    String userFirstName;

    @NotNull
    LocalDate date;

    @NotNull
    Long sum;
}
