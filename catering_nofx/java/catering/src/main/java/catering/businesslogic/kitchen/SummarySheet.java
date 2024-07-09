package catering.businesslogic.kitchen;

import catering.businesslogic.event.Service;
import catering.businesslogic.event.ServiceInfo;
import catering.businesslogic.recipe.KitchenActivity;
import catering.businesslogic.shift.KitchenShift;
import catering.businesslogic.user.User;

import java.util.ArrayList;

public class SummarySheet {
    private String sorting;
    private ArrayList<Task> tasks;
    private User owner;



    private ServiceInfo service; //Maybe it's ServiceInfo

    public SummarySheet(User owner, ServiceInfo service) {
        this.owner = owner;
        this.service = service;
        this.tasks = new ArrayList<>();
        this.sorting = "default";

        ArrayList<KitchenActivity> kitchenActivities = service.getKitchenActivities(); //TODO: implement getKitchenActivities
        for (KitchenActivity ka : kitchenActivities) {
            Task t = new Task(ka);
            tasks.add(t);
        }
    }

    public Task addTask(KitchenActivity ka) {
        Task t = new Task(ka);
        tasks.add(t);
        return t;
    }

    public void removeTask(Task t) {
        tasks.remove(t);
    }

    public void completeTask(Task t) {
        t.completeTask();
    }

    //TODO: implement sorting by sortType
    public void sort(String sortType, Task firstTask, Task secondTask) {
        if(firstTask == null || secondTask == null) {
            throw new IllegalArgumentException("Both tasks must be not null");
        }
        else if(!tasks.contains(firstTask) || !tasks.contains(secondTask)) {
            throw new IllegalArgumentException("Both tasks must be in the summary sheet");
        } else {
            // swap the two tasks
            int firstIndex = tasks.indexOf(firstTask);
            int secondIndex = tasks.indexOf(secondTask);

            tasks.set(firstIndex, tasks.get(secondIndex));
            tasks.set(secondIndex, tasks.get(firstIndex));
        }
    }

    public Task updateTask(Task task,KitchenShift shift,User cook,int portion,int quantity,int estimatedTime) {
        return task.updateTask(shift, cook, portion, quantity, estimatedTime);
    }

    public void addTask(Task t) {
        t.getCook().addTask(t);
        t.getShift().addTask(t);
    }

    public void removeAssigne(Task t) {
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

    // toString method
    public String toString() {
        return "SummarySheet{" +
                "sorting='" + sorting + '\'' +
                ", tasks=" + tasks +
                ", owner=" + owner +
                ", service=" + service +
                '}';
    }
}
