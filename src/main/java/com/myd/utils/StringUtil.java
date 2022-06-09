package com.myd.utils;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.sql.Clob;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * �ַ�������
 *
 * @author Xiang Weiping May 12, 2008 3:42:40 PM ����
 */
public class StringUtil {

    static Logger logger = LoggerFactory.getLogger(StringUtil.class);

    private StringUtil() {

    }

    public static void main(String[] args) {
        System.out.print(StringUtil.substringADSLSuffix("gzDSL1234388@iptv.gd"));
    }

    /**
     * ȥ���ַ��еĿո�<br>
     * ע���˷�����Ҫ��Ϊ�˷����������<br>
     * handleNULL(Map.get("str"))��handleNULL(request.getAttribute("str"))��
     * <p>
     * ������null������"";����" "������"";����"sdf ss df"������"sdfssdf"������" null"������""
     *
     * @param input ����Ķ���
     * @return �����Ķ������ַ��Ϊnull���򷵻ؿ��ַ�""
     */
    public static String handleNULL(Object input) {
        if (input != null) {
            if (input instanceof String)
                return handleNULL((String) input, "");
        }
        return "";
    }

    /**
     * ȥ���ַ��еĿո�<br>
     * <p>
     * ������null������"";����" "������"";����"sdf ss df"������"sdfssdf"������" null"������""
     *
     * @param input ������ַ�
     * @return �����Ķ������ַ��Ϊnull���򷵻ؿ��ַ�""
     */
    public static String handleNULL(String input) {
        return handleNULL(input, "");
    }

    /**
     * ȥ���ַ��еĿո�<br>
     * <p>
     * ������null������def;����" "������def;����"sdf ss df"������"sdfssdf"������" null"������def
     *
     * @param input ������ַ�
     * @param def   Ĭ���ַ�
     * @return �����Ķ������ַ��Ϊnull���򷵻�def֪�����ַ�
     */
    public static String handleNULL(String input, String def) {
        if (null == input || input.trim().length() <= 0
                || input.trim().toLowerCase().equals("null")) {
            return def;
        } else {
            return input.trim();
        }
    }

