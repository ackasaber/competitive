package codeforces.beta05;

// E. Bindian Signalizing

import util.AsciiScanner;

/**
 * If the circle consists of <i>k</i> hills of equal height, then signals from a hill are visible from
 * any other hill. The answer then is the total number of distinct hill pairs
 * <i>k</i>(<i>k</i>&minus;1)/2.
 *
 * <p>If not all hills are of equal height, there must be a feature in the sequence of hills that
 * looks like a "valley":</p>
 * <pre>
 *     \________/
 * </pre>
 *  <p>That is, a group of hills of equal height are all lower than their neighbours from both sides.
 *  Clearly, signals from the hills in the "valley" are visible from any hill of the "valley". They
 *  are also  visible from the immediate "valley neighbours". They can't be visible from any other hill
 *  though since the neighbours are higher than any hill in the "valley".</p>
 *
 *  <p>This means that if <i>k</i> is the number of hills in the valley, the "valley" accounts for
 *  the following term in the answer:</p>
 *
 *  <p><i>k</i>(<i>k</i>+3)/2 if the "valley" has two distinct neighbours or<br>
 *  <i>k</i>(<i>k</i>+1)/2 if the "value" only has a single neighbour.</p>
 *
 *  <p>Once we account for this term in the answer we can exclude the "valley" from consideration and
 *  continue solving without it.</p>
 *
 *  <p>The algorithm implemented here solves the problem by systematically traversing the hill
 *  sequence in one direction and handling "valley"-like features along the way. The hill sequence
 *  is modelled by a circular list where neighbouring hills of equal height are compressed into a
 *  single node. If the current node does not form a "valley", we switch to the next node. Since
 *  the "valleys" are excluded, at any point of the traversal the sequence of traversed hills
 *  looks like this:</p>
 *  <pre>
 *       /\
 *      /  \
 *          \
 *  </pre>
 *  <p>Since the hills close in a circle, we will traverse the sequence no more than twice before
 *  collapsing it to no more than two nodes. Then we apply our considerations to them and total
 *  the answer. That gives a linear algorithm.</p>
 */
public class BindianSignalizing {
    public static void main(String[] args) {
        // Java built-in scanner doesn't handle such a large text input in a reasonable time.
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
