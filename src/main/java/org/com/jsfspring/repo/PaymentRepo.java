package org.com.jsfspring.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.com.jsfspring.entity.Payment;
import org.com.jsfspring.service.PaymentService;

import java.util.List;

/**
 * Репозиторий для манипуляции записями оплат
 */
@Repository
public interface PaymentRepo extends JpaRepository<Payment, Long> {

    /**
     * Получение суммы оплат всех работников(в сомах)
     * @return long sum
     */
    @Query(value = "SELECT SUM(p.sum) FROM payment p", nativeQuery = true)
    Long getAllBalance();

    /**
     * Получение суммы оплат одного работника(в сомах)
     * @param userId
     * @return long sum
     */
    @Query(value = "SELECT SUM(p.sum) FROM payment p WHERE p.user_entity_id = ?1", nativeQuery = true)
    Long getBalanceByUserId(Long userId);

    /**
     * Для получения всех записей оплаты сотрудников сортированный по created_date в порядке убывания
     * RIGHT JOIN - Для отображения сотрудников без оплаты тоже сo значениями null
     * @return createdDate -  дата создания,
     *         firstName - имя сотрудника,
     *         id = id оплаты
     *         sum - сумма оплаты
     */
    @Query(value = "SELECT p.created_date as createdDate, u.first_name as firstName, u.id as id, p.sum as sum\n" +
            "FROM payment p\n" +
            "         RIGHT JOIN users u on p.user_entity_id = u.id\n" +
            "GROUP BY u.id, u.first_name, p.created_date, p.sum\n" +
            "ORDER BY p.created_date desc;", nativeQuery = true)
    List<PaymentService> getAllPayment();

    /**
     * Для получения всех записей оплаты одного сотрудниеп,сортированный по created_date в порядке убывания
     * RIGHT JOIN - Для отображения сотрудников без оплаты тоже сo значениями null
     * @param userId
     * @return
     * createdDate -  дата создания,
     *             firstName - имя сотрудника,
     *             id = id оплаты
     *             sum - сумма оплаты
     */
    @Query(value = "SELECT p.created_date as createdDate, u.first_name as firstName, u.id as id, p.sum as sum\n" +
            "FROM payment p\n" +
            "         RIGHT JOIN users u on p.user_entity_id = u.id\n" +
            "where u.id = ?1\n" +
            "GROUP BY u.id, u.first_name, p.created_date, p.sum\n" +
            "ORDER BY p.created_date desc", nativeQuery = true)
    List<PaymentService> getAllPaymentByUserId(Long userId);
}
