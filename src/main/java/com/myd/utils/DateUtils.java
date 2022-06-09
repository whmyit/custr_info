package com.myd.utils;

import java.sql.Timestamp;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * Copyright: Copyright (c) 2019 Sinosoft Co.,Ltd.
 *
 * @Description: 时间工具类
 *
 * @author: WuDi
 * @date: 2019年4月17日 下午3:48:58
 */
public class DateUtils {

    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    /**
     *
     * @Description: 获取系统当前时间
     *
     * @return：Date
     * @author: WuDi
     * @date: 2019年4月17日 下午3:56:24
     */
    public static Date getDateTime() {
        Timestamp  timestamp = new Timestamp(new Date().getTime());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String timeStr = simpleDateFormat.format(timestamp);
        timestamp = Timestamp.valueOf(timeStr);
        return timestamp;
    }

    /**
     *
     * @Description: 获取yyyy-MM-dd HH:mm:ss格式系统当前时间
     *
     * @return：String
     * @author: WuDi
     * @date: 2019年4月17日 下午3:56:24
     */
    public static String getStrTime() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.format(new Date());
    }

    /**
     *
     * @Description: 获取指定格式系统当前时间
     *
     * @param: dateType 时间类型
     * @return：String
     * @author: WuDi
     * @date: 2019年4月17日 下午4:39:56
     */
    public static String getStrDate(String dateType) {
        SimpleDateFormat df = new SimpleDateFormat(dateType);
        return df.format(new Date());
    }

    /**
     *
     * @Description: 根据日期获取年份
     *
     * @param: date 日期
     * @return：int
     * @throws：异常描述
     * @author: WuDi
     * @date: 2019年4月17日 下午3:52:30
     */
    public static int getYear(String date) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy");
        Date startDate = null;
        try {
            startDate = df.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar c = Calendar.getInstance();
        c.setTime(startDate);
        return c.get(Calendar.YEAR);
    }

    /**
     *
     * @Description: 获取当前年份
     *
     * @return：String
     * @author: WuDi
     * @date: 2019年4月17日 下午3:55:09
     */
    public static String getYear() {
        Calendar calendar = Calendar.getInstance();
        String year = calendar.get(Calendar.YEAR) + "";
        return "".equals(year) ? "" : year;
    }

    /**
     *
     * @Description: 获取月份
     *
     * @return：String
     * @author: WuDi
     * @date: 2019年4月17日 下午4:17:54
     */
    public static String getMonth() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(new Date());
    }

    /**
     *
     * @Description: 将日期转换为指定格式类型
     *
     * @param: date 日期
     * @param: dateType 格式类型
     * @return：String
     * @author: WuDi
     * @date: 2019年4月17日 下午3:53:51
     */
    public static String DateToString(Date date, String dateType) {
        Format format = new SimpleDateFormat(dateType);
        return format.format(date);
    }
	/**
	 * 获取月份
	 * @return
	 */
    public static String getNowMonth(){
    	Calendar calendar=Calendar.getInstance();
    	String month=calendar.get(Calendar.MONTH)+1+"";
    	return month;
    }
    /**
     * 获取哪天
     * @return
     */
    public static String getDay(){
    	Calendar calendar=Calendar.getInstance();
    	String day=calendar.get(Calendar.DATE)+"";
    	return day;
    }

    /**
     *
     * @Description: 将指定时间加/减指定分钟
     *
     * @param: date 指定时间
     * @param: minute 指定分钟
     * @return：Date 加减后时间
     * @author: WuDi
     * @date: 2019年4月22日 下午5:56:40
     */
    public static String addOrSubtract(Date date ,int dayNums){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, dayNums);
        Date time = calendar.getTime();
        return DateToString(time, YYYY_MM_DD_HH_MM_SS);
    }

    /**
     *
     * @Description: 获取时间毫秒
     *
     * @param: inVal 时间
     * @return：long 毫秒
     * @author: WuDi
     * @date: 2019年4月22日 下午6:45:20
     */
    public static long fromDateStringToLong(String inVal) { // 此方法计算时间毫秒
        Date date = null;// 定义时间类型       
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-mm-dd hh:ss");
        try {
            date = inputFormat.parse(inVal); // 将字符型转换成日期型
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date.getTime();// 返回毫秒数
    }

    /**
     *
     * @Description: 俩毫秒型日期相减获的毫秒差
     *
     * @param: strDate1 日期1
     * @param：strDate2 日期2
     * @return： long 毫秒差
     * @author: WuDi
     * @date: 2019年4月22日 下午6:42:08
     */
    public static long dateReduce(String strDate1,String strDate2) {
        Long date1 = DateUtils.fromDateStringToLong(strDate1);
        Long date2 = DateUtils.fromDateStringToLong(strDate2);
        long ss=(date1-date2)/(1000); //共计秒数
        return ss;
    }


	public static void main(String[] args) {
//	    System.out.println(DateUtils.getDay());
//	    System.out.println(DateUtils.getNowMonth());
//	    System.out.println(DateUtils.getYear());
	    System.out.println(addOrSubtract(getDateTime(), 5));
	}
}
