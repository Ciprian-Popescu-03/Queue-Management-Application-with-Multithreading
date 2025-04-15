package com.queueManagement.Business_Logic;

import com.queueManagement.Model.Client;
import com.queueManagement.Model.Server;

import java.util.List;

public class ConcreteStrategyTime implements Strategy {
    @Override
    public synchronized void addClient(List<Server> servers, Client client) {
        Server bestServer = null;
        int minWaitTime = Integer.MAX_VALUE;
        for (Server server : servers) {
            if(server.getTotalServiceTime() < minWaitTime){
                minWaitTime = server.getTotalServiceTime();
                bestServer = server;
            }
        }

        if(bestServer != null){
            bestServer.addClient(client);
        }

    }
}
