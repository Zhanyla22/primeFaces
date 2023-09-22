package org.com.jsfspring.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.com.jsfspring.entity.base.BaseEntity;
import org.com.jsfspring.enums.Status;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "attendance")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AttendRecord extends BaseEntity {

    @Column(name = "delay_in_min")
    Integer delayInMin;

    @Column(name = "attend_date")
    LocalDate attendDate;

    @Column(name = "streak")
    Integer streak;

    @Column(name = "money")
    Long money;

    @ManyToOne(optional = false)
    @JoinColumn(columnDefinition = "user_id",
            referencedColumnName = "id")
    UserEntity userEntity;

    @Enumerated(EnumType.STRING)
    Status status;
}