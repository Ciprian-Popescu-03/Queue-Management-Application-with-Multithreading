package com.queueManagement.Model;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Server implements Runnable {
    private int id;
    private BlockingQueue<Client> clients;
    private AtomicInteger waitingPeriod;
    private volatile boolean running;
    private Client currentClient;

    public Server(int id) {
        this.id = id;
        this.clients = new LinkedBlockingQueue<>();
        this.waitingPeriod = new AtomicInteger(0);
        this.running = true;
    }

    public synchronized void addClient(Client newClient) {
        int waitingTime = waitingPeriod.get();
        newClient.setWaitingTime(waitingTime);
        clients.add(newClient);
        waitingPeriod.addAndGet(newClient.getServiceTime());
    }

    public int getId() {
        return id;
    }

    @Override
    public void run() {
        while (running) {
            try {
                currentClient = clients.take();

                while (currentClient.getRemainingServiceTime() > 0 && running) {
                    Thread.sleep(1000);
                    currentClient.decrementRemainingServiceTime();
                    waitingPeriod.decrementAndGet();
                }

                currentClient = null;
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    public int getTotalClients() {
        int count = clients.size();
        if (currentClient != null) {
            count += 1;
        }
        return count;
    }

    public int getTotalServiceTime() {
        int time = waitingPeriod.get();
        if (currentClient != null) {
            time += currentClient.getServiceTime();
        }
        return time;
    }

    public String getClientQueueAsString() {
        StringBuilder sb = new StringBuilder();

        if (currentClient == null && clients.isEmpty()) {
            return "closed";
        }

        if (currentClient != null) {
            sb.append("(")
                    .append(currentClient.getId()).append(", ")
                    .append(currentClient.getArrivalTime()).append(", ")
                    .append(currentClient.getRemainingServiceTime()).append("); ");
        }

        for (Client client : clients) {
            sb.append("(")
                    .append(client.getId()).append(", ")
                    .append(client.getArrivalTime()).append(", ")
                    .append(client.getRemainingServiceTime()).append("); ");
        }

        return sb.toString();
    }

    public boolean isEmpty() {
        return clients.isEmpty() && currentClient == null;
    }
}
