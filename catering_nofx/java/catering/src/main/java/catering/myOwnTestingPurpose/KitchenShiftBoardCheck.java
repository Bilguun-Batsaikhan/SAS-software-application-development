package catering.myOwnTestingPurpose;

import catering.businesslogic.shift.KitchenShift;

import java.util.ArrayList;

public class KitchenShiftBoardCheck {
    public static void main(String[] args) {
        ArrayList<KitchenShift> kitchenShifts = KitchenShift.loadAllKitchenShift();
        for (KitchenShift ks : kitchenShifts) {
            System.out.println(ks);
        }
    }
}
