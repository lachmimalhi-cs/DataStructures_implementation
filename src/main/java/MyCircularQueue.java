public class MyCircularQueue {
    private String[] jobs;   // the slots (fixed capacity)
    private int front;       // slot of the oldest job (next to leave)
    private int count;       // how many jobs are waiting

    public MyCircularQueue(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be above 0");
        }
        jobs = new String[capacity];
        front = 0;
        count = 0;
    }

    public boolean isEmpty() {
        return count == 0;
    }

    public boolean isFull() {
        return count == jobs.length;
    }

    public int size() {
        return count;
    }

    // add a job at the back of the line
    public void enqueue(String job) {
        if (job == null) {
            throw new IllegalArgumentException("Job must not be null");
        }
        if (isFull()) {
            throw new IllegalStateException("Queue is full");
        }
        int rear = (front + count) % jobs.length;   // wraps around to slot 0
        jobs[rear] = job;
        count++;
    }

    // remove and return the job at the front of the line
    public String dequeue() {
        if (isEmpty()) {
            throw new IllegalStateException("Queue is empty");
        }
        String job = jobs[front];
        jobs[front] = null;
        front = (front + 1) % jobs.length;          // wraps around to slot 0
        count--;
        return job;
    }

    // look at the front job without removing it
    public String peek() {
        if (isEmpty()) {
            throw new IllegalStateException("Queue is empty");
        }
        return jobs[front];
    }

    public void clear() {
        for (int i = 0; i < jobs.length; i++) {
            jobs[i] = null;
        }
        front = 0;
        count = 0;
    }

    // prints the jobs in the order they will leave (front first)
    public void show() {
        if (isEmpty()) {
            System.out.println("[]");
            return;
        }
        StringBuilder text = new StringBuilder("[");
        for (int i = 0; i < count; i++) {
            text.append(jobs[(front + i) % jobs.length]);
            if (i < count - 1) {
                text.append(", ");
            }
        }
        System.out.println(text + "]");
    }

    // prints every raw slot, so the wrap-around can be seen
    public void showSlots() {
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < jobs.length; i++) {
            text.append(i).append(":").append(jobs[i] == null ? "-" : jobs[i]).append("  ");
        }
        System.out.println(text + "(front slot = " + front + ")");
    }
}