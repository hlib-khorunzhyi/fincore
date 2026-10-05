package com.hlibkhorunzhyi.fincore.account.entity;

public enum Currency {
    PLN(2),
    USD(2),
    EUR(2),
    JPY(0);

    private final int scale;

    Currency(int scale){
        this.scale = scale;
    }

    public int getScale() {
        return scale;
    }

}
