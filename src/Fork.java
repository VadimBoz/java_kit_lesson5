public class Fork {
    private final String name;
    private static int countForks = 0;
    private  volatile boolean available = true;

    public Fork() {
        this.name = "fork №" + ++countForks;;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    @Override
    public String toString() {
        return name;
    }
}
