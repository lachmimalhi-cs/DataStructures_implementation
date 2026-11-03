# Data Structures Implementation

## Project Overview

This project contains my own implementation of two data structures from scratch in Java, without using Java's ready-made collection classes:

- **Category A:** Circular Queue (`MyCircularQueue`)
- **Category B:** Hash Table with chaining (`MyHashTable`)

Each structure is used inside one algorithm. The algorithms are kept in their own class, so the structures stay clean and reusable:

- **`lastSurvivor`** (Josephus game) uses the circular queue. People stand in a circle and every k-th person leaves. The last person left is returned.
- **`removeDuplicates`** uses the hash table as a "seen" list. Repeated IDs are removed and the original order is kept.

## Project Structure

- **`MyCircularQueue.java`**: A reusable circular queue built on a fixed-size array. `enqueue`, `dequeue` and `peek` are O(1).
- **`MyHashTable.java`**: A reusable hash table. Collisions are handled by chaining (a linked list of nodes in each bucket). `put`, `get`, `contains` and `remove` are O(1) on average.
- **`Algorithms.java`**: The two algorithms, `lastSurvivor` and `removeDuplicates`, as static methods.
- **`Demo.java`**: Shows every operation with printed output (wrap-around, collisions, both algorithms).
- **`Tests.java`**: 63 automatic checks for normal cases and edge cases. Each check prints PASS or FAIL.
- **`Main.java`**: Runs the demo first, then the tests.

## How to Run

1. Open the project in IntelliJ IDEA.
2. Make sure Java JDK 8 (or higher) is installed.
3. Open `Main.java` and click the Run button (or press Shift + F10).
4. The console shows the demo output first, then the test results. The last line is `Passed: 63   Failed: 0`.

To run it from the terminal instead:

```bash
javac *.java
java Main
```

## Requirements Met

- Implemented two data structures: Circular Queue (Category A) and Hash Table (Category B).
- Implemented two algorithms that use them: `lastSurvivor` and `removeDuplicates`.
- Handled errors with exceptions: `IllegalArgumentException`, `IllegalStateException` and `NoSuchElementException` (tested in `Tests.java`).
- Analyzed time complexity:

  | Operation | Time |
  |-----------|------|
  | Queue `enqueue`, `dequeue`, `peek` | O(1) |
  | Hash table `put`, `get`, `remove` | O(1) average, O(n) worst case |
  | `lastSurvivor` | O(n · k) |
  | `removeDuplicates` | O(n) average |

- Kept each class in its own file, and the algorithms in a separate class.
- Tested normal operations and edge cases (63 checks: 27 queue, 23 hash table, 13 algorithms).
