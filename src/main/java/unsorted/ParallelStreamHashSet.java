package unsorted;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;
/*
https://bugs.openjdk.org/browse/JDK-8190974
https://stackoverflow.com/questions/28985704/parallel-stream-from-a-hashset-doesnt-run-in-parallel/29272776#29272776
 1)

 */

public class ParallelStreamHashSet {
    public static void main(String[] args) {
        ParallelTest test = new ParallelTest();
        List<Integer> list = Arrays.asList(1,2,3,4,5,6);
        Set<Integer> set = new HashSet<>(list);

        ForkJoinPool forkJoinPool = new ForkJoinPool(2);

        System.out.println("set print");
        try {
            forkJoinPool.submit(() ->
                    set.parallelStream().forEach(test::print)
            ).get();
        } catch (Exception e) {
            return;
        }
        set.parallelStream().forEach(test::print);

        System.out.println("\n\nlist print");
        try {
            forkJoinPool.submit(() ->
                    list.parallelStream().forEach(test::print)
            ).get();

        } catch (Exception e) {
            return;
        }
        list.parallelStream().forEach(test::print);

    }

    static class ParallelTest{
        public void print(int i){
            System.out.println("start: " + i+" "+Thread.currentThread().getName());
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
            }
            System.out.println("end: " + i+" "+Thread.currentThread().getName());
        }
    }
}
