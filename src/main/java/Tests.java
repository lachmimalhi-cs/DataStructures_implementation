import java.util.Arrays;

// Tests for normal cases and edge cases. Prints PASS or FAIL for each one.
public class Tests {
    private static int passed = 0;
    private static int failed = 0;

    public static void run() {
        System.out.println("===== TESTS =====");
        testQueue();
        testHashTable();
        testAlgorithms();
        System.out.println();
        System.out.println("Passed: " + passed + "   Failed: " + failed);
    }

    // ----- helpers -----

    private static void check(String name, boolean ok) {
        if (ok) {
            passed++;
            System.out.println("PASS: " + name);
        } else {
            failed++;
            System.out.println("FAIL: " + name);
        }
    }

    // "() -> ..." just means: run this piece of code
    private static void checkError(String name, Class<?> expected, Runnable code) {
        try {
            code.run();
            failed++;
            System.out.println("FAIL: " + name + " (no error thrown)");
        } catch (Exception e) {
            check(name, expected.isInstance(e));
        }
    }

    // ----- MyCircularQueue -----

    private static void testQueue() {
        MyCircularQueue q = new MyCircularQueue(3);
        check("queue: new queue is empty", q.isEmpty() && q.size() == 0);

        q.enqueue("a");
        q.enqueue("b");
        check("queue: peek shows the front", q.peek().equals("a"));
        check("queue: peek does not remove", q.size() == 2);
        check("queue: first in, first out", q.dequeue().equals("a") && q.dequeue().equals("b"));
        check("queue: empty again after dequeue", q.isEmpty());

        q.enqueue("x");
        q.enqueue("y");
        q.enqueue("z");
        check("queue: isFull at capacity", q.isFull());
        checkError("queue: enqueue when full", IllegalStateException.class, () -> q.enqueue("w"));

        q.dequeue();
        q.enqueue("w");   // reuses the freed slot: wrap-around
        check("queue: wrap-around keeps the order",
                q.dequeue().equals("y") && q.dequeue().equals("z") && q.dequeue().equals("w"));

        MyCircularQueue lap = new MyCircularQueue(2);
        for (int i = 0; i < 10; i++) {        // goes around the circle many times
            lap.enqueue("job" + i);
            check("queue: many laps, job" + i, lap.dequeue().equals("job" + i));
        }

        q.enqueue("p");
        q.clear();
        check("queue: clear empties it", q.isEmpty() && q.size() == 0);
        q.enqueue("again");
        check("queue: reusable after clear", q.peek().equals("again"));

        MyCircularQueue empty = new MyCircularQueue(2);
        checkError("queue: dequeue when empty", IllegalStateException.class, () -> empty.dequeue());
        checkError("queue: peek when empty", IllegalStateException.class, () -> empty.peek());
        checkError("queue: enqueue null", IllegalArgumentException.class, () -> empty.enqueue(null));
        checkError("queue: capacity 0", IllegalArgumentException.class, () -> new MyCircularQueue(0));
        checkError("queue: negative capacity", IllegalArgumentException.class, () -> new MyCircularQueue(-3));

        MyCircularQueue one = new MyCircularQueue(1);
        one.enqueue("only");
        check("queue: capacity 1 is full after one", one.isFull());
        check("queue: capacity 1 dequeue works", one.dequeue().equals("only") && one.isEmpty());
    }

    // ----- MyHashTable -----

