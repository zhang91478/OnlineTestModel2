package cn.xu419;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * @author 22948
 */
public class DatabaseHelp {
    /*返回用户等级若为1则为普通用户，为2则为管理员用户*/
    public int getUserPermission(String account,String password){
        int result = -1;
        try{
            String sql = "SELECT * FROM t_user WHERE account = ? AND password password = ?";
            Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,account);
            pstmt.setString(2,password);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()){
                result = rs.getInt("permission");
            }
            rs.close();
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
    public void addUser(User user){
        Connection conn = null;
        try{
            String sql = "INSERT INTO t_user(account,password,permission) VALUES(?,?,?)";
            conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,user.getAccount());
            pstmt.setString(2,user.getPassword());
            pstmt.setString(3,user.getPermission());
            pstmt.execute();
            conn.close();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updatePasswordForUser(String account,String newPassword){
        try {
            String sql = "UPDATE FROM USER SET password = ? WHERE account = ?";
            Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,newPassword);
            pstmt.setString(2,account);
            pstmt.execute();
            conn.close();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //有一条插入不成功就回滚
    public void addUsers(List<User> list) throws SQLException {
        Connection conn = null;
        try {
            String sql = "INSERT INTO t_user(account,password,permission) VALUES(?,?,?)";
            conn = getConnection();
            conn.setAutoCommit(false);
            PreparedStatement pstmt = conn.prepareStatement(sql);
            for (int i = 0; i < list.size(); i++) {
                pstmt.setString(1,list.get(i).getAccount());
                pstmt.setString(2,list.get(i).getPassword());
                pstmt.setString(3,list.get(i).getPermission());
                pstmt.execute();
            }
            conn.commit();
            conn.close();
            pstmt.close();
        } catch (SQLException e) {
            conn.rollback();
            e.printStackTrace();
        }
    }




    private Connection getConnection() {
        Connection connection = null;
        String user = "JavaWeb";
        String password = "q1w2e3r4";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://123.206.43.242:3336/zsb?useUnicode=true&characterEncoding=utf-8&useSSL=false";
            connection = DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("cn.xu419.conn:类加载失败");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("cn.xu419.conn:连接失败");
        }
        return connection;
    }

}
