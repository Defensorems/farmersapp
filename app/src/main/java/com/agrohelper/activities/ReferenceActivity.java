package com.agrohelper.activities;

import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import androidx.appcompat.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.agrohelper.R;
import com.agrohelper.adapters.ReferenceAdapter;
import com.agrohelper.models.PlantIdResponse;
import com.agrohelper.network.ApiClient;
import com.agrohelper.network.PlantIdApiService;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.search.SearchBar;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReferenceActivity extends AppCompatActivity {
    private ReferenceAdapter adapter;
    private ProgressBar progressBar;
    private PlantIdApiService plantIdApiService;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.reference_menu, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reference);

        // Инициализация элементов
        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        SearchView searchView = findViewById(R.id.search_view);
        EditText searchEditText = searchView.findViewById(androidx.appcompat.R.id.search_src_text);
        RecyclerView recyclerView = findViewById(R.id.reference_recycler_view);
        progressBar = findViewById(R.id.progress_bar);

        // Настройка тулбара
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(v -> finish());

        // Настройка RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ReferenceAdapter();
        recyclerView.setAdapter(adapter);

        // Обработчик ввода текста
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 2) performSearch(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        // Инициализация API
        plantIdApiService = ApiClient.getPlantIdApiService();
    }

    private void performSearch(String query) {
        Log.d("API_DEBUG", "Starting search for: " + query);

        plantIdApiService.searchPlants(query).enqueue(new Callback<PlantIdResponse>() {
            @Override
            public void onResponse(Call<PlantIdResponse> call, Response<PlantIdResponse> response) {
                Log.d("API_DEBUG", "Response code: " + response.code());
                Log.d("API_DEBUG", "Request URL: " + call.request().url());

                if (response.isSuccessful() && response.body() != null) {
                    List<PlantIdResponse.Entity> results = response.body().getEntities();

                    if (results != null && !results.isEmpty()) {
                        adapter.submitList(results);
                        Log.i("API_SUCCESS", "Received " + results.size() + " entities");
                    } else {
                        Toast.makeText(ReferenceActivity.this, "Ничего не найдено", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    try {
                        Log.e("API_ERROR", "Error body: " + response.errorBody().string());
                    } catch (IOException e) {
                        Log.e("API_ERROR", "Failed to read error body", e);
                    }
                }
            }

            @Override
            public void onFailure(Call<PlantIdResponse> call, Throwable t) {
                Log.e("API_FAILURE", "Request failed: " + t.getMessage(), t);
                Log.d("API_FAILURE", "Failed URL: " + call.request().url());
            }
        });
    }

    private void showLoading(boolean show) {
        progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
        findViewById(R.id.reference_recycler_view)
                .setVisibility(show ? View.GONE : View.VISIBLE);
    }
}