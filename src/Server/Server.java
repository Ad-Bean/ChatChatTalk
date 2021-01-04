package Server;

import Server.Service.ClientHandler;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server extends Application {
    private static final ArrayList<ClientHandler> clients = new ArrayList<>();

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("UI/Server.fxml"));
        primaryStage.getIcons().add(new Image("Assets/monitor.png"));
        primaryStage.setTitle("ChatChatTalk-Server");
        primaryStage.setScene(new Scene(root, 480, 240));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        ServerSocket serverSocket;
        Socket socket;
        new Thread(()-> launch(args)).start();
        try {
//            launch(args);
            System.out.println("Starting server...");
            serverSocket = new ServerSocket(8000);
            while (true) {
                System.out.println("Waiting for clients...");
                socket = serverSocket.accept();
                System.out.println("Client: " + socket.getInetAddress() + socket.getLocalPort() + " Connected");
                ClientHandler clientThread = new ClientHandler(socket, clients);
                clients.add(clientThread);
                clientThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
