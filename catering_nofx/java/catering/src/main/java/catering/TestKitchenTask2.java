package catering;

import catering.businesslogic.CatERing;
import catering.businesslogic.event.EventInfo;
import catering.businesslogic.event.ServiceInfo;
import catering.businesslogic.kitchen.SummarySheet;
import catering.businesslogic.recipe.Recipe;

import java.util.ArrayList;

public class TestKitchenTask2 {
    public static void main(String[] args) {
        System.out.println("TestKitchenTask2\n");
        try {
            System.out.println("TEST FAKE LOGIN");
            CatERing.getInstance().getUserManager().fakeLogin("Lidia");
            System.out.println(CatERing.getInstance().getUserManager().getCurrentUser());

            System.out.println("\nTEST GET EVENT INFO");
            ArrayList<EventInfo> eventInfos = CatERing.getInstance().getEventManager().getEventInfo();
            EventInfo eventInfo = eventInfos.get(0);
            System.out.println("EventInfo: " + eventInfo);

            ArrayList<ServiceInfo> serviceInfos = eventInfo.getServices();

            System.out.println("CREATE SUMMARY SHEET 1");
            //serviceInfos.get(2) approved menu_id = 82
            CatERing.getInstance().getKitchenManager().createSummarySheet(serviceInfos.get(2), eventInfo);
            SummarySheet currentSummarySheet = CatERing.getInstance().getKitchenManager().getCurrentSummarySheet();
            System.out.println("Current summary sheet: " + currentSummarySheet);

            System.out.println("Add a task to the current summary sheet");

            ArrayList<Recipe> recipes = new ArrayList<>();
            recipes = Recipe.loadAllRecipes();
            CatERing.getInstance().getKitchenManager().addTask(recipes.get(0));
            System.out.println("The current SUMMARY SHEET AFTER ADDING A TASK:\n" + currentSummarySheet);

        } catch (Exception e) {
            System.out.println("Errore di logica nello use case");
        }
    }
}
