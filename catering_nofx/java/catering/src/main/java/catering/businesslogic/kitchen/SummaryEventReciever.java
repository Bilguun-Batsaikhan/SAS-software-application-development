package catering.businesslogic.kitchen;

public interface SummaryEventReciever {
    public void updateSummaryCreated(SummarySheet s);
    public void updateTaskCreated(SummarySheet s, Task t);
    public void updateTaskAssigned(SummarySheet s, Task t, boolean changeCook);
    public void updateTaskModify(SummarySheet currentSummarySheet, Task task);
    public void updateTaskRemoveAssign(SummarySheet currentSummarySheet, Task task);
    public void updateCompleteTask(SummarySheet currentSummarySheet, Task task);
    public void updateRemoveTask(SummarySheet currentSummarySheet, Task task);
    public void updateSummaryRecreate(SummarySheet currentSummarySheet, SummarySheet newSummarySheet);
}
