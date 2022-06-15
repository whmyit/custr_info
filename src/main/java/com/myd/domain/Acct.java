package com.myd.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class Acct {

    private Integer  prodLffl;

    private String category;

    private String currNum;

    private String currNum2;
    private String closeCode;

}
