package org.xtremebiker.jsfspring.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Scope;

import javax.faces.bean.ManagedBean;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@ManagedBean(name = "loginDto")
@Scope
public class LoginRequest {

    String userName;

    String password;
}
