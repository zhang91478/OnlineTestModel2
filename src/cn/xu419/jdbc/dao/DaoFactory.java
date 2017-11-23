package cn.xu419.jdbc.dao;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DaoFactory {
    private static UserDao userDao = null;
    private static DaoFactory instance = new DaoFactory();
    private DaoFactory(){
        try{
            Properties prop = new Properties();
            // InputStream inStream = new FileInputStream(new File("src/daoconfig.properties"));
            //  优点：文件不一定要和路径绑定，只要文件从在于 ClassPath 中 就可以找得到
            InputStream inStream = DaoFactory.class.getClassLoader().getResourceAsStream("daoconfig.properties");
            prop.load(inStream);
            // 从配置文件中获得的 value 值 都是字符串(String)
            String userDaoClzz = prop.getProperty("userDaoFactory");
            // 通过反射方式 创建对象
            Class clazz = Class.forName(userDaoClzz);
            userDao = (UserDao) clazz.newInstance();
        } catch (IllegalAccessException | InstantiationException | IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static DaoFactory getInstance(){
        return instance;
    }

    public static UserDao getUserDao(){
        return userDao;
    }
}
