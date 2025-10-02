package com.brotech;

import java.util.Random;
import java.util.UUID;

public class EchoProcess implements RpcProcess{
    @Override
    public RpcMessage processRequest(RpcMessage request) {
        return RpcMessage.response(new Random().nextInt(),"response from server","null");
    }
}
