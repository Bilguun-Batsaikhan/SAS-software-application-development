package catering.businesslogic.shift;

import catering.businesslogic.kitchen.Task;

import java.util.ArrayList;

public class KitchenShift extends Shift {
    int duration;
    GroupShift group;
    ArrayList<Task> tasks;
    //Constructor is missing

    public GroupShift getGroup() {
        return group;
    }

    @Override
    public boolean hasGroup() {
        //return group != null; //TODO: initialize group
        // for now return true
        return true;
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
}
