package com.example.Utils;

import com.google.gson.Gson;

public class Request {
    private String type;
    private Object data;

    public String getType() {
        return type;
    }

    public Request setType(String type) {
        this.type = type;
        return this;
    }

    public Object getData() {
        return data;
    }

    public Request setData(Object data) {
        this.data = data;
        return this;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this).toString();
    }
}
