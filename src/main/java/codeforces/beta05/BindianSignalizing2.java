package codeforces.beta05;

import util.AsciiScanner;

import java.util.ArrayDeque;

public class BindianSignalizing2 {
    public static void main(String[] args) {
        var scanner = new AsciiScanner(System.in, 4 * 1024);
        var heights = readHeights(scanner);
        System.out.println(countVisiblePairs(heights));
    }

    private static long countVisiblePairs(int[] heights) {
        int n = heights.length;
        assert n != 0;
        var totaller = new Totaller();
        var stack = new ArrayDeque<HillGroup>();
        var queue = new ArrayDeque<HillGroup>();
        var top = new HillGroup(heights[0]);

        for (int i = 1; i < n; i++) {
            while (heights[i] > top.height && !stack.isEmpty()) {
                totaller.update(top.count);
                top = stack.pop();
            }

            if (heights[i] == top.height)
                top.count++;
            else {
                if (heights[i] < top.height)
                    stack.push(top);
                else
                    queue.offer(top);
                top = new HillGroup(heights[i]);
            }
        }

        while (!queue.isEmpty()) {
            var bottom = queue.poll();
            while (bottom.height > top.height) {
                totaller.update(top.count);
                top = stack.pop();
            }
            if (bottom.height == top.height)
                top.count += bottom.count;
            else // bottom.height < top.height
                totaller.update(bottom.count);
        }

        while (!stack.isEmpty()) {
            totaller.update(top.count);
            top = stack.pop();
        }

        totaller.finalUpdate(top.count);
        return totaller.total();
    }

    private static class HillGroup {
        int height;
        int count;

        public HillGroup(int height) {
            this.height = height;
            this.count = 1;
        }
    }

    private static class Totaller {
        private long total = 0;
        private int m = 0;

        public void update(int k) {
            total += (long) m*(m+3)/2;
            m = k;
        }

        public void finalUpdate(int k) {
            if (k == 1)
                total += (long) m*(m+1)/2;
            else
                total += (long) m*(m+3)/2;
            total += (long) k*(k-1)/2;
        }

        public long total() {
            return total;
        }
    }

    private static int[] readHeights(AsciiScanner scanner) {
        int n = scanner.nextInt();
        if (n < 3)
            throw new IllegalArgumentException("n < 3");

        var heights = new int[n];
        for (int i = 0; i < n; i++)
            heights[i] = scanner.nextInt();
        return heights;
    }
}
