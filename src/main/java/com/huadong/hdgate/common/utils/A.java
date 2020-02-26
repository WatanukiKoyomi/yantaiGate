package com.huadong.hdgate.common.utils;

/**
 * TODO
 *
 * @author chuyz
 * @date 2020/2/26 15:12
 */
public class A {
    private static int flag;
    static{
        flag = 1;
    }

    public static int getFlag() {
        return flag;
    }

    public static void setFlag(int flag) {
        A.flag = flag;
    }
}
