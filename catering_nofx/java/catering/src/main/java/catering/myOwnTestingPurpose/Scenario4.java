package catering.myOwnTestingPurpose;

import catering.businesslogic.CatERing;
import catering.businesslogic.kitchen.KitchenManager;
import catering.businesslogic.shift.KitchenShift;

import java.util.ArrayList;

public class Scenario4 {
    public static void main(String[] args) {
        KitchenManager ssm = CatERing.getInstance().getKitchenManager();
        System.out.println("(4) List of shift: ");
        ArrayList<KitchenShift> shiftBoard = ssm.getShiftBoard();
        for(KitchenShift k: shiftBoard)
        {
            System.out.println(k+"\n");
        }
    }
}
