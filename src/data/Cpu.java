package data;

public class Cpu {
    private Process currentProcess;
    private Scheduler scheduler;
    private int currentTime;

    public Cpu(Scheduler scheduler) {
        this.scheduler = scheduler;
        currentProcess = null;
        currentTime = 0;
    }

    public boolean hasProcess() {
        return (currentProcess != null);
    }

    public int getCurrentTime() {
        return currentTime;
    }

    public void cpuExecute() {
        while(scheduler.hasProcessesLeft() || !scheduler.isQueueEmpty() || hasProcess()) {
            scheduler.checkForProcesses(currentTime);
            if(!hasProcess() && !scheduler.isQueueEmpty()) { // Current process changed
                currentProcess = scheduler.getNextProcess();
                if(currentProcess.isFirstTime()) {
                    currentProcess.setResponseTime(currentTime);
                }
                currentProcess.updateWaitingTime(currentTime);
            }
            currentTime++;
            if(hasProcess()) {
                currentProcess.decrementBurstTime();
                if(currentProcess.getBurstTime() == 0) {
                    currentProcess.setTurnaroundTime(currentTime);
                    scheduler.updateStats(currentProcess);
                    currentProcess = null;
                }
            }
        }
        scheduler.printStats();
    }
}
