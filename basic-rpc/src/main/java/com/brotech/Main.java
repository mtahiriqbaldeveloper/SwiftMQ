package com.brotech;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        RpcServer rpcServer = new RpcServer();
        rpcServer.registerProcess(RpcMethod.ECHO, new EchoProcess());
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            rpcServer.shutDownServer();
            System.out.println("server shutdown");
        }
        ));
        rpcServer.start();
    }
}