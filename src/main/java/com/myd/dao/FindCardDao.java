package com.myd.dao;

import com.myd.domain.*;
import org.springframework.stereotype.Component;

@Component
public interface FindCardDao  {

    public Custr findCustrByBankAndCardId(String glbBank, String custId);

    Asst findAsst(String dbCurfom2, String glbBank, String custId);

    Linx findLinx(String dbCurfom2, String glbBank, String custId);

    Acct findAcct(String bank, String xaccount);

    Card findCardTable(String cardNbr);

    Linx findLinx1(String glbBank, String xaccount, String cardholder, String c, String cardNbr);

    Acct fingAcct(String glbBank, String unCk_acct);

    Linx findLinx3(String dbCurfom2, String glbBank, String chL214_id18, String unAccount, String unCd_hid, String unAppjday, String unAppseq, String s, String s1);

    Custr findCustr(String glbBank, String cardNbr);

    Card findCardTable1(String cardNbr, String cardholder);

    Busns findBusns(String bank, String businfss);

    Asst findAsst1(String dbCurfom2, String glbBank, String chL214_id18, String chCustrid);
}
