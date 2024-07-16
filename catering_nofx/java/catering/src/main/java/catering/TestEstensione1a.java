package catering;

import catering.businesslogic.CatERing;
import catering.businesslogic.event.EventInfo;
import catering.businesslogic.event.ServiceInfo;
import catering.businesslogic.kitchen.KitchenManager;
import catering.businesslogic.kitchen.SummarySheet;
import catering.businesslogic.kitchen.Task;
import catering.businesslogic.recipe.Recipe;
import catering.businesslogic.shift.KitchenShift;
import catering.businesslogic.user.User;

import java.util.ArrayList;

public class TestEstensione1a {
    public static void main(String[] args) {

        System.out.println("This is a test class for the SummarySheet class");
        CatERing.getInstance().getUserManager().fakeLogin("Lidia");
        KitchenManager ssm = CatERing.getInstance().getKitchenManager();
        try {
            //1.a
            ArrayList<SummarySheet> sumArr = SummarySheet.loadAllSummarySheets();
            SummarySheet sh = ssm.chooseSummarySheet(sumArr.get(0));

            ArrayList<Recipe> recipes = Recipe.loadAllRecipes();
            ssm.addTask(recipes.get(0));

            System.out.println("(1.a) Summary Choose for modify: \n" + ssm.getCurrentSummarySheet());
            //4 2/3 are opt operation
            ArrayList<KitchenShift> shiftBoard = ssm.getShiftBoard();
            Task first = sh.getTasks().get(0); //87
            System.out.println("(5)First task before modify: \n" + first);
            User user = User.loadUser("Marinella");
            //5.b
            ssm.modifyTask(first, shiftBoard.get(1), user, 50, 5, 70);
            System.out.println("First task after modify: \n" + first);
            Task second = sh.getTasks().get(2);
            System.out.println("Second task before modify: \n" + second);
            //User users = User.loadUser() //TODO: some name or id
            ssm.assignTask(second, shiftBoard.get(0), user, 0, 30, 30);
            System.out.println("Second task after modify: \n" + second);
            //some print
        } catch (Exception e) {
            System.out.println("Exception at createSummarySheet: " + e);
        }

    }
}
