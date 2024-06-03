public class Fork {
    private String name;
    private static int countForks = 0;
    private  boolean available = true;

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
