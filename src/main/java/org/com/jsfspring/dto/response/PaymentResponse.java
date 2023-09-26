package org.com.jsfspring.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * для получения данных об оплате
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaymentResponse {

    @NotNull
    Long id;

    @NotNull
    @JsonProperty("first_name")
    String firstName;

    @JsonProperty("created_date")
    LocalDate createdDate;

    Long sum;
}
