package catering.myOwnTestingPurpose;

import catering.businesslogic.CatERing;
import catering.businesslogic.event.EventInfo;
import catering.businesslogic.event.ServiceInfo;
import catering.businesslogic.kitchen.KitchenManager;
import catering.businesslogic.kitchen.SummarySheet;
import catering.businesslogic.recipe.KitchenActivity;
import catering.businesslogic.recipe.Recipe;

public class CreateSummarySheetCheck {
    SummarySheet s;
    public static void main(String[] args) {
        CreateSummarySheetCheck c = new CreateSummarySheetCheck();
        System.out.println("This is a test class for the SummarySheet class");
        CatERing.getInstance().getUserManager().fakeLogin("Lidia");
        System.out.println(CatERing.getInstance().getUserManager().getCurrentUser());
        KitchenManager ssm = new KitchenManager();
        try {
            SummarySheet s = ssm.createSummarySheet(new ServiceInfo("Test"), new EventInfo("Test"));
            c.setSummarySheet(s);
            System.out.println(s);

            for(int i = 0; i < 5; i++) {
                s.addTask(new Recipe("Recipe_" + i));
            }
            //----------------------------------------------
            SummarySheet s1 = ssm.createSummarySheet(new ServiceInfo("Test_1"), new EventInfo("Test_2"));
            System.out.println(s1);
            System.out.println("Current Summary Sheet: " + ssm.getCurrentSummarySheet());
            System.out.println("Choose Summary Sheet: " + ssm.chooseSummarySheet(s));
            System.out.println("Current Summary Sheet: " + ssm.getCurrentSummarySheet());
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
    }
    public void setSummarySheet(SummarySheet s) {
        this.s = s;
    }

    public SummarySheet getS() {
        return s;
    }
}
