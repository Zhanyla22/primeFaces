package org.com.jsfspring.service;

import java.time.LocalDate;

public interface PaymentService {

    Long getId();

    String getFirstName();

    LocalDate getCreatedDate();

    Long getSum();
}
