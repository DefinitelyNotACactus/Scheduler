package structures;

import data.Process;

public class CircularQueue implements Queue {

    private Process[] data;
    private int capacity;
    private int size;
    private int frontIndex;
    private int backIndex;

    public CircularQueue() {
        data = new Process[100];
        capacity = data.length;
        size = 0;
        frontIndex = 0;
        backIndex = 0;
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
            data[backIndex] = target;
            size++;
            backIndex = (backIndex + 1) % capacity;
            return true;
        }
        return false;
    }

    @Override
    public Process dequeue() {
        if(!isEmpty()) {
            Process front = data[frontIndex];
            data[frontIndex] = null;
            size--;
            frontIndex = (frontIndex + 1) % capacity;
            return front;
        }
        return null; // structures.Queue is empty
    }
}
