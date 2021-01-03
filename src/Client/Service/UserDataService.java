package Client.Service;

import Client.Model.User;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UserDataService implements Serializable {
    private static final long serialVersionUID = 1;
    public static List<User> loadAllRegisteredUsers() {
        List<User> list;
        list = new ArrayList<>(100);
        ObjectInputStream objectInputStream;
        try {
            objectInputStream = new ObjectInputStream(new FileInputStream(DataBuffer.properties.getProperty("dataBase")));
            Object obj;
            obj = objectInputStream.readObject();
            list = (List<User>) obj;
            objectInputStream.close();
        } catch (EOFException e) {
            System.out.println("Registered user list is empty!");
        } catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }

    public static void saveAllRegisteredUsers(List<User> users) {
        ObjectOutputStream objectOutputStream;
        try {
            objectOutputStream = new ObjectOutputStream(new FileOutputStream(DataBuffer.properties.getProperty("dataBase")));
            // 写回用户信息
            objectOutputStream.writeObject(users);
            objectOutputStream.flush();
            objectOutputStream.close();
        } catch (Exception e) {
            System.out.println("save all users ERROR!");
            e.printStackTrace();
        }
    }

    public static List<User> loadAllOnlineUsers() {
        List<User> list;
        list = new ArrayList<>(100);
        ObjectInputStream objectInputStream;
        try {
            objectInputStream = new ObjectInputStream(new FileInputStream(DataBuffer.properties.getProperty("onlineUsers")));
            Object obj;
            obj = objectInputStream.readObject();
            list = (List<User>) obj;
            objectInputStream.close();
        } catch (EOFException e) {
            System.out.println("Online user list is empty");
        } catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }

    public static void saveAllOnlineUsers(List<User> users) {
        ObjectOutputStream objectOutputStream;
        try {
            objectOutputStream = new ObjectOutputStream(new FileOutputStream(DataBuffer.properties.getProperty("onlineUsers")));
            // 写回用户信息
            objectOutputStream.writeObject(users);
            objectOutputStream.flush();
            objectOutputStream.close();
        } catch (Exception e) {
            System.out.println("save all online users ERROR!");
            e.printStackTrace();
        }
    }

}
