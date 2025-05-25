package com.agrohelper.activities;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Handler;
import android.os.Looper;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.agrohelper.R;
import com.agrohelper.adapters.TaskAdapter;
import com.agrohelper.models.Plant;
import com.agrohelper.models.Task;
import com.agrohelper.utils.NotificationHelper;
import com.agrohelper.viewmodels.PlantViewModel;
import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class PlantDetailActivity extends AppCompatActivity {
    private PlantViewModel plantViewModel;
    private NotificationHelper notificationHelper;
    private Plant currentPlant;
    private int plantId;
    
    private ImageView plantImageView;
    private TextView nameTextView;
    private TextView typeTextView;
    private TextView dateTextView;
    private TextView notesTextView;
    private RecyclerView tasksRecyclerView;
    private TaskAdapter taskAdapter;
    
    private SimpleDateFormat dateFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant_detail);
        
        // Set up the toolbar with back button
        setSupportActionBar(findViewById(R.id.toolbar));
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(R.string.plant_details);
        }
        
        // Get plant ID from intent
        plantId = getIntent().getIntExtra("plantId", -1);
        if (plantId == -1) {
            Toast.makeText(this, R.string.error_plant_not_found, Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        
        // Initialize ViewModel and NotificationHelper
        plantViewModel = new ViewModelProvider(this).get(PlantViewModel.class);
        notificationHelper = new NotificationHelper(this);
        dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        
        // Initialize views
        plantImageView = findViewById(R.id.plant_image);
        nameTextView = findViewById(R.id.plant_name);
        typeTextView = findViewById(R.id.plant_type);
        dateTextView = findViewById(R.id.plant_date);
        notesTextView = findViewById(R.id.plant_notes);
        tasksRecyclerView = findViewById(R.id.tasks_recycler_view);
        
        // Set up tasks RecyclerView
        tasksRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        taskAdapter = new TaskAdapter(this);
        tasksRecyclerView.setAdapter(taskAdapter);
        
        // Observe plant data
        plantViewModel.getPlantById(plantId).observe(this, plant -> {
            if (plant != null) {
                currentPlant = plant;
                updateUI(plant);
            } else {
                Toast.makeText(this, R.string.error_plant_not_found, Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        
        // Observe tasks for this plant
        plantViewModel.getTasksForPlant(plantId).observe(this, tasks -> {
            taskAdapter.setTasks(tasks);
            
            // Show empty view if no tasks
            View emptyTasksView = findViewById(R.id.empty_tasks_view);
            if (tasks != null && tasks.isEmpty()) {
                emptyTasksView.setVisibility(View.VISIBLE);
                tasksRecyclerView.setVisibility(View.GONE);
            } else {
                emptyTasksView.setVisibility(View.GONE);
                tasksRecyclerView.setVisibility(View.VISIBLE);
            }
        });
        
        // Set up quick task buttons
        Button waterButton = findViewById(R.id.button_water);
        Button fertilizeButton = findViewById(R.id.button_fertilize);
        Button harvestButton = findViewById(R.id.button_harvest);
        
        waterButton.setOnClickListener(v -> addTask(Task.TaskType.WATER));
        fertilizeButton.setOnClickListener(v -> addTask(Task.TaskType.FERTILIZE));
        harvestButton.setOnClickListener(v -> addTask(Task.TaskType.HARVEST));
        
        // Set up FAB for adding custom tasks
        FloatingActionButton fab = findViewById(R.id.fab_add_task);
        fab.setOnClickListener(view -> showAddTaskDialog());
    }

    // Set up FAB for adding custom tasks


    // Метод для отображения диалога
    private void showAddTaskDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_add_task, null);
        builder.setView(dialogView);
        builder.setTitle(R.string.add_task);

        // Инициализация элементов диалога
        Spinner taskTypeSpinner = dialogView.findViewById(R.id.spinner_task_type);
        Button buttonDate = dialogView.findViewById(R.id.button_date);
        Button buttonTime = dialogView.findViewById(R.id.button_time);
        EditText editNotes = dialogView.findViewById(R.id.edit_notes);

        // Настройка спиннера
        ArrayAdapter<Task.TaskType> adapter = new ArrayAdapter<Task.TaskType>(
                this,
                android.R.layout.simple_spinner_item,
                Task.TaskType.values()
        ) {
            @NonNull
            @Override
            public View getView(int position, View convertView, @NonNull ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView textView = (TextView) view.findViewById(android.R.id.text1);
                textView.setText(getItem(position).getLabelResId());
                return view;
            }

            @Override
            public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView textView = (TextView) view.findViewById(android.R.id.text1);
                textView.setText(getItem(position).getLabelResId());
                return view;
            }
        };
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        taskTypeSpinner.setAdapter(adapter);

        // Выбор даты и времени
        Calendar selectedDateTime = Calendar.getInstance();
        selectedDateTime.add(Calendar.HOUR_OF_DAY, 1); // По умолчанию через 1 час

        buttonDate.setOnClickListener(v -> {
            new DatePickerDialog(
                    this,
                    (view, year, month, day) -> {
                        selectedDateTime.set(year, month, day);
                        updateDateTimeButtons(buttonDate, buttonTime, selectedDateTime);
                    },
                    selectedDateTime.get(Calendar.YEAR),
                    selectedDateTime.get(Calendar.MONTH),
                    selectedDateTime.get(Calendar.DAY_OF_MONTH)
            ).show();
        });

        buttonTime.setOnClickListener(v -> {
            new TimePickerDialog(
                    this,
                    (view, hour, minute) -> {
                        selectedDateTime.set(Calendar.HOUR_OF_DAY, hour);
                        selectedDateTime.set(Calendar.MINUTE, minute);
                        updateDateTimeButtons(buttonDate, buttonTime, selectedDateTime);
                    },
                    selectedDateTime.get(Calendar.HOUR_OF_DAY),
                    selectedDateTime.get(Calendar.MINUTE),
                    true
            ).show();
        });

        updateDateTimeButtons(buttonDate, buttonTime, selectedDateTime);

        // Обработка подтверждения
        builder.setPositiveButton(R.string.add, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Task.TaskType taskType = (Task.TaskType) taskTypeSpinner.getSelectedItem();
                String notes = editNotes.getText().toString().trim();

                Task task = new Task(
                        plantId,
                        taskType,
                        selectedDateTime.getTime(),
                        false
                );
                task.setNotes(notes);

                plantViewModel.insertTask(task);

                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (currentPlant != null && task.getId() != 0) {
                            notificationHelper.scheduleTaskNotification(task, currentPlant);
                        }
                    }
                }, 100);
            }
        });

        builder.setNegativeButton(R.string.cancel, null);
        builder.create().show();
    }

    private void updateDateTimeButtons(Button dateButton, Button timeButton, Calendar calendar) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());

        dateButton.setText(dateFormat.format(calendar.getTime()));
        timeButton.setText(timeFormat.format(calendar.getTime()));
    }

    private void updateUI(Plant plant) {
        nameTextView.setText(plant.getName());
        typeTextView.setText(plant.getType());
        
        if (plant.getDatePlanted() != null) {
            dateTextView.setText(dateFormat.format(plant.getDatePlanted()));
        }
        
        notesTextView.setText(plant.getNotes());
        
        if (plant.getPhotoUri() != null && !plant.getPhotoUri().isEmpty()) {
            Glide.with(this)
                    .load(Uri.parse(plant.getPhotoUri()))
                    .placeholder(R.drawable.placeholder_plant)
                    .error(R.drawable.placeholder_plant)
                    .centerCrop()
                    .into(plantImageView);
        } else {
            plantImageView.setImageResource(R.drawable.placeholder_plant);
        }
    }

    private void addTask(Task.TaskType taskType) {
        // Add task for today
        Task task = new Task(plantId, taskType, new Date(), false);
        plantViewModel.insertTask(task);
        
        if (currentPlant != null) {
            notificationHelper.scheduleTaskNotification(task, currentPlant);
        }
        
        Toast.makeText(this, R.string.task_added, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_plant_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        } else if (id == R.id.action_edit) {
            // Open edit plant activity
            Intent intent = new Intent(this, AddPlantActivity.class);
            intent.putExtra("plantId", plantId);
            startActivity(intent);
            return true;
        } else if (id == R.id.action_delete) {
            // Show delete confirmation dialog
            new AlertDialog.Builder(this)
                    .setTitle(R.string.delete_plant)
                    .setMessage(R.string.delete_plant_confirmation)
                    .setPositiveButton(R.string.delete, (dialog, which) -> {
                        if (currentPlant != null) {
                            plantViewModel.deletePlant(currentPlant);
                            Toast.makeText(this, R.string.plant_deleted, Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    })
                    .setNegativeButton(R.string.cancel, null)
                    .show();
            return true;
        }
        
        return super.onOptionsItemSelected(item);
    }
}
