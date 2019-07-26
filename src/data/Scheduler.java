package data;

import structures.FcfsQueue;
import structures.Queue;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Scheduler {
    private int mode;
    private Queue processQueue;
    private List<Process> processList;
    private final Cpu cpu;

    private int totalResponseTime;
    private int totalTurnaroundTime;
    private int totalWaitTime;
    private int numberOfProcesses;

    public Scheduler(int mode) {
        this.mode = mode;
        switch (mode) {
            case 0: // FCFS
                processQueue = new FcfsQueue();
                break;
            case 1: // SJF
                //TODO
                break;
            case 2: // RR
                //TODO
                break;
        }

        processList = new ArrayList<>();
        totalResponseTime = 0;
        totalTurnaroundTime = 0;
        totalWaitTime = 0;
        numberOfProcesses = 0;

        parseInputFile();
        cpu = new Cpu(this);
        cpu.cpuExecute();
    }

    // Parses the input file
    public void parseInputFile() {
        File input = new File("./test.txt");
        try {
            BufferedReader in = new BufferedReader(new FileReader(input));
            String line;
            String[] split;
            while((line = in.readLine()) != null) {
                split = line.split(" ");
                processList.add(new Process(Integer.parseInt(split[0]), Integer.parseInt(split[1])));
                numberOfProcesses++;
            }
        } catch(IOException ex) {
            System.out.println("Error while parsing file");
        }
    }

    public void checkForProcesses(int time) {
        Iterator<Process> it = processList.iterator();
        Process p;
        while(it.hasNext()) {
            p = it.next();
            if(p.getArrivalTime() == time) {
                processQueue.enqueue(p);
                it.remove();
            } else { // Assuming the list is sorted
                break;
            }
        }
    }

    public boolean hasProcessesLeft() {
        return !processList.isEmpty();
    }

    public boolean isQueueEmpty() {
        return processQueue.isEmpty();
    }

    public Process getNextProcess() {
        return processQueue.dequeue();
    }

    public void updateStats(Process source) {
        totalResponseTime += source.getResponseTime();
        totalTurnaroundTime += source.getTurnaroundTime();
        totalWaitTime += source.getWaitingTime();
    }

    public String toString() {
        switch(mode) {
            case 0:
                return "FCFS";
            case 1:
                return "SJF";
            case 2:
                return "RR";

                default:
                return null;
        }
    }

    public void printStats() {
        double mTurn = (double) totalTurnaroundTime/numberOfProcesses,
                mResp = (double) totalResponseTime/numberOfProcesses,
                mWait = (double) totalWaitTime/numberOfProcesses;
        System.out.println(String.format("%s %.1f %.1f %.1f", toString(), mTurn, mResp, mWait));
    }

    public static void main(String[] args) {
        new Scheduler(0);
    }

}
