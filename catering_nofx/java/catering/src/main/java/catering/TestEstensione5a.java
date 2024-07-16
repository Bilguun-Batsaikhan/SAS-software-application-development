package catering;

import catering.businesslogic.CatERing;
import catering.businesslogic.event.EventInfo;
import catering.businesslogic.event.ServiceInfo;
import catering.businesslogic.kitchen.KitchenManager;
import catering.businesslogic.kitchen.SummarySheet;
import catering.businesslogic.kitchen.Task;
import catering.businesslogic.recipe.Recipe;
import catering.businesslogic.shift.KitchenShift;

import java.util.ArrayList;

public class TestEstensione5a {
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
            System.out.println(first +" task before assign without cook");
            //User users = User.loadUser() //TODO: some name or id
            //5.a
            ssm.assignTaskWithoutCook(first, shiftBoard.get(0), 20, 0, 25);
            System.out.println(first +" task after assign without cook");
        } catch (Exception e) {
            System.out.println("Exception at createSummarySheet: " + e);
        }
    }
}
