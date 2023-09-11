package org.xtremebiker.jsfspring.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.xtremebiker.jsfspring.entity.Payment;
import org.xtremebiker.jsfspring.service.PaymentService;

import java.util.List;

@Repository
public interface PaymentRepo extends JpaRepository<Payment, Long> {

    @Query(value = "SELECT SUM(p.sum) FROM payment p", nativeQuery = true)
    Long getAllBalance();

    @Query(value = "SELECT SUM(p.sum) FROM payment p WHERE p.user_entity_id = ?1", nativeQuery = true)
    Long getBalanceByUserId(Long userId);

    @Query(value = "SELECT p.created_date as createdDate, u.first_name as firstName, u.id as id, p.sum as sum\n" +
            "FROM payment p\n" +
            "         JOIN users u on p.user_entity_id = u.id\n" +
            "WHERE sum != 0\n" +
            "GROUP BY u.id, u.first_name, p.created_date, p.sum\n" +
            "ORDER BY p.created_date desc", nativeQuery = true)
    List<PaymentService> getAllPayment();

    @Query(value = "SELECT p.created_date as createdDate, u.first_name as firstName, u.id as id, p.sum as sum\n" +
            "FROM payment p\n" +
            "         JOIN users u on p.user_entity_id = u.id\n" +
            "WHERE u.id = ?1\n AND sum !=0" +
            "GROUP BY u.id, u.first_name, p.created_date, p.sum\n" +
            "ORDER BY p.created_date desc", nativeQuery = true)
    List<PaymentService> getAllPaymentByUserId(Long userId);
}
