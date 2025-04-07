package com.example.demo.util;

import com.example.demo.entity.People;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


/**
 * 同步数据库操作
 */
public class JdbcUtil {
    static String url = "jdbc:mysql://localhost:3306/z_test?serverTimezone=GMT%2B8&useUnicode=true&characterEncoding=utf8";
    public static final String USERNAME = "root";
    public static final String PASSWORD = "ggi1289";

    static String url_prd = "jdbc:mysql://175.178.194.2:8706/hl_sys?serverTimezone=GMT%2B8&useUnicode=true&characterEncoding=utf8";
    public static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    public static final String USERNAME_PRD = "zcc";
    public static final String PASSWORD_PRD = "zcc@123456";


    public static void getUrl() {
        try{
            Class.forName(DRIVER);
            //Connection connection = DriverManager.getConnection(url, USERNAME, PASSWORD);
            Connection connection = DriverManager.getConnection(url_prd, USERNAME_PRD, PASSWORD_PRD);
            Statement statement = connection.createStatement();
            String query = "select * from sys_user";
            ResultSet resultSet = statement.executeQuery(query);
            List<People> list = new ArrayList<>();
            while (resultSet.next()) {
                People people = new People();
                people.setName(resultSet.getString("username"));
                list.add(people);
            }
//            String insertQuery = "INSERT INTO people (id, age, name) VALUES (?, ?, ?)";
//            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
//
//            // 设置参数
//            preparedStatement.setLong(1, 2222L);
//            preparedStatement.setInt(2, 42);
//            preparedStatement.setString(3, "鲲鲲");

            // 执行插入语句
            //int rowsAffected = preparedStatement.executeUpdate();


//            String countQuery = "SELECT COUNT(*) AS totalRows FROM people";
//            preparedStatement = connection.prepareStatement(countQuery);
//
//            // 执行查询
//            resultSet = preparedStatement.executeQuery();

            // 处理查询结果
            if (resultSet.next()) {
                // 获取总的数据条数
                int totalRows = resultSet.getInt("totalRows");
                System.out.println("Total Rows: " + totalRows);
            }


            System.out.println(list.size());

        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }


}
