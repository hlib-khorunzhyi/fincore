package com.hlibkhorunzhyi.fincore.account.service;

import com.hlibkhorunzhyi.fincore.account.dto.AccountResponse;
import com.hlibkhorunzhyi.fincore.account.dto.CreateAccountRequest;
import com.hlibkhorunzhyi.fincore.account.entity.Account;
import com.hlibkhorunzhyi.fincore.account.entity.AccountStatus;
import com.hlibkhorunzhyi.fincore.account.generator.AccountNumberGenerator;
import com.hlibkhorunzhyi.fincore.account.repository.AccountRepository;
import com.hlibkhorunzhyi.fincore.exception.AccountAlreadyClosedException;
import com.hlibkhorunzhyi.fincore.exception.AccountHasBalanceException;
import com.hlibkhorunzhyi.fincore.exception.AccountNotFoundException;
import com.hlibkhorunzhyi.fincore.exception.UserNotFoundException;
import com.hlibkhorunzhyi.fincore.user.entity.User;
import com.hlibkhorunzhyi.fincore.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final AccountNumberGenerator accountNumberGenerator;

    public AccountServiceImpl(AccountRepository accountRepository, UserRepository userRepository, AccountNumberGenerator accountNumberGenerator) {
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
        this.accountNumberGenerator = accountNumberGenerator;
    }

    @Override
    public AccountResponse getAccount(Long id) {
        Account account = accountRepository.findById(id).orElseThrow(
                () -> new AccountNotFoundException(id)
        );

        return AccountResponse.from(account);
    }

    @Override
    public List<AccountResponse> getAccountsByUserId(Long userId) {
        return accountRepository.findAllByUserId(userId).stream().map(AccountResponse::from).toList();
    }

    @Override
    public List<AccountResponse> getAccountsByUserIdAndStatus(Long userId, AccountStatus status) {
        return accountRepository.findAllByUserIdAndStatus(userId, status).stream().map(AccountResponse::from).toList();
    }

    @Override
    @Transactional
    public AccountResponse createAccount(CreateAccountRequest request) {
        User user = userRepository.findById(request.userId())
                .orElseThrow(() -> new UserNotFoundException(request.userId()));

        String accountNumber;

        do {
            accountNumber = accountNumberGenerator.generate();
        } while (accountRepository.existsByAccountNumber(accountNumber));

        Account account = new Account();

        account.setUser(user);
        account.setAccountNumber(accountNumber);
        account.setCurrency(request.currency());

        Account saved = accountRepository.save(account);

        return AccountResponse.from(saved);
    }

    @Override
    @Transactional
    public void closeAccount(Long id) {
        Account account = accountRepository.findById(id).orElseThrow(
                () -> new AccountNotFoundException(id)
        );

        if (account.getStatus() == AccountStatus.CLOSED)
            throw new AccountAlreadyClosedException(id);

        if (account.getBalance().compareTo(BigDecimal.ZERO) != 0)
            throw new AccountHasBalanceException(id);

        account.setStatus(AccountStatus.CLOSED);
    }
}
