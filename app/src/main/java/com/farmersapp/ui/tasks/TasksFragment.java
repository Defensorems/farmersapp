package com.farmersapp.ui.tasks;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.farmersapp.R;
import com.farmersapp.data.model.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class TasksFragment extends Fragment {

    private TasksViewModel viewModel;
    private RecyclerView recyclerView;
    private TaskAdapter adapter;
    private FloatingActionButton addTaskButton;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(TasksViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tasks, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.tasks_recycler_view);
        adapter = new TaskAdapter(new TaskDiffCallback(), task -> {
            // Обработка клика по задаче
            // Например, открытие диалога редактирования
        });
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        addTaskButton = view.findViewById(R.id.add_task_button);
        addTaskButton.setOnClickListener(v -> showAddTaskDialog());

        setupObservers();
    }

    private void setupObservers() {
        viewModel.getTasks().observe(getViewLifecycleOwner(), tasks -> {
            adapter.submitList(tasks);
        });

        viewModel.getIsLoading().observe(getViewLifecycleOwner(), isLoading -> {
            // Показать/скрыть индикатор загрузки
        });

        viewModel.getErrorMessage().observe(getViewLifecycleOwner(), errorMsg -> {
            if (errorMsg != null && !errorMsg.isEmpty()) {
                Toast.makeText(requireContext(), errorMsg, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void showAddTaskDialog() {
        // Показать диалог добавления новой задачи
        // Это будет реализовано позже
    }
}