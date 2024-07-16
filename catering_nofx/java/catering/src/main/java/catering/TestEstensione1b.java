package catering;

import catering.businesslogic.CatERing;
import catering.businesslogic.kitchen.KitchenManager;
import catering.businesslogic.kitchen.SummarySheet;
import catering.businesslogic.kitchen.Task;
import catering.businesslogic.shift.KitchenShift;

import java.util.ArrayList;

public class TestEstensione1b {
    public static void main(String[] args) {

        System.out.println("This is a test class for the SummarySheet class");
        CatERing.getInstance().getUserManager().fakeLogin("Lidia");
        KitchenManager ssm = CatERing.getInstance().getKitchenManager();
        try {
            //1.b
            ArrayList<SummarySheet> sumArr = SummarySheet.loadAllSummarySheets();
            System.out.println("(1.b) summary before recreate: "+ sumArr.get(0));
           for(Task before: sumArr.get(0).getTasks())
               {
                   System.out.println(before);
               }
            SummarySheet sh = ssm.recreateSummarySheet(sumArr.get(0));
            System.out.println("\n(1.b) summary after recreate: "+ sh);
            for(Task after: sh.getTasks())
            {
                System.out.println(after);
            }

        } catch (Exception e) {
            System.out.println("Exception at createSummarySheet: " + e);
        }

    }
}
