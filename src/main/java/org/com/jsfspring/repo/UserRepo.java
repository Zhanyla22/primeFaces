package org.com.jsfspring.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.com.jsfspring.entity.UserEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByUserName(String username);

    @Query(value = "SELECT * FROM users where role='ROLE_USER'", nativeQuery = true)
    List<UserEntity> findAll();

    boolean existsByUserName(String userName);
}
