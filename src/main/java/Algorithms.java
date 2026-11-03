import java.util.Arrays;

// This class holds the algorithms. They use our own data structures.
public class Algorithms {

    // Algorithm 1 (Josephus game): people stand in a circle and every k-th
    // person is removed until one is left. Uses MyCircularQueue.
    public static String lastSurvivor(String[] names, int k) {
        if (names == null || names.length == 0) {
            throw new IllegalArgumentException("Need at least one name");
        }
        if (k <= 0) {
            throw new IllegalArgumentException("k must be above 0");
        }
        MyCircularQueue circle = new MyCircularQueue(names.length);
        for (String name : names) {
            circle.enqueue(name);
        }
        while (circle.size() > 1) {
            for (int i = 1; i < k; i++) {
                circle.enqueue(circle.dequeue());   // skip: send to the back of the line
            }
            circle.dequeue();                       // the k-th person leaves the circle
        }
        return circle.dequeue();
    }

    // Algorithm 2: removes repeated IDs but keeps the original order.
    // Uses MyHashTable to remember which IDs were already seen.
    public static long[] removeDuplicates(long[] ids) {
        if (ids == null) {
            throw new IllegalArgumentException("Array must not be null");
        }
        MyHashTable seen = new MyHashTable(Math.max(1, ids.length));
        long[] result = new long[ids.length];
        int count = 0;
        for (long id : ids) {
            if (!seen.contains(id)) {
                seen.put(id, "seen");
                result[count] = id;
                count++;
            }
        }
        return Arrays.copyOf(result, count);
    }
}