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
        scheduler.checkForProcesses(0);
        while(scheduler.hasProcessesLeft() || !scheduler.isQueueEmpty() || hasProcess()) {
            if(!hasProcess() && !scheduler.isQueueEmpty()) { // Current process changed
                currentProcess = scheduler.getNextProcess();
                if(currentProcess.isFirstTime()) {
                    currentProcess.setResponseTime(currentTime);
                }
                currentProcess.updateWaitingTime(currentTime);
            }
            currentTime++;
            scheduler.checkForProcesses(currentTime);
            if(hasProcess()) {
                currentProcess.decrementBurstTime();
                if(currentProcess.getBurstTime() == 0) { // Current process has ended
                    currentProcess.setTurnaroundTime(currentTime);
                    scheduler.updateStats(currentProcess);
                    currentProcess = null;
                } else {
                    scheduler.updateStatus();
                }
            }
        }
        scheduler.printStats();
    }

    public Process preempt() {
        Process p = currentProcess;
        currentProcess = null;
        p.setLastExecutionTime(currentTime);
        return p;
    }

    public Process getCurrentProcess() {
        return currentProcess;
    }
}
