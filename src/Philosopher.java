import java.util.concurrent.CountDownLatch;

public class Philosopher extends Thread {
    private final String name;
    private  final Fork forkLeft;
    private final Fork forkRight;
    private boolean philosopherWaiting = true;
    private static int countPhilosopher = 0;
    private int countEating  = 0;
    private final CountDownLatch countDownLatch;


    public Philosopher(Fork forkLeft, Fork forkRight, CountDownLatch countDownLatch) {
        this.forkLeft = forkLeft;
        this.forkRight = forkRight;
        countPhilosopher++;
        this.name = "Philosopher №" + countPhilosopher;
        this.countDownLatch  = countDownLatch;
    }


    @Override
    public void run() {
        System.out.println(getName() + " start");
        try {
            while (true) {
                if(countEating == 3) {
                    System.out.println(name  +  " is full");
                    thinking();
                    countDownLatch.countDown();
                    break;
                }
                if (isReadyToEat()) eating();
                else thinking();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void eating() throws InterruptedException {
        setBusyForks();
        System.out.println(name + " is eating");
        philosopherWaiting = false;
        countEating++;
        sleep(1000);
        philosopherWaiting = true;
        setFreeForks();
        if (countEating == 3) return;
        thinking();

    }

    public void thinking() throws InterruptedException {
        System.out.println(name + " is thinking");
        philosopherWaiting = false;
        setFreeForks();
        sleep(1000);
        philosopherWaiting = true;
    }


    private synchronized boolean isReadyToEat()  {
        return forkLeft.isAvailable()  && forkRight.isAvailable() && philosopherWaiting;
    }

    private synchronized void setFreeForks()  {
        forkLeft.setAvailable(true);
        forkRight.setAvailable(true);
    }

    private synchronized void setBusyForks()  {
       forkLeft.setAvailable(false);
       forkRight.setAvailable(false);
    }

    @Override
    public String toString() {
        return name  + ": forkLeft= " + forkLeft + ", forkRight= " + forkRight + "\n";
    }
}
