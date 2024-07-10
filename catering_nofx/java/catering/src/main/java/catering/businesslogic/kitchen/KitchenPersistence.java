package catering.businesslogic.kitchen;

public class KitchenPersistence implements SummaryEventReciever {
        @Override
        public void updateSummaryCreated(SummarySheet s) {
            SummarySheet.saveKitchenSummary(s);
        }

        @Override
        public void updateTaskCreated(SummarySheet s, Task t) {
            Task.saveKitchenTask(s);
        }
}
