package com.myd.domain;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Getter
@Setter
public class ReturnParam {

    private Integer trxType; //交易代码
    private String retCode; //响应吗
    private Integer bnKnbr; //银行代号
    private String source;//交易来源
    private Integer brnNo; //网点代号
    private Integer opeNo;//操作员好
    private Integer seqNo;//流水号

    private String  head;//报文
    private String  keyType;//证件类型
    private String  custid;//证件号码
    private String  custrName;//客户姓名
    private Integer  homeArea;//家庭电话区号
    private String  homePhone;//家庭电话
    private String  busiPhone;//公司电话
    private String  busiex;//公司电话分机
    private String  mobilePhone;//手机号码
    private String  property;//证件归属标注
    private Integer  count;//本次返回交易条数
    private String  rtnInd;//翻页标注
    private List<Pack3073> pack3073s;


}
