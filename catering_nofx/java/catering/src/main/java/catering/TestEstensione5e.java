package catering;

import catering.businesslogic.CatERing;
import catering.businesslogic.kitchen.KitchenManager;
import catering.businesslogic.kitchen.SummarySheet;
import catering.businesslogic.kitchen.Task;
import catering.businesslogic.shift.KitchenShift;

import java.util.ArrayList;

public class TestEstensione5e {
    public static void main(String ars[]) {
        System.out.println("This is a test class for the SummarySheet class");
        CatERing.getInstance().getUserManager().fakeLogin("Lidia");
        ArrayList<SummarySheet> sumArr = SummarySheet.loadAllSummarySheets();
        KitchenManager ssm = CatERing.getInstance().getKitchenManager();

        try {
            SummarySheet sh = ssm.chooseSummarySheet(sumArr.get(0));
            Task first = sh.getTasks().get(0);
            System.out.println("Task before complete task" + first);
            ssm.completeTask(first);
            System.out.println("Task after complete task" + first);
        } catch (Exception e) {
            System.out.println("Exception at createSummarySheet: " + e);
        }
    }
}
