package codeforces.beta05;

import util.AsciiScanner;

public class BindianSignalizing {
    public static void main(String[] args) {
        var scanner = new AsciiScanner(System.in, 4 * 1024);
        var circle = new HillCircle(scanner);
        System.out.println(circle.countVisiblePairs());
    }

    private static class HillCircle {
        private HillGroup chain;

        public HillCircle(AsciiScanner scanner) {
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

            while (chain.next != chain.previous) {
                if (chain.previous.height > chain.height &&
                    chain.next.height > chain.height) {
                    var c = chain;
                    chain = c.previous;
                    count += (long) c.count * (c.count + 3) / 2;
                    c.remove();

                    if (chain.next != chain && chain.height == chain.next.height)
                        chain.merge(chain.next);
                } else {
                    chain = chain.next;
                }
            }

            if (chain.height > chain.next.height)
                chain = chain.next;

            if (chain.height < chain.next.height) {
                if (chain.next.count == 1)
                    count += (long) chain.count * (chain.count + 1) / 2;
                else
                    count += (long) chain.count * (chain.count + 3) / 2;
                chain = chain.next;
            }
            count += (long) chain.count * (chain.count - 1) / 2;
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

        public void remove() {
            if (next == this)
                throw new IllegalStateException("can't remove last hill group");

            previous.next = next;
            next.previous = previous;
            next = previous = null;
        }
    }
}
