package Client.Service;

import Client.Model.User;
import Database.MySqlFunction;
import animatefx.animation.FadeIn;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import static Client.Service.Controller.users;

public class Room extends Thread implements Initializable {
    @FXML
    public Label clientName;
    @FXML
    public Button chatBtn;
    @FXML
    public Pane chat;
    @FXML
    public TextField msgField;
    @FXML
    public Label online;
    @FXML
    public Label nickName;
    @FXML
    private Label userName;
    @FXML
    public Label email;
    @FXML
    public Label phoneNo;
    @FXML
    public Label gender;
    @FXML
    public Pane profile;
    @FXML
    public Button profileBtn;
    @FXML
    public TextField fileChoosePath;
    @FXML
    public ImageView proImage;
    @FXML
    private ImageView genderImage;
    @FXML
    public Circle showProPic;
    @FXML
    private TextFlow testRoom;
    @FXML
    private ScrollPane scrollPane;

    private FileChooser fileChooser;
    private File filePath;
    // Changing profile picture
    public boolean toggleChat = false, toggleProfile = false, saveControl = false;
    BufferedReader reader;
    PrintWriter writer;
    Socket socket;

    public void connectSocket() {
        try {
            socket = new Socket("localhost", 8000);
            System.out.println("Client: " + socket.getInetAddress() + ":" + socket.getLocalPort() + " is connected with server!");
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream(), true);
            this.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            while (true) {
                String msg = reader.readLine();
                String[] tokens = msg.split(" ");
                String cmd = tokens[0];
//                System.out.println(cmd);
                StringBuilder fullMsg = new StringBuilder();
                for (int i = 1; i < tokens.length; i++) {
                    fullMsg.append(tokens[i]);
                }
                // System.out.println(fullMsg);
                // Dont send messages to user itself
                if (cmd.equalsIgnoreCase(Controller.nickname + ":")) {
                    continue;
                } else if (fullMsg.toString().equalsIgnoreCase("exit")) {
                    break;
                }

                Platform.runLater(() -> {
                    Text others = new Text();
                    others.setStyle("-fx-fill: #000000; -fx-font-size: 20px; -fx-font-weight: bold; -fx-text-alignment: left;");

                    Text time = new Text();
                    time.setStyle("-fx-fill: #000000; -fx-font-size: 12px; -fx-font-weight: bold; -fx-text-alignment: left;");

                    LocalDateTime myDateObj = LocalDateTime.now();
                    DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

                    String formattedDate = myDateObj.format(myFormatObj);
                    time.setText(formattedDate + "\n");
                    others.setText(msg + "\n");

                    ImageView avatar = new ImageView();
                    String othersAvatar = Controller.avatar;

                    for (User onlineUser : users) {
                        if (cmd.equals(onlineUser.nickName + ":") && !cmd.equals(Controller.nickname + ":")) {
                            othersAvatar = onlineUser.icon;
                        }
                    }
                    System.out.println("other's avatar " + othersAvatar);
                    System.out.println("other's " + cmd);

                    Image ava = new Image(othersAvatar);
                    avatar.setImage(ava);
                    avatar.setFitWidth(32);
                    avatar.setFitHeight(32);
                    testRoom.getChildren().addAll(time, avatar, others);
                });
            }
            reader.close();
            writer.close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void handleProfileBtn(ActionEvent event) {
        if (event.getSource().equals(profileBtn) && !toggleProfile) {
            new FadeIn(profile).play();
            profile.toFront();
            chat.toBack();
            toggleProfile = true;
            toggleChat = false;
            profileBtn.setText("Back");
            setProfile();
        } else if (event.getSource().equals(profileBtn) && toggleProfile) {
            new FadeIn(chat).play();
            chat.toFront();
            toggleProfile = false;
            toggleChat = false;
            profileBtn.setText("Profile");
        }
    }

    public void handleShowProPic(MouseEvent event) {
        if (event.getSource() == showProPic && !toggleProfile) {
            new FadeIn(profile).play();
            profile.toFront();
            chat.toBack();
            toggleProfile = true;
            toggleChat = false;
            profileBtn.setText("Back");
            setProfile();
        } else if (event.getSource() == showProPic && toggleProfile) {
            new FadeIn(chat).play();
            chat.toFront();
            toggleProfile = false;
            toggleChat = false;
            profileBtn.setText("Profile");
        }
    }

    public void setProfile() {
        try{
            ResultSet resultSet = MySqlFunction.findUserbyUsername("user_info", Controller.username);
            if(resultSet.next()) {
                    nickName.setText(resultSet.getString("nickName"));
                    nickName.setOpacity(1);
                    userName.setText(resultSet.getString("username"));
                    userName.setOpacity(1);
                    email.setText(resultSet.getString("email"));
                    email.setOpacity(1);
                    phoneNo.setText(resultSet.getString("phone"));
                    gender.setText(resultSet.getString("gender"));
                    Image image;
                    if (resultSet.getString("gender").equals("Male")) {
                        image = new Image("Assets/male.png");
                    } else {
                        image = new Image("Assets/female.png");
                    }
                    genderImage.setImage(image);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
    }


    public void handleSendEvent(MouseEvent event) {
        send();

    }


    public void send() {
        String msg = msgField.getText();
        writer.println(Controller.nickname + ": " + msg);

        msgField.setText("");

        Text me = new Text();
        me.setStyle("-fx-fill: #48a868; -fx-font-size: 20px; -fx-font-weight: bold; -fx-text-alignment: left; ");

        Text time = new Text();
        time.setStyle("-fx-fill: #48a868; -fx-font-size: 12px; -fx-font-weight: bold; -fx-text-alignment: left;");

        LocalDateTime myDateObj = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDate = myDateObj.format(myFormatObj);

        time.setText(formattedDate + "\n");
        me.setText("Me: " + msg + "\n");
        ImageView avatar = new ImageView();
        avatar.setImage(proImage.getImage());
        avatar.setFitWidth(32);
        avatar.setFitHeight(32);
        testRoom.getChildren().addAll(time, avatar, me);
    }

    public void chooseImageButton(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        fileChooser = new FileChooser();
        fileChooser.setTitle("Open Image");
        this.filePath = fileChooser.showOpenDialog(stage);
        fileChoosePath.setText(filePath.getPath());
        saveControl = true;
    }

    public void sendMessageByKey(KeyEvent event) {
        if (event.getCode().toString().equals("ENTER")) {
            send();
        }
    }

    public void saveImage() {
        if (saveControl) {
            try {
                BufferedImage bufferedImage = ImageIO.read(filePath);
                String[] temp = fileChoosePath.getText().split("Avatar\\\\");
                String icon = temp[temp.length - 1];
                MySqlFunction.updateUserIcon(Controller.username, icon);
                Image image = SwingFXUtils.toFXImage(bufferedImage, null);
                proImage.setImage(image);
                showProPic.setFill(new ImagePattern(image));
                saveControl = false;
                fileChoosePath.setText("");
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        showProPic.setStroke(Color.valueOf("#90a4ae"));
        Image image = new Image("Assets/Avatar/1.png", false);
        try{
            ResultSet resultSet = MySqlFunction.findUserbyUsername("user_info", Controller.username);
            if(resultSet.next()){
                image = new Image(resultSet.getString("icon"));
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        proImage.setImage(image);
        showProPic.setFill(new ImagePattern(image));
        clientName.setText(Controller.nickname);
        connectSocket();
    }
}
