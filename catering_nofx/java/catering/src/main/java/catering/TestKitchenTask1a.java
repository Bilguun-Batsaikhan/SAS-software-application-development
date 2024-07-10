package catering;

import catering.businesslogic.CatERing;
import catering.businesslogic.event.EventInfo;
import catering.businesslogic.event.ServiceInfo;
import catering.businesslogic.kitchen.SummarySheet;

import java.util.ArrayList;

public class TestKitchenTask1a {
    public static void main(String[] args) {
        System.out.println("TestKitchenTask1a\n");
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
            System.out.println("Current summary sheet: " + catering.businesslogic.CatERing.getInstance().getKitchenManager().getCurrentSummarySheet());

            System.out.println("CREATE SUMMARY SHEET 2");
            //serviceInfos.get(0) approved menu_id = 80
            CatERing.getInstance().getKitchenManager().createSummarySheet(serviceInfos.get(0), eventInfo);
            System.out.println("TEST CHOOSE SUMMARY SHEET");
            CatERing.getInstance().getKitchenManager().chooseSummarySheet(CatERing.getInstance().getKitchenManager().getCurrentSummarySheet());
            System.out.println("Current summary sheet: " + catering.businesslogic.CatERing.getInstance().getKitchenManager().getCurrentSummarySheet());

        } catch (Exception e) {
            System.out.println("Errore di logica nello use case");
        }
    }
}
