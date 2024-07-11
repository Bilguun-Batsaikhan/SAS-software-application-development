package catering.myOwnTestingPurpose;

import catering.businesslogic.kitchen.Task;

import java.util.ArrayList;

public class LoadTasksByShiftId1 {
    public static void main(String[] args) {
        ArrayList<Task> tasks = Task.loadTasksByShiftId(1);
        System.out.println(tasks);
    }
}
