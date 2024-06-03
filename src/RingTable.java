import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

public class RingTable {
    private final int philosophersCount;
    private final ArrayList<Philosopher> philosophers;
    private final ArrayList<Fork> forks;
    private final CountDownLatch countDownLatch; //latch for the countdown


    public RingTable(int philosophersCount) {
        this.philosophersCount = philosophersCount;
        countDownLatch = new CountDownLatch(philosophersCount);
        philosophers = new ArrayList<>();
        forks = new ArrayList<>();
        initializationRingTable();

    }

    public void startEating () throws InterruptedException {
        for (Philosopher philosopher  :  philosophers)  {
             philosopher.start();
        }
        countDownLatch.await();
        System.out.println("All philosophers have finished eating");
    }

    private void initializationRingTable()  {
        for (int i = 0; i < philosophersCount; i++) {
            forks.add(new Fork());
        }
        for (int i = 0; i < philosophersCount; i++){
            if (i == 0 ) {
                philosophers.add(new Philosopher(forks.get(philosophersCount - 1), forks.get(i), countDownLatch));
            } else if (i == philosophersCount - 1)  {
                philosophers.add(new Philosopher(forks.get(i), forks.get(0), countDownLatch));
            } else philosophers.add(new Philosopher(forks.get(i), forks.get(i + 1), countDownLatch));
        }

    }

    public ArrayList<Philosopher> getPhilosophers() {
        return philosophers;
    }
}
