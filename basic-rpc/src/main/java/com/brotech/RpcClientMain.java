package com.brotech;

public class RpcClientMain {
    public static void main(String[] args) {
        RpcClient rpcClient = new RpcClient();
        rpcClient.connect("localhost",9090);
        RpcMessage response = rpcClient.echoMessage("hello server");
        System.out.println(response);
        rpcClient.closeClient();
    }
}
