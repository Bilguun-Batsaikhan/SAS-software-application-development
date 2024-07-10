package catering.businesslogic.shift;

import catering.businesslogic.kitchen.Task;
import catering.businesslogic.recipe.KitchenActivity;
import catering.businesslogic.recipe.Recipe;
import catering.persistence.PersistenceManager;
import catering.persistence.ResultHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class KitchenShift extends Shift {
    private int id;
    int duration;
    GroupShift group;
    ArrayList<Task> tasks;
    //Constructor is missing
    ArrayList<KitchenShift> KitchenShiftBoard;
    private static Map<Integer, KitchenShift> all = new HashMap<>();

    public KitchenShift(GroupShift group, int duration) {
        super();
        this.duration = duration;
        this.group = group;
        tasks = new ArrayList<>();
    }
    public KitchenShift(GroupShift gr) {
        super();
        group = gr;
        tasks = new ArrayList<>();
    }

    public KitchenShift() {
        super();
        tasks = new ArrayList<>();
    }

    public GroupShift getGroup() {
        return group;
    }

    @Override
    public boolean hasGroup() {
        return group != null; //TODO: initialize group
//        // for now return true
//        return true;
    }

    public static ArrayList<KitchenShift> loadAllKitchenShift() {
        String query = "SELECT * FROM kitchenshift";
        PersistenceManager.executeQuery(query, new ResultHandler() {
            @Override
            public void handle(ResultSet rs) throws SQLException {
                int id = rs.getInt("id");
                if (all.containsKey(id)) {
                    KitchenShift ks = all.get(id);
                    ks.place = rs.getString("place");
                    ks.recurring = rs.getInt("recurring") == 1;
                    ks.duration = rs.getInt("duration");
                } else {
                    KitchenShift ks = new KitchenShift();
                    ks.id = id;
                    ks.place = rs.getString("place");
                    ks.recurring = rs.getInt("recurring") == 1;
                    ks.duration = rs.getInt("duration");
                    all.put(ks.id, ks);
                }
            }
        });
        return new ArrayList<KitchenShift>(all.values());
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public void removeTask(Task task) {
        tasks.remove(task);
    }

    public int getDuration() {
        return duration;
    }

    public ArrayList<KitchenShift> getShiftsInGroup() {
        return group.getShifts();
    }

    public String toString() {
        return "id: " + id + " place: " + place;
    }
}
