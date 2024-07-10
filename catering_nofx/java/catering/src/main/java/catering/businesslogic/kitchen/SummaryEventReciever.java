package catering.businesslogic.kitchen;

public interface SummaryEventReciever {
    public void updateSummaryCreated(SummarySheet s);
    public void updateTaskCreated(SummarySheet s, Task t);
}
