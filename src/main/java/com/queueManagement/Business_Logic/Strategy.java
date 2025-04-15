package com.queueManagement.Business_Logic;

import com.queueManagement.Model.Client;
import com.queueManagement.Model.Server;

import java.util.List;

public interface Strategy {
    public void addClient(List<Server> servers, Client client);
}
