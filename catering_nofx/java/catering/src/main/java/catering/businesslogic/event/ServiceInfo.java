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
import java.util.HashMap;
import java.util.Map;

public class ServiceInfo implements EventItemInfo {
    private static Map<Integer, ServiceInfo> loadedServices = new HashMap<Integer, ServiceInfo>();

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
    private Menu approvedMenu = null;
    private int sumsheet_id = -1;

    public int getSumsheet_id() {
        return sumsheet_id;
    }

    public ServiceInfo(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public int getAssignedChefID() {
        return assignedChefID;
    }

    public String toString() {
        return "ID: " + this.id + " "+ name + ": " + date + " (" + timeStart + "-" + timeEnd + "), \n" + "participants: " + participants + " pp. \n" + "Assigned chef: " + assignedChef + "\n" + "Menu: " + approvedMenu + ".\n";
    }

    public boolean hasMenu() {
        return this.approvedMenu != null;
    }

    public boolean chefAssigned() {
        return this.assignedChef != null;
    }

    //TODO: implement getKitchenActivities
    public ArrayList<KitchenActivity> getKitchenActivities() {
        System.out.println(approvedMenu.getID());
        return this.approvedMenu.getMenuItems();
        //return new ArrayList<>();
    }



    public static void saveSummaryId(int id, int service_id)
    {
        String upd = "UPDATE services SET sumsheet_id = " + id +
                " WHERE id = " + service_id;
        PersistenceManager.executeUpdate(upd);
    }
    /*
    *     public static void saveRemoveTask(Task t) {
        String rem = "DELETE FROM kitchentask WHERE id = " + t.getId();
        System.out.println(t.getId());
        PersistenceManager.executeUpdate(rem);
    }*/
//    public static void saveRemoveServiceBySumId(int id) {
//        String rem = "DELETE FROM services WHERE sumsheet_id = " + id;
//        PersistenceManager.executeUpdate(rem);
//    }

    // STATIC METHODS FOR PERSISTENCE
    // This method is used to load all the services for a given event from the database.
    public static ArrayList<ServiceInfo> loadServiceInfoForEvent(int event_id) {
        ArrayList<ServiceInfo> result = new ArrayList<>();
        String query = "SELECT id, name, approved_menu_id, service_date, time_start, time_end, expected_participants, sumsheet_id, assigned_chef_id FROM Services WHERE event_id = " + event_id;
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
                serv.sumsheet_id = rs.getInt("sumsheet_id");
                result.add(serv);
            }
        });
        for(ServiceInfo s: result) {
            s.assignedChef = User.loadUserById(s.assignedChefID);
            loadedServices.put(s.id, s);
            ArrayList<Menu> menus = Menu.loadAllMenus();
            //TODO: something is wrong here
            for(Menu m: menus) {
                if(m.getId() == s.approvedMenuID) {
                    s.approvedMenu = m;
                    break;
                    //.println(m.getMenuItems());
                }
            }
        }
        return result;
    }

    public Menu getApprovedMenu() {return approvedMenu;}

    public static ServiceInfo loadServiceBySumSheetId(int sumsheet_id) {
        if(loadedServices.containsKey(sumsheet_id)) return loadedServices.get(sumsheet_id);
        ServiceInfo load = new ServiceInfo("");
        //we don't have sumsheet id in services table!!!
        String userQuery = "SELECT * FROM services WHERE sumsheet_id='"+sumsheet_id+"'";
        PersistenceManager.executeQuery(userQuery, new ResultHandler() {
            @Override
            public void handle(ResultSet rs) throws SQLException {
                load.id = rs.getInt("id");
                load.name = rs.getString("name");
                load.date = rs.getDate("service_date");
                load.timeStart = rs.getTime("time_start");
                load.timeEnd = rs.getTime("time_end");
                load.participants = rs.getInt("expected_participants");
                load.assignedChefID = rs.getInt("assigned_chef_id");
                load.approvedMenuID = rs.getInt("approved_menu_id");
            }
        });
        load.assignedChef = User.loadUserById(load.assignedChefID);
        ArrayList<Menu> menus = Menu.loadAllMenus();
        for(Menu m: menus) {
            if(m.getId() == load.approvedMenuID) {
                load.approvedMenu = m;
            }
        }
        loadedServices.put(load.id, load);
        return load;
    }
}
