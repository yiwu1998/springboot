package com.example.demo.temp;

import org.springframework.scheduling.annotation.Async;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicReference;

@Async(value = "admitExecutorr")
public class Temp4 {
    public static void main(String[] args) {
        // 生成八位数字
        String eightDigitNumber = generateEightDigitNumber();
        System.out.println("八位数字：" + eightDigitNumber);

        // 生成十位数字
        String tenDigitNumber = generateTenDigitNumber();
        System.out.println("十位数字：" + tenDigitNumber);

    }


    // 生成八位数字
    public static String generateEightDigitNumber() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder(8);
        for (int i = 0; i < 8; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }

    // 生成十位数字
    public static String generateTenDigitNumber() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder(10);
        for (int i = 0; i < 10; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }
}
