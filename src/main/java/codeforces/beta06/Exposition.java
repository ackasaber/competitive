package codeforces.beta06;

// E. Exposition

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * The task solution using two monotonic queues.
 * 
 * <p>We will compute the table of maximum time periods that satisfy the task condition and end
 * on a given book. The final answer is then trivially reconstructed from such a table.</p>
 * 
 * <p>In order to compute the table, we'll have a sliding window over time periods that increases
 * to the right when a new book gets published and and shrinks from the left when difference
 * in book height violates the task limitation.</p>
 * 
 * <p>The book height difference in the sliding window is computed with the help of two data
 * structures. There are actually many possible choices, but monotonic queues give the optimal
 * asymptotic performance. We'll have one monotonic queue to track the minimum book height
 * in the sliding window and the other to track the corresponding maximum book height.</p>
 * 
 * <p>See also {@link leetcode.queue.SlidingWindowMax} task.</p>
 */
public class Exposition {
    public static void main(String[] args) {
        var reader = new InputStreamReader(System.in);
        var scanner = new Scanner(new BufferedReader(reader));
        var exp = new Exposition(scanner);
        var sol = exp.findMaxPeriod();
        var writer = new OutputStreamWriter(System.out);
        var printer = new PrintWriter(new BufferedWriter(writer));
        exp.reportSolution(printer, sol);
        printer.flush();
    }
    
    private int maxSpread;
    private int[] heights;
    
    public Exposition(Scanner scanner) {
        int n = scanner.nextInt();
        
        if (n < 1)
            throw new IllegalArgumentException("n < 1");
        
        maxSpread = scanner.nextInt();
        
        if (maxSpread < 0)
            throw new IllegalArgumentException("k < 0");

        heights = new int[n];
        
        for (int i = 0; i < n; i++) {
            heights[i] = scanner.nextInt();
        }
    }
    
    public int[] findMaxPeriod() {
        int n = heights.length;
        var p = new int[n];
        var minq = new MonotonicQueue(n);
        var maxq = new MonotonicQueue(n);
        int window = 0;
        
        for (int i = 0; i < n; i++) {
            minq.addMin(heights[i]);
            maxq.addMax(heights[i]);
            window++;
            
            while (maxq.stat() - minq.stat() > maxSpread) {
                window--;
                minq.remove(heights[i - window]);
                maxq.remove(heights[i - window]);
            }
            
            p[i] = window;
        }
        
        return p;
    }
    
    public void reportSolution(PrintWriter writer, int[] maxSuffix) {
        int maxPeriod = 0;
        int periodCount = 0;
        int n = maxSuffix.length;
        
        for (int i = 0; i < n; i++) {
            if (maxSuffix[i] > maxPeriod) {
                maxPeriod = maxSuffix[i];
                periodCount = 1;
            } else if (maxSuffix[i] == maxPeriod) {
                periodCount++;
            }
        }
        
        writer.print(maxPeriod);
        writer.print(" ");
        writer.println(periodCount);
        
        for (int i = 0; i < n; i++) {
            if (maxSuffix[i] == maxPeriod) {
                writer.print(i + 2 - maxPeriod);
                writer.print(" ");
                writer.println(i + 1);
            }
        }
    }
    
    /**
     * A joint implementation for non-decreasing and non-increasing monotonic queues.
     * 
     * <p>Use {@code addMin} for a non-decreasing and {@code addMax} for a
     * non-increasing queue.</p>
     */
    
    static private class MonotonicQueue {
        private final int[] queue;
        private int head; // points at the head
        private int tail; // points one past the tail
        
        public MonotonicQueue(int capacity) {
            queue = new int[capacity];
            head = 0;
            tail = 0;
        }
        
        public void addMin(int x) {
            while (tail > head && queue[tail - 1] > x)
                tail--;
            queue[tail] = x;
            tail++;
        }
        
        public void addMax(int x) {
            while (tail > head && queue[tail - 1] < x)
                tail--;
            queue[tail] = x;
            tail++;
        }
        
        /**
         * Removes the head element from the monotonic queue.
         * 
         * <p>Note that it might be that this element had already been removed when a later element
         * was added. We don't store such elements here and therefore take the removed element value
         * as the method argument.</p>
         * 
         * @param x the head element value
         */
        public void remove(int x) {
            if (x == queue[head])
                head++;
        }
        
        /**
         * Target value for the queue.
         * 
         * @return minimum for the non-decreasing and maximum for non-increasing monotoric queue
         */
        public int stat() {
            return queue[head];
        }
    }
}
