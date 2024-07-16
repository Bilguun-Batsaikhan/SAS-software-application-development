package catering.myOwnTestingPurpose;

import catering.businesslogic.CatERing;
import catering.businesslogic.kitchen.KitchenManager;
import catering.businesslogic.kitchen.SummarySheet;
import catering.businesslogic.kitchen.Task;
import catering.businesslogic.recipe.Recipe;

import java.util.ArrayList;

public class Scenario1a {
    public static void main(String[] args) {
        CatERing.getInstance().getUserManager().fakeLogin("Lidia");
        KitchenManager ssm = CatERing.getInstance().getKitchenManager();
        ArrayList<SummarySheet> sumArr = SummarySheet.loadAllSummarySheets();
        try {
            SummarySheet sh = ssm.chooseSummarySheet(sumArr.get(0));
            ArrayList<Task> oldTasksSh = sh.getTasks();
            System.out.println("# of tasks before adding more: " + oldTasksSh.size());
            ArrayList<Recipe> recipes = Recipe.loadAllRecipes();
            for(Recipe r: recipes) {
                ssm.addTask(r);
            }
            ArrayList<Task> newTasksSh = sh.getTasks();
            System.out.println("(2) summary sheet after adding some task: " + newTasksSh.size());
//            int countAfterAdding = 0;
//            for(Task sumT: sh.getTasks())
//            {
//                System.out.println("Task n:"+countAfterAdding+"  "+ sumT);
//                countAfterAdding++;
//            }
        } catch (Exception e) {
            System.out.println("Exception " + e);
        }
    }
}