    private static void testHashTable() {
        MyHashTable t = new MyHashTable(7);
        check("table: new table is empty", t.isEmpty() && t.size() == 0);
        check("table: contains on empty is false", !t.contains(5));
        checkError("table: get on empty", java.util.NoSuchElementException.class, () -> t.get(5));
        checkError("table: remove on empty", java.util.NoSuchElementException.class, () -> t.remove(5));

        check("table: put new key returns true", t.put(10, "ten"));
        check("table: put same key returns false", !t.put(10, "TEN"));
        check("table: same key updates the value", t.get(10).equals("TEN"));
        check("table: update does not change size", t.size() == 1);

        // 1, 8 and 15 all land in bucket 1 when there are 7 buckets (collision)
        t.put(1, "one");
        t.put(8, "eight");
        t.put(15, "fifteen");
        check("table: collisions, all keys found",
                t.get(1).equals("one") && t.get(8).equals("eight") && t.get(15).equals("fifteen"));
        check("table: size counts collisions", t.size() == 4);

        check("table: remove from the middle of a chain", t.remove(8).equals("eight") && !t.contains(8));
        check("table: others in the chain survive", t.get(1).equals("one") && t.get(15).equals("fifteen"));
        check("table: remove the first in a chain", t.remove(15).equals("fifteen"));
        check("table: remove the last in a chain", t.remove(1).equals("one"));
        check("table: size after removes", t.size() == 1);
        checkError("table: get a removed key", java.util.NoSuchElementException.class, () -> t.get(8));

        t.put(-3, "negative");
        check("table: negative key works", t.get(-3).equals("negative"));
        t.put(0, "zero");
        check("table: zero key works", t.get(0).equals("zero"));

        t.clear();
        check("table: clear empties it", t.isEmpty() && t.size() == 0 && !t.contains(10));
        t.put(7, "seven");
        check("table: reusable after clear", t.get(7).equals("seven"));

        checkError("table: null value", IllegalArgumentException.class, () -> t.put(1, null));
        checkError("table: bucket count 0", IllegalArgumentException.class, () -> new MyHashTable(0));

        MyHashTable single = new MyHashTable(1);   // every key collides
        single.put(1, "a");
        single.put(2, "b");
        single.put(3, "c");
        check("table: one bucket still works",
                single.get(1).equals("a") && single.get(2).equals("b") && single.get(3).equals("c"));
    }

    // ----- Algorithms -----

    private static void testAlgorithms() {
        String[] seven = {"A", "B", "C", "D", "E", "F", "G"};
        check("lastSurvivor: 7 people, k=3 gives D", Algorithms.lastSurvivor(seven, 3).equals("D"));
        check("lastSurvivor: k=1 gives the last person", Algorithms.lastSurvivor(seven, 1).equals("G"));
        check("lastSurvivor: one person", Algorithms.lastSurvivor(new String[]{"Solo"}, 5).equals("Solo"));
        check("lastSurvivor: k larger than the group",
                Algorithms.lastSurvivor(new String[]{"A", "B", "C"}, 10).equals("B"));
        checkError("lastSurvivor: empty array", IllegalArgumentException.class,
                () -> Algorithms.lastSurvivor(new String[]{}, 2));
        checkError("lastSurvivor: null array", IllegalArgumentException.class,
                () -> Algorithms.lastSurvivor(null, 2));
        checkError("lastSurvivor: k = 0", IllegalArgumentException.class,
                () -> Algorithms.lastSurvivor(seven, 0));

        check("removeDuplicates: keeps first and the order",
                Arrays.equals(Algorithms.removeDuplicates(new long[]{5, 3, 5, 9, 3}),
                        new long[]{5, 3, 9}));
        check("removeDuplicates: empty array",
                Algorithms.removeDuplicates(new long[]{}).length == 0);
        check("removeDuplicates: no duplicates",
                Arrays.equals(Algorithms.removeDuplicates(new long[]{1, 2, 3}), new long[]{1, 2, 3}));
        check("removeDuplicates: all the same",
                Arrays.equals(Algorithms.removeDuplicates(new long[]{4, 4, 4}), new long[]{4}));
        check("removeDuplicates: negative numbers",
                Arrays.equals(Algorithms.removeDuplicates(new long[]{-1, -1, 2}), new long[]{-1, 2}));
        checkError("removeDuplicates: null array", IllegalArgumentException.class,
                () -> Algorithms.removeDuplicates(null));
    }
}