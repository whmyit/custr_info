package com.myd.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class Card {

    private String custrNbr;

    private String xaccount;

    private String cardholder;

    private String cardNbr;

    private String issueDay;
}
