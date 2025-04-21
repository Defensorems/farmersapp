package com.farmersapp.utils;

public class WeatherUtils {

    private WeatherUtils() {
        // Приватный конструктор для предотвращения создания экземпляров
    }

    public static int getWeatherIconResource(String iconCode) {
        // Преобразование кода иконки погоды в ресурс drawable
        // Это заглушка, которую нужно будет реализовать с реальными ресурсами
        return android.R.drawable.ic_menu_compass;
    }

    public static String getWeatherDescription(String condition) {
        // Преобразование кода состояния погоды в читаемое описание
        switch (condition.toLowerCase()) {
            case "clear":
            case "sunny":
                return "Ясно";
            case "partly_cloudy":
                return "Переменная облачность";
            case "cloudy":
                return "Облачно";
            case "overcast":
                return "Пасмурно";
            case "rain":
                return "Дождь";
            case "snow":
                return "Снег";
            case "thunderstorm":
                return "Гроза";
            case "fog":
                return "Туман";
            default:
                return condition;
        }
    }

    public static String getTemperatureString(double temperature) {
        // Форматирование температуры в строку с градусами Цельсия
        return String.format("%.1f°C", temperature);
    }

    public static String getWindSpeedString(double windSpeed) {
        // Форматирование скорости ветра в строку с м/с
        return String.format("%.1f м/с", windSpeed);
    }

    public static String getHumidityString(int humidity) {
        // Форматирование влажности в строку с процентами
        return String.format("%d%%", humidity);
    }

    public static String getPrecipitationChanceString(double chance) {
        // Форматирование вероятности осадков в строку с процентами
        return String.format("%.0f%%", chance * 100);
    }
}