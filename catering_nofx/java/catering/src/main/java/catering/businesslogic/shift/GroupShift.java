package catering.businesslogic.shift;

import java.sql.Date;
import java.util.ArrayList;

public class GroupShift {
    Date date;
    boolean recurring;
    ArrayList<KitchenShift> shifts;

    public ArrayList<KitchenShift> getShifts() {
        return shifts;
    }

}
