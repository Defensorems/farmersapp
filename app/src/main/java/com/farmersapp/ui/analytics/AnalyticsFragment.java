package com.farmersapp.ui.analytics;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.farmersapp.R;

public class AnalyticsFragment extends Fragment {

    private AnalyticsViewModel viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(AnalyticsViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_analytics, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Настройка графиков и других компонентов UI
        setupYieldChart(view.findViewById(R.id.yield_chart));
        setupExpensesChart(view.findViewById(R.id.expenses_chart));
        setupWeatherImpactChart(view.findViewById(R.id.weather_impact_chart));

        setupObservers();
    }

    private void setupObservers() {
        viewModel.getYieldData().observe(getViewLifecycleOwner(), yieldData -> {
            // Обновление графика урожайности
            // Здесь будет код для обновления графика
        });

        viewModel.getExpensesData().observe(getViewLifecycleOwner(), expensesData -> {
            // Обновление графика расходов
            // Здесь будет код для обновления графика
        });

        viewModel.getWeatherImpactData().observe(getViewLifecycleOwner(), weatherData -> {
            // Обновление графика влияния погоды
            // Здесь будет код для обновления графика
        });

        viewModel.getErrorMessage().observe(getViewLifecycleOwner(), errorMsg -> {
            if (errorMsg != null && !errorMsg.isEmpty()) {
                Toast.makeText(requireContext(), errorMsg, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setupYieldChart(View chartView) {
        // Настройка графика урожайности
        // Здесь будет код для настройки графика
    }

    private void setupExpensesChart(View chartView) {
        // Настройка графика расходов
        // Здесь будет код для настройки графика
    }

    private void setupWeatherImpactChart(View chartView) {
        // Настройка графика влияния погоды
        // Здесь будет код для настройки графика
    }
}