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
import java.util.Base64;

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

    public void SendMail(ActionEvent actionEvent) throws IOException {
        String receiver = txtReceiver.getText();
        String topic = txtTopic.getText();
        String content = txtContent.getText();
        instance.sendDataToServer(new JSONObject().put("receiver", receiver).put("topic", topic)
                .put("message", content).put("status", "SUCCESS").put("type", "SENDMAIL").toString());
        listenData(actionEvent);
    }

    private void listenData(ActionEvent actionEvent) throws IOException {
        String data = instance.receiveData();
        JSONObject res = new JSONObject(data);

        byte[] decodedBytes = Base64.getDecoder().decode(res.getString("encode"));
        String decodeString = new String(decodedBytes);
        JSONObject jsonObject = new JSONObject(decodeString);
        System.out.println(jsonObject);
    }

    public void Close(ActionEvent actionEvent) {
        instance.closeConnection();
    }
}
