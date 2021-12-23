package com.example.demo4;

import com.example.DTO.Account;
import com.example.Utils.Client;
import com.example.Utils.Request;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class HellorController {
    public TextField txtUsername;
    public TextField txtPassword;
    public Button btnLogin;
    public Button btnRegister;
    private Client instance = Client.getInstance();

    public HellorController() throws IOException {
    }

    public void Login(ActionEvent actionEvent) throws IOException, NoSuchAlgorithmException {
        String username = txtUsername.getText();
        String password = txtPassword.getText();

        if (!isValid(username, password)) {
            instance.showAlertWithoutHeaderText("Invalid username and password");
        } else {
            instance.sendDataToServer(new JSONObject().put("username", username).put("password", password).put("type", "LOGIN").toString());
            listenData(actionEvent);
        }
//        String originalInput = "123";
//        String encodedString = Base64.getEncoder().encodeToString(originalInput.getBytes());
//
//        byte[] decodedBytes = Base64.getDecoder().decode(encodedString);
//        String decodedString = new String(decodedBytes);
    }

    private void listenData(ActionEvent actionEvent) throws IOException {
        String data = instance.receiveData();
        JSONObject res = new JSONObject(data);
//        System.out.println(res);
        switch (res.getString("type")) {
            case "LOGIN":
                if (res.getString("status").equalsIgnoreCase("SUCCESS")) {
                    Parent loginViewParent = FXMLLoader.load(getClass().getResource("home-view.fxml"));
                    Scene loginViewScene = new Scene(loginViewParent);

                    Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                    window.setScene(loginViewScene);
                    window.show();
                    break;
                } else if (res.getString("status").equalsIgnoreCase("ERROR")) {
                    instance.showAlertWithoutHeaderText("Tài khoản hoặc mật khẩu không hợp lệ");
                }
            default:
                
        }
    }

    private boolean isValid(String username, String password) {
        return username != null && password != null;
    }

    public void Register(ActionEvent actionEvent) throws Exception {
        Parent loginViewParent = FXMLLoader.load(getClass().getResource("register-view.fxml"));
        Scene loginViewScene = new Scene(loginViewParent);

        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(loginViewScene);
        window.show();
    }

    public void Close(ActionEvent actionEvent) {
        instance.closeConnection();
    }
}
