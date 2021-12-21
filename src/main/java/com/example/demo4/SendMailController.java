package com.example.demo4;

import com.example.Utils.Client;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.json.JSONObject;

import java.io.IOException;

public class SendMailController {
    public TextArea txtContent;
    public TextField txtTopic;
    public TextField txtReceiver;
    Client instance = Client.getInstance();

    public SendMailController() throws IOException {
    }

    public void BackToHome(ActionEvent actionEvent) throws IOException {
        Parent loginViewParent = FXMLLoader.load(getClass().getResource("home-view.fxml"));
        Scene loginViewScene = new Scene(loginViewParent);

        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(loginViewScene);
        window.show();
    }
    public void SendMail(ActionEvent actionEvent) {
        String receiver = txtReceiver.getText();
        String topic = txtTopic.getText();
        String content = txtContent.getText();
        instance.sendDataToServer(new JSONObject().put("receiver", receiver).put("topic", topic)
                .put("message", content).put("status", "SUCCESS").put("type", "SENDMAIL").toString());
    }
}
