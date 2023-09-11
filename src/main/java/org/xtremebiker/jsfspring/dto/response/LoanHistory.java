package org.xtremebiker.jsfspring.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LoanHistory {

    Long userId;

    String userName;

    Long loanSum;

    Long balanceSum;

    Long left;
}
