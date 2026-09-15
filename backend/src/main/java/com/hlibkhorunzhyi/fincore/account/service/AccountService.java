package com.hlibkhorunzhyi.fincore.account.service;

import com.hlibkhorunzhyi.fincore.account.dto.AccountResponse;
import com.hlibkhorunzhyi.fincore.account.dto.CreateAccountRequest;
import com.hlibkhorunzhyi.fincore.account.entity.AccountStatus;

import java.util.List;

public interface AccountService {

    AccountResponse getAccount(Long id, Long userId);

    List<AccountResponse> getAccountsByUserId(Long userId);

    List<AccountResponse> getAccountsByUserIdAndStatus(Long userId, AccountStatus status);

    AccountResponse createAccount(Long usrId, CreateAccountRequest request);

    void closeAccount(Long id);
}
