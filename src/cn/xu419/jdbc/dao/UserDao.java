package cn.xu419.jdbc.dao;

import cn.xu419.User;

public interface UserDao {
    public void addUser(User user) throws DaoException;

    public User getUser(int uid);

    public User getUser(String account,String password,int permission);

    public void delUser(int uid);

    public void updateUser(User user);
}
