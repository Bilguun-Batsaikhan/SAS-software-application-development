package catering.businesslogic.shift;

import java.sql.Date;
import java.util.ArrayList;

public class GroupShift {
    Date date;
    boolean recurring;
    ArrayList<KitchenShift> shifts; //TODO: Initialize shifts

    public ArrayList<KitchenShift> getShifts() {
        return shifts;
    }

    /*
    *
    * +create(shifts:ArrayList<Shift>, ?frequency:int, expirationDay:Date, ?LGoO: ListShiftOccurrences): gr
    * +getShifts(): ArrayList<Shift>
    * */
}
