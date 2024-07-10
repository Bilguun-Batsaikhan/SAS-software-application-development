package catering.businesslogic.event;

import catering.businesslogic.menu.Menu;
import catering.businesslogic.recipe.KitchenActivity;
import catering.businesslogic.user.User;
import catering.persistence.PersistenceManager;
import catering.persistence.ResultHandler;

import java.util.ArrayList;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;

public class ServiceInfo implements EventItemInfo {
    private int id;
    private String name;
    private Date date;
    private Time timeStart;
    private Time timeEnd;
    private int participants;

    //private String place;
    private int assignedChefID;
    private int approvedMenuID;
    private User assignedChef;
    private Menu approvedMenu;

    public ServiceInfo(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }


    public String toString() {
        return name + ": " + date + " (" + timeStart + "-" + timeEnd + "), \n" + "participants: " + participants + " pp. \n" + "Chef: " + assignedChef + "\n" + "Menu: " + approvedMenu + ".\n";
    }

    public boolean hasMenu() {
        return this.approvedMenu != null;
    }

    public boolean chefAssigned() {
        return this.assignedChef != null;
    }

    //TODO: implement getKitchenActivities
    public ArrayList<KitchenActivity> getKitchenActivities() {
        return this.approvedMenu.getMenuItems();
        //return new ArrayList<>();
    }




    // STATIC METHODS FOR PERSISTENCE
    // This method is used to load all the services for a given event from the database.
    public static ArrayList<ServiceInfo> loadServiceInfoForEvent(int event_id) {
        ArrayList<ServiceInfo> result = new ArrayList<>();
        String query = "SELECT id, name, approved_menu_id, service_date, time_start, time_end, expected_participants, assigned_chef_id FROM Services WHERE event_id = " + event_id;
        PersistenceManager.executeQuery(query, new ResultHandler() {
            @Override
            public void handle(ResultSet rs) throws SQLException {
                String s = rs.getString("name");
                ServiceInfo serv = new ServiceInfo(s);
                serv.id = rs.getInt("id");
                serv.approvedMenuID = rs.getInt("approved_menu_id");
                serv.date = rs.getDate("service_date");
                serv.timeStart = rs.getTime("time_start");
                serv.timeEnd = rs.getTime("time_end");
                serv.participants = rs.getInt("expected_participants");
                serv.assignedChefID = rs.getInt("assigned_chef_id");
                result.add(serv);
            }
        });
        for(ServiceInfo s: result) {
            s.assignedChef = User.loadUserById(s.assignedChefID);
            ArrayList<Menu> menus = Menu.loadAllMenus();
            for(Menu m: menus) {
                if(m.getId() == s.approvedMenuID) {
                    s.approvedMenu = m;
                    //System.out.println(m.getMenuItems());
                }
            }
        }
        return result;
    }
}
