import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {

        RingTable ringTable = new RingTable(5);

        try {
            ringTable.startEating();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


    }

}