package org.xtremebiker.jsfspring.dto.response;


import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class JoinDto {

    Long money;

    Long userId;

    String firstName;

    Long balance;
}
