package com.agrohelper.models;

public class WeatherData {
    private double temperature;
    private double humidity;
    private String description;
    private double windSpeed;
    private double precipitation;
    private String icon;
    private String recommendation;

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

    private String generateRecommendation() {
        // Logic to generate recommendations based on weather conditions
        if (precipitation > 0) {
            return "Осадки ожидаются. Отложите полив растений.";
        } else if (temperature > 30) {
            return "Высокая температура. Рекомендуется дополнительный полив.";
        } else if (windSpeed > 10) {
            return "Сильный ветер. Проверьте опоры для высоких растений.";
        }
        return "Благоприятные условия для садоводства.";
    }

    // Getters and Setters
    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public double getPrecipitation() {
        return precipitation;
    }

    public void setPrecipitation(double precipitation) {
        this.precipitation = precipitation;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getRecommendation() {
        return recommendation;
    }

    public void setRecommendation(String recommendation) {
        this.recommendation = recommendation;
    }
}
