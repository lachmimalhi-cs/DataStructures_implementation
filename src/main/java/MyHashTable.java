import java.util.NoSuchElementException;

public class MyHashTable {

    // one entry. "next" links entries that land in the same bucket.
    private static class Node {
        long key;
        String value;
        Node next;

        Node(long key, String value) {
            this.key = key;
            this.value = value;
        }
    }

    private Node[] buckets;   // each bucket holds a small chain of nodes
    private int total;        // how many entries are stored

    public MyHashTable(int bucketCount) {
        if (bucketCount <= 0) {
            throw new IllegalArgumentException("Bucket count must be above 0");
        }
        buckets = new Node[bucketCount];
        total = 0;
    }

    public boolean isEmpty() {
        return total == 0;
    }

    public int size() {
        return total;
    }

    // hash function: turns a key into a bucket number (0 .. length-1)
    private int bucketOf(long key) {
        long n = buckets.length;
        return (int) (((key % n) + n) % n);   // the extra "+ n" handles negative keys
    }

    // returns true if the key is new, false if it replaced an old value
    public boolean put(long key, String value) {
        if (value == null) {
            throw new IllegalArgumentException("Value must not be null");
        }
        int b = bucketOf(key);
        Node current = buckets[b];
        while (current != null) {
            if (current.key == key) {
                current.value = value;      // key already there: update it
                return false;
            }
            current = current.next;
        }
        Node newNode = new Node(key, value);
        newNode.next = buckets[b];          // new node goes to the front of the chain
        buckets[b] = newNode;
        total++;
        return true;
    }

    public String get(long key) {
        Node current = buckets[bucketOf(key)];
        while (current != null) {
            if (current.key == key) {
                return current.value;
            }
            current = current.next;
        }
        throw new NoSuchElementException("No entry for key " + key);
    }

    public boolean contains(long key) {
        Node current = buckets[bucketOf(key)];
        while (current != null) {
            if (current.key == key) {
                return true;
            }
            current = current.next;
        }
        return false;
    }

    // removes the entry and returns its value
    public String remove(long key) {
        int b = bucketOf(key);
        Node current = buckets[b];
        Node previous = null;
        while (current != null) {
            if (current.key == key) {
                if (previous == null) {
                    buckets[b] = current.next;      // it was the first in the chain
                } else {
                    previous.next = current.next;   // skip over it
                }
                total--;
                return current.value;
            }
            previous = current;
            current = current.next;
        }
        throw new NoSuchElementException("No entry for key " + key);
    }

    public void clear() {
        for (int i = 0; i < buckets.length; i++) {
            buckets[i] = null;
        }
        total = 0;
    }

    // prints every bucket and its chain, so collisions can be seen
    public void show() {
        for (int i = 0; i < buckets.length; i++) {
            StringBuilder line = new StringBuilder("bucket " + i + ":");
            Node current = buckets[i];
            while (current != null) {
                line.append(" ").append(current.key).append("=").append(current.value);
                if (current.next != null) {
                    line.append(" ->");
                }
                current = current.next;
            }
            System.out.println(line);
        }
    }
}