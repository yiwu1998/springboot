package com.example.demo.temp;

import org.springframework.scheduling.annotation.Async;

import java.util.Arrays;
import java.util.List;

@Async(value = "admitExecutorr")
public class Temp2 {
    public static void main(String[] args) {

         String STR_BASIC = "基础数据信息缺失:";
         StringBuffer stringBuffer = new StringBuffer(STR_BASIC);
         System.out.println(stringBuffer.length());

    }
    public  static String getProject(String input) {
        int firstSlashIndex = input.indexOf("/");
        int secondSlashIndex = input.indexOf("/", firstSlashIndex + 1);
        int thirdSlashIndex = input.indexOf("/", secondSlashIndex + 1);
        int fourthSlashIndex = input.indexOf("/", thirdSlashIndex + 1);
        // 如果找到了第三个和第四个斜杠，则获取它们之间的子字符串作为结果
        String result = (thirdSlashIndex != -1 && fourthSlashIndex != -1) ?
                input.substring(thirdSlashIndex + 1, fourthSlashIndex) : "";
        return result;
    }

    public void xx(){
        String a1 = "01,03,08";

        List<String> b = Arrays.asList("02","04","06","09");
        String[] a = a1.split(",");

        boolean found = Arrays.stream(a)
                .anyMatch(b::contains);

        if (found) {
            System.out.println("1");
        } else {
            System.out.println("2");
        }
    }
}
