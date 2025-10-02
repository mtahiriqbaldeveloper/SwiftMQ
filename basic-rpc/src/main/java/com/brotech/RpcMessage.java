package com.brotech;

import com.google.gson.Gson;

public class RpcMessage {
    private long id;
    private String payload;
    private int methodCode;
    private String errorMessage;

    public RpcMessage() {
    }

    public RpcMessage(long id, String body,int methodCode) {
        this.id = id;
        this.payload = body;
        this.methodCode = methodCode;
    }
    public static RpcMessage response(long id, String response, String errorMessage){
        RpcMessage rpcMessage = new RpcMessage();
        rpcMessage.id = id;
        rpcMessage.payload = response;
        rpcMessage.errorMessage = errorMessage;
        return rpcMessage;
    }
    public String toJson(){
        return new Gson().toJson(this);
    }

    public static RpcMessage fromJson(String request){
        return new Gson().fromJson(request, RpcMessage.class);
    }

    public int getMethodCode() {
        return methodCode;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public String toString() {
        return "RpcMessage{" +
                "id=" + id +
                ", response='" + payload + '\'' +
                ", methodCode=" + methodCode +
                ", errorMessage='" + errorMessage + '\'' +
                '}';
    }
}
