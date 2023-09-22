package org.com.jsfspring.dto.response;


import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AddUserResponse {

    Long id;

    String uuid;

    String userName;

    String firstName;

    String lastName;

    LocalDate date;
}
