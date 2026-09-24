package com.hlibkhorunzhyi.fincore.account.repository;

import com.hlibkhorunzhyi.fincore.account.entity.Account;
import com.hlibkhorunzhyi.fincore.account.entity.AccountStatus;
import com.hlibkhorunzhyi.fincore.user.entity.User;
import jakarta.persistence.LockModeType;
import org.jspecify.annotations.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Account> findByIdAndUserId(Long id, Long userId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT a FROM Account a WHERE a.id = :id")
    Optional<Account> findByIdForUpdate(@Param("id") Long id);

    List<Account> findAllByUserId(Long userId);

    List<Account> findAllByUserIdAndStatus(Long userId, AccountStatus status);

    boolean existsByAccountNumber(String accountNumber);

    Long user(User user);
}
