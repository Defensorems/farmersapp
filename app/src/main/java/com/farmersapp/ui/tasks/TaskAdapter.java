package com.farmersapp.ui.tasks;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.farmersapp.R;
import com.farmersapp.data.model.Task;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class TaskAdapter extends ListAdapter<Task, TaskAdapter.TaskViewHolder> {

    private final OnTaskClickListener listener;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());

    public TaskAdapter(DiffUtil.ItemCallback<Task> diffCallback, OnTaskClickListener listener) {
        super(diffCallback);
        this.listener = listener;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_task, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task task = getItem(position);
        holder.bind(task, listener);
    }

    class TaskViewHolder extends RecyclerView.ViewHolder {
        private TextView titleTextView;
        private TextView descriptionTextView;
        private TextView dueDateTextView;
        private TextView priorityTextView;
        private TextView statusTextView;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.task_title);
            descriptionTextView = itemView.findViewById(R.id.task_description);
            dueDateTextView = itemView.findViewById(R.id.task_due_date);
            priorityTextView = itemView.findViewById(R.id.task_priority);
            statusTextView = itemView.findViewById(R.id.task_status);
        }

        public void bind(Task task, OnTaskClickListener listener) {
            titleTextView.setText(task.getTitle());
            descriptionTextView.setText(task.getDescription());
            dueDateTextView.setText(dateFormat.format(task.getDueDate()));
            priorityTextView.setText(task.getPriority().toString());
            statusTextView.setText(task.getStatus().toString());

            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onTaskClick(task);
                }
            });
        }
    }

    public interface OnTaskClickListener {
        void onTaskClick(Task task);
    }
}