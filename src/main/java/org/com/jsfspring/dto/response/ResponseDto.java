package org.com.jsfspring.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.com.jsfspring.enums.ResultCode;

/**
 * Ответ на уровне контроллера - структура каждого ответа
 */
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
