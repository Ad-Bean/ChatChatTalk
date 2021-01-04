package Database;

import java.sql.ResultSet;
import java.sql.SQLException;
import Client.Model.User;
import Database.MySqlConnection;

public class MySqlFunction {
    public static ResultSet findUserByUsername(String dataSheet, String username){
        ResultSet resultSet;
        try{
            String sql = "SELECT * FROM "+ dataSheet +" WHERE username = \""+ username +"\"";
            resultSet = MySqlConnection.statement.executeQuery(sql);
            return resultSet;
        } catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public static ResultSet findUserByNickName(String dataSheet, String nickName){
        ResultSet resultSet;
        try{
            String sql = "SELECT * FROM "+ dataSheet +" WHERE nickName = \""+ nickName +"\"";
            resultSet = MySqlConnection.statement.executeQuery(sql);
            return resultSet;
        } catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public static ResultSet findUserByEmail(String dataSheet, String email){
        ResultSet resultSet;
        try{
            String sql = "SELECT * FROM "+ dataSheet +" WHERE email = \""+ email +"\"";
            resultSet = MySqlConnection.statement.executeQuery(sql);
            return resultSet;
        } catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public static ResultSet findUserByUsernameAndPassword(String dataSheet, String username, String password){
        ResultSet resultSet;
        try{
            String sql = "SELECT * FROM "+ dataSheet +" WHERE username = \""+ username +"\" AND password = \""+ password + "\"";
            resultSet = MySqlConnection.statement.executeQuery(sql);
            return resultSet;
        } catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public static void updateUserIcon(String username, String icon){
        try{
            String sql = "UPDATE user_info SET icon = \"" + icon +"\" WHERE username = \"" + username + "\"";
            MySqlConnection.statement.execute(sql);
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static void addUserToRegisterSheet(User user){
        try{
            String sql = String.format("INSERT INTO user_info (icon, nickName, userName, password, email, gender, phone)" +
                    "VALUES (\"%s\", \"%s\", \"%s\", \"%s\", \"%s\", \"%s\", \"%s\") "
                    , user.icon, user.nickName, user.username, user.password, user.email, user.gender, user.phone);
            MySqlConnection.statement.execute(sql);
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static void addUserToOnlineSheet(String username){
        try{
            String sql = "INSERT INTO online_user (userName) VALUES (\""+ username +"\")";
            MySqlConnection.statement.execute(sql);
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static void removeUserFromSheetByUsername(String datasheet, String username){
        try{
            String sql = "DELETE FROM " + datasheet + " WHERE username = \"" + username + "\"";
            MySqlConnection.statement.execute(sql);
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static ResultSet getAllRegisterUsers(){
        ResultSet resultSet;
        try{
            String sql = "SELECT * FROM user_info";
            resultSet = MySqlConnection.statement.executeQuery(sql);
            return resultSet;
        } catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }
}
