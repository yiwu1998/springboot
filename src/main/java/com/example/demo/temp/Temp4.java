package com.example.demo.temp;

import cn.hutool.json.JSONUtil;
import com.example.demo.util.JdbcUtil;
import org.springframework.scheduling.annotation.Async;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.Year;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Async(value = "admitExecutorr")
public class Temp4 {
    public static void main(String[] args) {
      List<String> list = new ArrayList<>();
      list.add("222");
        list.add("11");
        list.add("333");

        Map<String, String> paramMap = new HashMap<>();
        String touser = list.stream()
                .collect(Collectors.joining("|")); // 使用|连接字符串
        paramMap.put("touser", touser);

        System.out.println(JSONUtil.toJsonStr(paramMap));


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
}
