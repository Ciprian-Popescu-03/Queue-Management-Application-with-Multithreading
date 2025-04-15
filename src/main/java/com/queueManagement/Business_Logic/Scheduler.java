package com.queueManagement.Business_Logic;

import com.queueManagement.Model.Client;
import com.queueManagement.Model.Server;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

public class Scheduler {

    private List<Server> servers;
    private int maxNoServers;
    private Strategy strategy;

    public Scheduler(int maxNoServers) {
        this.maxNoServers = maxNoServers;
        this.servers = new ArrayList<>();

        for(int i = 0; i < maxNoServers; i++){
            Server server = new Server(i + 1);
            servers.add(server);

            Thread serverThread = new Thread(server);
            serverThread.start();
        }
    }

    public void setStrategy(SelectionPolicy policy){
        if(policy == SelectionPolicy.SHORTEST_QUEUE){
            this.strategy = new ConcreteStrategyQueue();
        } else if(policy == SelectionPolicy.SHORTEST_TIME){
            this.strategy = new ConcreteStrategyTime();
        }
    }

    public void dispatchClient(Client client){
        if(strategy != null){
            strategy.addClient(servers, client);
        }
    }

    public void printQueue(){
        for(Server server : servers){
            System.out.print("Queue " + server.getId() + ": ");
            String queueString = server.getClientQueueAsString();
            System.out.println(queueString);
        }
    }

    public String logQueueState() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < servers.size(); i++) {
            sb.append("Queue ").append(i + 1).append(": ");
            Server server = servers.get(i);

            sb.append(server.getClientQueueAsString());
            sb.append("\n");
        }
        return sb.toString();
    }

    public boolean isEmpty(){
        for(Server server : servers){
            if(!server.isEmpty()){
                return false;
            }
        }
        return true;
    }

}
