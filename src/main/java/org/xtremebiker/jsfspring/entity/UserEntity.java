package org.xtremebiker.jsfspring.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.xtremebiker.jsfspring.entity.base.BaseEntity;
import org.xtremebiker.jsfspring.enums.Position;
import org.xtremebiker.jsfspring.enums.Role;

import javax.persistence.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserEntity extends BaseEntity {

    @Column(name = "user_name")
    String userName;

    @Column(name = "password")
    String password;

    @Column(name = "first_name")
    String firstName;

    @Column(name = "last_name")
    String lastName;

    @Enumerated(EnumType.STRING)
    Position position;

    @Enumerated(EnumType.STRING)
    Role role;
}
