package org.com.jsfspring.service;

import java.time.LocalDate;

/**
 * В PaymentRepo ответ от соединенных таблиц  присваивается сюда,
 * так как отсутсвует именно такая таблица с такими полями
 */
public interface PaymentService {

    Long getId();

    String getFirstName();

    LocalDate getCreatedDate();

    Long getSum();
}
