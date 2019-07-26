package structures;

import data.Process;

public interface Queue {

    public boolean isEmpty();

    public boolean isFull();

    public boolean enqueue(Process target);

    public Process dequeue();
}
