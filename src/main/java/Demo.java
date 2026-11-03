// Shows every operation of both data structures and both algorithms.
public class Demo {

    public static void run() {
        demoQueue();
        System.out.println();
        demoHashTable();
        System.out.println();
        demoAlgorithms();
    }

    private static void demoQueue() {
        System.out.println("===== DEMO 1: MyCircularQueue (print queue) =====");
        MyCircularQueue queue = new MyCircularQueue(4);
        System.out.println("isEmpty at start: " + queue.isEmpty());          // O(1)

        queue.enqueue("report.pdf");                                          // O(1)
        queue.enqueue("photo.png");
        queue.enqueue("notes.txt");
        queue.enqueue("slides.ppt");
        System.out.println("isFull: " + queue.isFull());                      // O(1)
        System.out.print("show: ");
        queue.show();                                                         // O(n)
        queue.showSlots();

        System.out.println("peek: " + queue.peek());                          // O(1)
        System.out.println("dequeue: " + queue.dequeue());                    // O(1)
        System.out.println("dequeue: " + queue.dequeue());
        queue.enqueue("memo.doc");   // these two reuse the freed slots 0 and 1
        queue.enqueue("map.jpg");
        System.out.println("After 2 dequeues and 2 enqueues (wrap-around):");
        queue.showSlots();
        System.out.print("show: ");
        queue.show();
        System.out.println("size: " + queue.size());                          // O(1)

        try {
            queue.enqueue("one too many");
        } catch (IllegalStateException e) {
            System.out.println("Error caught: " + e.getMessage());
        }

        queue.clear();                                                        // O(n)
        System.out.println("size after clear: " + queue.size());
        try {
            queue.dequeue();
        } catch (IllegalStateException e) {
            System.out.println("Error caught: " + e.getMessage());
        }
    }

    private static void demoHashTable() {
        System.out.println("===== DEMO 2: MyHashTable (student ID -> name) =====");
        MyHashTable table = new MyHashTable(5);
        System.out.println("isEmpty at start: " + table.isEmpty());

        table.put(4420250980L, "Bai Lachmi");                                 // O(1) average
        table.put(4420250981L, "Sara");
        table.put(4420250982L, "Ali");
        table.put(4420250985L, "Noor");   // same bucket as ...980 -> a collision
        table.put(4420250990L, "Zain");
        System.out.println("size: " + table.size());                          // O(1)
        table.show();                                                         // O(buckets + n)

        System.out.println("get 4420250982: " + table.get(4420250982L));       // O(1) average
        System.out.println("contains 4420250981: " + table.contains(4420250981L));
        System.out.println("contains 1234: " + table.contains(1234L));
        System.out.println("put 4420250981 again (new name): " + table.put(4420250981L, "Sara K."));
        System.out.println("get 4420250981: " + table.get(4420250981L));
        System.out.println("remove 4420250985: " + table.remove(4420250985L)); // O(1) average
        System.out.println("size after remove: " + table.size());

        try {
            table.get(999L);
        } catch (java.util.NoSuchElementException e) {
            System.out.println("Error caught: " + e.getMessage());
        }

        table.clear();                                                        // O(buckets)
        System.out.println("size after clear: " + table.size());
    }

    private static void demoAlgorithms() {
        System.out.println("===== DEMO 3: Algorithms =====");
        String[] people = {"Ali", "Sara", "Noor", "Zain", "Bai", "Omar", "Hina"};
        System.out.println("lastSurvivor(7 people, every 3rd leaves) = "
                + Algorithms.lastSurvivor(people, 3));

        long[] messy = {50, 20, 50, 10, 20, 80};
        long[] clean = Algorithms.removeDuplicates(messy);
        System.out.print("removeDuplicates keeps the order: ");
        for (long id : clean) {
            System.out.print(id + " ");
        }
        System.out.println();
    }
}