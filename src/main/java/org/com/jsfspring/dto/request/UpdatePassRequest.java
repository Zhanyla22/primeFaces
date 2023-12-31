package org.com.jsfspring.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotNull;

/**
 * dto для обновления пароля-для пользователя
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdatePassRequest {

    @NotNull
    @JsonProperty("old_password")
    String oldPassword;

    @NotNull
    @JsonProperty("new_password")
    String newPassword;
}
