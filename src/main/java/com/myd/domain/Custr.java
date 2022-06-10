package com.myd.domain;

import lombok.Data;

@Data
public class Custr {

    private String raceCode ;

    private String bank;

    public String getRaceCode() {
        return raceCode;
    }

    public void setRaceCode(String raceCode) {
        this.raceCode = raceCode;
    }
}
