package catering.businesslogic;

import catering.businesslogic.event.EventManager;
import catering.businesslogic.kitchen.KitchenManager;
import catering.businesslogic.kitchen.KitchenPersistence;
import catering.businesslogic.kitchen.SummaryEventReciever;
import catering.businesslogic.menu.MenuManager;
import catering.businesslogic.recipe.RecipeManager;
import catering.businesslogic.shift.ShiftManager;
import catering.businesslogic.user.UserManager;
import catering.persistence.MenuPersistence;

public class CatERing {
    private static CatERing singleInstance;

    public static CatERing getInstance() {
        if (singleInstance == null) {
            singleInstance = new CatERing();
        }
        return singleInstance;
    }

    private MenuManager menuMgr;
    private RecipeManager recipeMgr;
    private UserManager userMgr;
    private EventManager eventMgr;

    // I added
    private ShiftManager shiftMgr;
    private KitchenManager kitchenMgr;
    private KitchenPersistence kitchenPersistence;

    private MenuPersistence menuPersistence;

    private CatERing() {
        menuMgr = new MenuManager();
        recipeMgr = new RecipeManager();
        userMgr = new UserManager();
        eventMgr = new EventManager();
        menuPersistence = new MenuPersistence();

        // I added
        shiftMgr = new ShiftManager();
        kitchenMgr = new KitchenManager();
        kitchenPersistence = new KitchenPersistence();
        kitchenMgr.addEventReceiver(kitchenPersistence);


        menuMgr.addEventReceiver(menuPersistence);
    }


    public MenuManager getMenuManager() {
        return menuMgr;
    }

    public RecipeManager getRecipeManager() {
        return recipeMgr;
    }

    public UserManager getUserManager() {
        return userMgr;
    }

    public EventManager getEventManager() { return eventMgr; }

    public ShiftManager getShiftManager() {
        return shiftMgr;
    }

    public KitchenManager getKitchenManager() {
        return kitchenMgr;
    }
}
