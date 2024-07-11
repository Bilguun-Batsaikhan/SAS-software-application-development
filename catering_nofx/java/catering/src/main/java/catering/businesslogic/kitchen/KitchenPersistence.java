package catering.businesslogic.kitchen;

import catering.businesslogic.event.ServiceInfo;

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

    @Override
    public void updateTaskAssigned(SummarySheet s, Task t, boolean changeCook) {
        Task.saveUpdateTask(t,changeCook);
    }

    @Override
    public void updateTaskModify(SummarySheet s, Task t) {
        Task.saveUpdateByModifyTask(t);
    }
    @Override
    public void updateTaskRemoveAssign(SummarySheet currentSummarySheet, Task task) {
        Task.saveRemoveAssignTask(task);
    }
    @Override
    public  void updateCompleteTask(SummarySheet currentSummarySheet, Task task){
        Task.saveCompleteTask(task);
    }
    @Override
    public void updateRemoveTask(SummarySheet currentSummarySheet, Task task){
        Task.saveRemoveTask(task);
    }
    @Override
    public void updateSummaryRecreate(SummarySheet oldSummarySheet, SummarySheet newSummarySheet) {
//        ServiceInfo.saveRemoveServiceBySumId(oldSummarySheet.getId());
        SummarySheet.saveRecreateSummarySheet(oldSummarySheet,newSummarySheet);
    }
}
