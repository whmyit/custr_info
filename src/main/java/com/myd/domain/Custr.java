package com.myd.domain;

import lombok.Data;
import lombok.Getter;
import org.springframework.stereotype.Service;

@Data
@Getter
@Service
public class Custr {

    private String raceCode ;
    private String bank;
    private String surName ;
    private String homePhone;
    private String busiPhone;
    private String extension;
    private String moPhone;
    private String cardName;
    private String custrNbr;


}
