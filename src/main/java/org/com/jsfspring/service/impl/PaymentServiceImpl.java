package org.com.jsfspring.service.impl;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.com.jsfspring.service.PaymentService;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Реализация методов payment
 */
@AllArgsConstructor
@NoArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private Long id;
    private String firstName;
    private LocalDate createdDate;
    private Long sum;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public String getFirstName() {
        return firstName;
    }

    @Override
    public LocalDate getCreatedDate() {
        return createdDate;
    }

    @Override
    public Long getSum() {
        return sum;
    }
}
