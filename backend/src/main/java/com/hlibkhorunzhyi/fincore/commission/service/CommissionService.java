package com.hlibkhorunzhyi.fincore.commission.service;

import com.hlibkhorunzhyi.fincore.account.entity.Account;
import com.hlibkhorunzhyi.fincore.commission.model.Commission;

import java.math.BigDecimal;

public interface CommissionService {

    Commission calculate(Account sourceAccount, Account destinationAccount, BigDecimal amount);
}
