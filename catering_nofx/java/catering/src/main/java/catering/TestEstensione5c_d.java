package catering;

import catering.businesslogic.CatERing;
import catering.businesslogic.kitchen.KitchenManager;
import catering.businesslogic.kitchen.SummarySheet;
import catering.businesslogic.kitchen.Task;
import catering.businesslogic.shift.KitchenShift;

import java.util.ArrayList;

public class TestEstensione5c_d {
    public static void main(String ars[]) {
        System.out.println("This is a test class for the SummarySheet class");
        CatERing.getInstance().getUserManager().fakeLogin("Lidia");
        ArrayList<SummarySheet> sumArr = SummarySheet.loadAllSummarySheets();
        KitchenManager ssm = CatERing.getInstance().getKitchenManager();

        try {
            //1.a
            SummarySheet sh = ssm.chooseSummarySheet(sumArr.get(0));
            System.out.println("(1.a) Summary Choose for modify: \n" + ssm.getCurrentSummarySheet());
            System.out.println("Summary before remove task");
            for(Task inside: ssm.getCurrentSummarySheet().getTasks()){
                System.out.println("\n"+inside);
            }
            //4 2/3 are opt operation
            ArrayList<KitchenShift> shiftBoard = ssm.getShiftBoard();
            Task first = sh.getTasks().get(0);


            ssm.removeAssignment(first);
            System.out.println(first +" task after remove assign");
            ssm.removeTask(first);
            System.out.println("Summary after remove task");
           for(Task inside: ssm.getCurrentSummarySheet().getTasks()){
               System.out.println("\n"+inside);
           }
        } catch (Exception e) {
            System.out.println("Exception at createSummarySheet: " + e);
        }
    }
}
