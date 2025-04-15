package com.queueManagement.Business_Logic;

import com.queueManagement.Model.Client;
import com.queueManagement.Model.Server;

import java.util.List;

public class ConcreteStrategyQueue implements Strategy{
    @Override
    public synchronized void addClient(List<Server> servers, Client client) {
        Server bestServer = null;
        int minQueueSize = Integer.MAX_VALUE;
        for (Server server : servers) {
            if (server.getTotalClients() < minQueueSize) {
                minQueueSize = server.getTotalClients();
                bestServer = server;
            }
        }

        if(bestServer != null) {
            bestServer.addClient(client);
        }

    }
}
