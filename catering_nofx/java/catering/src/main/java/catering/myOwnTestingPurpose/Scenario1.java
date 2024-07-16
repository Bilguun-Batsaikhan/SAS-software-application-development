package catering.myOwnTestingPurpose;

import catering.businesslogic.CatERing;
import catering.businesslogic.event.EventInfo;
import catering.businesslogic.event.ServiceInfo;
import catering.businesslogic.kitchen.KitchenManager;
import catering.businesslogic.kitchen.SummarySheet;
import catering.businesslogic.kitchen.Task;

import java.util.ArrayList;

public class Scenario1 {
    public static void main(String[] args) {
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
            for (Task sumT : sh.getTasks()) {
                System.out.println("Task n:" + count + "  " + sumT);
                count++;
            }
        } catch (Exception e) {
            System.out.println("Exception " + e);
        }
    }
}
