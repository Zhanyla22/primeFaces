package org.com.jsfspring.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotNull;

/**
 * для получения данных о задолженности, сколько оплатил и сколько осталось(в сомах)
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LoanHistoryResponse {

    @NotNull
    @JsonProperty("user_id")
    Long userId;

    @NotNull
    @JsonProperty("user_name")
    String userName;

    @NotNull
    @JsonProperty("loan_sum")
    Long loanSum;

    @NotNull
    @JsonProperty("balance_sum")
    Long balanceSum;

    @NotNull
    Long left;
}
