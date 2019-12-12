package fabflix.core;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class StatTimerLogger {
    private long startTime;
    private long endTime;
    private long elapsedTime;
    private String query;
    private FileWriter writer;
    private PrintWriter out;

    public StatTimerLogger(String fileName) {
        startTime = 0;
        endTime = 0;
        elapsedTime = 0;
        query = "";

        try {
            writer = new FileWriter(fileName, true);
            out = new PrintWriter(writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startTiming() {
        startTime = System.nanoTime();
    }

    public void stopTiming() {
        endTime = System.nanoTime();
        elapsedTime = endTime - startTime;
        printResultsToFile();
    }

    public void printResultsToFile() {
        out.println(elapsedTime);
        out.close();
    }

    public long getStartTime() {
        return startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public long getElapsedTime() {
        return elapsedTime;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }
}
