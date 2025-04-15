package com.queueManagement.GUI;

import com.queueManagement.Business_Logic.SimulationManager;
import com.queueManagement.Business_Logic.SelectionPolicy;

import javax.swing.*;
import java.awt.*;

public class SimulationGUI extends JFrame {

    private JTextArea logArea;
    private JSpinner[] inputSpinners;
    private JButton startButton;

    public SimulationGUI() {
        setTitle("Queue Management Simulation");
        setSize(1000, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initializeComponents();
    }

    private void initializeComponents() {
        setLayout(new BorderLayout());

        JLabel title = new JLabel("Queue Management Application!", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        add(title, BorderLayout.NORTH);

        JPanel inputPanel = new JPanel(new GridLayout(8, 2, 10, 10));
        String[] inputTitles = {
                "Number of Clients", "Number of Queues", "Simulation Interval",
                "Min Arrival Time", "Max Arrival Time", "Min Service Time", "Max Service Time"
        };
        inputSpinners = new JSpinner[inputTitles.length];

        for (int i = 0; i < inputTitles.length; i++) {
            JLabel label = new JLabel(inputTitles[i] + ":");
            inputSpinners[i] = new JSpinner(new SpinnerNumberModel(1, 1, 1000, 1));
            inputPanel.add(label);
            inputPanel.add(inputSpinners[i]);
        }

        startButton = new JButton("Start Simulation");
        inputPanel.add(new JLabel());
        inputPanel.add(startButton);

        add(inputPanel, BorderLayout.CENTER);

        logArea = new JTextArea(10, 50);
        logArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(logArea);
        add(scrollPane, BorderLayout.SOUTH);

        startButton.addActionListener(e -> {
            int numberOfClients = (int) inputSpinners[0].getValue();
            int numberOfQueues = (int) inputSpinners[1].getValue();
            int timeLimit = (int) inputSpinners[2].getValue();
            int minArrival = (int) inputSpinners[3].getValue();
            int maxArrival = (int) inputSpinners[4].getValue();
            int minService = (int) inputSpinners[5].getValue();
            int maxService = (int) inputSpinners[6].getValue();

            SimulationManager manager = new SimulationManager(
                    timeLimit, minService, maxService, minArrival, maxArrival,
                    numberOfClients, numberOfQueues,
                    SelectionPolicy.SHORTEST_TIME, this, "simulation_log.txt"
            );

            new Thread(manager).start();
        });
    }

    public void appendLog(String message) {
        SwingUtilities.invokeLater(() -> logArea.append(message));
    }

    private static void runTestsCases(){
        // Test 1
        SimulationManager test1 = new SimulationManager(
                60, 2, 4, 2,
                30, 4, 2,
                SelectionPolicy.SHORTEST_TIME,
                null,
                "test1_log.txt"
        );
        new Thread(test1).start();

        // Test 2
        SimulationManager test2 = new SimulationManager(
                60, 1, 7, 2,
                40, 50, 5,
                SelectionPolicy.SHORTEST_TIME,
                null,
                "test2_log.txt"
        );
        new Thread(test2).start();

        // Test 3
        SimulationManager test3 = new SimulationManager(
                200, 3, 9, 10,
                100, 1000, 20,
                SelectionPolicy.SHORTEST_TIME,
                null,
                "test3_log.txt"
        );
        new Thread(test3).start();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SimulationGUI gui = new SimulationGUI();
            gui.setVisible(true);
        });

//        runTestsCases();
    }
}
