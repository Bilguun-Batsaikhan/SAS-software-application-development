package catering.businesslogic.event;

import catering.businesslogic.recipe.KitchenActivity;
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
    //TODO: Add approved menu_id and assigned chef_id (which is not in the db yet)

    public ServiceInfo(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }


    public String toString() {
        return name + ": " + date + " (" + timeStart + "-" + timeEnd + "), " + participants + " pp.";
    }

    //TODO: Implement the following methods, this information comes from db
    public boolean hasMenu() {
        return true;
    }

    public ArrayList<KitchenActivity> getKitchenActivities() {
        return new ArrayList<>();
    }

    public boolean chefAssigned() {
        return true;
    }



    // STATIC METHODS FOR PERSISTENCE
    // This method is used to load all the services for a given event from the database.
    public static ArrayList<ServiceInfo> loadServiceInfoForEvent(int event_id) {
        ArrayList<ServiceInfo> result = new ArrayList<>();
        String query = "SELECT id, name, service_date, time_start, time_end, expected_participants " +
                "FROM Services WHERE event_id = " + event_id;
        PersistenceManager.executeQuery(query, new ResultHandler() {
            @Override
            public void handle(ResultSet rs) throws SQLException {
                String s = rs.getString("name");
                ServiceInfo serv = new ServiceInfo(s);
                serv.id = rs.getInt("id");
                serv.date = rs.getDate("service_date");
                serv.timeStart = rs.getTime("time_start");
                serv.timeEnd = rs.getTime("time_end");
                serv.participants = rs.getInt("expected_participants");
                result.add(serv);
            }
        });

        return result;
    }
}
