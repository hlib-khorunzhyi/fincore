package com.hlibkhorunzhyi.fincore.account.controller;

import com.hlibkhorunzhyi.fincore.account.dto.AccountResponse;
import com.hlibkhorunzhyi.fincore.account.dto.CreateAccountRequest;
import com.hlibkhorunzhyi.fincore.account.service.AccountService;
import com.hlibkhorunzhyi.fincore.transfer.dto.TransferResponse;
import com.hlibkhorunzhyi.fincore.transfer.service.TransferService;
import com.hlibkhorunzhyi.fincore.user.entity.User;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/accounts")
public class AccountController {

    private final AccountService accountService;
    private final TransferService transferService;

    public AccountController(AccountService accountService, TransferService transferService) {
        this.accountService = accountService;
        this.transferService = transferService;
    }

    @GetMapping("/user/{user_id}")
    public ResponseEntity<List<AccountResponse>> getAccountsByUserId(@PathVariable("user_id") Long userId) {
        return ResponseEntity.ok(accountService.getAccountsByUserId(userId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountResponse> getAccountById(
            @PathVariable Long id,
            @AuthenticationPrincipal User user
    ) {
        return ResponseEntity.ok(accountService.getAccount(id, user.getId()));
    }

    @PostMapping
    public ResponseEntity<AccountResponse> createAccount(
            @AuthenticationPrincipal User user,
            @Valid @RequestBody CreateAccountRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(accountService.createAccount(user.getId(), request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> closeAccount(@PathVariable Long id) {
        accountService.closeAccount(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/transfers")
    public Page<TransferResponse> getTransfers(
            @PathVariable Long id,
            @AuthenticationPrincipal User user,
            Pageable pageable
    ) {
        return transferService.getTransfersByAccountId(id, user.getId(), pageable);
    }
}
