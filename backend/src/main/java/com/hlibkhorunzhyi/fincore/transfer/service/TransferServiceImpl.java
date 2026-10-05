package com.hlibkhorunzhyi.fincore.transfer.service;

import com.hlibkhorunzhyi.fincore.account.entity.Account;
import com.hlibkhorunzhyi.fincore.account.entity.AccountStatus;
import com.hlibkhorunzhyi.fincore.account.repository.AccountRepository;
import com.hlibkhorunzhyi.fincore.commission.model.Commission;
import com.hlibkhorunzhyi.fincore.commission.service.CommissionService;
import com.hlibkhorunzhyi.fincore.exceptions.exception.*;
import com.hlibkhorunzhyi.fincore.exchange.service.ExchangeRateService;
import com.hlibkhorunzhyi.fincore.transfer.dto.TransferRequest;
import com.hlibkhorunzhyi.fincore.transfer.dto.TransferResponse;
import com.hlibkhorunzhyi.fincore.transfer.entity.Transfer;
import com.hlibkhorunzhyi.fincore.transfer.repository.TransferRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class TransferServiceImpl implements TransferService {

    private final TransferRepository transferRepository;
    private final AccountRepository accountRepository;
    private final CommissionService commissionService;
    private final ExchangeRateService exchangeRateService;

    public TransferServiceImpl(TransferRepository transferRepository, AccountRepository accountRepository, CommissionService commissionService, ExchangeRateService exchangeRateService) {
        this.transferRepository = transferRepository;
        this.accountRepository = accountRepository;
        this.commissionService = commissionService;
        this.exchangeRateService = exchangeRateService;
    }

    @Override
    @Transactional(readOnly = true)
    public TransferResponse getTransfer(Long transferId, Long userId) {

        Transfer transfer = transferRepository.findByIdAndUserId(transferId, userId).orElseThrow(() ->
                new TransferNotFoundException(transferId)
        );

        return TransferResponse.from(transfer);
    }

    @Override
    public Page<TransferResponse> getTransfersByAccountId(Long accountId, Long userId, Pageable pageable) {

        accountRepository.findByIdAndUserId(accountId, userId)
                .orElseThrow(() -> new TransferNotFoundException(accountId));

        return transferRepository.findAllByAccountId(accountId, pageable).map(TransferResponse::from);
    }


    @Override
    @Transactional
    public TransferResponse transfer(TransferRequest request, Long userId) {

        Account sourceAccount;
        Account destinationAccount;

        int comparison = request.sourceAccountNumber()
                .compareTo(request.destinationAccountNumber());

        if (comparison == 0) {
            throw new InvalidTransferException();
        } else if (comparison < 0) {
            sourceAccount = accountRepository.findByAccountNumberAndUserId(request.sourceAccountNumber(), userId).orElseThrow(() ->
                    new AccountNotFoundException(request.sourceAccountNumber())
            );

            destinationAccount = accountRepository.findByAccountNumberForUpdate(request.destinationAccountNumber()).orElseThrow(() ->
                    new AccountNotFoundException((request.destinationAccountNumber()))
            );
        } else {
            destinationAccount = accountRepository.findByAccountNumberForUpdate(request.destinationAccountNumber()).orElseThrow(() ->
                    new AccountNotFoundException((request.destinationAccountNumber()))
            );

            sourceAccount = accountRepository.findByAccountNumberAndUserId(request.sourceAccountNumber(), userId).orElseThrow(() ->
                    new AccountNotFoundException(request.sourceAccountNumber())
            );
        }

        Commission fee = commissionService.calculate(sourceAccount, destinationAccount, request.amount());
        BigDecimal totalDebit = request.amount().add(fee.amount());

        // TODO: In future, add bank account where fee will be sent

        if (sourceAccount.getStatus() != AccountStatus.ACTIVE)
            throw new AccountNotActiveException(sourceAccount.getId());

        if (destinationAccount.getStatus() != AccountStatus.ACTIVE)
            throw new AccountNotActiveException(destinationAccount.getId());

        if (sourceAccount.getBalance().compareTo(totalDebit) < 0)
            throw new InsufficientFundsException(sourceAccount.getId());

        if (sourceAccount.getCurrency() != destinationAccount.getCurrency())
            throw new CurrencyMismatchException();


        Transfer transfer = new Transfer();

        transfer.setSourceAccount(sourceAccount);
        transfer.setSourceAmount(request.amount());
        transfer.setDestinationAccount(destinationAccount);
        transfer.setDestinationAmount(request.amount());

        sourceAccount.setBalance(
                sourceAccount.getBalance().subtract(totalDebit)
        );
        destinationAccount.setBalance(
                destinationAccount.getBalance().add(request.amount())
        );

        Transfer createdTransfer = transferRepository.save(transfer);

        return TransferResponse.from(createdTransfer);
    }
}
