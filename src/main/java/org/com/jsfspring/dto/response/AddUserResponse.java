package org.com.jsfspring.dto.response;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * Ответ при добавлении пользователя
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AddUserResponse {

    @NotNull
    Long id;

    @NotNull
    String uuid;

    @NotNull
    @JsonProperty("user_name")
    String userName;

    @JsonProperty("first_name")
    String firstName;

    @JsonProperty("last_name")
    String lastName;

    @NotNull
    LocalDate date;
}
