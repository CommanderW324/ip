import java.time.LocalDate;

public class Event extends Task {
    private LocalDate date;
    Event(String description, boolean isDone, LocalDate date) {
        super(description, isDone);
        this.date = date;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + "(at: " + date + ")";
    }
}
