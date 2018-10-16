package org.lanqiao.test;

import org.lanqiao.dao.IUser;
import org.lanqiao.dao.impl.UserDaoImpl;
import org.lanqiao.entity.User;

import java.sql.SQLException;
import java.util.Scanner;


public class Test {
    public static void main(String[] args) throws SQLException {
        IUser iUser = new UserDaoImpl();
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入您的用户名：");
        String username = scanner.nextLine();
        System.out.println("请输入您的密码：");
        String password = scanner.nextLine();
        User user = iUser.getUserByUsernameAndPassword(username,password);
        if (user != null){
            System.out.println("恭喜您登陆成功");
        }else {
            System.out.println("您的账号或密码有误，请重新登陆");
        }
        scanner.close();
        System.out.println("------------------------------");
        iUser.insertUser("小米","dddd");

    }
}
