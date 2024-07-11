package catering.myOwnTestingPurpose;

import catering.businesslogic.recipe.KitchenActivity;

public class LoadActivityById3 {
    public static void main(String[] args) {
        KitchenActivity ka = KitchenActivity.loadActivityById(3);
        System.out.println("kitchen activity: " + ka);
    }
}
