package com.hlibkhorunzhyi.fincore.account.generator;

import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadLocalRandom;

@Component
public class AccountNumberGenerator {

    private static final int ACCOUNT_NUMBER_LENGTH = 12;

    public String generate() {
        ThreadLocalRandom random = ThreadLocalRandom.current();

        long number = random.nextLong(
                100_000_000_000L,
                1_000_000_000_000L
        );

        return String.valueOf(number);
    }
}
