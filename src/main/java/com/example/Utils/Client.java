package com.example.Utils;

import com.google.gson.Gson;

import java.io.*;
import java.net.Socket;

public class Client {
    private static final String HOST = "localhost";
    private static final int PORT = 5000;
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
            System.out.println(Data.toString());
            bufferedWriter.write(Data.toString());
            bufferedWriter.newLine();
            bufferedWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
            closeConnection();
        }
    }

    private void closeConnection() {
        try {
            bufferedWriter.close();
            bufferedReader.close();
            mainSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String receiveData() {
        String data = "{}";
        try {

            data = bufferedReader.readLine();
            String gson = new Gson().toJson(data);

            System.out.println(gson);
            System.out.println("Client receive: "+ data);
        } catch (IOException e) {
            e.printStackTrace();
            closeConnection();
        }
        return new Gson().toJson(data).toString();
    }
}
