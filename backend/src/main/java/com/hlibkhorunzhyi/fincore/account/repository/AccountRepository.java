package com.hlibkhorunzhyi.fincore.account.repository;

import com.hlibkhorunzhyi.fincore.account.entity.Account;
import com.hlibkhorunzhyi.fincore.account.entity.AccountStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByIdAndUserId(Long id, Long userId);

    List<Account> findAllByUserId(Long userId);

    List<Account> findAllByUserIdAndStatus(Long userId, AccountStatus status);

    boolean existsByAccountNumber(String accountNumber);
}
