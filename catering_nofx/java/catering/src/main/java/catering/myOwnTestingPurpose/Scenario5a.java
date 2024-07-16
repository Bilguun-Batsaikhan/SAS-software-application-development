package catering.myOwnTestingPurpose;

import catering.businesslogic.CatERing;
import catering.businesslogic.kitchen.KitchenManager;
import catering.businesslogic.kitchen.SummarySheet;
import catering.businesslogic.kitchen.Task;
import catering.businesslogic.shift.KitchenShift;
import catering.businesslogic.user.User;

import java.util.ArrayList;

public class Scenario5a {
    public static void main(String[] args) {
        CatERing.getInstance().getUserManager().fakeLogin("Lidia");
        KitchenManager ssm = CatERing.getInstance().getKitchenManager();
        ArrayList<SummarySheet> summarySheets = SummarySheet.loadAllSummarySheets();
        ArrayList<KitchenShift> shiftBoard = ssm.getShiftBoard();
        try {
            ssm.chooseSummarySheet(summarySheets.get(0));
            SummarySheet sh = ssm.getCurrentSummarySheet();
            ArrayList<Task> tasks = sh.getTasks();

            Task first = sh.getTasks().get(1);
            System.out.println("(5)First task before assigned: \n"+ first);

            ssm.assignTaskWithoutCook( first, shiftBoard.get(0) , 50, 3, 25);
            System.out.println("(5)First task after assigned: \n"+ first);
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }
}
