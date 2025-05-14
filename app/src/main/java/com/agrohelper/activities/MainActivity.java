package com.agrohelper.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.agrohelper.R;
import com.agrohelper.adapters.PlantAdapter;
import com.agrohelper.viewmodels.PlantViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    private PlantViewModel plantViewModel;
    private PlantAdapter plantAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        // Set up the toolbar
        setSupportActionBar(findViewById(R.id.toolbar));
        
        // Initialize RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        
        // Set up the adapter
        plantAdapter = new PlantAdapter(this);
        recyclerView.setAdapter(plantAdapter);
        
        // Initialize ViewModel
        plantViewModel = new ViewModelProvider(this).get(PlantViewModel.class);
        
        // Observe plants data
        plantViewModel.getAllPlants().observe(this, plants -> {
            plantAdapter.setPlants(plants);
            
            // Show empty view if no plants
            View emptyView = findViewById(R.id.empty_view);
            if (plants != null && plants.isEmpty()) {
                emptyView.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
            } else {
                emptyView.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
            }
        });
        
        // Set up FAB for adding new plants
        FloatingActionButton fab = findViewById(R.id.fab_add_plant);
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, AddPlantActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        
        if (id == R.id.action_weather) {
            startActivity(new Intent(this, WeatherActivity.class));
            return true;
        } else if (id == R.id.action_reference) {
            startActivity(new Intent(this, ReferenceActivity.class));
            return true;
        } else if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }
        
        return super.onOptionsItemSelected(item);
    }
}
