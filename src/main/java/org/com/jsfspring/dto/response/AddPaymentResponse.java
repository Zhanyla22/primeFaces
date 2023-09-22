package org.com.jsfspring.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AddPaymentResponse {

    Long id;

    String uuid;

    String userFirstName;

    LocalDate date;

    Long sum;
}
