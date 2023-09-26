package org.com.jsfspring.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

/**
 * при создании записи опоздания чз JSF
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SaveDto {

    private Long userId;

    private Integer min;

    private LocalDate date;
}
