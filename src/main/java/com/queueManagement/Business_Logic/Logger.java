package com.queueManagement.Business_Logic;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Logger {

    private String logFileName;
    private boolean isFirstRun = true;

    public Logger(String logFileName) {
        this.logFileName = "logs/" + logFileName;
    }

    public void log(String message) {
        try {
            File logFile = new File(logFileName);

            File parentDir = logFile.getParentFile();
            if (parentDir != null && !parentDir.exists()) {
                parentDir.mkdirs();
            }

            if (isFirstRun) {
                if (logFile.exists()) {
                    logFile.delete();
                }
                isFirstRun = false;
            }

            FileWriter writer = new FileWriter(logFile, true);
            writer.write(message);
            writer.write(System.lineSeparator());
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
