package demos.set;

import dataStructures.set.*;

public class SetsPerformance {
    public enum Implementation { LinkedSet,SortedLinkedSet, SortedArraySet }

    public static double test(Implementation impl, int seed, int length) {
        long t0 = System.currentTimeMillis();
        Set<Integer> set = (impl == Implementation.LinkedSet) ? new LinkedSet<>():
                            (impl == Implementation.SortedLinkedSet)? new SortedLinkedSet<>()
                                    : new SortedArraySet<>();
        RandomSet.fill(set, seed, length);
        long t1 = System.currentTimeMillis();
        return (t1-t0) / 1e3; //execution time in seconds
    }

    static double avgTime(Implementation impl, int tests) {
        double t = 0.0;

        for(int s=0; s<tests; s++)
            t += test(impl, s, 50000);

        return t/tests;
    }

    public static void main(String[] args) {
        final int tests = 5;

        double t1 = avgTime(Implementation.LinkedSet, tests);
        System.out.printf("Average execution time for LinkedSet is %f seconds\n", t1);
        double t2 = avgTime(Implementation.SortedLinkedSet, tests);
        System.out.printf("Average execution time for SortedLinkedSet is %f seconds\n", t2);
        double t3 = avgTime(Implementation.SortedArraySet, tests);
        System.out.printf("Average execution time for SortedArraySet is %f seconds\n", t3);
    }
}
