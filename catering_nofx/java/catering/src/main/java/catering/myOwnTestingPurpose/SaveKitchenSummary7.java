package catering.myOwnTestingPurpose;

import catering.businesslogic.CatERing;
import catering.businesslogic.event.EventInfo;
import catering.businesslogic.event.ServiceInfo;
import catering.businesslogic.kitchen.KitchenManager;
import catering.businesslogic.kitchen.SummarySheet;

import java.util.ArrayList;

public class SaveKitchenSummary7 {
    public static void main(String[] args) {
        CatERing.getInstance().getUserManager().fakeLogin("Lidia");
        ArrayList<EventInfo> eventInfos = CatERing.getInstance().getEventManager().getEventInfo();
        EventInfo eventInfo = eventInfos.get(0);
        ArrayList<ServiceInfo> serviceInfos = eventInfo.getServices();
        KitchenManager ssm = CatERing.getInstance().getKitchenManager();

        try {
            SummarySheet ss = ssm.createSummarySheet(serviceInfos.get(0), eventInfo);
        } catch (Exception e) {
            System.out.println("Exception at createSummarySheet: " + e);
        }
    }
}
