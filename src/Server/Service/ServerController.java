package Server.Service;

import Database.MySqlFunction;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.io.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ServerController {

    @FXML
    private Button exportBtn;
    @FXML
    private Button closeBtn;
    @FXML
    private Text digitClock;

    private volatile boolean enough = false;

    public ServerController() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        final String initial = simpleDateFormat.format(new Date());
        Platform.runLater(()-> digitClock.setText(initial));
        new Thread(() -> {
            SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            while(!enough) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                final String time = dt.format(new Date());
                Platform.runLater(()-> digitClock.setText(time));
            }
        }).start();
    }
    @FXML
    void closeServer(MouseEvent event) {
        if (event.getSource() == closeBtn) {
            System.out.println("Shutting down server...");
            System.exit(0);
        }
    }

    // export user data into txt
    @FXML
    void exportUserData(MouseEvent event) {
        if (event.getSource() == exportBtn) {
            System.out.println("Exporting user data...");
            try{
                BufferedWriter out = new BufferedWriter(new FileWriter("RegisterUsers.txt"));
                ResultSet resultSet = MySqlFunction.getAllRegisterUsers();
                String users = new String();
                try {
                    while (resultSet.next()) {
                        users += resultSet.getString("icon") + ", ";
                        users += resultSet.getString("nickName") + ", ";
                        users += resultSet.getString("username") + ", ";
                        users += resultSet.getString("password") + ", ";
                        users += resultSet.getString("email") + ", ";
                        users += resultSet.getString("gender") + ", ";
                        users += resultSet.getString("phone") + "\n";
                    }
                } catch (SQLException e){
                    e.printStackTrace();
                }
                out.write(users);
                out.close();
                System.out.println("Export successfully!");
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }


}

