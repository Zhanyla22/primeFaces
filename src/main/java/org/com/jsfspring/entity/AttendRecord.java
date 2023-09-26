package org.com.jsfspring.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.com.jsfspring.entity.base.BaseEntity;
import org.com.jsfspring.enums.Status;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * Сущность для работы с записями опозданий
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "attendance")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AttendRecord extends BaseEntity {

    /**
     * Опоздание в минутах
     */
    @Column(name = "delay_in_min")
    Integer delayInMin;

    /**
     * Дата опоздания
     */
    @Column(name = "attend_date")
    LocalDate attendDate;

    /**
     * Количсество подряд опозданий
     */
    @Column(name = "streak")
    Integer streak;

    /**
     * Задолженность за опоздание в сомах
     */
    @Column(name = "money")
    Long money;

    /**
     * У пользователя могут быть один и более записей опозданий
     */
    @ManyToOne(optional = false)
    @JoinColumn(columnDefinition = "user_id",
            referencedColumnName = "id")
    UserEntity userEntity;

    /**
     * Статус записа
     */
    @Enumerated(EnumType.STRING)
    Status status;
}
