package Database;

import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;

public class MySqlConnection {
    private static Connection connection;
    private static String url = "jdbc:mysql://localhost:3306/test?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static String databaseUser = "Amou";
    private static String databasePassword = "hwh123456";

    public static Statement statement = null;

    static{
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("连接数据库");
            connection = DriverManager.getConnection(url, databaseUser, databasePassword);
            statement = connection.createStatement();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
