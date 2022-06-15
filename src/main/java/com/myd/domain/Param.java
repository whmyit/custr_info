package com.myd.domain;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class Param {

    private Integer trxType; //交易代码
    private String retCode; //响应吗
    private Integer bnKnbr; //银行代号
    private String source;//交易来源
    private Integer brnNo; //网点代号
    private Integer opeNo;//操作员好
    private Integer seqNo;//流水号

    private String head;     //报文头
    private String ketType; //证件类型
    private String custId;   //证件号码
    private String pinFlag;  //是否检查密码标识
    private String pin;      //密码
    private String rtnInd;   // 翻页标识 首次查询上送空 翻页查询上送1
    private String rtnCustId;//证件号码
    private String cardNbr;  // 卡号
    private String asstFlag; //卡片归属标识
    private String revs;     //保留字段

}
