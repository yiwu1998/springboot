package com.example.demo.temp;

import com.example.demo.util.JdbcUtil;
import org.springframework.scheduling.annotation.Async;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Async(value = "admitExecutorr")
public class Temp4 {
    public static void main(String[] args) {
        String project = getProject("/瀚蓝环境股份有限公司/固废事业群/瀚蓝（厦门）固废处理有限公司/瀚蓝工程技术有限公司/项目指挥部/安溪公司/经营班子");
        System.out.println(project
        );
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
