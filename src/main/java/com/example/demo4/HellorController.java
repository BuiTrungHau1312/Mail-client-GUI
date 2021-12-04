package com.example.demo4;

import com.example.DTO.Account;
import com.example.Utils.Client;
import com.example.Utils.Request;
import com.google.gson.Gson;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;

public class HellorController {
    public TextField txtusername;
    public TextField txtpassword;
    public Button btnLogin;
    public Button btnRegister;
    private Client instance = Client.getInstance();

    public HellorController() throws IOException {
    }

    public void Login(ActionEvent actionEvent) {
        String username = txtusername.getText();
        String password = txtpassword.getText();

        Account account = new Account(username, password);
        String s = new Gson().toJson(account);
        System.out.println(s);
        instance.sendDataToServer(new Request().setData(account).setType("LOGIN"));
        String response = instance.receiveData();
        System.out.println(response);


//        showAlertWithoutHeaderText(new Gson().toJson(account));
    }

    private void showAlertWithoutHeaderText(String data) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Test Connection");

        // Header Text: null
        alert.setHeaderText(null);
        alert.setContentText(data.toString());

        alert.showAndWait();
    }
}
