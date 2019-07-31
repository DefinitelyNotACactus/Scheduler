package structures;

import data.Process;

public class PriorityQueue implements Queue {

    private Process[] data;
    private int capacity;
    private int size;

    public PriorityQueue() {
        data = new Process[100];
        capacity = data.length;
        size = 0;
    }

    public boolean isFull() {
        return (size == capacity);
    }

    public boolean isEmpty() {
        return (size == 0);
    }

    @Override
    public boolean enqueue(Process target) {
        if(!isFull()) {
            int index = findPosition(target.getBurstTime());
            for(int i = size; i > index; i--) {
                data[i] = data[i-1];
            }
            data[index] = target;
            size++;
            return true;
        }
        return false;
    }

    @Override
    public Process dequeue() {
        if(!isEmpty()) {
            Process front = data[0];
            data[0] = null;
            for(int i = 0; i < size-1; i++) {
                data[i] = data[i+1];
            }
            size--;
            return front;
        }
        return null; // structures.Queue is empty
    }

    private int findPosition(int burstTime) {
        for(int i = 0; i < size; i++) {
            if(burstTime < data[i].getBurstTime()) {
                return i;
            }
        }
        return size;
    }
}
