package org.xtremebiker.jsfspring.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.xtremebiker.jsfspring.entity.AttendRecord;
import org.xtremebiker.jsfspring.enums.Status;
import org.xtremebiker.jsfspring.service.JoinDtoService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AttendanceRepo extends JpaRepository<AttendRecord, Long> {

    boolean existsByUserEntityIdAndAttendDate(Long id, LocalDate dateTime);

    @Query(value = "SELECT * FROM attendance a WHERE a.user_entity_id = ?1 AND status = 'ACTIVE' AND streak!=0 ORDER BY a.created_date desc LIMIT 1 ", nativeQuery = true)
    Optional<AttendRecord> findLast(Long id);

    List<AttendRecord> getAttendRecordsByUserEntityUserNameOrderByAttendDateDesc(String userName);

    @Query(value = "SELECT * FROM attendance\n" +
            "WHERE status = 'ACTIVE'\n" +
            "AND streak !=0\n" +
            "ORDER BY attend_date DESC",nativeQuery = true)
    List<AttendRecord> findAttendRecordsByStatusOrderByAttendDateDesc(Status status);

    @Query(value = "SELECT *\n" +
            "FROM attendance a\n" +
            "WHERE status = 'ACTIVE'\n" +
            "  AND user_entity_id = ?1\n" +
            "  AND streak != 0\n" +
            "ORDER BY attend_date DESC", nativeQuery = true)
    List<AttendRecord> findAttendRecordsByUserEntityIdAndStatusOrderByAttendDateDesc(Long userId);

    @Query(value = "SELECT SUM(a.money) FROM attendance a WHERE a.user_entity_id = ?1 AND status = 'ACTIVE' ", nativeQuery = true)
    Long sumOfMoneyByUserIdAndStatus(Long userId);

    @Query(value = "SELECT SUM(a.money)  FROM attendance a WHERE status = 'ACTIVE';", nativeQuery = true)
    Long getAllSum();

    @Query(value = "SELECT p.sum     as balance,\n" +
            "       u.first_name     as firstName,\n" +
            "       SUM(a.money)     as money,\n" +
            "       a.user_entity_id as userId\n" +
            "FROM attendance a\n" +
            "         JOIN users u ON a.user_entity_id = u.id\n" +
            "         Join (SELECT sum(p.sum) as sum, p.user_entity_id FROM payment p GROUP BY p.user_entity_id) as p\n" +
            "              on u.id = p.user_entity_id\n" +
            "WHERE a.status = 'ACTIVE'\n" +
            "GROUP BY u.first_name, a.user_entity_id, u.first_name, p.user_entity_id, p.sum\n", nativeQuery = true)
    List<JoinDtoService> getJoinedDatas();

    Optional<AttendRecord> findByUuid(UUID uuid);
}

