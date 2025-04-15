package com.queueManagement.Business_Logic;

import com.queueManagement.Model.Client;
import com.queueManagement.GUI.SimulationGUI;

import java.util.*;

public class SimulationManager implements Runnable {
    public int timeLimit;
    public int maxServiceTime;
    public int minServiceTime;
    public int minArrivalTime;
    public int maxArrivalTime;
    public int numberOfServers;
    public int numberOfClients;
    public SelectionPolicy selectionPolicy = SelectionPolicy.SHORTEST_TIME;

    private Scheduler scheduler;
    private List<Client> generatedClients;
    private Logger logger;
    private SimulationGUI gui;

    private double totalServiceTime = 0;
    private double totalWaitingTime = 0;

    int[] waitingTimes;

    public SimulationManager(int timeLimit, int minServiceTime, int maxServiceTime, int minArrivalTime, int maxArrivalTime,
                             int numberOfClients, int numberOfServers, SelectionPolicy selectionPolicy,
                             SimulationGUI gui, String logFileName) {

        this.timeLimit = timeLimit;
        this.minServiceTime = minServiceTime;
        this.maxServiceTime = maxServiceTime;
        this.minArrivalTime = minArrivalTime;
        this.maxArrivalTime = maxArrivalTime;
        this.numberOfClients = numberOfClients;
        this.numberOfServers = numberOfServers;
        this.selectionPolicy = selectionPolicy;
        this.gui = gui;
        waitingTimes = new int[numberOfClients + 1];

        scheduler = new Scheduler(numberOfServers);
        scheduler.setStrategy(selectionPolicy);
        generateNRandomClients();

        logger = new Logger(logFileName);
    }

    private void generateNRandomClients() {
        generatedClients = new ArrayList<>();
        Random rand = new Random();

        for (int i = 1; i <= numberOfClients; i++) {
            int arrivalTime = rand.nextInt(maxArrivalTime - minArrivalTime) + 1;
            int serviceTime = rand.nextInt(maxServiceTime - minServiceTime) + 1;

            totalServiceTime += serviceTime;

            Client client = new Client(i, arrivalTime, serviceTime);
            generatedClients.add(client);
        }

        generatedClients.sort(Comparator.comparingInt(Client::getArrivalTime));
    }

    @Override
    public void run() {
        int currentTime = 0;

        String initialMessage = "Client format: (Client id, Client arrival time, Client service time)\n";
        if(gui != null) {
            gui.appendLog(initialMessage + "\n");
        }
        System.out.println(initialMessage);
        logger.log(initialMessage);

        int stopFlag = 0;

        while (currentTime <= timeLimit && stopFlag < 1) {
            if (generatedClients.isEmpty() && scheduler.isEmpty()) {
                stopFlag++;
            }

            Iterator<Client> iterator = generatedClients.iterator();
            while (iterator.hasNext()) {
                Client client = iterator.next();
                if (client.getArrivalTime() == currentTime) {
                    scheduler.dispatchClient(client);
                    waitingTimes[client.getId()] = client.getWaitingTime();
                    totalWaitingTime += client.getWaitingTime();
                    iterator.remove();
                }
            }

            StringBuilder logMessage = new StringBuilder();
            logMessage.append("Time: ").append(currentTime).append("\n");
            logMessage.append("Waiting Clients: ");

            if(gui != null) {
                gui.appendLog("Time: " + currentTime + "\n");
                gui.appendLog("Waiting Clients: ");
            }
            System.out.println("\nTime: " + currentTime);
            System.out.print("Waiting Clients: ");

            for (Client client : generatedClients) {
                logMessage.append("(")
                        .append(client.getId()).append(", ")
                        .append(client.getArrivalTime()).append(", ")
                        .append(client.getRemainingServiceTime()).append("); ");

                if(gui != null) {
                    gui.appendLog("(" + client.getId() + ", " + client.getArrivalTime() + ", " + client.getRemainingServiceTime() + "); ");
                }
                System.out.print("(" + client.getId() + ", " + client.getArrivalTime() + ", " + client.getRemainingServiceTime() + "); ");
            }

            System.out.println();
            logMessage.append("\n");
            logMessage.append(scheduler.logQueueState()).append("\n");

            if(gui != null) {
                gui.appendLog("\n");
                gui.appendLog(scheduler.logQueueState() + "\n");
            }

            scheduler.printQueue();

            logger.log(logMessage.toString());

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }

            currentTime++;
        }

        double averageServiceTime = totalServiceTime / numberOfClients;
        String formattedAverageServiceTime = String.format("Average Service Time: %.2f", averageServiceTime);

        double averageWaitingTime = totalWaitingTime / numberOfClients;
        String formattedAverageWaitingTime = String.format("Average Waiting Time: %.2f", averageWaitingTime);

        if(gui != null) {
            gui.appendLog("\n" + formattedAverageWaitingTime + "\n");
        }
        System.out.println("\n" + formattedAverageWaitingTime);
        logger.log(formattedAverageWaitingTime + "\n");

        if(gui != null) {
            gui.appendLog("\n" + formattedAverageServiceTime + "\n");
        }
        System.out.println("\n" + formattedAverageServiceTime);
        logger.log(formattedAverageServiceTime + "\n");

        System.out.println("\nThe waiting times are: ");
        for (int i = 1; i <= numberOfClients; i++) {
            System.out.println("Client " + i + " waiting time: " + waitingTimes[i]);
        }

    }
}
