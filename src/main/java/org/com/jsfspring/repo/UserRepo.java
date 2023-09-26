package org.com.jsfspring.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.com.jsfspring.entity.UserEntity;

import java.util.List;
import java.util.Optional;

/**
 * Репозиторий для манипуляции данными пользователя
 */
@Repository
public interface UserRepo extends JpaRepository<UserEntity, Long> {

    /**
     * искать по userName
     * @param username
     * @return лист из UserEntity
     */
    Optional<UserEntity> findByUserName(String username);

    /**
     * получить всех пользователей с ролем ROLE_USER
     * @return лист из UserEntity
     */
    @Query(value = "SELECT * FROM users where role='ROLE_USER'", nativeQuery = true)
    List<UserEntity> findAll();

    /**
     * проверка если существует такой пользователь с Username
     * @param userName
     * @return если существует - true или если не существует - false
     */
    boolean existsByUserName(String userName);
}
