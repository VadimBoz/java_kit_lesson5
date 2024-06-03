import java.util.concurrent.CountDownLatch;

public class Philosopher extends Thread {
    private final String name;
    String ring;
    private  volatile Fork forkLeft;
    private volatile Fork forkRight;
    boolean philosopherWaiting = true;
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
        try {
            while (true) {
                if(countEating == 3) {
                    thinking();
                    System.out.println(name  +  " is full");
                    countDownLatch.countDown();
                    break;
                }
                if (readyToEat()) eating();
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
        Thread.sleep(2000);
        philosopherWaiting = true;
        setFreeForks();
    }

    public void thinking() throws InterruptedException {
        System.out.println(name + " is thinking");
        philosopherWaiting = false;
        setFreeForks();
        Thread.sleep(2000);
        philosopherWaiting = true;
    }


    private boolean readyToEat()  {
        return forkLeft.isAvailable()  && forkRight.isAvailable() && philosopherWaiting;
    }

    private void setFreeForks()  {
        forkLeft.setAvailable(true);
        forkRight.setAvailable(true);
    }

    private void setBusyForks()  {
       forkLeft.setAvailable(false);
       forkRight.setAvailable(false);
    }

    @Override
    public String toString() {
        return name  + ": forkLeft= " + forkLeft + ", forkRight= " + forkRight + "\n";
    }
}
