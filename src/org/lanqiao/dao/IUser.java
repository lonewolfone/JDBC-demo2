package org.lanqiao.dao;

import org.lanqiao.entity.User;

import java.sql.SQLException;

public interface IUser {
    //查找一条记录：根据用户名和密码
    public User getUserByUsernameAndPassword(String name, String password) throws SQLException;
    //添加一条记录
    public  void  insertUser(String name,String password);
}
