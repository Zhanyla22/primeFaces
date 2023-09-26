package org.com.jsfspring.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.com.jsfspring.entity.AttendRecord;
import org.com.jsfspring.enums.Status;
import org.com.jsfspring.service.JoinDtoService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Репозиторий для манипуляции записями опозданий
 */
@Repository
public interface AttendanceRepo extends JpaRepository<AttendRecord, Long> {

    /**
     * Ищет последний запись по created_date(для логики добавления опозданий)
     * @param id
     * @return AttendRecord
     */
    @Query(value = "SELECT * FROM attendance a WHERE a.user_entity_id = ?1 AND status = 'ACTIVE' AND streak!=0 ORDER BY a.created_date desc LIMIT 1 ", nativeQuery = true)
    Optional<AttendRecord> findLast(Long id);

    /**
     * для получения всех активных записей опозданий и сортировка по attend_date в порядке убывания
     * @param status
     * @return лист активных записей опозданий - AttendRecord
     */
    @Query(value = "SELECT * FROM attendance\n" +
            "WHERE status = 'ACTIVE'\n" +
            "AND streak !=0\n" +
            "ORDER BY attend_date DESC",nativeQuery = true)
    List<AttendRecord> findAttendRecordsByStatusOrderByAttendDateDesc(Status status);

    /**
     * Для получения всех активных записей опозданий пользователя и сортировка по attend_date в порядке убывания
     * @param userId
     * @return лист активных записей опозданий - AttendRecord
     */
    @Query(value = "SELECT *\n" +
            "FROM attendance a\n" +
            "WHERE status = 'ACTIVE'\n" +
            "  AND user_entity_id = ?1\n" +
            "  AND streak != 0\n" +
            "ORDER BY attend_date DESC", nativeQuery = true)
    List<AttendRecord> findAttendRecordsByUserEntityIdAndStatusOrderByAttendDateDesc(Long userId);

    /**
     * Сколько пользователь задолжил опоздывая на работу(общяя сумма в сомах)
     * @param userId
     * @return long sum
     */
    @Query(value = "SELECT SUM(a.money) FROM attendance a WHERE a.user_entity_id = ?1 AND status = 'ACTIVE' ", nativeQuery = true)
    Long sumOfMoneyByUserIdAndStatus(Long userId);

    /**
     * Общяя сумма задолжностей всех работников(в сомах
     * * @return long sum
     */
    @Query(value = "SELECT SUM(a.money)  FROM attendance a WHERE status = 'ACTIVE';", nativeQuery = true)
    Long getAllSum();

    /**
     * для получения всех активных записей по оплатам и остаткам задолженностей
     * JOIN users(кто) - attendance(общяя сумма задолженности) - payment(общяя сумма оплаты)
     * @return balance = сколько оплачено,
     *         firstName = имя работника,
     *         money = общяя задолженность в сомах
     *         userId = id работника
     */
    @Query(value = "SELECT p.sum     as balance,\n" +
            "       u.first_name     as firstName,\n" +
            "       SUM(a.money)     as money,\n" +
            "       a.user_entity_id as userId\n" +
            "FROM attendance a\n" +
            "         JOIN users u ON a.user_entity_id = u.id\n" +
            "         JOIN (SELECT sum(p.sum) as sum, p.user_entity_id FROM payment p GROUP BY p.user_entity_id) as p\n" +
            "              on u.id = p.user_entity_id\n" +
            "WHERE a.status = 'ACTIVE'\n" +
            "GROUP BY u.first_name, a.user_entity_id, u.first_name, p.user_entity_id, p.sum\n", nativeQuery = true)
    List<JoinDtoService> getJoinedDatas();

    /**
     * для поиска записи по uuid
     * @param uuid
     * @return информация о записе опоздания
     */
    Optional<AttendRecord> findByUuid(UUID uuid);

    boolean existsByAttendDate(LocalDate date);
}

