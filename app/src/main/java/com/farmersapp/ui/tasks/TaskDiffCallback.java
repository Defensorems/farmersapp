package com.farmersapp.ui.tasks;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.farmersapp.data.model.Task;

public class TaskDiffCallback extends DiffUtil.ItemCallback<Task> {

    @Override
    public boolean areItemsTheSame(@NonNull Task oldItem, @NonNull Task newItem) {
        return oldItem.getId() == newItem.getId();
    }

    @Override
    public boolean areContentsTheSame(@NonNull Task oldItem, @NonNull Task newItem) {
        return oldItem.getTitle().equals(newItem.getTitle()) &&
                oldItem.getDescription().equals(newItem.getDescription()) &&
                oldItem.getDueDate().equals(newItem.getDueDate()) &&
                oldItem.getPriority() == newItem.getPriority() &&
                oldItem.getStatus() == newItem.getStatus();
    }
}