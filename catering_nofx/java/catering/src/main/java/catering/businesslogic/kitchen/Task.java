package catering.businesslogic.kitchen;

import catering.businesslogic.recipe.KitchenActivity;
import catering.businesslogic.shift.KitchenShift;
import catering.businesslogic.shift.Shift;
import catering.businesslogic.user.User;
import catering.persistence.BatchUpdateHandler;
import catering.persistence.PersistenceManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Task {
    //TODO: initialize variables
    private int estimatedTime;
    private int portion;
    private int quantity;
    private boolean completed;
    private KitchenActivity activity;
    private User cook;
    private KitchenShift shift;

    public Task(KitchenActivity activity) {
        this.activity = activity;
//        this.estimatedTime = activity.getEstimatedTime();
//        this.portion = activity.getPortion();
//        this.quantity = activity.getQuantity();
//        this.completed = false;
    }

    // initialize variables
    public void assignTask(KitchenShift shift,User cook,int portion,int quantity,int estimatedTime) {
        this.shift = shift;
        this.cook = cook;
        this.estimatedTime = estimatedTime;
        if(portion > 0) this.portion = portion;
        if(quantity > 0) this.quantity = quantity;
        //the boolean completed is not initialized here (check DSD)
    }

    public void assignTaskWithoutCook(KitchenShift shift, int portion, int quantity, int estimatedTime) {
        this.shift = shift;
        this.completed = false;
        this.estimatedTime = estimatedTime;
        if(estimatedTime > 0) this.estimatedTime = estimatedTime;
        if(portion > 0) this.portion = portion;
        if(quantity > 0) this.quantity = quantity;
    }

    public Task updateTask(KitchenShift shift, User cook, int portion, int quantity, int estimatedTime) {
        if(shift != null) this.shift = shift;
        if(cook != null) this.cook = cook;
        if(estimatedTime > 0) this.estimatedTime = estimatedTime;
        if(portion > 0) this.portion = portion;
        if(quantity > 0) this.quantity = quantity;
        return this;
    }

    public void removeAssignment() {
        if(shift != null) this.shift.removeTask(this);
        if(cook != null) this.cook.removeTask(this);
        this.shift = null;
        this.cook = null;
        this.estimatedTime = 0;
        this.portion = 0;
        this.quantity = 0;
    }

    public void completeTask() {
        this.completed = true;
        shift.removeTask(this);
        cook.removeTask(this);
        this.cook = null;
        this.shift = null;
    }

    public boolean isCompleted() {
        return completed;
    }

    public User getCook() {
        return cook;
    }

    public int getEstimatedTime() {
        return estimatedTime;
    }

    public KitchenShift getShift() {
        return shift;
    }

    public KitchenActivity getActivity() {
        return activity;
    }

    public static void saveKitchenTask(SummarySheet summarySheet) {
        String activitiesToInsert = "INSERT INTO catering.kitchentask (estimatedTime, portion, quantity, completed, activityId, cookId, shiftId) VALUES (?, ?, ?, ?, ?, ?, ?)";
        ArrayList<Task> tasks = summarySheet.getTasks();
        PersistenceManager.executeBatchUpdate(activitiesToInsert, tasks.size(), new BatchUpdateHandler() {
            @Override
            public void handleBatchItem(PreparedStatement ps, int batchCount) throws SQLException {
                Task task = tasks.get(batchCount);
                ps.setInt(1, task.getEstimatedTime());
                ps.setInt(2, 0);
                ps.setInt(3, 0);
                ps.setBoolean(4, false);
                ps.setInt(5, task.getActivity().getId());
                ps.setNull(6, java.sql.Types.INTEGER); // Use NULL for cookId
                ps.setNull(7, java.sql.Types.INTEGER); // Use NULL for shiftId
            }

            @Override
            public void handleGeneratedIds(ResultSet rs, int count) throws SQLException {
                if (count == 0) {
                    int generatedId = rs.getInt(1);
                    System.out.println("Generated ID: " + generatedId);
                }
            }
        });
    }

    public String toString() {
        return "Task: " + activity.getName() + " User: " + this.cook + " Shift: " + this.shift + " Portion: " + this.portion + " Quantity: " + this.quantity + " Estimated Time: " + this.estimatedTime + " Completed: " + this.completed + "\n";
    }

}
