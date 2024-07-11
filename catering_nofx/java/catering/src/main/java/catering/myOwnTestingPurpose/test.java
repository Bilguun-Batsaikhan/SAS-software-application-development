package catering.myOwnTestingPurpose;

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

public class test {
    public static void main(String[] args) {
        CatERing.getInstance().getUserManager().fakeLogin("Lidia");
        ArrayList<SummarySheet> summarySheets = SummarySheet.loadAllSummarySheets();
        SummarySheet oldS = summarySheets.get(0);
        try {
            CatERing.getInstance().getKitchenManager().recreateSummarySheet(oldS);
        } catch (Exception e) {
            System.out.println(e);
        }


        //SummarySheet summarySheet = summarySheets.get(0);
        //ArrayList<Task> tasks = summarySheet.getTasks();

//        User user = User.loadUserById(5);
//        KitchenShift kitchenShift = KitchenShift.loadKitchenShiftById(1);
//CatERing.getInstance().getUserManager().fakeLogin("Lidia");
//        KitchenManager km = CatERing.getInstance().getKitchenManager();
//        ArrayList<EventInfo> eventInfos = EventInfo.loadAllEventInfo();
//        for(EventInfo eventInfo: eventInfos){
//            ArrayList<ServiceInfo> serviceInfos = eventInfo.getServices();
//            for(ServiceInfo serviceInfo: serviceInfos) {
//                try {
//                    SummarySheet summarySheet = km.createSummarySheet(serviceInfo, eventInfo);
//                    ArrayList<Recipe> recipes = Recipe.loadAllRecipes();
//                    for(Recipe recipe: recipes){
//                        summarySheet.addTask(recipe);
//                    }
//                } catch (Exception e) {
//                    System.out.println(e);
//                }
//            }
//        }



    }
}
