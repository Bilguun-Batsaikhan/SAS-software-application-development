package catering;

import catering.businesslogic.CatERing;
import catering.businesslogic.kitchen.KitchenManager;
import catering.businesslogic.kitchen.SummarySheet;
import catering.businesslogic.kitchen.Task;
import catering.businesslogic.shift.KitchenShift;
import catering.businesslogic.user.User;

import java.util.ArrayList;

public class TestEstensione5b {
    public static void main(String ars[]) {
        System.out.println("This is a test class for the SummarySheet class");
        CatERing.getInstance().getUserManager().fakeLogin("Lidia");
        ArrayList<SummarySheet> sumArr = SummarySheet.loadAllSummarySheets();
        KitchenManager ssm = CatERing.getInstance().getKitchenManager();

        try {
            //1.a
            SummarySheet sh = ssm.chooseSummarySheet(sumArr.get(0));
            System.out.println("(1.a) Summary Choose for modify: \n" + ssm.getCurrentSummarySheet());
            //4 2/3 are opt operation
            ArrayList<KitchenShift> shiftBoard = ssm.getShiftBoard();
            Task first = sh.getTasks().get(0);
            System.out.println(first +" task before modify assign ");

            //5.b
            User user = User.loadUserById(4);
            ssm.modifyTask(first, shiftBoard.get(2), user, 10, 0, 25);
            System.out.println(first +" task after modify assign");
        } catch (Exception e) {
            System.out.println("Exception at createSummarySheet: " + e);
        }
    }
}
