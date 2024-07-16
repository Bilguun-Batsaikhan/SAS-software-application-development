package catering.myOwnTestingPurpose;

import catering.businesslogic.CatERing;
import catering.businesslogic.kitchen.KitchenManager;
import catering.businesslogic.kitchen.SummarySheet;
import catering.businesslogic.kitchen.Task;

import java.util.ArrayList;

public class Scenario3 {
    public static void main(String[] args) {
        CatERing.getInstance().getUserManager().fakeLogin("Lidia");
        KitchenManager ssm = CatERing.getInstance().getKitchenManager();
        ArrayList<SummarySheet> sumArr = SummarySheet.loadAllSummarySheets();
        try {
            ssm.chooseSummarySheet(sumArr.get(0));
            SummarySheet sh = ssm.getCurrentSummarySheet();
            System.out.println("(3) summary sheet before sorting task: ");
            ArrayList<Task> shTasksBefore = sh.getTasks();
            for(int i = 0; i < shTasksBefore.size(); i++) {
                System.out.println("T: " + i + " " + shTasksBefore.get(i).getActivityName());
            }

            ssm.sortSummarySheet("difficulty", null, null);
            System.out.println("(3) summary sheet after sorting task: ");
            ArrayList<Task> shTasksAfter = sh.getTasks();
            for(int i = 0; i < shTasksBefore.size(); i++) {
                System.out.println("T: " + i + " " + shTasksAfter.get(i).getActivityName());
            }
        } catch (Exception e) {
            System.out.println("Exception " + e);
        }
    }
}
