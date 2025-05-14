package com.agrohelper.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.lifecycle.Observer;
import androidx.room.Room;

import com.agrohelper.database.AppDatabase;
import com.agrohelper.models.Plant;
import com.agrohelper.models.Task;
import com.agrohelper.utils.NotificationHelper;

import java.util.List;

public class BootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            // Reschedule all pending notifications after device reboot
            AppDatabase database = Room.databaseBuilder(
                    context.getApplicationContext(),
                    AppDatabase.class,
                    "agrohelper_db"
            ).build();
            
            NotificationHelper notificationHelper = new NotificationHelper(context);
            
            // Get all pending tasks
            database.taskDao().getPendingTasks().observeForever(new Observer<List<Task>>() {
                @Override
                public void onChanged(List<Task> tasks) {
                    if (tasks != null) {
                        for (Task task : tasks) {
                            // Get the plant for each task
                            database.plantDao().getPlantById(task.getPlantId()).observeForever(new Observer<Plant>() {
                                @Override
                                public void onChanged(Plant plant) {
                                    if (plant != null) {
                                        // Reschedule notification
                                        notificationHelper.scheduleTaskNotification(task, plant);
                                    }
                                    
                                    // Remove observer after use
                                    database.plantDao().getPlantById(task.getPlantId()).removeObserver(this);
                                }
                            });
                        }
                    }
                    
                    // Remove observer after use
                    database.taskDao().getPendingTasks().removeObserver(this);
                }
            });
        }
    }
}
