package com.example.demo4;

import com.example.Utils.Client;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.json.JSONObject;

import java.io.IOException;

public class RegisterController {
    public Button btnExit;
    public Button btnRegister;
    public TextField txtUsername;
    public TextField txtPassword;
    public TextField txtPasswordConfirm;
    public Client instance = Client.getInstance();

    public RegisterController() throws IOException {
    }

    static boolean isValid(String email) {
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        return email.matches(regex);
    }

//    static boolean isValidPassword(String password) {
//        String regex = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\\\S+$).{8,}";
//        return password.matches(regex);
//    }

    public void Register(ActionEvent actionEvent) throws IOException {
        String username = txtUsername.getText();
        String password = txtPassword.getText();
        String confirmPassword = txtPasswordConfirm.getText();
        String regex = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}";

        System.out.println("uesrname" + username + " password " + password + " confirm " + confirmPassword);
        if (!isValid(username)) {
            instance.showAlertWithoutHeaderText("Tài khoản không hợp lệ");
        } else if (password.matches(regex) == false) {
            instance.showAlertWithoutHeaderText("Mật khẩu không hợp lệ\n" +
                    "Mật khẩu phải có:\n\t" +
                    "+ Mật khẩu và xác nhận phải trùng nhau\n\t" +
                    "+ Ít nhất 8 kí tự\n\t" +
                    "+ Chứa 1 số\n\t" +
                    "+ Chứa chữ in hoa và chữ thường\n\t" +
                    "+ Chứa kí tự đặc biệt\n\t" +
                    "+ Không được chứa khoảng trắng");
        } else if (!password.equals(confirmPassword)) {
            instance.showAlertWithoutHeaderText("Mật khẩu không khớp");
        } else {
            instance.sendDataToServer(new JSONObject().put("username", username).put("password", password).put("type", "REGISTER").toString());
            listenData(actionEvent);
        }
    }

    private void listenData(ActionEvent actionEvent) throws IOException {
        String data = instance.receiveData();
        JSONObject res = new JSONObject(data);
        System.out.println(res);
        switch (res.getString("type")) {
            case "REGISTER":
                if (res.getString("status").equalsIgnoreCase("SUCCESS")) {
                    instance.showAlertWithoutHeaderText("Đăng kí tài khoản thành công");
                    Parent loginViewParent = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
                    Scene loginViewScene = new Scene(loginViewParent);

                    Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                    window.setScene(loginViewScene);
                    window.show();
                } else {
                    instance.showAlertWithoutHeaderText("đăng kí thất bại");
                }
            default:

        }
    }

    public void BackToLogin(ActionEvent actionEvent) throws IOException {
        Parent loginViewParent = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
        Scene loginViewScene = new Scene(loginViewParent);

        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(loginViewScene);
        window.show();
    }

    public void Close(ActionEvent actionEvent) {
        instance.closeConnection();
    }
}
