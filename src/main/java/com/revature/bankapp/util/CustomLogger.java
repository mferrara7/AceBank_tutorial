package com.revature.bankapp.util;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.time.LocalDateTime;

public class CustomLogger {
    private static CustomLogger logger; // just references logger for later
    private final boolean printToConsole; //using final since its value will not change once initialized
    private final Writer logWriter;

    private CustomLogger(boolean printToConsole) {
        Writer writer = null;
        this.printToConsole = printToConsole;
        try {
            writer = new FileWriter("src/main/resources/log.txt", true);
        } catch (IOException e) {

        }
        this.logWriter = writer;
    }

    public static CustomLogger getLogger(boolean printToConsole) {
        // CustomLogger will not be created until it is needed
        if (logger == null) {
            logger = new CustomLogger(printToConsole);
        }
        return logger;
    }

    private String formatMessage(String level, String message) {
        return String.format("[%s] %s at %s", level, message, LocalDateTime.now());
    }

    //The logMessageToFile method will write the formattedMessage into resources/log.txt, has to be public to be accessed by classes in other packages
    public void logMessageToFile(String formattedMessage) {
        if (logWriter != null) {
            try {
                logWriter.write(formattedMessage + "\n");
                logWriter.flush();
            } catch (IOException e) {
                printMessageToConsole("ERROR", "Could not write message to file");
            }
        }
    }


    private void printMessageToConsole(String level, String message) {
        switch (level) {
            case "INFO":
                System.out.println(message);
                break;
            case "WARN":
                System.out.println(message);
                break;
            case "ERROR": // to be filled out later
            case "FATAL":
                System.out.println(message);
                break;
        }
    }

    public void info(String message, Object... extra) { // the Object... extra is in case we want to pass multiple values inside our message
        String formattedMessage = formatMessage("INFO", String.format(message, extra)); // creates a string with the message provided, the extra is for a case like String.format("[%s] %s at %s", level, message, LocalDateTime.now())
        logMessageToFile(formattedMessage);
        //actually logs in the message to the log.txt file
        if (printToConsole)
            printMessageToConsole("INFO", formattedMessage);
        //if printToConsole is True, also put the same formattedMessage into console
    }

    public void warn(String message, Object... extra) {
        String formattedMessage = formatMessage("WARN", String.format(message, extra));
        logMessageToFile(formattedMessage);
        if (printToConsole) printMessageToConsole("WARN", formattedMessage);
    }

    public void error(String message, Object... extra) {
        String formattedMessage = formatMessage("ERROR", String.format(message, extra));
        logMessageToFile(formattedMessage);
        if (printToConsole) printMessageToConsole("ERROR", formattedMessage);
    }

    public void fatal(String message, Object... extra) {
        String formattedMessage = formatMessage("FATAL", String.format(message, extra));
        logMessageToFile(formattedMessage);
        if (printToConsole) printMessageToConsole("FATAL", formattedMessage);
    }
}
