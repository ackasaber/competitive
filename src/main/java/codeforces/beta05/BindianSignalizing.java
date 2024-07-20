package codeforces.beta05;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

import static java.nio.charset.StandardCharsets.US_ASCII;

public class BindianSignalizing {
    public static void main(String[] args) {
        var reader = new InputStreamReader(System.in, US_ASCII);
        var scanner = new Scanner(new BufferedReader(reader));
        var circle = new HillCircle(scanner);
        System.out.println(circle.countVisiblePairs());
    }

    private static class HillCircle {
        private HillGroup chain;

        public HillCircle(int[] heights) {
            int n = heights.length;
            if (n < 3)
                throw new IllegalArgumentException("n < 3");

            int h1 = heights[0];
            chain = new HillGroup(h1);

            for (int i = 1; i < n; i++) {
                int h = heights[i];

                if (chain.height == h)
                    chain.count++;
                else
                    chain = new HillGroup(h, chain);
            }

            if (chain.next != chain && chain.height == chain.next.height) {
                HillGroup extra = chain;
                chain = chain.next;
                chain.merge(extra);
            }
        }

        public HillCircle(Scanner scanner) {
            int n = scanner.nextInt();

            if (n < 3)
                throw new IllegalArgumentException("n < 3");

            int h1 = scanner.nextInt();
            chain = new HillGroup(h1);

            for (int i = 1; i < n; i++) {
                int h = scanner.nextInt();

                if (chain.height == h)
                    chain.count++;
                else
                    chain = new HillGroup(h, chain);
            }

            if (chain.next != chain && chain.height == chain.next.height) {
                HillGroup extra = chain;
                chain = chain.next;
                chain.merge(extra);
            }
        }

        // Destroys the list in the process
        public long countVisiblePairs() {
            long count = 0;

            while (chain.next != chain) {
                HillGroup lowGround = chain.runDownhill();
                int k = lowGround.count;

                if (lowGround.next.next == lowGround && lowGround.next.count == 1)
                    count += (long) k * (k + 1) / 2;
                else
                    count += (long) k * (k + 3) / 2;

                chain = lowGround.previous;
                lowGround.remove();

                if (chain.next != chain && chain.height == chain.next.height)
                    chain.merge(chain.next);
            }

            int m = chain.count;
            count += (long) m * (m - 1) / 2;
            return count;
        }
    }

    private static class HillGroup {
        int height;
        int count = 1;
        HillGroup next;
        HillGroup previous;

        // Create a circle of one hill.
        public HillGroup(int height) {
            this.height = height;
            next = previous = this;
        }

        // Inserts a new single-hill group after the given group.
        public HillGroup(int height, HillGroup after) {
            this.height = height;
            next = after.next;
            previous = after;
            previous.next = next.previous = this;
        }

        public void merge(HillGroup group) {
            count += group.count;
            group.remove();
        }

        public HillGroup runDownhill() {
            HillGroup g = this;

            while (g.height > g.previous.height)
                g = g.previous;

            while (g.height > g.next.height)
                g = g.next;

            return g;
        }

        public void remove() {
            if (next == this)
                throw new IllegalStateException("can't remove last hill group");

            previous.next = next;
            next.previous = previous;
            next = previous = null;
        }
    }
}
