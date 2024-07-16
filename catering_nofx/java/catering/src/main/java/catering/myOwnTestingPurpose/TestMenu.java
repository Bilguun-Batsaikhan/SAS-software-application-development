package catering.myOwnTestingPurpose;

import catering.businesslogic.kitchen.Task;
import catering.businesslogic.menu.Menu;
import catering.businesslogic.recipe.KitchenActivity;

import java.util.ArrayList;

public class TestMenu {
    public static void main(String[] args) {
        Menu.loadAllMenus();
        ArrayList<Menu> menus = Menu.loadAllMenus();
        ArrayList<Task> tasks = new ArrayList<>();
        for (Menu menu : menus) {
            if(menu.getID() == 82) {
                ArrayList<KitchenActivity> items = menu.getMenuItems();
                System.out.println(items);
            }
        }
//        for (Task task : tasks) {
//            System.out.println(task);
//        }
    }
}
