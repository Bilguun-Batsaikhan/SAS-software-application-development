package catering.myOwnTestingPurpose;

import catering.businesslogic.kitchen.SummarySheet;
import catering.businesslogic.kitchen.Task;
import catering.businesslogic.recipe.Recipe;

import java.util.ArrayList;

public class LoadAllSummarySheets6 {
    public static void main(String[] args) {
        ArrayList<SummarySheet> summarySheets = SummarySheet.loadAllSummarySheets();
        System.out.println(summarySheets);
        ArrayList<Recipe> recipes = Recipe.loadAllRecipes();
        Recipe recipe = recipes.get(0);
        Task.saveKitchenTask(summarySheets.get(1).getId(), new Task(recipe));
    }
}
