package com.example.demo4;

import com.example.Utils.Client;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;
import org.json.JSONObject;

import java.io.IOException;

public class HomeController {
    private Client instance = Client.getInstance();

    public HomeController() throws IOException {
    }

    public void LogOut(ActionEvent actionEvent) throws IOException {
        instance.sendDataToServer(new JSONObject().put("type", "LOGOUT").toString());

        Parent loginViewParent = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
        Scene loginViewScene = new Scene(loginViewParent);

        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(loginViewScene);
        window.show();
    }

    public void WriteMail(ActionEvent actionEvent) throws IOException {
        Parent loginViewParent = FXMLLoader.load(getClass().getResource("send-mail-view.fxml"));
        Scene loginViewScene = new Scene(loginViewParent);

        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(loginViewScene);
        window.show();


//        Stage popupwindow=new Stage();
//
//        popupwindow.initModality(Modality.APPLICATION_MODAL);
////        VBox layout= new VBox(10);
////        Scene scene1= new Scene(layout, 300, 250);
//        Parent loginViewParent = FXMLLoader.load(getClass().getResource("send-mail-view.fxml"));
//        Scene loginViewScene = new Scene(loginViewParent);
//        popupwindow.setScene(loginViewScene);
//
//        popupwindow.showAndWait();
    }
}
