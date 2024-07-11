package catering.myOwnTestingPurpose;

import catering.businesslogic.shift.KitchenShift;

public class LoadKitchenShiftById2 {
    public static void main(String[] args) {
        KitchenShift ks = KitchenShift.loadKitchenShiftById(2);
        System.out.println(ks);
    }
}
