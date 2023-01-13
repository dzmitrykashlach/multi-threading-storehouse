package watercompiler;

import lombok.extern.slf4j.Slf4j;

import java.util.Random;
import java.util.concurrent.Semaphore;

// https://leetcode.com/problems/building-h2o/
@Slf4j
public class H2O {
    public String OXYGEN = "OXYGEN";
    public String HYDROGEN = "HYDROGEN";
    public String LOG_ENTRY_ENTERED_REACTOR = " entered reactor";
    public static Semaphore semaphore = new Semaphore(4);

    public H2O() {

    }

    public void buildMolecule() {
        long start = System.currentTimeMillis();
        // gas producer will work for 15 sec
        int hydrogenCount = 0;
//        while (System.currentTimeMillis() < start + 300000) {
        while (System.currentTimeMillis() < start + 100) {

            if (semaphore.availablePermits() > 0) {
                String gas = produceAtom();
                switch (gas) {
                    case "HYDROGEN" -> {
                        if (hydrogenCount < 2 && semaphore.tryAcquire()) {
                            hydrogenCount++;
                            new Thread(() -> System.out.println(gas + LOG_ENTRY_ENTERED_REACTOR)).start();
                        }
                        break;
                    }
                    case "OXYGEN" -> {
                        if (hydrogenCount == 2 && semaphore.tryAcquire()) {
                            new Thread(() -> System.out.println(gas + LOG_ENTRY_ENTERED_REACTOR)).start();
                        }
                        break;
                    }
                    default -> throw new IllegalStateException("Unexpected value: " + gas);
                }
            }
            if (semaphore.availablePermits() == 1 && semaphore.tryAcquire()) {
                semaphore.release(4);
                hydrogenCount = 0;
            }
        }
    }

    public String produceAtom() {
        Random random = new Random();
        return random.nextBoolean() ? HYDROGEN : OXYGEN;
    }

    public static void main(String[] args) {
        H2O h2O = new H2O();
        h2O.buildMolecule();
    }
}
