package com.agrohelper.models;

import java.util.HashMap;
import java.util.Map;

public class WeatherData {
    private double temperature;
    private double humidity;
    private String description;
    private double windSpeed;
    private double precipitation;
    private String icon;
    private String recommendation;

    private static final Map<String, String> WEATHER_TRANSLATIONS = new HashMap<>();
    static {
        WEATHER_TRANSLATIONS.put("clear sky", "Ясно");
        WEATHER_TRANSLATIONS.put("few clouds", "Небольшая облачность");
        WEATHER_TRANSLATIONS.put("scattered clouds", "Облачно");
        WEATHER_TRANSLATIONS.put("broken clouds", "Пасмурно");
        WEATHER_TRANSLATIONS.put("shower rain", "Ливень");
        WEATHER_TRANSLATIONS.put("rain", "Дождь");
        WEATHER_TRANSLATIONS.put("thunderstorm", "Гроза");
        WEATHER_TRANSLATIONS.put("snow", "Снег");
        WEATHER_TRANSLATIONS.put("mist", "Туман");
    }

    public WeatherData(double temperature, double humidity, String description,
                       double windSpeed, double precipitation, String icon) {
        this.temperature = temperature;
        this.humidity = humidity;
        this.description = description;
        this.windSpeed = windSpeed;
        this.precipitation = precipitation;
        this.icon = icon;
        this.recommendation = generateRecommendation();
    }

    public String getLocalizedDescription() {
        return WEATHER_TRANSLATIONS.getOrDefault(description.toLowerCase(), description);
    }

    private String generateRecommendation() {
        String localizedDesc = getLocalizedDescription().toLowerCase();

        if (precipitation > 0) {
            return "Осадки ожидаются. Отложите полив растений.";
        } else if (temperature > 30) {
            return "Высокая температура. Рекомендуется дополнительный полив.";
        } else if (windSpeed > 10) {
            return "Сильный ветер. Проверьте опоры для высоких растений.";
        }

        switch (localizedDesc) {
            case "дождь":
                return "Избегайте полива и работ с почвой.";
            case "ясно":
                return "Идеальные условия для полевых работ.";
            case "небольшая облачность":
            case "облачно":
                return "Подходящее время для посадки культур.";
            default:
                return "Благоприятные условия для садоводства.";
        }
    }

    // Getters
    public String getRecommendation() {
        return recommendation;
    }
    public double getTemperature() { return temperature; }
    public double getHumidity() { return humidity; }
    public String getDescription() { return description; }
    public double getWindSpeed() { return windSpeed; }
    public String getIcon() { return icon; }
}