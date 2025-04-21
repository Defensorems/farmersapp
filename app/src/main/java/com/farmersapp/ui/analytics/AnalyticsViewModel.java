package com.farmersapp.ui.analytics;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class AnalyticsViewModel extends ViewModel {

    private MutableLiveData<List<Map<String, Object>>> yieldData = new MutableLiveData<>();
    private MutableLiveData<List<Map<String, Object>>> expensesData = new MutableLiveData<>();
    private MutableLiveData<List<Map<String, Object>>> weatherImpactData = new MutableLiveData<>();
    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);
    private MutableLiveData<String> errorMessage = new MutableLiveData<>();

    public AnalyticsViewModel() {
        loadAnalyticsData();
    }

    private void loadAnalyticsData() {
        isLoading.setValue(true);
        try {
            // Загрузка данных об урожайности
            yieldData.setValue(generateYieldData());

            // Загрузка данных о расходах
            expensesData.setValue(generateExpensesData());

            // Загрузка данных о влиянии погоды
            weatherImpactData.setValue(generateWeatherImpactData());
        } catch (Exception e) {
            errorMessage.setValue("Failed to load analytics data: " + e.getMessage());
        } finally {
            isLoading.setValue(false);
        }
    }

    // Геттеры для LiveData
    public LiveData<List<Map<String, Object>>> getYieldData() {
        return yieldData;
    }

    public LiveData<List<Map<String, Object>>> getExpensesData() {
        return expensesData;
    }

    public LiveData<List<Map<String, Object>>> getWeatherImpactData() {
        return weatherImpactData;
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    // Вспомогательные методы для генерации фиктивных данных
    private List<Map<String, Object>> generateYieldData() {
        List<Map<String, Object>> data = new ArrayList<>();

        // Данные по урожайности пшеницы
        Map<String, Object> wheat = new HashMap<>();
        wheat.put("crop", "Пшеница");
        wheat.put("year", 2022);
        wheat.put("yield", 5.2); // т/га
        data.add(wheat);

        // Данные по урожайности кукурузы
        Map<String, Object> corn = new HashMap<>();
        corn.put("crop", "Кукуруза");
        corn.put("year", 2022);
        corn.put("yield", 7.8); // т/га
        data.add(corn);

        // Данные по урожайности сои
        Map<String, Object> soy = new HashMap<>();
        soy.put("crop", "Соя");
        soy.put("year", 2022);
        soy.put("yield", 2.5); // т/га
        data.add(soy);

        return data;
    }

    private List<Map<String, Object>> generateExpensesData() {
        List<Map<String, Object>> data = new ArrayList<>();

        // Расходы на семена
        Map<String, Object> seeds = new HashMap<>();
        seeds.put("category", "Семена");
        seeds.put("amount", 150000.0); // рубли
        data.add(seeds);

        // Расходы на удобрения
        Map<String, Object> fertilizers = new HashMap<>();
        fertilizers.put("category", "Удобрения");
        fertilizers.put("amount", 200000.0); // рубли
        data.add(fertilizers);

        // Расходы на топливо
        Map<String, Object> fuel = new HashMap<>();
        fuel.put("category", "Топливо");
        fuel.put("amount", 120000.0); // рубли
        data.add(fuel);

        // Расходы на оплату труда
        Map<String, Object> labor = new HashMap<>();
        labor.put("category", "Оплата труда");
        labor.put("amount", 300000.0); // рубли
        data.add(labor);

        return data;
    }

    private List<Map<String, Object>> generateWeatherImpactData() {
        List<Map<String, Object>> data = new ArrayList<>();

        // Влияние температуры
        Map<String, Object> temperature = new HashMap<>();
        temperature.put("factor", "Температура");
        temperature.put("impact", 0.8); // коэффициент влияния
        data.add(temperature);

        // Влияние осадков
        Map<String, Object> precipitation = new HashMap<>();
        precipitation.put("factor", "Осадки");
        precipitation.put("impact", 0.9); // коэффициент влияния
        data.add(precipitation);

        // Влияние солнечного света
        Map<String, Object> sunlight = new HashMap<>();
        sunlight.put("factor", "Солнечный свет");
        sunlight.put("impact", 0.7); // коэффициент влияния
        data.add(sunlight);

        return data;
    }
}