package org.com.jsfspring.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * ответ при успешном авторизации-аутентификации
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthenticationResponse {

    @NotNull
    @JsonProperty("jwt_token")
    String jwtToken;

    @NotNull
    @JsonProperty("date_expired_access_token")
    Date dateExpiredAccessToken;

    @NotNull
    @JsonProperty("date_expired_refresh_token")
    Date dateExpiredRefreshToken;

    @NotNull
    @JsonProperty("refresh_token")
    String refreshToken;
}
