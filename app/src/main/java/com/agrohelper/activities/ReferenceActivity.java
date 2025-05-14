package com.agrohelper.activities;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.agrohelper.R;
import com.agrohelper.adapters.ReferenceAdapter;
import com.agrohelper.models.ReferenceItem;

import java.util.ArrayList;
import java.util.List;

public class ReferenceActivity extends AppCompatActivity {
    private ReferenceAdapter referenceAdapter;
    private List<ReferenceItem> allReferenceItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reference);
        
        // Set up the toolbar with back button
        setSupportActionBar(findViewById(R.id.toolbar));
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(R.string.reference);
        }
        
        // Initialize RecyclerView
        RecyclerView recyclerView = findViewById(R.id.reference_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        
        // Initialize search view
        SearchView searchView = findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filterReferenceItems(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterReferenceItems(newText);
                return true;
            }
        });
        
        // Load reference data
        allReferenceItems = loadReferenceData();
        
        // Set up adapter
        referenceAdapter = new ReferenceAdapter(this, allReferenceItems);
        recyclerView.setAdapter(referenceAdapter);
    }

    private List<ReferenceItem> loadReferenceData() {
        // In a real app, this data would come from a database or API
        List<ReferenceItem> items = new ArrayList<>();
        
        // Vegetables
        items.add(new ReferenceItem(
                "Томаты",
                "Овощ",
                "Теплолюбивая культура. Требует регулярного полива и подкормки.",
                "Полив: 2-3 раза в неделю, избегая попадания воды на листья.\n" +
                "Подкормка: каждые 2 недели комплексным удобрением.",
                R.drawable.placeholder_plant
        ));
        
        items.add(new ReferenceItem(
                "Огурцы",
                "Овощ",
                "Влаголюбивая культура с быстрым ростом.",
                "Полив: ежедневно в жаркую погоду.\n" +
                "Подкормка: раз в 10 дней азотными удобрениями.",
                R.drawable.placeholder_plant
        ));
        
        items.add(new ReferenceItem(
                "Картофель",
                "Овощ",
                "Неприхотливая культура, требующая окучивания.",
                "Полив: умеренный, особенно в период формирования клубней.\n" +
                "Подкормка: 2-3 раза за сезон калийно-фосфорными удобрениями.",
                R.drawable.placeholder_plant
        ));
        
        // Fruits
        items.add(new ReferenceItem(
                "Яблоня",
                "Фрукт",
                "Долговечное плодовое дерево, требующее регулярной обрезки.",
                "Полив: 1-2 раза в неделю в засушливый период.\n" +
                "Подкормка: весной азотными, осенью фосфорно-калийными удобрениями.",
                R.drawable.placeholder_plant
        ));
        
        items.add(new ReferenceItem(
                "Клубника",
                "Фрукт",
                "Многолетняя ягодная культура, требующая мульчирования.",
                "Полив: регулярный, не допуская пересыхания почвы.\n" +
                "Подкормка: весной азотными, во время цветения комплексными удобрениями.",
                R.drawable.placeholder_plant
        ));
        
        // Grains
        items.add(new ReferenceItem(
                "Пшеница",
                "Злак",
                "Основная зерновая культура, требующая плодородных почв.",
                "Полив: в засушливый период на ранних стадиях роста.\n" +
                "Подкормка: азотными удобрениями в период кущения.",
                R.drawable.placeholder_plant
        ));
        
        items.add(new ReferenceItem(
                "Кукуруза",
                "Злак",
                "Теплолюбивая культура с высоким потреблением питательных веществ.",
                "Полив: регулярный, особенно в период формирования початков.\n" +
                "Подкормка: 2-3 раза за сезон комплексными удобрениями.",
                R.drawable.placeholder_plant
        ));
        
        return items;
    }

    private void filterReferenceItems(String query) {
        List<ReferenceItem> filteredList = new ArrayList<>();
        
        if (query == null || query.isEmpty()) {
            filteredList.addAll(allReferenceItems);
        } else {
            String lowerCaseQuery = query.toLowerCase();
            
            for (ReferenceItem item : allReferenceItems) {
                if (item.getName().toLowerCase().contains(lowerCaseQuery) ||
                        item.getType().toLowerCase().contains(lowerCaseQuery) ||
                        item.getDescription().toLowerCase().contains(lowerCaseQuery)) {
                    filteredList.add(item);
                }
            }
        }
        
        referenceAdapter.updateData(filteredList);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
