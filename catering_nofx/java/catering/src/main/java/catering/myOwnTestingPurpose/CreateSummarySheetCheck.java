package catering.myOwnTestingPurpose;

import catering.businesslogic.CatERing;
import catering.businesslogic.event.EventInfo;
import catering.businesslogic.event.ServiceInfo;
import catering.businesslogic.kitchen.KitchenManager;
import catering.businesslogic.kitchen.SummarySheet;
import catering.businesslogic.kitchen.Task;
import catering.businesslogic.recipe.KitchenActivity;
import catering.businesslogic.recipe.Recipe;

import java.util.ArrayList;

public class CreateSummarySheetCheck {
    SummarySheet s;
    public static void main(String[] args) {
        CreateSummarySheetCheck c = new CreateSummarySheetCheck();
        System.out.println("This is a test class for the SummarySheet class");
        CatERing.getInstance().getUserManager().fakeLogin("Lidia");

        ArrayList<EventInfo> eventInfos = CatERing.getInstance().getEventManager().getEventInfo();
        EventInfo eventInfo = eventInfos.get(0);
        System.out.println("EventInfo: " + eventInfo);
        KitchenManager ssm = new KitchenManager();


        ArrayList<ServiceInfo> serviceInfos = eventInfo.getServices();
//        System.out.println("ServiceInfo: " + serviceInfos.get(0));

        try {
            SummarySheet sh = ssm.createSummarySheet(serviceInfos.get(2), eventInfo);
            ArrayList<Recipe> recipes = Recipe.loadAllRecipes();
            for(Recipe r: recipes) {
                sh.addTask(r);
            }
            System.out.println("SummarySheet: " + sh);
            Task first = sh.getTasks().get(0);
            Task second = sh.getTasks().get(1);
            sh.sort("", first, second);
            System.out.println("SummarySheet after swapping 2 tasks: " + sh);
//            sh.sort("difficulty", null, null);
//            System.out.println("SummarySheet after sorting: " + sh);
        } catch (Exception e) {
            System.out.println("Exception at createSummarySheet: " + e);
        }

    }
    public void setSummarySheet(SummarySheet s) {
        this.s = s;
    }

    public SummarySheet getS() {
        return s;
    }
}
