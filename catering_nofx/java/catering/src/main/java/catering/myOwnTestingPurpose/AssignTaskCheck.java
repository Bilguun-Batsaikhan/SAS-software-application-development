package catering.myOwnTestingPurpose;

import catering.businesslogic.CatERing;
import catering.businesslogic.event.EventInfo;
import catering.businesslogic.event.ServiceInfo;
import catering.businesslogic.kitchen.KitchenManager;
import catering.businesslogic.kitchen.SummarySheet;
import catering.businesslogic.user.User;
import catering.businesslogic.user.UserManager;

import java.util.ArrayList;

public class AssignTaskCheck {
    public static void main(String[] args) {
        UserManager usrm = CatERing.getInstance().getUserManager();
        usrm.fakeLogin("Lidia");
        User currentUser = usrm.getCurrentUser();

        ArrayList<EventInfo> eventInfos = CatERing.getInstance().getEventManager().getEventInfo();
        EventInfo eventInfo = eventInfos.get(0);
        KitchenManager km = CatERing.getInstance().getKitchenManager();
        ArrayList<ServiceInfo> serviceInfos = eventInfo.getServices();
        try {
            SummarySheet sh = km.createSummarySheet(serviceInfos.get(2), eventInfo);
            //sh.saveKitchenTask(sh);
            sh.getTasks().get(0).getActivity().getId();
        } catch (Exception e) {
            System.out.println("Exception at createSummarySheet: " + e);
        }
    }
}
