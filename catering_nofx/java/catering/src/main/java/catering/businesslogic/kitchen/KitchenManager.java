package catering.businesslogic.kitchen;

import catering.businesslogic.CatERing;
import catering.businesslogic.SummarySheetException;
import catering.businesslogic.UseCaseLogicException;
import catering.businesslogic.event.EventInfo;
import catering.businesslogic.event.ServiceInfo;
import catering.businesslogic.recipe.KitchenActivity;
import catering.businesslogic.shift.KitchenShift;
import catering.businesslogic.user.User;

import java.util.ArrayList;

public class KitchenManager {
    private SummarySheet currentSummarySheet;
    private ArrayList<SummaryEventReciever> summaryReceivers;

    public KitchenManager() {
        this.summaryReceivers = new ArrayList<>();
    }

    // (1) createSummarySheet. This method creates a new SummarySheet for a given service and event.
    public SummarySheet createSummarySheet(ServiceInfo service, EventInfo eventInfo) throws UseCaseLogicException, SummarySheetException {
        User user = CatERing.getInstance().getUserManager().getCurrentUser();
        if (!user.isChef() || !service.hasMenu()) {
            throw new UseCaseLogicException();
        }
        if (!service.chefAssigned()) {
            throw new SummarySheetException();
        }
        SummarySheet sumsheet = new SummarySheet(user, service);
        setCurrentSummarySheet(sumsheet);
        notifySummarySheetAdded(sumsheet);
        ServiceInfo.saveSummaryId(sumsheet.getId(),service.getId());
        return sumsheet;
    }

    // (1a) chooseSummarySheet. This method sets the currentSummarySheet to the given SummarySheet.
    public SummarySheet chooseSummarySheet(SummarySheet s) throws UseCaseLogicException, SummarySheetException {
        User user = CatERing.getInstance().getUserManager().getCurrentUser();
        if (!user.isChef()) {
            throw new UseCaseLogicException();
        }
        if (!s.getOwner().equals(user)) {
            throw new SummarySheetException();
        }
        setCurrentSummarySheet(s);
        return currentSummarySheet; // I don't know why this is returned (check DSD)
    }

    // (1b) recreateSummarySheet. This method creates a new SummarySheet for a given old SummarySheet.
    public SummarySheet recreateSummarySheet(SummarySheet oldSummarySheet) throws UseCaseLogicException, SummarySheetException {
        User user = CatERing.getInstance().getUserManager().getCurrentUser();
        if (!user.isChef()) {
            throw new UseCaseLogicException();
        }
        // check if the user is the owner of the oldSummarySheet
        if (!oldSummarySheet.getOwner().equals(user)) {
            throw new SummarySheetException();
        }
        ServiceInfo service = oldSummarySheet.getService();
        oldSummarySheet.removeAllTasks();
        // Only thing is it's returning same tasks
        SummarySheet sumsheet = new SummarySheet(user, service);

        setCurrentSummarySheet(sumsheet);
        //notifySummaryRecreate(oldSummarySheet); // I don't know why this is called with oldSummarySheet (Ask Alex)
        return sumsheet;
    }

    // (2) addTask. This method adds a task to the currentSummarySheet.
    public Task addTask(KitchenActivity ka) throws UseCaseLogicException, SummarySheetException {
        Task t = currentSummarySheet.addTask(ka);
        notifyTaskAdded(currentSummarySheet, t);
        return t; // why return here?
    }


    // (3) sortSummarySheet. This method swaps two tasks in the currentSummarySheet.
    public void sortSummarySheet(String sortType, Task firstTask, Task secondTask) {
        currentSummarySheet.sort(sortType, firstTask, secondTask); // I really don't know why we have to pass the sortType??? (Ask Alex)
        //notifySummarySheetSorted(); //TODO: implement notifySummarySheetSorted (DSD seems wrong, calling this method on current summer sheet)
    }

    // (5) assignTask. This method assigns a task to a cook from current summary sheet (task should be already added).
    public void assignTask(Task task, KitchenShift shift, User cook, int portion, int quantity, int estimatedTime) throws UseCaseLogicException, SummarySheetException {
        if (this.currentSummarySheet == null || !currentSummarySheet.hasTask(task)) {
            throw new UseCaseLogicException();
        }

        if (checkTotalDuration(task, shift, estimatedTime, cook)) {
            task.assignTask(shift, cook, portion, quantity, estimatedTime);
        } else {
            throw new SummarySheetException();
        }
        // in DSD I wrote notifyAssignSummarySheet(Task)
        //notifySummarySheetAssigned(task); //TODO: implement notifySummarySheetAssigned
    }

