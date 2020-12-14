package com.framemark.test;

/**
 * @description
 * @author: liudawei
 * @date: 2020/12/3 16:22
 */
public class JavaBase {

    public static void main(String[] args) {
        bitOperation(8);
    }

    public static void bitOperation(int number){
        // 左移
        System.out.println(number >> 1);
        // 右移
        System.out.println(number << 1);

        // 与
        System.out.println((number & 4));
        // 或
        System.out.println((number | 4));
        // 异或 ^
        System.out.println(number);
        // 非
        System.out.println((~number));
    }
}
