package catering.businesslogic.kitchen;

import java.util.ArrayList;

public class KitchenPersistence implements SummaryEventReciever {
        @Override
        public void updateSummaryCreated(SummarySheet s) {
            SummarySheet.saveKitchenSummary(s);
            Task.saveKitchenTasks(s);
        }

        @Override
        public void updateTaskCreated(SummarySheet s, Task t) {
            Task.saveKitchenTask(s.getId(), t);
        }
}
