package org.com.jsfspring.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotNull;

/**
 * dto для добавления оплаты -reqyest
 */

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AddNewPaymentRequest {

    @NotNull
    @JsonProperty("user_id")
    Long userId;

    @NotNull
    Long money;
}
