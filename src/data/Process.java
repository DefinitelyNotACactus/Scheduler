package data;

public class Process {

    private final int arrivalTime;
    private int burstTime;
    private int turnaroundTime;
    private int responseTime;
    private int waitingTime;

    private boolean firstTime;
    private int lastExecutionTime;

    public Process(int arrivalTime, int burstTime) {
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;

        turnaroundTime = 0;
        responseTime = 0;
        waitingTime = 0;

        firstTime = true;
        lastExecutionTime = arrivalTime;
    }

    public boolean isFirstTime() {
        return firstTime;
    }

    public void setResponseTime(int firstExecutionTime) {
        firstTime = false;
        responseTime = firstExecutionTime - arrivalTime;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getBurstTime() {
        return burstTime;
    }

    public void decrementBurstTime() {
        burstTime--;
    }

    public int getTurnaroundTime() {
        return turnaroundTime;
    }

    public void setTurnaroundTime(int endTime) {
        turnaroundTime = endTime - arrivalTime;
    }

    public int getResponseTime() {
        return responseTime;
    }

    public int getWaitingTime() {
        return waitingTime;
    }

    public void updateWaitingTime(int timeUpdated) {
        if(timeUpdated >= lastExecutionTime) {
            waitingTime += timeUpdated - lastExecutionTime;
        }
    }
    
    public int getLastExecutionTime() {
        return lastExecutionTime;
    }

    public void setLastExecutionTime(int lastExecutionTime) {
        this.lastExecutionTime = lastExecutionTime;
    }
}
