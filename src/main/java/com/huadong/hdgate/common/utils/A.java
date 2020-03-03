package com.huadong.hdgate.common.utils;

/**
 * TODO
 *
 * @author chuyz
 * @date 2020/2/26 15:12
 */
public class A {
    private static int flag;
    private static String m;
    static{
        flag = 1;
        m = "异常";
    }

    public static int getFlag() {
        return flag;
    }

    public static void setFlag(int flag) {
        A.flag = flag;
    }

    public static String getM() {
        return m;
    }

    public static void setM(String m) {
        A.m = m;
    }
}
