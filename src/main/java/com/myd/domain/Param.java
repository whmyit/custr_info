package com.myd.domain;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class Param {

    private String ketType ;
    private String custId;
    private String pinFlag;
    private String pin;
    private String rtnInd;
    private String cardNbr;
    private String asstFlag;
    private String rtnCustId;

}
