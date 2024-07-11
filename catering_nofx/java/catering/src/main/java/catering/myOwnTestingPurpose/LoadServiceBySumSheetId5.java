package catering.myOwnTestingPurpose;

import catering.businesslogic.event.ServiceInfo;

public class LoadServiceBySumSheetId5 {
    public static void main(String[] args) {
        ServiceInfo s = ServiceInfo.loadServiceBySumSheetId(5);
        System.out.println(s);
    }
}
