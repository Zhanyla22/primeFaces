package org.xtremebiker.jsfspring.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthenticationResponse {

    String jwtToken;

    Date dateExpiredAccessToken;

    Date dateExpiredRefreshToken;

    String refreshToken;
}
