package com.example.demo;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Created by lumen on 18-1-4.
 */
public class TestDB {
    public static void main(String[] args){
        String url = "jdbc:mysql://localhost:3306/springboot_redis_demo";
        String driver = "org.gjt.mm.mysql.Driver";
        try{
            Class.forName(driver);
        }catch(Exception e){
            System.out.println("无法加载驱动");
        }
        try {
            Connection con = DriverManager.getConnection(url,"root","0801");
            if(!con.isClosed())
                System.out.println("success");
            con.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
