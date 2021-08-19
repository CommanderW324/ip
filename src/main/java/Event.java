public class Event extends Task {
    public String date;
    Event(String description, boolean isDone, String date) {
        super(description, isDone);
        this.date = date;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + "(at: " + date + ")";
    }
}
