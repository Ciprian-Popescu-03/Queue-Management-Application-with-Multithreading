package com.queueManagement.Model;

public class Client {
    private int id;
    private int arrivalTime;
    private int serviceTime;
    private int remainingServiceTime;
    private int waitingTime;

    public Client(int id, int arrivalTime, int serviceTime) {
        this.id = id;
        this.arrivalTime = arrivalTime;
        this.serviceTime = serviceTime;
        this.remainingServiceTime = serviceTime;
        this.waitingTime = 0;
    }

    public int getId() {
        return id;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getServiceTime() {
        return serviceTime;
    }

    public int getWaitingTime(){
        return waitingTime;
    }

    public void setWaitingTime(int waitingTime){
        this.waitingTime = waitingTime;
    }

    public synchronized int getRemainingServiceTime() {
        return remainingServiceTime;
    }

    public synchronized void decrementRemainingServiceTime() {
        if(remainingServiceTime > 0) {
            remainingServiceTime--;
        }
    }
}
