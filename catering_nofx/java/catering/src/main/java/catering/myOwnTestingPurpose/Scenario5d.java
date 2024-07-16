package catering.myOwnTestingPurpose;

import catering.businesslogic.CatERing;
import catering.businesslogic.kitchen.KitchenManager;
import catering.businesslogic.kitchen.SummarySheet;
import catering.businesslogic.kitchen.Task;

import java.util.ArrayList;

public class Scenario5d {
    public static void main(String[] ars) {
        CatERing.getInstance().getUserManager().fakeLogin("Lidia");
        ArrayList<SummarySheet> summarySheets = SummarySheet.loadAllSummarySheets();
        KitchenManager ssm = CatERing.getInstance().getKitchenManager();

        try {
            ssm.chooseSummarySheet(summarySheets.get(0));
            SummarySheet sh = ssm.getCurrentSummarySheet();
            ArrayList<Task> tasks = sh.getTasks();

            Task first = tasks.get(1);
            ssm.removeTask(first);
        } catch (Exception e) {
            System.out.println("Exception at createSummarySheet: " + e);
        }
    }
}
