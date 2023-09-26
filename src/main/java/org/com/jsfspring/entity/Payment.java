package org.com.jsfspring.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.com.jsfspring.entity.base.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Сущность для учета оплат
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "payment")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Payment extends BaseEntity {

    /**
     * Сумма оплаты
     */
    Long sum;

    /**
     * у пользователя может быть один и более оплат
     */
    @ManyToOne(optional = false)
    @JoinColumn(columnDefinition = "user_id",
            referencedColumnName = "id")
    UserEntity userEntity;
}