    /**
     * Check if a string is null or empty.
     *
     * @param vStr the string to be checked
     * @return true if the string is empty or null, false otherwise
     */
    public static boolean isEmpty(String vStr) {
        if (vStr == null)
            return true;
        return (vStr.trim().length() <= 0);
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    /**
     * ��input����scaleλС��תΪ�ַ�
     *
     * @param input ����
     * @param scale С��λ��
     * @return �ַ�
     */
    public static String handleScale(double input, int scale) {
        BigDecimal bigDecimal = new BigDecimal(input);
        bigDecimal = bigDecimal.setScale(scale, BigDecimal.ROUND_HALF_EVEN);
        return handleNULL(bigDecimal.toString());

    }

    /**
     * ��input����scaleλС��תΪ�ַ�
     *
     * @param input �����ַ�
     * @param scale С��λ��
     * @return �ַ�
     */
    public static String handleScale(String input, int scale) {
        BigDecimal bigDecimal = new BigDecimal(input);
        bigDecimal = bigDecimal.setScale(scale, BigDecimal.ROUND_HALF_EVEN);
        return handleNULL(bigDecimal.toString());

    }

    /**
     * ����ַ���С��length��ǰ�����ָ�����ַ���
     * <p>
     * �磺leadingChar("123",6,'0')������"000123"
     *
     * @param input  �ַ�
     * @param length ����
     * @return �ַ�
     */
    public static String leadingChar(String input, int length, char ch) {
        input = handleNULL(input);
        char nils[] = new char[Math.max(0, length - input.length())];
        Arrays.fill(nils, ch);
        input = String.valueOf(nils) + input;
        return handleNULL(input);
    }

    /**
     * ����ַ���С��length��ǰ�����0����
     * <p>
     * �磺leadingChar("123",6)������"000123"
     *
     * @param input  �ַ�
     * @param length ����
     */
    public static String leadingZero(String input, int length) {
        return leadingChar(input, length, '0');
    }

    /**
     * ���÷ֱ�ʾ���ַ����ת��Ԫ(��ʽ��"0.00")
     *
     * @param fen �÷ֱ�ʾ�Ļ��ҽ���ַ�
     * @return ��Ԫ��ʾ�Ļ��ҽ���ַ�
     */
    public static String convertFenToYuan(String fen) {
        return convertFenToYuan(Double.parseDouble(fen));
    }

    /**
     * ���÷ֱ�ʾ�Ļ��ҽ��ת��Ԫ(��ʽ��"0.00")
     *
     * @param fen �÷ֱ�ʾ�Ļ��ҽ��
     * @return ��Ԫ��ʾ�Ļ��ҽ���ַ�
     */
    public static String convertFenToYuan(double fen) {
        String result;
        try {
            double yuan = fen / 100;
            result = handleScale(yuan, 2);
        } catch (Exception ex) {
            result = "0.00";
        }
        return result;
    }

    /**
     * ���ַ�һ���ķָ����������<br>
     * ��ע���˷������᷵��null��<br>
     * ͬString.split(String)�����Ƚϣ��ŵ��Ƕ��������ַ�ָ�ʱ����Ҫת�壬<br>
     * ��"asd\\asd".split("\\\\")��String2Arr("asd\\asd","\\");
     *
     * @param str   ��ָ���ַ�
     * @param delim �ָ���
     * @return �ַ�����
     */
    public static String[] String2Array(String str, String delim) {
        if (str == null || delim == null) {
            return new String[0];
        }
        StringTokenizer st = new StringTokenizer(str, delim);
        String[] retArr = new String[st.countTokens()];
        int i = 0;
        while (st.hasMoreTokens()) {
            retArr[i] = st.nextToken();
            i++;
        }
        return retArr;
    }

    /**
     * Check if an array of strings contains the string s
     *
     * @param    a    the array of strings
     * @param    s    the string
     * @return true if a contains s, false otherwise
     */
    public static boolean containsValue(String[] a, String s) {
        boolean bRetVal = false;

        for (int i = 0; i < a.length; i++) {
            if (handleNULL(a[i]).equals(handleNULL(s))) {
                bRetVal = true;
                break;
            }
        }

        return bRetVal;
    }

    /**
     * ��ȡ��ADSL��׺�󷵻�,��gzDSL234234266@163.gd������gzDSL234234266
     *
     * @param    s    the string
     * @return true if a contains s, false otherwise
     */
    public static String substringADSLSuffix(String s) {
        if (isEmpty(s)) {
            return "";
        }
        if (s.indexOf("@") != -1) {
            return s.substring(0, s.indexOf("@"));
        }
        return s;
    }

    /**
     * Within a string, replace a substring with another string.
     *
     * @param vStr a string
     * @param vOld the old word to be replaced
     * @param vNew the new word to be replaced with
     * @return the new string
     */
    static public String replaceString(String vStr, String vOld, String vNew) {
        if (isEmpty(vStr))
            return "";

        String sRetVal = "";
        int pos = vStr.indexOf(vOld);

        while (pos != -1) {
            sRetVal = sRetVal + vStr.substring(0, pos) + vNew;
            vStr = vStr.substring(pos + vOld.length());

            pos = vStr.indexOf(vOld);
        }

        sRetVal = sRetVal + vStr;

        return sRetVal;
    }

    /**
     * Convert an int to a string.
     *
     * @param vValue the int to be converted
     * @param len    the string length
     * @param vChar  a single-char string to be repeatly padded before the int if the int is shorter
     *               than len
     * @return the String representation of the int
     */
    static public String intToStr(int vValue, int len, String vChar) {
        String sTemp = "" + vValue;

        if (!vChar.equals("")) {
            while (sTemp.length() < len) {
                sTemp = vChar + sTemp;
            }
        }

        return sTemp;
    }

    /**
     * Convert an int to a string.
     *
     * @param vValue the int to be converted
     * @return the String representation of the int
     */
    static public String intToStr(int vValue) {
        return intToStr(vValue, 0, "");
    }

    /**
     * Convert the array a to a string, each element is separated by sDivider.
     *
     * @param a        the array of strings
     * @param sDivider the divider that separates each element
     * @return the resulted string
     */
    static public String arrayToString(String[] a, String sDivider) {
        String sRetVal = "";

        for (int i = 0; i < a.length; i++) {
            if (sRetVal.equals(""))
                sRetVal = a[i];
            else
                sRetVal = sRetVal + sDivider + a[i];
        }

        return sRetVal;
    }

    /**
     * Parse a list in string format
     *
     * @param vList      the list in string
     * @param vDelimiter a one charactor delimiter
     * @return the list in an array
     */
    static public String[] stringToArray(String vList, String vDelimiter) {
        Vector avList = new Vector();

        StringTokenizer st = new StringTokenizer(vList, vDelimiter);

        while (st.hasMoreTokens()) {
            avList.addElement(st.nextToken());
        }

        String[] aList = new String[avList.size()];
        avList.copyInto(aList);

        return aList;
    }


    /**
     * Get a random string with letters or digits
     *
     * @param vStrLength the length of the random string
     * @return the string
     */
    static public String getRandomString(int vStrLength) {
        String s = "";
        for (int i = 1; i <= vStrLength; i++) {
            int nextChar = (int) (Math.random() * 62);
            if (nextChar < 10) // 0-9
                s += nextChar;
            else if (nextChar < 36) // a-z
                s += (char) (nextChar - 10 + 'a');
            else
                s += (char) (nextChar - 36 + 'A');
        }
        return s;
    }

    static public String getRandomNumberString(int vStrLength) {
        String s = "";
        for (int i = 1; i <= vStrLength; i++) {
            int nextChar = (int) (Math.random() * 10);
            if (nextChar < 10) // 0-9
                s += nextChar;
            else
                s += "0";
        }
        return s;
    }

    /**
     * Return text with the specified length
     *
     * @param s        the text
     * @param maxlimit the max limit
     * @return the substring
     */
    static public String truncate(String s, int maxlimit) {
        String sRetVal = "";
        if (s.length() > maxlimit) {
            sRetVal = s.substring(0, maxlimit);
        }

        return sRetVal;
    }

    static public boolean isValidEmail(String vEmailAddress) {
        Pattern pattern = Pattern.compile("^.+\\@(\\[?)[a-zA-Z0-9\\-\\.]+\\.([a-zA-Z]{2,20}|[0-9]{1,3})(\\]?)$");
        Matcher matcher = pattern.matcher(vEmailAddress);

        return matcher.matches();
    }

    static public boolean isValidMultiEmails(String vEmailAddress) {
        boolean bValidEmail = true;

        String[] aEmail = stringToArray(vEmailAddress, ",");

        for (int i = 0; i < aEmail.length; i++) {
            if (!isEmpty(aEmail[i])) {
                if (!isValidEmail(aEmail[i])) {
                    bValidEmail = false;
                    break;
                }
            }
        }
        return bValidEmail;
    }

    /**
     * ��ȡ�޶��ֽ�����ַ� ����Ϊһ���ַ���ֽ������Ϊ���ֽڽ�ȡ���ַ� ����Ҫ��֤���ֲ����ذ����
     * �硰��ABC��4��Ӧ�ý�Ϊ����AB�������롰��ABC��DEF����6��Ӧ�����Ϊ����ABC�����ǡ���ABC+���İ����
     *
     * @param str
     * @param len
     * @return
     * @throws Exception
     */
    public static String getLimitBytesString(String str, int len) throws Exception {
        if (str == null || str.length() == 0)
            return str;
        int counterOfDoubleByte = 0;
        byte b[];

        b = str.getBytes("GBK");
        if (b.length <= len)
            return str;
        for (int i = 0; i < len; i++) {
            if (b[i] < 0)
                counterOfDoubleByte++;
        }

        if (counterOfDoubleByte % 2 == 0) {
            return new String(b, 0, len, "GBK");
        } else {
            return new String(b, 0, len - 1, "GBK");
        }

    }

    /**
     * ��Clob�����ж�ȡ�ַ�
     *
     * @param clob
     * @return �ַ�
     * @throws SQLException
     */
    public static String getClob(Clob clob) throws SQLException {
        String str = "";
        if (clob != null && clob.length() != 0) {
            try {
                str = clob.getSubString((long) 0, (int) clob.length());
            } catch (SQLException e) {
                if (logger.isInfoEnabled()) {
                    logger.info("INFO:the current driver wants to start from 1,not 0,"
                            + e.getMessage());
                }
                str = clob.getSubString((long) 1, (int) clob.length());
            }
            int i = str.indexOf("<CLOB ");
            if (i >= 0)
                str = str.substring(0, i);
        }
        return str;
    }

    /**
     * �ж��ַ��Ƿ�ȫ���������
     *
     * @param str String
     * @return boolean
     */
    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    /**
     * check string is vaild integer or not.
     *
     * @param sValue
     * @return
     */
    public static boolean isValidInteger(String sValue) {
        if (isEmpty(sValue)) {
            return false;
        }
        for (int i = 0; i < sValue.length(); i++) {
            char c = sValue.charAt(i);
            if ((c < '0') || (c > '9'))
                return false;
        }
        return true;
    }

    /**
     * ��url�л�ȡ�����ֵ
     *
     * @param url url�ַ�
     * @param key ����
     * @return û���ҵ������""
     */
    public static String getParameterFromURL(String url, String key) {
        if (isEmpty(url) || isEmpty(key))
            return "";
        String[] tmp1 = url.split("\\?");
        if (tmp1.length <= 1)
            return "";
        String[] tmp2 = tmp1[1].split("&");
        int length = tmp2.length;
        String tmp3 = null;
        String[] tmp4 = null;
        for (int i = 0; i < length; i++) {
            tmp3 = tmp2[i];
            if (!isEmpty(tmp3)) {
                tmp4 = tmp3.split("=");
                if (tmp4.length <= 1) {
                    if (tmp4[0].equals(key))
                        return "";
                } else {
                    if (tmp4[0].equals(key))
                        return tmp4[1];
                }
            }
        }
        return "";
    }

    /**
     * ��BigDecimal����ת��Ϊint
     *
     * @param bg BigDecimal
     * @return int
     */
    public static int convertBigDecimalToInt(BigDecimal bg) {
        if (bg == null) {
            return 0;
        }
        try {
            return bg.intValue();
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * ��BigDecimal����ת��Ϊint
     *
     * @param bg BigDecimal
     * @return int
     */
    public static String convertBigDecimalToStr(BigDecimal bg) {
        if (bg == null) {
            return null;
        }
        try {
            return String.valueOf(bg.intValue());
        } catch (Exception e) {
            return null;
        }
    }

}
