package com.example.demo.temp;

import cn.hutool.core.lang.UUID;
import com.example.demo.util.GenUtil;

import java.util.Random;

public class ExcelTest {
    public static void main(String[] args) {

        System.out.println(GenUtil.generateName());
        System.out.println(UUID.randomUUID().toString());
    }
}
