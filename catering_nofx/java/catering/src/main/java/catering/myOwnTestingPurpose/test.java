package catering.myOwnTestingPurpose;

import catering.businesslogic.CatERing;
import catering.businesslogic.event.EventInfo;
import catering.businesslogic.event.ServiceInfo;
import catering.businesslogic.kitchen.KitchenManager;
import catering.businesslogic.kitchen.SummarySheet;
import catering.businesslogic.kitchen.Task;
import catering.businesslogic.menu.Menu;
import catering.businesslogic.menu.MenuItem;
import catering.businesslogic.recipe.KitchenActivity;
import catering.businesslogic.recipe.Recipe;
import catering.businesslogic.shift.KitchenShift;
import catering.businesslogic.user.User;

import java.util.ArrayList;

public class test {
    public static void main(String[] args) {
//        CatERing.getInstance().getUserManager().fakeLogin("Lidia");
//        KitchenManager ssm = CatERing.getInstance().getKitchenManager();
//        ArrayList<EventInfo> eventInfos = CatERing.getInstance().getEventManager().getEventInfo();
//        EventInfo eventInfo = eventInfos.get(0);
//        ArrayList<ServiceInfo> serviceInfos = eventInfo.getServices();
//        ServiceInfo serviceInfo = serviceInfos.get(2);
//        ArrayList<MenuItem> menuItems = serviceInfo.getApprovedMenu().getFreeItems();


//        for(MenuItem mi: menuItems) {
//            System.out.println(mi.getItemRecipe());
//        }

        // They're fine
        ArrayList<Menu> menus = Menu.loadAllMenus();
        ArrayList<KitchenActivity> items = null;
        Menu tempMenu = null;
        for (Menu m : menus) {
            if (m.getID() == 82) {
                items = new ArrayList<>(m.getMenuItems()); // Copy the list to avoid referencing issues
                tempMenu = m;
                break; // Exit loop once the menu is found
            }
        }

        if (items != null && !items.isEmpty()) {
            System.out.println("1. " + items);
        } else {
            System.out.println("Items list is empty or null");
        }

        if (tempMenu != null) {
            System.out.println(tempMenu.getMenuItems());
        } else {
            System.out.println("Temp menu is null");
        }

//        try {
//            //SummarySheet sh = ssm.createSummarySheet(serviceInfo, eventInfo);
//            ArrayList<SummarySheet> summarySheets = SummarySheet.loadAllSummarySheets();
//            SummarySheet summarySheet = summarySheets.get(0);
//            System.out.println("Summary sheet after creation: " + summarySheet);
//            ArrayList<Task> summarySheetTasks = summarySheet.getTasks();
//            for(Task task: summarySheetTasks){
//                System.out.println("Task: " + task);
//            }
//        } catch (Exception e) {
//            System.out.println(e);
//        }

//        User user = CatERing.getInstance().getUserManager().getCurrentUser();
//        SummarySheet summarySheet = new SummarySheet(user, serviceInfo);
//        System.out.println(summarySheet.getTasks());


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
