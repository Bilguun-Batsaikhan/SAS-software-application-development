package catering.businesslogic.kitchen;

import catering.businesslogic.event.ServiceInfo;
import catering.businesslogic.recipe.KitchenActivity;
import catering.businesslogic.shift.KitchenShift;
import catering.businesslogic.user.User;
import catering.persistence.BatchUpdateHandler;
import catering.persistence.PersistenceManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class SummarySheet {
    private int id;
    private String sorting;
    private ArrayList<Task> tasks;
    private User owner;
    private ServiceInfo service;

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
        if(result[0] > 0) {
            //save tasks

        }
    }
    //for loading the summary sheet look at serviceinfo example
    /*
    *     public static void saveNewMenu(Menu m) {
        String menuInsert = "INSERT INTO catering.Menus (title, owner_id, published) VALUES (?, ?, ?);";
        int[] result = PersistenceManager.executeBatchUpdate(menuInsert, 1, new BatchUpdateHandler() {
            @Override
            public void handleBatchItem(PreparedStatement ps, int batchCount) throws SQLException {
                ps.setString(1, PersistenceManager.escapeString(m.title));
                ps.setInt(2, m.owner.getId());
                ps.setBoolean(3, m.published);
            }

            @Override
            public void handleGeneratedIds(ResultSet rs, int count) throws SQLException {
                // should be only one
                if (count == 0) {
                    m.id = rs.getInt(1);
                }
            }
        });

        if (result[0] > 0) { // menu effettivamente inserito
            // salva le features
            featuresToDB(m);

            // salva le sezioni
            if (m.sections.size() > 0) {
                Section.saveAllNewSections(m.id, m.sections);
            }

            // salva le voci libere
            if (m.freeItems.size() > 0) {
                MenuItem.saveAllNewItems(m.id, 0, m.freeItems);
            }
            loadedMenus.put(m.id, m);
        }
    }
    * */





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

    // toString method
    public String toString() {
        return "sorting: " + sorting + "\ntasks: " + tasks + "\nowner:" + owner + "\nservice[\n" + service + "]\n";
    }
}
