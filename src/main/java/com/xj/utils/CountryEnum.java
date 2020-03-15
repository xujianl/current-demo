package com.xj.utils;

import lombok.Getter;

import java.util.Iterator;

public enum CountryEnum {
    ONE(1,"楚"),TWO(2,"齐"),THREE(3,"韩"),FOUR(4,"魏"),FIVE(5,"赵"),SIX(6,"燕");

    @Getter  private Integer retCode;
    @Getter  private String retMessage;

    CountryEnum(Integer retCode, String retMessage) {
        this.retCode = retCode;
        this.retMessage = retMessage;
    }

    public static CountryEnum foreachCountry(Integer index){
        CountryEnum[] enums = CountryEnum.values();
        for(CountryEnum element : enums){
            if (element.retCode == index){
                return element;
            }
        }
        return  null;
    }
}
