package com.agrohelper.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.agrohelper.R;
import com.agrohelper.adapters.PlantAdapter;
import com.agrohelper.viewmodels.PlantViewModel;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

public class MainActivity extends AppCompatActivity {
    private PlantViewModel plantViewModel;
    private PlantAdapter plantAdapter;
    private RecyclerView recyclerView;
    private View emptyView;
    private ExtendedFloatingActionButton fabAddPlant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set up the toolbar
        setSupportActionBar(findViewById(R.id.toolbar));

        // Initialize RecyclerView
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Add animation to RecyclerView
        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(
                this, R.anim.layout_animation_fall_down);
        recyclerView.setLayoutAnimation(animation);

        // Set up the adapter
        plantAdapter = new PlantAdapter(this);
        recyclerView.setAdapter(plantAdapter);

        // Initialize ViewModel
        plantViewModel = new ViewModelProvider(this).get(PlantViewModel.class);

        // Initialize views
        emptyView = findViewById(R.id.empty_view);
        fabAddPlant = findViewById(R.id.fab_add_plant);

        // Observe plants data
        plantViewModel.getAllPlants().observe(this, plantsList -> {
            plantAdapter.setPlants(plantsList);

            // Show empty view if no plants
            if (plantsList != null && plantsList.isEmpty()) {
                emptyView.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
                fabAddPlant.extend(); // Show extended FAB when empty
            } else {
                emptyView.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                fabAddPlant.shrink(); // Show icon-only FAB when not empty

                // Run layout animation
                recyclerView.scheduleLayoutAnimation();
            }
        });

        // Set up FAB for adding new plants
        fabAddPlant.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, AddPlantActivity.class);
            startActivity(intent);
        });

        // Set up scroll listener to hide/show FAB
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0 && fabAddPlant.isExtended()) {
                    // Скролл вниз — схлопнуть FAB
                    fabAddPlant.shrink();
                } else if (dy < 0 && !fabAddPlant.isExtended()
                        && plantAdapter.getItemCount() == 0) {
                    // Скролл вверх и пустой список — развернуть FAB
                    fabAddPlant.extend();
                }
            }
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