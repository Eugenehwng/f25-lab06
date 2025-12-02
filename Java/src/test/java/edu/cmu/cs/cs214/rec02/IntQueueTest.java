package edu.cmu.cs.cs214.rec02;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;


/**
 * TODO: 
 * 1. The {@link LinkedIntQueue} has no bugs. We've provided you with some example test cases.
 * Write your own unit tests to test against IntQueue interface with specification testing method 
 * using mQueue = new LinkedIntQueue();
 * 
 * 2. 
 * Comment `mQueue = new LinkedIntQueue();` and uncomment `mQueue = new ArrayIntQueue();`
 * Use your test cases from part 1 to test ArrayIntQueue and find bugs in the {@link ArrayIntQueue} class
 * Write more unit tests to test the implementation of ArrayIntQueue, with structural testing method
 * Aim to achieve 100% line coverage for ArrayIntQueue
 *
 * @author Alex Lockwood, George Guo, Terry Li
 */
public class IntQueueTest {

    private IntQueue mQueue;
    private List<Integer> testList;

    /**
     * Called before each test.
     */
    @Before
    public void setUp() {
        // comment/uncomment these lines to test each class
        // mQueue = new LinkedIntQueue();
        mQueue = new ArrayIntQueue();

        testList = new ArrayList<>(List.of(1, 2, 3));
    }

    @Test
    public void testIsEmpty() {
        // This is an example unit test
        assertTrue(mQueue.isEmpty());
    }

    @Test
    public void testNotEmpty() {
        // TODO: write your own unit test
        mQueue.enqueue(5);
        assertTrue(!mQueue.isEmpty());
    }

    @Test
    public void testPeekEmptyQueue() {
        // TODO: write your own unit test
        assertEquals(mQueue.peek(), null);
    }

    @Test
    public void testPeekNoEmptyQueue() {
        // TODO: write your own unit test
        mQueue.enqueue(5);
        assertEquals(Integer.valueOf(5), mQueue.peek());
    }

    @Test
    public void testEnqueue() {
        // This is an example unit test
        for (int i = 0; i < testList.size(); i++) {
            mQueue.enqueue(testList.get(i));
            assertEquals(testList.get(0), mQueue.peek());
            assertEquals(i + 1, mQueue.size());
        }
    }

    @Test
    public void testDequeue() {
        // TODO: write your own unit test
        for (int i = 0; i < testList.size(); i++) {
            mQueue.enqueue(testList.get(i));
        }
        for (int i = 0; i < testList.size(); i++) {
            Integer value = mQueue.dequeue();
            assertEquals(testList.get(i), value);
            assertEquals(mQueue.size(), testList.size() - i - 1);
        }
    }

    @Test
    public void testContent() throws IOException {
        // This is an example unit test
        InputStream in = new FileInputStream("src/test/resources/data.txt");
        try (Scanner scanner = new Scanner(in)) {
            scanner.useDelimiter("\\s*fish\\s*");

            List<Integer> correctResult = new ArrayList<>();
            while (scanner.hasNextInt()) {
                int input = scanner.nextInt();
                correctResult.add(input);
                System.out.println("enqueue: " + input);
                mQueue.enqueue(input);
            }

            for (Integer result : correctResult) {
                assertEquals(mQueue.dequeue(), result);
            }
        }
    }

    @Test
    public void testClear() {
        mQueue.clear();
        assertEquals(mQueue.size(), 0);
    }


    @Test
    public void testDequeueEmpty() {
        assertEquals(mQueue.dequeue(), null);
    }

    @Test
    public void testEnsureCapacityWithWrapping() {
        // Step 1: Fill array to capacity (head = 0, size = 10)
        for (int i = 0; i < 10; i++) {
            mQueue.enqueue(i);
        }
        
        // Step 2: Dequeue some elements to move head forward (head > 0)
        mQueue.dequeue(); // head = 1, size = 9
        mQueue.dequeue(); // head = 2, size = 8
        
        // Step 3: Fill back to capacity (head = 2, size = 10)
        // Now the array has wrapped: elements at indices 2-9 and 0-1
        for (int i = 10; i < 12; i++) {
            mQueue.enqueue(i); // head = 2, size = 10
        }
        
        // Step 4: Enqueue one more to trigger resize with head = 2
        mQueue.enqueue(12); // This triggers ensureCapacity() with head = 2
        
        // Verify all elements are preserved in correct order
        assertEquals(Integer.valueOf(2), mQueue.dequeue());
        assertEquals(Integer.valueOf(11), mQueue.dequeue());
        // ... continue checking
    }
}
