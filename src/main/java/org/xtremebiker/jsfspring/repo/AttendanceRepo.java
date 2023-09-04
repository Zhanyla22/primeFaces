package org.xtremebiker.jsfspring.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.xtremebiker.jsfspring.entity.AttendRecord;
import org.xtremebiker.jsfspring.enums.Status;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface AttendanceRepo extends JpaRepository<AttendRecord, Long> {

    boolean existsByUserEntityIdAndAttendDate(Long id, LocalDate dateTime);

    @Query(value = "SELECT * FROM attendance a WHERE a.user_entity_id = ?1 AND status = 'ACTIVE' ORDER BY a.created_date desc LIMIT 1 ", nativeQuery = true)
    Optional<AttendRecord> findLast(Long id);


    List<AttendRecord> getAttendRecordsByUserEntityUserNameOrderByAttendDateDesc(String userName);


    List<AttendRecord> findAttendRecordsByStatusOrderByAttendDateDesc(Status status);

    List<AttendRecord> findAttendRecordsByUserEntityIdAndStatus(Long userId, Status status);

    @Query(value = "SELECT SUM(a.money) WHERE a.user_entity_id = ?1 AND status = 'ACTIVE' FROM attendance a", nativeQuery = true)
    Long sumOfMoneyByUserIdAndStatus(Long userId);

    @Query(value = "SELECT SUM(a.money) WHERE status = 'ACTIVE' FROM attendance a", nativeQuery = true)
    Long getAllSum();
}
