package catering.businesslogic.kitchen;

import catering.businesslogic.event.ServiceInfo;
import catering.businesslogic.recipe.KitchenActivity;
import catering.businesslogic.shift.KitchenShift;
import catering.businesslogic.user.User;
import catering.persistence.BatchUpdateHandler;
import catering.persistence.PersistenceManager;
import catering.persistence.ResultHandler;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class SummarySheet {
    private static Map<Integer, SummarySheet> loadedSummarySheets = new HashMap<Integer, SummarySheet>();
    private int id;
    private String sorting;
    private ArrayList<Task> tasks;
    private User owner;
    private ServiceInfo service;

    private int owner_id;
    private int service_id;

    public SummarySheet(User owner, ServiceInfo service) {
        this.id = 0;
        this.owner = owner;
        this.service = service;
        this.tasks = new ArrayList<>();
        this.sorting = "default";

        ArrayList<KitchenActivity> kitchenActivities = service.getKitchenActivities();
        ArrayList<KitchenActivity> uniqueKitchenActivities = new ArrayList<>(removeDuplicates(kitchenActivities));

        for (KitchenActivity ka : uniqueKitchenActivities) {
            Task t = new Task(ka);
            tasks.add(t);
        }
    }
    public static void saveKitchenSummary(SummarySheet summarySheet) {
        // SQL query to insert a summary sheet
        String summaryInsertQuery = "INSERT INTO summarysheet (sorting, owner_id, service_id) VALUES (?, ?, ?)";

        // SQL query to insert into the intermediary table
        String summaryTaskInsertQuery = "INSERT INTO summarysheet_kitchentask (summarysheet_id, kitchentask_id) VALUES (?, ?)";

        // SQL query to insert kitchen tasks
        String taskInsertQuery = "INSERT INTO kitchentask (task_description) VALUES (?)";
        int[] result = PersistenceManager.executeBatchUpdate(summaryInsertQuery, 1, new BatchUpdateHandler() {
            @Override
            public void handleBatchItem(PreparedStatement ps, int batchCount) throws SQLException {
                ps.setString(1, summarySheet.sorting);
                ps.setInt(2, summarySheet.owner.getId());
                ps.setInt(3, summarySheet.service.getId());
            }

            @Override
            public void handleGeneratedIds(ResultSet rs, int count) throws SQLException {
                if (count == 0) {
                    summarySheet.id = rs.getInt(1);
                }
            }
        });
    }

    public static ArrayList<SummarySheet> loadAllSummarySheets() {
        String query = "SELECT * FROM summarysheet WHERE " + true;
        ArrayList<SummarySheet> newSummarySheets = new ArrayList<>();
        ArrayList<SummarySheet> oldSummarySheets = new ArrayList<>();

        ArrayList<Integer> newSummarySheetOwnerIds = new ArrayList<>();
        ArrayList<Integer> oldSummarySheetOwnerIds = new ArrayList<>();

        ArrayList<Integer> newSummarySheetServiceIds = new ArrayList<>();
        ArrayList<Integer> oldSummarySheetsServiceIds = new ArrayList<>();

        PersistenceManager.executeQuery(query, new ResultHandler() {
            @Override
            public void handle(ResultSet rs) throws SQLException {
                int id = rs.getInt("id");

                if (loadedSummarySheets.containsKey(id)) {
                    SummarySheet s = loadedSummarySheets.get(id);
                    s.sorting = rs.getString("sorting");
                    s.owner_id = rs.getInt("owner_id");
                    s.service_id = rs.getInt("service_id");
                    oldSummarySheetOwnerIds.add(rs.getInt("owner_id"));
                    oldSummarySheetOwnerIds.add(rs.getInt("service_id"));
                    oldSummarySheets.add(s);
                } else {
                    SummarySheet s = new SummarySheet(null, null);
                    s.id = id;
                    s.sorting = rs.getString("sorting");
                    s.owner_id = rs.getInt("owner_id");
                    s.service_id = rs.getInt("service_id");
                    newSummarySheetOwnerIds.add(rs.getInt("owner_id"));
                    newSummarySheetServiceIds.add(rs.getInt("service_id"));
                    newSummarySheets.add(s);
                }
            }
        });
        for (int i = 0; i < newSummarySheets.size(); i++) {
            SummarySheet s = newSummarySheets.get(i);
            // Create the owner from new summary sheet owner ids
            s.owner = User.loadUserById(newSummarySheetOwnerIds.get(i));
            s.service = ServiceInfo.loadServiceBySumSheetId(newSummarySheetServiceIds.get(i));
            s.tasks = Task.loadTasksBySumSheetId(s.id);
        }
        for (int i = 0; i < oldSummarySheets.size(); i++) {
            SummarySheet s = oldSummarySheets.get(i);
            // Create the owner from old summary sheet owner ids
            s.owner = User.loadUserById(oldSummarySheetOwnerIds.get(i));
            s.service = ServiceInfo.loadServiceBySumSheetId(oldSummarySheetsServiceIds.get(i));
            s.tasks = Task.loadTasksBySumSheetId(s.id);
        }

        for(SummarySheet s: newSummarySheets) {
            loadedSummarySheets.put(s.id, s);
        }
        //TODO: implement loadTasksBySumSheetId

        return new ArrayList<SummarySheet>(loadedSummarySheets.values());
    }




    public List<KitchenActivity> removeDuplicates(ArrayList<KitchenActivity> kitchenActivities) {
        HashSet<KitchenActivity> set = new HashSet<>(kitchenActivities);
        return new ArrayList<>(set);
    }

    public Task addTask(KitchenActivity ka) {
        Task t = new Task(ka);
        tasks.add(t);
        return t;
    }

    public void removeAllTasks() {
        for(Task t: tasks) {
            t.removeAssignment();
        }
    }

    public void removeTask(Task t) {
        tasks.remove(t);
    }

    public void completeTask(Task t) {
        t.completeTask();
    }

    //TODO: implement sorting by sortType
    public void sort(String sortType, Task firstTask, Task secondTask) {
        if(sortType.equals("difficulty")) {
            Collections.sort(tasks, new Comparator<Task>() {
                @Override
                public int compare(Task t1, Task t2) {
                    return t1.getActivity().getDifficulty().compareTo(t2.getActivity().getDifficulty());
                }
            });
        } else {
            if(firstTask == null || secondTask == null) {
                throw new IllegalArgumentException("Both tasks must be not null");
            }
            else if(!tasks.contains(firstTask) || !tasks.contains(secondTask)) {
                throw new IllegalArgumentException("Both tasks must be in the summary sheet");
            } else {
                // swap the two tasks
                int firstIndex = tasks.indexOf(firstTask);
                int secondIndex = tasks.indexOf(secondTask);
                Task temp = tasks.get(firstIndex);
                tasks.set(firstIndex, tasks.get(secondIndex));
                tasks.set(secondIndex, temp);
            }
        }
    }

    public Task updateTask(Task task,KitchenShift shift,User cook,int portion,int quantity,int estimatedTime) {
        return task.updateTask(shift, cook, portion, quantity, estimatedTime);
    }

    public void addTask(Task t) {
        t.getCook().addTask(t);
        t.getShift().addTask(t);
    }

    public void removeAssign(Task t) {
       t.removeAssignment();
    }

    public void assignTaskWithoutCook(Task task, KitchenShift shift, int portion, int quantity, int estimatedTime) {
        task.assignTaskWithoutCook(shift, portion, quantity, estimatedTime);
    }

    public User getOwner() {
        return owner;
    }

    public boolean hasTask(Task t) {
        return tasks.contains(t);
    }

    public ServiceInfo getService() {
        return service;
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public int getId() {
        return id;
    }

    // toString method
    public String toString() {
        return "sorting: " + sorting + "\ntasks: " + tasks + "\nowner:" + owner + "\nservice[\n" + service + "]\n";
    }
}
