package com.hlibkhorunzhyi.fincore.commission.service;

import com.hlibkhorunzhyi.fincore.account.entity.Account;
import com.hlibkhorunzhyi.fincore.commission.model.Commission;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CommissionServiceImpl implements CommissionService {


    private final BigDecimal feePercentage;

    private final BigDecimal minFee;

    private final BigDecimal maxFee;

    public CommissionServiceImpl(
            @Value("${fincore.commission.percentage}") BigDecimal feePercentage,
            @Value("${fincore.commission.minimum}") BigDecimal minFee,
            @Value("${fincore.commission.maximum}") BigDecimal maxFee
    ) {
        this.feePercentage = feePercentage;
        this.minFee = minFee;
        this.maxFee = maxFee;
    }

    @Override
    public Commission calculate(Account sourceAccount, Account destinationAccount, BigDecimal amount) {

        // I've created this service for future scale this service

        BigDecimal fee = amount.multiply(feePercentage);

        if (fee.compareTo(minFee) < 0)
            return new Commission(minFee);
        if (fee.compareTo(maxFee) > 0)
            return new Commission(maxFee);

        return new Commission(fee);
    }
}
