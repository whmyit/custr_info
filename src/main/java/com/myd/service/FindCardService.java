package com.myd.service;


import com.myd.dao.FindCardDao;
import com.myd.domain.*;
import com.myd.utils.Const;
import com.myd.utils.ServiceResponse;
import com.myd.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class FindCardService {

    @Autowired
    private FindCardDao findCardDao;


    public ServiceResponse findCard(Param param) {

        String unFound = "";
        String unCk_acct = "";
        String unAccount = "";
        String unCd_hid = "";
        String unAppjday = "";
        String unAppseq = "";
        String unFlag = "";
        String unFlag1 = "";
        Integer unCount = null;
        String unFl_linx = "";
        String chCustd = "";
        String chAsst_flg = "";
        String chAsst = "";
        String chL214_id18 = "";
        String chCustrid = "";
        String chCk_cust = "";


        //TODO 输出报文赋值

        ServiceResponse serviceResponse = new ServiceResponse();

        String employeeN = Const.GLB_EMPLOYEE_N;

        if (StringUtils.isEmpty(employeeN)) {
            serviceResponse.setresultCode(-1);
            serviceResponse.setresultDesc("9071");  //操作人员编号
            return serviceResponse;
        }

        if (StringUtils.isEmpty(param.getKetType())) {
            serviceResponse.setresultCode(-1);
            serviceResponse.setresultDesc("9004"); //证件类型
            return serviceResponse;
        }

        if (StringUtils.isEmpty(param.getCustId())) {
            serviceResponse.setresultCode(-1);
            serviceResponse.setresultDesc("9005");//证件号码
            return serviceResponse;
        }

        chCustd = param.getCustId();
        chL214_id18 = chCustd;

        String ketType = param.getKetType();
        String custId = param.getCustId();

        if ("02".equals(ketType)) {
            if (custId.length() == 15) {
                boolean flag = L214_id15to18(chCustd, chL214_id18);
                if (!flag) {
                    serviceResponse.setresultCode(-1);
                    serviceResponse.setresultDesc("885023");
                    return serviceResponse;
                }
            }
        }
        /*查找客户资料 客户信息表 custr*/
        Custr custr = findCardDao.findCustrByBankAndCardId(Const.GLB_BANK, custId);

        String raceCode = custr.getRaceCode().trim(); //去空格
        if ("02".equals(ketType)) {
            if (!"01".equals(raceCode)) {
                serviceResponse.setresultCode(-1);
                serviceResponse.setresultDesc("9006"); //证件类型不符
            }
        } else {
            if (!raceCode.equals(ketType)) {
                serviceResponse.setresultCode(-1);
                serviceResponse.setresultDesc("9006"); //证件类型不符
            }
        }

        if (!Const.GLB_BANK.equals(custr.getBank())) {
            serviceResponse.setresultCode(-1);
            serviceResponse.setresultDesc("3403"); //不能处理非本行卡片
        }
        //TODO  输出报文赋值

        chAsst = "0";
        Asst asst = findCardDao.findAsst(Const.DB_CURFOM2, Const.GLB_BANK, chL214_id18);
        if (asst != null) {
            chAsst = "1";
        }
        Card card = null;
        if ("0".equals(chAsst)) {
            unFound = "0";
            Linx linx = findCardDao.findLinx(Const.DB_CURFOM2, Const.GLB_BANK, chL214_id18);
            //TODO  FNTRY_RSN[0]  249
            if (("C".equals(linx.getFntryRsn()) && (linx.getBussinfss().length() == 0))) {
                Acct acct = findCardDao.findAcct(linx.getBank(), linx.getXaccount());
                if (acct.getProdLffl() >= 40) {
                    unFound = "1";
                }
            }
            if ("0".equals(unFound)) {
                serviceResponse.setresultCode(-1);
                serviceResponse.setresultDesc("9444");
                return serviceResponse;
            }
            card = findCardDao.findCardTable(linx.getCardNbr());
            //TODO  306  输出报文赋值

            String property = "1";
        }
        if ("1".equals(chAsst)) {
            String property = "U";
            Linx linx = findCardDao.findLinx(Const.DB_CURFOM2, Const.GLB_BANK, chL214_id18);

            if (("C".equals(linx.getFntryRsn()) && (linx.getBussinfss().length() == 0))) {
                Acct acct = findCardDao.findAcct(linx.getBank(), linx.getXaccount());
                if (acct.getProdLffl() >= 40) {
                    //TODO  306  输出报文赋值
                    property = "A";
                }
            }
        }

        if (!"0".equals(param.getPinFlag()) && !"3".equals(param.getPinFlag())) {
            serviceResponse.setresultCode(-1);
            serviceResponse.setresultDesc("807");
            return serviceResponse;
        }

        //密码检查
        L777_pinchk(card, null, custr, param.getPinFlag(), param.getPin(), Const.GLB_SOURCE);
        //翻页查询
        if ((!"0".equals(param.getRtnInd())) && (param.getRtnInd().length() != 0)) {
            //卡号不能为空
            if (StringUtils.isEmpty(param.getCardNbr())) {
                serviceResponse.setresultCode(-1);
                serviceResponse.setresultDesc("294");
            }
            //默认翻页时从管理账户所属查询
            if ("0".equals(param.getAsstFlag())) {
                chAsst_flg = "1";
            } else {
                chAsst_flg = param.getAsstFlag();
            }
            //翻页查询卡片所属标志位必须I/U
            if (!"U".equals(chAsst_flg) && (!"I".equals(chAsst_flg))) {
                serviceResponse.setresultCode(-1);
                serviceResponse.setresultDesc("9009");
                return serviceResponse;
            }
            //通过入参的卡号查询card表
            card = findCardDao.findCardTable(param.getCardNbr());
            //TODO 437
            //输入的证件号和从卡号中查询到的不一致
            if (!card.getCustrNbr().trim().equals(param.getRtnCustId().trim())) {
                serviceResponse.setresultCode(-1);
                serviceResponse.setresultDesc("9228"); //证件号与卡号不符
                return serviceResponse;
            }
            unCk_acct = card.getXaccount();
            unAccount = card.getXaccount();
            unCd_hid = card.getCardholder();

            Linx linx = findCardDao.findLinx1(Const.GLB_BANK, card.getXaccount(), card.getCardholder(), "C", card.getCardNbr());

            unAppjday = linx.getAppJday();
            unAppseq = linx.getAppSeq();
            chCustrid = param.getRtnCustId();
            /*获取账户类型和币种*/
            Acct acct = findCardDao.fingAcct(Const.GLB_BANK, unCk_acct);

            //TODO 出参赋值

        } else {
            unAccount = "0";
            unCd_hid = "0";
            unAppjday = "9999";
            unAppseq = "999999";
            //TODO 496
        }
        if ("1".equals(chAsst)) {
            unCount = 0;
            unFlag = "0";
            unFlag1 = "0";
            if (!"0".equals(param.getRtnInd()) && param.getRtnInd().length() != 0) {
                if (!param.getRtnCustId().equals(chL214_id18) || chAsst_flg != "U") {
                    unFlag = "1";
                }
            }
            //先查询自己的卡信息
            if ("0".equals(unFlag)) {
                unFl_linx = "0";
                Linx linx = null;
                linx = findCardDao.findLinx3(Const.DB_CURFOM2, Const.GLB_BANK, chL214_id18, unAccount, unCd_hid, unAppjday, unAppseq, "", "");
                String cardNbr = linx.getCardNbr().trim();
                if (cardNbr.length() != 0) {
                    //证件号长度不能为0
                    //continue;
                }
                if ("1".equals(param.getRtnInd()) && ("0".equals(unFl_linx)) && linx.getCardNbr().equals(param.getCardNbr())) {
                    //continue;
                }
                if ("1".equals(param.getRtnInd()) && linx.getCardNbr().equals(param.getCardNbr())) {
                    unFl_linx = "1";
                    //continue;
                }
                //查询custr获取持卡人相关信息
                Custr custr1 = findCardDao.findCustr(Const.GLB_BANK, linx.getCardNbr());
                //TODO    输出报文赋值

                //查询card获取持卡人相关信息
                Card card1 = findCardDao.findCardTable1(linx.getCardNbr(), linx.getCardholder());
                //TODO    输出报文赋值

                //日期转化
                if (card.getIssueDay().equals(Const.DC_DAYNUM)) {
                    //TODO    输出报文赋值
                } else {
                    //TODO    输出报文赋值
                }
                Busns busns = null;
                if (linx.getBussinfss().length() != 0) {
                    //查找公司编号
                    busns = findCardDao.findBusns(linx.getBank(), linx.getBusinfss());
                }
                if ("1".equals(busns.getBusnsType())) {
                    /*专用公司卡*/
                }
                //TODO    输出报文赋值
                if (!linx.getXaccount().equals(unCk_acct)) {
                    unCk_acct = "0";
                    unCk_acct = linx.getXaccount();
                    /*获得账号和币种*/
                    Acct acct = findCardDao.findAcct(linx.getBank(), unCk_acct);
                }
                //TODO    输出报文赋值
                if (unCount > 5) {
                    unFlag1 = "1";
                }
                if ("0".equals(unFlag1)) {
                    unAccount = "0";
                    unCk_acct = "0";
                    unCd_hid = "0";
                    unAppjday = "9999";
                    unAppseq = "999999";
                    //TODO 774
                }
            }
            if ("0".equals(unFlag1)) {
                unFl_linx = "0";
                Asst asst1 = findCardDao.findAsst1(Const.DB_CURFOM2, Const.GLB_BANK, chL214_id18, chCustrid);
                String custrNbr = asst1.getCustrNbr().trim();
                chCk_cust = chCk_cust.trim();
                if (custrNbr.equals(chCk_cust)) {
                    unAccount = "0";
                    unCd_hid = "0";
                    unAppjday = "9999";
                    unAppseq = "999999";
                    //continute;
                }
                if ((!asst1.getCustrNbr().equals(chCk_cust)) && (chCk_cust.length() != 0)) {
                    unAccount = "0";
                    unCd_hid = "0";
                    unAppjday = "9999";
                    unAppseq = "999999";
                }
                chCk_cust = asst1.getCustrNbr().trim();
                Linx linx = findCardDao.findLinx3(Const.DB_CURFOM2, Const.GLB_BANK, asst1.getCustrNbr(), unAccount, unCd_hid, unAppjday, unAppseq, "", "");

                if (linx.getCardNbr().length() != 0) {
                    //continute;
                }
                String cardNbr = linx.getCardNbr().trim();
                if ("1".equals(chAsst_flg)) {
                    if (("1".equals(param.getRtnInd())) && ("0".equals(unFl_linx) && (param.getCardNbr().equals(linx.getCardNbr())))) {
                        //continute;
                    }
                    if (("1".equals(param.getRtnInd())) && (linx.getCardNbr().equals(param.getCardNbr()))) {
                        unFl_linx = "1";
                        //continute;
                    }
                }
                if (linx.getXaccount() != unCk_acct) {
                    unCk_acct = "0";
                    unCk_acct = linx.getXaccount();
                    Acct acct = findCardDao.findAcct(linx.getBank(), unCk_acct);
                }
                //TODO    输出报文赋值
                //获取custr表中持卡人的信息
                Custr custr1 = findCardDao.findCustr(Const.GLB_BANK, linx.getCardNbr());
                //TODO    输出报文赋值

                //查询card获取持卡人相关信息
                Card card1 = findCardDao.findCardTable1(linx.getCardNbr(), linx.getCardholder());
                //TODO    输出报文赋值
                if ("0".equals(card1.getIssueDay())) {
                    // 日期转化  赋值操作
                } else {
                    //赋值操作
                }
                //TODO    输出报文赋值
                Busns busns = null;
                if (linx.getBusinfss().length() != 0) {
                    busns = findCardDao.findBusns(linx.getBank(), linx.getBusinfss());
                    if ("1".equals(busns.getBusnsType())) {
                        /*专用公司卡*/
                    }
                }
                //TODO    输出报文赋值
            }
            unAccount = "0";
            unCd_hid = "0";
            unAppjday = "9999";
            unAppseq = "999999";
        }
        if ("0".equals(unFlag1)) {
            Linx linx = findCardDao.findLinx3(Const.DB_CURFOM2, Const.GLB_BANK, chL214_id18, unAccount, unCd_hid, unAppjday, unAppseq, "", "");

            String cardNbr = linx.getCardNbr().trim();
            if (cardNbr.length() != 0) {
                //continute;
            }
            if ("1".equals(param.getRtnInd()) && "0".equals(unFl_linx) && (linx.getCardNbr().equals(param.getCardNbr()))) {
                unFl_linx = "1";
            }

            //查询custr获取持卡人相关信息
            Custr custr1 = findCardDao.findCustr(Const.GLB_BANK, linx.getCardNbr());
            //TODO    输出报文赋值

            //查询card获取持卡人相关信息
            Card card1 = findCardDao.findCardTable1(linx.getCardNbr(), linx.getCardholder());
            //TODO    输出报文赋值

            //日期转化
            if (card.getIssueDay().equals(Const.DC_DAYNUM)) {
                //TODO    输出报文赋值
            } else {
                //TODO    输出报文赋值
            }
            Busns busns = null;
            if (linx.getBussinfss().length() != 0) {
                //查找公司编号
                busns = findCardDao.findBusns(linx.getBank(), linx.getBusinfss());
            }
            if ("1".equals(busns.getBusnsType())) {
                /*专用公司卡*/
            }
            //TODO    输出报文赋值
            if (!linx.getXaccount().equals(unCk_acct)) {
                unCk_acct = "0";
                unCk_acct = linx.getXaccount();
                /*获得账号和币种*/
                Acct acct = findCardDao.findAcct(linx.getBank(), unCk_acct);
            }
            //TODO    输出报文赋值
        }
        if (0 == unCount) {
            serviceResponse.setresultCode(-1);
            serviceResponse.setresultDesc("9425");
            return serviceResponse;
        }
        if (unCount > 5) {

        } else {

        }
        //代授权赋值
        return serviceResponse;
    }


    private void L777_pinchk(Card card, Object o, Custr custr, String pinFlag, String pin, String glbSource) {
    }

    private boolean L214_id15to18(String chCustd, String custId) {
        return true;
    }

}
