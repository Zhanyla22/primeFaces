package org.xtremebiker.jsfspring.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.xtremebiker.jsfspring.entity.base.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "payment")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Payment extends BaseEntity {

    Long sum;

    @ManyToOne(optional = false)
    @JoinColumn(columnDefinition = "user_id",
            referencedColumnName = "id")
    UserEntity userEntity;
}
