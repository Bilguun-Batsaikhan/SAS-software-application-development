package catering.myOwnTestingPurpose;

import catering.businesslogic.CatERing;
import catering.businesslogic.kitchen.KitchenManager;
import catering.businesslogic.kitchen.SummarySheet;
import catering.businesslogic.kitchen.Task;
import catering.businesslogic.shift.KitchenShift;
import catering.businesslogic.user.User;

import java.util.ArrayList;

public class Scenario5b {
    public static void main(String[] ars) {
        System.out.println("This is a test class for the SummarySheet class");
        CatERing.getInstance().getUserManager().fakeLogin("Lidia");
        ArrayList<SummarySheet> summarySheets = SummarySheet.loadAllSummarySheets();
        KitchenManager ssm = CatERing.getInstance().getKitchenManager();
        ArrayList<KitchenShift> shiftBoard = ssm.getShiftBoard();
        try {
            ssm.chooseSummarySheet(summarySheets.get(0));
            SummarySheet sh = ssm.getCurrentSummarySheet();
            ArrayList<Task> tasks = sh.getTasks();

            Task first = tasks.get(1);
            User user = User.loadUserById(6);
            //5.b
            // when user_id is null in DB and when try to modify the task with null user, it gives foreign key constraint error
            ssm.modifyTask(first, shiftBoard.get(2), user, 15, 4, 30);
            System.out.println(first +" task after modify assign");
        } catch (Exception e) {
            System.out.println("Exception at createSummarySheet: " + e);
        }
    }
}
