package com.example.Utils;

import javafx.scene.control.Alert;
import org.json.JSONObject;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;

public class Client {
    private static final String HOST = "localhost";
    private static final int PORT = 6003;
    private final Socket mainSocket;
    private final BufferedReader bufferedReader;
    private final BufferedWriter bufferedWriter;
    public static boolean isLogin = false;
    private static Client instance = null;

    private Client() throws IOException {
        mainSocket = new Socket(HOST,PORT);
        bufferedReader = new BufferedReader(new InputStreamReader(mainSocket.getInputStream()));
        bufferedWriter = new BufferedWriter(new OutputStreamWriter(mainSocket.getOutputStream()));
    }

    public static Client getInstance() throws IOException {
        if (instance == null) {
            instance = new Client();
        }
        return instance;
    }

    public void sendDataToServer(Object Data) {
        try {
            bufferedWriter.write(Data.toString());
            bufferedWriter.newLine();
            bufferedWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
            closeConnection();
        }
    }

    public void closeConnection() {
        try {
            bufferedWriter.close();
            bufferedReader.close();
            mainSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String receiveData() {
        String data = "";
        try {
            data = bufferedReader.readLine();
            return new JSONObject(data).toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void showAlertWithoutHeaderText(String data) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Test Connection");

        // Header Text: null
        alert.setHeaderText(null);
        alert.setContentText(data);

        alert.showAndWait();
    }
}
