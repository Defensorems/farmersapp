package com.farmersapp.data.repository;

import com.farmersapp.data.model.CurrentWeather;
import com.farmersapp.data.model.DailyForecast;
import com.farmersapp.data.model.Location;
import com.farmersapp.data.model.WeatherAlert;
import com.farmersapp.data.model.WeatherSummary;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class WeatherRepository {

    // TODO: Implement data sources (local database, remote API)

    public WeatherRepository() {
        // Временная реализация без реальных источников данных
    }

    public CurrentWeather getCurrentWeather(Location location) {
        // Временная реализация с фиктивными данными
        return new CurrentWeather(
                25.5, // температура
                24.0, // ощущается как
                65,   // влажность
                5.2,  // скорость ветра
                "Солнечно", // состояние
                "01d", // код иконки
                new Date(), // временная метка
                location // местоположение
        );
    }

    public WeatherSummary getCurrentWeather() {
        // Временная реализация с фиктивными данными
        return new WeatherSummary(
                "Ваше местоположение",
                25.5,
                "Солнечно",
                "01d"
        );
    }

    public List<DailyForecast> getForecast(Location location, int days) {
        // Временная реализация с фиктивными данными
        List<DailyForecast> forecast = new ArrayList<>();

        // Текущая дата
        Date currentDate = new Date();

        // Создаем прогноз на указанное количество дней
        for (int i = 0; i < days; i++) {
            // Создаем новую дату, добавляя i дней к текущей дате
            Date forecastDate = new Date(currentDate.getTime() + (i * 24 * 60 * 60 * 1000));

            // Создаем прогноз на этот день
            DailyForecast dailyForecast = new DailyForecast(
                    forecastDate,
                    20.0 + i, // минимальная температура
                    28.0 + i, // максимальная температура
                    65 - i,   // влажность
                    5.0 + (i * 0.5), // скорость ветра
                    i % 2 == 0 ? "Солнечно" : "Облачно", // состояние
                    i % 2 == 0 ? "01d" : "03d", // код иконки
                    i * 10.0 // вероятность осадков
            );

            forecast.add(dailyForecast);
        }

        return forecast;
    }

    public List<WeatherAlert> getWeatherAlerts(Location location) {
        // Временная реализация с фиктивными данными
        List<WeatherAlert> alerts = new ArrayList<>();

        // Текущая дата
        Date currentDate = new Date();

        // Создаем предупреждение о сильном ветре
        Date startDate = new Date(currentDate.getTime() + (24 * 60 * 60 * 1000)); // завтра
        Date endDate = new Date(currentDate.getTime() + (2 * 24 * 60 * 60 * 1000)); // послезавтра

        WeatherAlert windAlert = new WeatherAlert(
                1,
                "Сильный ветер",
                "Ожидается сильный ветер со скоростью до 20 м/с",
                startDate,
                endDate,
                WeatherAlert.AlertSeverity.WARNING
        );

        alerts.add(windAlert);

        return alerts;
    }
}