package data;

import structures.CircularQueue;
import structures.PriorityQueue;
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
    private int quantum;
    private int counter;

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
                processQueue = new CircularQueue();
                break;
            case 1: // SJF
                processQueue = new PriorityQueue();
                break;
            case 2: // RR
                quantum = 2;
                counter = 0;
                processQueue = new CircularQueue();
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
            System.out.println("Error while parsing file: ");
            ex.printStackTrace();
            System.exit(1);
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
        if(mode == 2) { // Reset quantum
            counter = 0;
        }
    }

    public void updateStatus() {
        if(mode != 0) {
            switch (mode) {
                case 2:
                    counter++;
                    if (counter == quantum) {
                        Process p = cpu.preempt();
                        processQueue.enqueue(p);
                        counter = 0;
                    }
                break;
            }
        }
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
        new Scheduler(0); //FCFS
        new Scheduler(1); //SJF
        new Scheduler(2); //RR
    }

}
