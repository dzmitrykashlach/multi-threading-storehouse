package unsorted;

public class StructuredConcurrencyTest {


    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            System.out.println("t1 is started");
            Thread t2 = new Thread(() -> {
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("t2 is finished");
            });
            System.out.println("t2 is created");
            t2.start();
            System.out.println("t1 has finished");
        });
        t1.start();
    }
}
