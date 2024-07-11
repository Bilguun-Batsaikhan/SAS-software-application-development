package catering.businesslogic.kitchen;

import catering.businesslogic.recipe.KitchenActivity;
import catering.businesslogic.shift.KitchenShift;
import catering.businesslogic.shift.Shift;
import catering.businesslogic.user.User;
import catering.persistence.BatchUpdateHandler;
import catering.persistence.PersistenceManager;
import catering.persistence.ResultHandler;

import java.sql.Array;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Task {
    //TODO: initialize variables
    private static Map<Integer, ArrayList<Task>> loadedTasks = new HashMap<Integer, ArrayList<Task>>();
    private int id;
    private int estimatedTime;
    private int portion;
    private int quantity;
    private boolean completed;
    private KitchenActivity activity;
    private User cook;
    private KitchenShift shift;

    private int activity_id;
    private int cook_id;
    private int shift_id;
    private int summarysheet_id;

    public Task(KitchenActivity activity) {
        id = 0;
        this.activity = activity;
//        this.estimatedTime = activity.getEstimatedTime();
//        this.portion = activity.getPortion();
//        this.quantity = activity.getQuantity();
//        this.completed = false;
    }

    public void setKitchenShift(KitchenShift ks) {
        this.shift=ks;
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


    //this method should be called from summarySheet load
    public static ArrayList<Task> loadTasksBySumSheetId(int summary_sheet_id) {
        if (loadedTasks.containsKey(summary_sheet_id)) return loadedTasks.get(summary_sheet_id);

        Task load = new Task(null);
        ArrayList<Task> loadedTasksBySumSheetId = new ArrayList<>();
        String taskQuery = "SELECT * FROM kitchentask WHERE summarysheet_id='"+summary_sheet_id+"'";
        PersistenceManager.executeQuery(taskQuery, new ResultHandler() {
            @Override
            public void handle(ResultSet rs) throws SQLException {
                load.id = rs.getInt("id");
                load.estimatedTime = rs.getInt("estimatedTime");
                load.portion = rs.getInt("portion");
                load.quantity = rs.getInt("quantity");
                load.completed = rs.getBoolean("completed");
                load.activity_id = rs.getInt("activityId");
                load.cook_id = rs.getInt("cookId");
                load.shift_id = rs.getInt("shiftId");
                load.summarysheet_id = rs.getInt("summarysheet_id");
                loadedTasksBySumSheetId.add(load);
            }
        });
        //initialize the other Class type parameters: KitchenActivity, User, KitchenShift
        for (Task t : loadedTasksBySumSheetId) {
            t.activity = KitchenActivity.loadActivityById(t.activity_id);
            t.cook = User.loadUserById(t.cook_id);
            t.shift = KitchenShift.loadKitchenShiftById(t.shift_id);
        }
        loadedTasks.put(summary_sheet_id, loadedTasksBySumSheetId); // I think I also should add this to the loadedTasks
        return loadedTasksBySumSheetId;
    }

    public static ArrayList<Task> loadTasksByShiftId(int id) {
        String query = "SELECT * FROM kitchentask WHERE shiftId="+ id;
        ArrayList<Task> shiftTasks =new ArrayList<>();
        PersistenceManager.executeQuery(query, new ResultHandler() {
            @Override
            public void handle(ResultSet rs) throws SQLException {
                Task t = new Task(null);
                t.id = (rs.getInt("id"));
                t.estimatedTime = (rs.getInt("estimatedTime"));
                t.portion = (rs.getInt("portion"));
                t.quantity = (rs.getInt("quantity"));
                t.completed = (rs.getBoolean("completed"));
                t.activity_id = (rs.getInt("activityId"));
                t.cook_id = (rs.getInt("cookId"));
                t.summarysheet_id = (rs.getInt("summarysheet_id"));
                shiftTasks.add(t);
            }
        });
        for(Task inside: shiftTasks)
        {
            inside.activity = KitchenActivity.loadActivityById(inside.activity_id);
            inside.cook = User.loadUserById(inside.cook_id);
        }
        return  shiftTasks;
    }

    public static ArrayList<Task> loadTasksByUserId(int id) {
        String query = "SELECT * FROM kitchentask WHERE cookId="+ id;
        ArrayList<Task> userTasks =new ArrayList<>();
        PersistenceManager.executeQuery(query, new ResultHandler() {
            @Override
            public void handle(ResultSet rs) throws SQLException {
                Task t = new Task(null);
                t.id = (rs.getInt("id"));
                t.estimatedTime = (rs.getInt("estimatedTime"));
                t.portion = (rs.getInt("portion"));
                t.quantity = (rs.getInt("quantity"));
                t.completed = (rs.getBoolean("completed"));
                t.activity_id = (rs.getInt("activityId"));
                t.shift_id = (rs.getInt("shiftId"));
                t.summarysheet_id = (rs.getInt("summarysheet_id"));
                userTasks.add(t);
            }
        });
        for(Task inside: userTasks)
        {
            inside.activity = KitchenActivity.loadActivityById(inside.activity_id);
            inside.shift = KitchenShift.loadKitchenShiftById(inside.shift_id);
        }
        return userTasks;
    }

    public static void saveKitchenTasks(SummarySheet summarySheet) {
        String activitiesToInsert = "INSERT INTO catering.kitchentask (estimatedTime, portion, quantity, completed, activityId, cookId, shiftId, summarysheet_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
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
                ps.setInt(8, summarySheet.getId());
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

    public static void saveKitchenTask(int sumsheet_id, Task t) {
        String activitiesToInsert = "INSERT INTO catering.kitchentask (estimatedTime, portion, quantity, completed, activityId, cookId, shiftId, summarysheet_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        PersistenceManager.executeBatchUpdate(activitiesToInsert, 1, new BatchUpdateHandler() {
            @Override
            public void handleBatchItem(PreparedStatement ps, int batchCount) throws SQLException {
                ps.setInt(1, t.getEstimatedTime());
                ps.setInt(2, 0);
                ps.setInt(3, 0);
                ps.setBoolean(4, false);
                ps.setInt(5, t.getActivity().getId());
                ps.setNull(6, java.sql.Types.INTEGER); // Use NULL for cookId
                ps.setNull(7, java.sql.Types.INTEGER); // Use NULL for shiftId
                ps.setInt(8, sumsheet_id);
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
