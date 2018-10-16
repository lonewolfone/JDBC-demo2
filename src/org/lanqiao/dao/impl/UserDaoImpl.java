package org.lanqiao.dao.impl;

import org.lanqiao.dao.IUser;
import org.lanqiao.entity.User;
import org.lanqiao.utils.JDBCUtils;

import java.sql.*;

public class UserDaoImpl implements IUser {
    @Override
    public User getUserByUsernameAndPassword(String name, String password) throws SQLException {
        Connection connection = JDBCUtils.getConnection();
        //Statement statement = connection.createStatement();
        //String sql = "select * from stu where name = '"+name+"' and password ='"+password+"'";
        String sql = "select * from stu where name = ? and password = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1,name);
        preparedStatement.setString(2,password);
        ResultSet resultSet = preparedStatement.executeQuery();
        User user = null;
        while (resultSet.next()){
            user =new User();
            user.setId(resultSet.getInt("id"));
            user.setName(resultSet.getString("name"));
            user.setAge(resultSet.getInt("age"));
            user.setPassword(resultSet.getString("password"));
            user.setSex(resultSet.getString("sex"));
        }
        return user;
    }

    @Override
    public void insertUser(String name ,String password) {
        Connection connection = JDBCUtils.getConnection();
        String sql = "insert into stu(name,password) values(?,?)";
        PreparedStatement preparedStatement = null;
        try {
            connection.setAutoCommit(false);//关闭数据库事务的自动提交
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,name);
            preparedStatement.setString(2,password);
            //System.out.println(1/0);发生异常
            preparedStatement.executeUpdate();
            connection.commit();//手动提交
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                //当数据在更新操作中，一旦发生异常，则执行回滚
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }finally {
            //释放资源
            JDBCUtils.releaseSource(null,preparedStatement,connection);
        }

    }
}