    // (5a) assign task without cook
    public void assignTaskWithoutCook(Task task, KitchenShift shift, int portion, int quantity, int estimatedTime) throws UseCaseLogicException {
        if (this.currentSummarySheet == null || !currentSummarySheet.hasTask(task)) {
            throw new UseCaseLogicException();
        }
        currentSummarySheet.assignTaskWithoutCook(task, shift, portion, quantity, estimatedTime);
        //notifySummarySheetAssigned(task); //TODO: implement notifySummarySheetAssigned
    }

    // getters and setters for currentSummarySheet
    public void setCurrentSummarySheet(SummarySheet s) {
        this.currentSummarySheet = s;
    }

    public SummarySheet getCurrentSummarySheet() {
        return this.currentSummarySheet;
    }

    private boolean checkTotalDuration(Task task, KitchenShift shift, int estimatedTime, User cook) {
        int totalDuration = 0;
        int totalEstimatedTime = 0;
        int eT = estimatedTime > 0 ? estimatedTime : task.getEstimatedTime();
        KitchenShift s = shift != null ? shift : task.getShift();
        if (s.hasGroup()) {
            ArrayList<KitchenShift> shifts = s.getShiftsInGroup();
            for (KitchenShift ks : shifts) {
                totalDuration += ks.getDuration();
                ArrayList<Task> userTasks = cook.getTasks();
                for (Task t : userTasks) {
                    totalEstimatedTime += t.getEstimatedTime();
                }
            }
            return totalDuration > totalEstimatedTime + eT;
        } else {
            ArrayList<Task> userTasks = cook != null ? cook.getTasks() : task.getCook().getTasks();
            for (Task t : userTasks) {
                totalEstimatedTime += t.getEstimatedTime();
            }
            return s.getDuration() > totalEstimatedTime + eT;
        }
    }

    // (5b) modifyTask.
    public void modifyTask(Task task, KitchenShift shift, User cook, int portion, int quantity, int estimatedTime) throws UseCaseLogicException, SummarySheetException {
        if (this.currentSummarySheet == null || !currentSummarySheet.hasTask(task)) {
            throw new UseCaseLogicException();
        }
        if (!task.isCompleted()) {
            throw new SummarySheetException();
        }

        if (checkTotalDuration(task, shift, estimatedTime, cook)) {
            currentSummarySheet.removeTask(task); //remove the old task from the summary sheet
            Task newTask = currentSummarySheet.updateTask(task, shift, cook, portion, quantity, estimatedTime);
            currentSummarySheet.addTask(newTask);
        } else {
            throw new SummarySheetException();
        }
        //notifySummarySheetModified(task); //TODO: implement notifySummarySheetModified
    }

    // (5c) remove assignment from task
    public void removeAssignment(Task task) throws UseCaseLogicException {
        if (this.currentSummarySheet == null || !currentSummarySheet.hasTask(task)) {
            throw new UseCaseLogicException();
        }
        currentSummarySheet.removeAssign(task);
        //notifyAssignment(task); //TODO: implement notifySummarySheetRemoved
    }

    // (5d) remove task
    public void removeTask(Task task) throws UseCaseLogicException, SummarySheetException {
        if (this.currentSummarySheet == null || !currentSummarySheet.hasTask(task)) {
            throw new UseCaseLogicException();
        }
        if(task.getCook() != null || task.getShift() != null) {
            throw new SummarySheetException();
        }
        currentSummarySheet.removeTask(task);
        //notifyRemoveTask(task); //TODO: implement notifySummarySheetRemoved
    }

    // (5e) completeTask
    public void completeTask(Task task) throws UseCaseLogicException, SummarySheetException {
        if (this.currentSummarySheet == null || !currentSummarySheet.hasTask(task)) {
            throw new UseCaseLogicException();
        }
        currentSummarySheet.completeTask(task);
        //notifyTaskCompleted(task); //TODO: implement notifyTaskCompleted
    }

    // (4) getShiftBoard (this might've changed in the DSD, check it out)
    public ArrayList<KitchenShift> getShiftBoard() {
        return CatERing.getInstance().getShiftManager().getShiftBoard(); //TODO: initialize getShiftBoard
    }

    // notify 1
    private void notifySummarySheetAdded(SummarySheet sumsheet) {
        for (SummaryEventReciever r : this.summaryReceivers) {
            //r.updateTaskCreated(sumsheet); TODO: move it to task added
            r.updateSummaryCreated(sumsheet);
        }
    }
    // notify 2
    public void notifyTaskAdded(SummarySheet sumsheet, Task t) {
        for (SummaryEventReciever r : this.summaryReceivers) {
            r.updateTaskCreated(sumsheet, t);
        }
    }

    public void addEventReceiver(SummaryEventReciever r) {
        this.summaryReceivers.add(r);
    }

    // toString method
    public String toString() {
        return "KitchenManager: " + currentSummarySheet.toString();
    }
}
