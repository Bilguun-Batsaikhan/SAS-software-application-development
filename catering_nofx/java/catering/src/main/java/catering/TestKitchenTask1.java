package catering;

import catering.businesslogic.CatERing;
import catering.businesslogic.event.EventInfo;
import catering.businesslogic.event.ServiceInfo;
import catering.businesslogic.kitchen.SummarySheet;

import java.util.ArrayList;

public class TestKitchenTask1 {
    public static void main(String[] args) {
        System.out.println("TestKitchenTask1\n");
        try {
            System.out.println("TEST FAKE LOGIN");
            CatERing.getInstance().getUserManager().fakeLogin("Lidia");
            System.out.println(CatERing.getInstance().getUserManager().getCurrentUser());

            System.out.println("\nTEST GET EVENT INFO");
            ArrayList<EventInfo> eventInfos = CatERing.getInstance().getEventManager().getEventInfo();
            EventInfo eventInfo = eventInfos.get(0);
            System.out.println("EventInfo: " + eventInfo);

            ArrayList<ServiceInfo> serviceInfos = eventInfo.getServices();

            System.out.println("TEST CREATE SUMMARY SHEET");
            //serviceInfos.get(2) approved menu_id = 82
            SummarySheet summarySheet = CatERing.getInstance().getKitchenManager().createSummarySheet(serviceInfos.get(2), eventInfo);
            System.out.println("SummarySheet: " + summarySheet);


        } catch (Exception e) {
            System.out.println("Exception at createSummarySheet: " + e);
        }
    }
}
