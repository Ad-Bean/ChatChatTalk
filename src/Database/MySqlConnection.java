package Database;

import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;

public class MySqlConnection {
    private static Connection connection;
    // replace test to your own database name
    private static String url = "jdbc:mysql://localhost:3306/test?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    // replace user and password to your own mysql server
    private static String databaseUser = "root";
    private static String databasePassword = "";

    public static Statement statement = null;

    static{
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, databaseUser, databasePassword);
            statement = connection.createStatement();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
