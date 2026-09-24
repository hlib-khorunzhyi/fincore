package com.hlibkhorunzhyi.fincore.transfer.repository;

import com.hlibkhorunzhyi.fincore.transfer.entity.Transfer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransferRepository extends JpaRepository<Transfer, Long> {


    @Query("""
            SELECT t
            FROM Transfer t
            WHERE t.sourceAccount.id = :accountId
            OR t.destinationAccount.id = :accountId
            ORDER BY t.createdAt DESC
            """)
    Page<Transfer> findAllByAccountId(
            @Param("accountId") Long accountId,
            Pageable pageable
    );

    @Query("""
                SELECT t
                FROM Transfer t
                    WHERE t.id = :transferId
                        AND (
                            t.sourceAccount.user.id = :userId
                            OR t.destinationAccount.user.id = :userId
                            )
            """)
    Optional<Transfer> findByIdAndUserId(
            @Param("transferId") Long transferId,
            @Param("userId") Long userId);
}
