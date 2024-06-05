import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

public class RingTable extends Thread {
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

    }

    private void initializationRingTable()  {
        for (int i = 0; i < philosophersCount; i++) {
            forks.add(new Fork());
        }
        for (int i = 0; i < philosophersCount; i++){
            if (i == 0 ) {
                philosophers.add(new Philosopher(forks.get(philosophersCount - 1), forks.get(0), countDownLatch, this));
            } else philosophers.add(new Philosopher(forks.get(i-1), forks.get(i), countDownLatch, this));
        }

    }

    @Override
    public void run() {
        try {
            startEating ();
            countDownLatch.await();
            System.out.println("All philosophers have finished eating");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public synchronized boolean tryGetForks(Fork leftFork, Fork rightFork) {
        if (leftFork.isAvailable() && rightFork.isAvailable()) {
            leftFork.setAvailable(false);
            rightFork.setAvailable(false);
            return true;
        }
        return false;
    }

    public synchronized void setForksFree(Fork leftFork, Fork rightFork){
        leftFork.setAvailable(true);
        rightFork.setAvailable(true);
    }



    public ArrayList<Philosopher> getPhilosophers() {
        return philosophers;
    }
}
