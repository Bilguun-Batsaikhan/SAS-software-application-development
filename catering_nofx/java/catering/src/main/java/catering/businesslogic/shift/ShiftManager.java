package catering.businesslogic.shift;
import java.util.ArrayList;

public class ShiftManager {
    ArrayList<KitchenShift> shiftBoard; //TODO: Initialize shiftBoard

    public ShiftManager() {
        shiftBoard = KitchenShift.loadAllKitchenShift();
    }

    public ArrayList<KitchenShift> getShiftBoard() {
        return shiftBoard;
    }
}
