package cn.xu419.jdbc.dao.impl;

import cn.xu419.User;
import cn.xu419.jdbc.JdbcUtils;
import cn.xu419.jdbc.dao.DaoException;
import cn.xu419.jdbc.dao.UserDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDaoJdbcImpl implements UserDao {

    @Override
    public void addUser(User user) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = JdbcUtils.getConn();
            String sql = "insert into t_user(account,password,permission) values(?,?,?)";
            ps = conn.prepareStatement(sql);
            ps.setString(1, user.getAccount());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getPermission());
            ps.executeUpdate();
        } catch (SQLException e) {
        //  为了不让业务逻辑层处理该异常。
            throw new DaoException(e.getMessage(), e);
        } finally {
            JdbcUtils.free(null, ps, conn);
        }
    }

    @Override
    public User getUser(int uid) {
        User user = new User();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = JdbcUtils.getConn();
            String sql = "select * from user where id= ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, uid);
            rs = ps.executeQuery();
            while (rs.next()) {
                user.setAccount(rs.getString("account"));
                user.setPassword(rs.getString("password"));
                user.setPermission(rs.getString("permission"));
            }
        } catch (SQLException e) {
            //  为了不让业务逻辑层处理该异常，
            throw new DaoException(e.getMessage(),e);
        } finally {
            JdbcUtils.free(rs, ps, conn);
        }
        return user;
    }

    @Override
    public User getUser(String account, String password, int permission) {

        return null;
    }

    @Override
    public void delUser(int uid) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = JdbcUtils.getConn();
            String sql = "delete from user where id=?";
            ps.setInt(1, uid);
            ps.executeUpdate();
        } catch (Exception e) {
            //  为了不让业务逻辑层处理该异常，
            throw new DaoException(e.getMessage(),e);
        } finally {
            JdbcUtils.free(null, ps, conn);
        }
    }

    @Override
    public void updateUser(User user) {

    }
}
