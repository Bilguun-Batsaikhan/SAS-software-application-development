package catering.myOwnTestingPurpose;

import catering.businesslogic.CatERing;
import catering.businesslogic.event.EventInfo;
import catering.businesslogic.event.ServiceInfo;

import java.util.ArrayList;

public class ServiceCheck {
    public static void main(String args[]) {
        ArrayList<ServiceInfo> si = new ServiceCheck().serviceLoadTest();
        for (ServiceInfo s : si) {
            System.out.println(s);
        }
    }

    EventInfo e = new EventInfo("Event1");

    public ArrayList<ServiceInfo> serviceLoadTest() {
        CatERing.getInstance().getUserManager().fakeLogin("Lidia");
        //System.out.println(CatERing.getInstance().getUserManager().getCurrentUser());

        e.setID(1);
        ArrayList<ServiceInfo> si = e.getServices();
        si = ServiceInfo.loadServiceInfoForEvent(1);

        //System.out.println(si);
        return si;
    }

    public EventInfo getEvent() {
        return e;
    }
}
