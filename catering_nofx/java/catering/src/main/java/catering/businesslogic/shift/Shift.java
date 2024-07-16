package catering.businesslogic.shift;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;

public abstract class Shift {
    Date date;
    Time time;
    String place;
    Boolean recurring;
    Date expirationDate;

    public abstract boolean hasGroup();

}
