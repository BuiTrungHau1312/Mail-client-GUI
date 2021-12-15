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

public class HellorController {
    public TextField txtusername;
    public TextField txtpassword;
    public Button btnLogin;
    public Button btnRegister;
    private Client instance = Client.getInstance();

    public HellorController() throws IOException {
    }

    public void Login(ActionEvent actionEvent) throws IOException {
        String username = txtusername.getText();
        String password = txtpassword.getText();

        if (!isValid(username, password)) {
            showAlertWithoutHeaderText("Invalid username and password");
        } else {
            instance.sendDataToServer(new JSONObject().put("username", username).put("password", password).put("type", "LOGIN").toString());
            listenData(actionEvent);
        }
    }

    private void listenData(ActionEvent actionEvent) throws IOException {
        String data = instance.receiveData();
        JSONObject res = new JSONObject(data);
        System.out.println(res);
        switch (res.getString("type")) {
            case "LOGIN":
                if (res.getString("status").equalsIgnoreCase("SUCCESS") ) {
                    Parent loginViewParent = FXMLLoader.load(getClass().getResource("home-view.fxml"));
                    Scene loginViewScene = new Scene(loginViewParent);

                    Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                    window.setScene(loginViewScene);
                    window.show();
                    break;
                } else if (res.getString("status").equalsIgnoreCase("ERROR")) {
                    showAlertWithoutHeaderText("Tai khoan hoac mat khau khong hop le");
                }
            default:
                
        }
    }

    private boolean isValid(String username, String password) {
        return username != null && password != null;
    }

    private void showAlertWithoutHeaderText(String data) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Test Connection");

        // Header Text: null
        alert.setHeaderText(null);
        alert.setContentText(data);

        alert.showAndWait();
    }

    public void Register(ActionEvent actionEvent) throws Exception {
        Parent loginViewParent = FXMLLoader.load(getClass().getResource("register-view.fxml"));
        Scene loginViewScene = new Scene(loginViewParent);

        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(loginViewScene);
        window.show();
    }
}
