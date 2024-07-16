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

public class TestScenarioPrincipale {
    public static void main(String[] args) {

        System.out.println("Success scenario for the SummarySheet class");
        CatERing.getInstance().getUserManager().fakeLogin("Lidia");

        // Load all events
        ArrayList<EventInfo> eventInfos = CatERing.getInstance().getEventManager().getEventInfo();
        EventInfo eventInfo = eventInfos.get(0);
        System.out.println("EventInfo: " + eventInfo); //id = 1
        KitchenManager ssm = CatERing.getInstance().getKitchenManager();
        /*id, event_id, name, proposed_menu_id, approved_menu_id, service_date, ts, te, expected_guests, sum_id, assigned_chef_id
          2,  1,Coffee break mattino,0,80,2020-09-25,10:30:00,11:30:00,                 |100,,2
          3,  1,Colazione di lavoro,0,0,2020-09-25,13:00:00,14:00:00,                   |80,,
          4,  1,Coffee break pomeriggio,0,82,2020-09-25,16:00:00,16:30:00,              |100,,2
          5,  1,Cena sociale,0,0,2020-09-25,20:00:00,22:30:00,                          |40,,
          */
        ArrayList<ServiceInfo> serviceInfos = eventInfo.getServices();

        try {
            //1
            /*4,  1,Coffee break pomeriggio,0,82,2020-09-25,16:00:00,16:30:00,              |100,,2*/
            SummarySheet sh = ssm.createSummarySheet(serviceInfos.get(2), eventInfo);
            System.out.println("(1) Summary sheet after creation: " + sh);
            System.out.println("\nAnd it contains: ");
            int count = 0;
            for(Task sumT: sh.getTasks())
            {
                System.out.println("Task n:"+count+"  "+ sumT);
                count++;
            }
            //2
            ArrayList<Recipe> recipes = Recipe.loadAllRecipes();
            for(Recipe r: recipes) {
                ssm.addTask(r);
            }
            System.out.println("(2) summary sheet after adding some task: ");
            int countAfterAdding = 0;
            for(Task sumT: sh.getTasks())
            {
                System.out.println("Task n:"+countAfterAdding+"  "+ sumT);
                countAfterAdding++;
            }
            //3
            ssm.sortSummarySheet("difficulty", null, null);
            System.out.println("(3) summary sheet after sorting task: ");
            int countAfterSort = 0;
            for(Task sumT: sh.getTasks())
            {
                System.out.println("Task n:"+countAfterSort+"  "+ sumT);
                countAfterSort++;
            }
            //4
            System.out.println("(4) List of shift: ");
            ArrayList<KitchenShift> shiftBoard = ssm.getShiftBoard();
            for(KitchenShift k: shiftBoard)
            {
                System.out.println(k+"\n");
            }
            //5
            Task first = sh.getTasks().get(0);
            System.out.println("(5)First task before assigned: \n"+ first);
            User user1 = User.loadUser("Marinella");
            ssm.assignTask( first, shiftBoard.get(0),user1 , 20, 0, 25);
            System.out.println("First task after assigned: \n"+ first);
            Task second = sh.getTasks().get(1); //93
            System.out.println("(5)Second task before assigned: \n"+ second);
            User user2 = User.loadUserById(6);
            ssm.assignTask(second, shiftBoard.get(1), user2 , 0, 20, 10);
            System.out.println("Second task after assigned: \n"+ second);
            //some print
        } catch (Exception e) {
            System.out.println("Exception at createSummarySheet: " + e);
        }
    }
}
