package org.xtremebiker.jsfspring.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.xtremebiker.jsfspring.enums.ResultCode;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ResponseDto {

    Object data;

    ResultCode status;

    String message;
}
