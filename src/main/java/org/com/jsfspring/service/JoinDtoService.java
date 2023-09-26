package org.com.jsfspring.service;

/**
 * В AttendanceRepo ответ от соединенных таблиц  присваивается сюда,
 * так как отсутсвует именно такая таблица с такими полями
 */
public interface JoinDtoService {

    Long getMoney();

    Long getUserId();

    String getFirstName();

    Long getBalance();
}
