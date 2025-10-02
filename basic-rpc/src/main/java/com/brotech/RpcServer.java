package com.brotech;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RpcServer {
    private final int port = 9090;
    private final ServerSocket serverSocket;
    private final ExecutorService executorService = Executors.newFixedThreadPool(4);
    private boolean isServerRunning = false;
    private Map<Integer,RpcProcess> process = new ConcurrentHashMap<>();

    public RpcServer() throws IOException {
        serverSocket = new ServerSocket(port);
    }

    public void registerProcess(int methodCode, RpcProcess rpcProcess){
        process.put(methodCode,rpcProcess);
    }

    public void start() {
        System.out.println("starting server");
        try{
            isServerRunning = true;
            while (isServerRunning) {
                Socket client = serverSocket.accept();
                executorService.submit(() -> handleClient(client));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void handleClient(Socket client) {
        System.out.printf("new client connect with address %s \n", client.getInetAddress());
        try (BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
             PrintWriter writer = new PrintWriter(client.getOutputStream(), true)) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                RpcMessage rpcMessage = RpcMessage.fromJson(inputLine);
                RpcMessage processedRequest = processRequest(rpcMessage);
                writer.println(processedRequest.toJson());
                System.out.println(processedRequest);
            }

        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    private RpcMessage processRequest(RpcMessage request){
        RpcProcess rpcProcess = process.get(request.getMethodCode());
        return rpcProcess.processRequest(request);
    }

    public void shutDownServer() {
        System.out.println("shutting down the server");
        isServerRunning = false;
        try {
            serverSocket.close();
            executorService.shutdown();
        } catch (IOException e) {
            System.err.println("Error shutting down server: " + e.getMessage());
        }
    }
}
