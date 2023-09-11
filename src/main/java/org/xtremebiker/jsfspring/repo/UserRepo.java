package org.xtremebiker.jsfspring.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.xtremebiker.jsfspring.entity.UserEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByUserName(String username);

    @Query(value = "SELECT SUM(u.money_on_balance) FROM users u", nativeQuery = true)
    Long getAllBalance();

    @Query(value = "SELECT * FROM users where role='ROLE_USER'", nativeQuery = true)
    List<UserEntity> findAll();

    boolean existsByUserName(String userName);
}
