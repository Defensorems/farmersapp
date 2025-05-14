package com.agrohelper.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.agrohelper.R;
import com.agrohelper.models.Task;
import com.agrohelper.viewmodels.PlantViewModel;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {
    private final Context context;
    private List<Task> tasks;
    private final SimpleDateFormat dateFormat;
    private final PlantViewModel plantViewModel;

    public TaskAdapter(Context context) {
        this.context = context;
        this.dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault());
        this.plantViewModel = new ViewModelProvider((ViewModelStoreOwner) context).get(PlantViewModel.class);
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_task, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        if (tasks != null && position < tasks.size()) {
            Task task = tasks.get(position);
            
            // Set task type
            String taskTypeText = "";
            switch (task.getTaskType()) {
                case WATER:
                    taskTypeText = context.getString(R.string.task_water);
                    break;
                case FERTILIZE:
                    taskTypeText = context.getString(R.string.task_fertilize);
                    break;
                case HARVEST:
                    taskTypeText = context.getString(R.string.task_harvest);
                    break;
            }
            holder.taskTypeTextView.setText(taskTypeText);
            
            // Set task date
            if (task.getDate() != null) {
                holder.taskDateTextView.setText(dateFormat.format(task.getDate()));
            }
            
            // Set task completion status
            holder.taskCheckBox.setChecked(task.isDone());
            
            // Set checkbox listener
            holder.taskCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                task.setDone(isChecked);
                plantViewModel.updateTask(task);
            });
            
            // Set delete button listener
            holder.deleteButton.setOnClickListener(v -> {
                plantViewModel.deleteTask(task);
            });
        }
    }

    @Override
    public int getItemCount() {
        return tasks != null ? tasks.size() : 0;
    }

    static class TaskViewHolder extends RecyclerView.ViewHolder {
        final TextView taskTypeTextView;
        final TextView taskDateTextView;
        final CheckBox taskCheckBox;
        final ImageButton deleteButton;

        TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            taskTypeTextView = itemView.findViewById(R.id.task_type);
            taskDateTextView = itemView.findViewById(R.id.task_date);
            taskCheckBox = itemView.findViewById(R.id.task_checkbox);
            deleteButton = itemView.findViewById(R.id.delete_button);
        }
    }
}
