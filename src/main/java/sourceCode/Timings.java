package sourceCode;

public class Timings {
    private int sourceAccountNumber;
    private int destinationAccountNumber;
    private long time;

    public Timings(int sourceAccountNumber, int destinationAccountNumber, long time) {
        //Method untestable due to the non-existing return type of the method
        this.sourceAccountNumber = sourceAccountNumber;
        this.destinationAccountNumber = destinationAccountNumber;
        this.time = time;
    }

    public int getSourceAccountNumber() {
        return sourceAccountNumber;
    }

    public void setSourceAccountNumber(int sourceAccountNumber) {
        this.sourceAccountNumber = sourceAccountNumber;
    }

    public int getDestinationAccountNumber() {
        return destinationAccountNumber;
    }

    public void setDestinationAccountNumber(int destinationAccountNumber) {
        this.destinationAccountNumber = destinationAccountNumber;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
