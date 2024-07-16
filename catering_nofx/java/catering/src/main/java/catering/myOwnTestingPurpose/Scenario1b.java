package catering.myOwnTestingPurpose;

import catering.businesslogic.CatERing;
import catering.businesslogic.kitchen.KitchenManager;
import catering.businesslogic.kitchen.SummarySheet;

import java.util.ArrayList;

public class Scenario1b {
    public static void main(String[] args) {
        CatERing.getInstance().getUserManager().fakeLogin("Lidia");
        KitchenManager ssm = CatERing.getInstance().getKitchenManager();
        ArrayList<SummarySheet> sumArr = SummarySheet.loadAllSummarySheets();

        try {
            SummarySheet summarySheet = sumArr.get(0);
            SummarySheet summarySheet1 = ssm.recreateSummarySheet(summarySheet);
            System.out.println(summarySheet1.getTasks().size());
        } catch (Exception e) {
            System.out.println("Exception " + e);
        }
    }

}
