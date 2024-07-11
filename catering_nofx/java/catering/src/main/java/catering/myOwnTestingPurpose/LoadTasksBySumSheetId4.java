package catering.myOwnTestingPurpose;

import catering.businesslogic.kitchen.Task;

import java.util.ArrayList;

public class LoadTasksBySumSheetId4 {
    public static void main(String[] args) {
        ArrayList<Task> tasks = Task.loadTasksBySumSheetId(6);
        for(Task t : tasks) {
            System.out.println(t);
        }
    }
}
