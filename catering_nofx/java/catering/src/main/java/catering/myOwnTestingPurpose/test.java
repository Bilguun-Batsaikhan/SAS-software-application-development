package catering.myOwnTestingPurpose;

import catering.businesslogic.CatERing;
import catering.businesslogic.kitchen.KitchenManager;
import catering.businesslogic.kitchen.SummarySheet;
import catering.businesslogic.kitchen.Task;
import catering.businesslogic.recipe.Recipe;
import catering.businesslogic.shift.KitchenShift;
import catering.businesslogic.user.User;

import java.util.ArrayList;

public class test {
    public static void main(String[] args) {

        ArrayList<SummarySheet> summarySheets = SummarySheet.loadAllSummarySheets();
        SummarySheet summarySheet = summarySheets.get(0);
        ArrayList<Task> tasks = summarySheet.getTasks();

        User user = User.loadUserById(5);
        KitchenShift kitchenShift = KitchenShift.loadKitchenShiftById(1);

        KitchenManager km = CatERing.getInstance().getKitchenManager();
        ArrayList<Recipe> recipes = Recipe.loadAllRecipes();
        Recipe recipe = recipes.get(0);
        try {
            km.setCurrentSummarySheet(summarySheet);
//            km.addTask(recipe);
            Task task = summarySheet.getTasks().get(0);
            System.out.println("Task: " + task);
            km.removeAssignment(task);
            km.removeTask(task);

        } catch (Exception e) {
            System.out.println(e);
        }

    }
}
