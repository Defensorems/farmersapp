import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.viewModelScope;

import com.farmersapp.data.model.CurrentWeather;
import com.farmersapp.data.model.DailyForecast;
import com.farmersapp.data.model.Location;
import com.farmersapp.data.model.WeatherAlert;
import com.farmersapp.data.repository.LocationRepository;
import com.farmersapp.data.repository.WeatherRepository;

import java.util.List;

import javax.inject.Inject;

import kotlin.Unit;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.launch;

public class WeatherViewModel extends ViewModel {

    private MutableLiveData<CurrentWeather> currentWeather = new MutableLiveData<>();
    private MutableLiveData<List<DailyForecast>> forecast = new MutableLiveData<>();
    private MutableLiveData<List<WeatherAlert>> weatherAlerts = new MutableLiveData<>();
    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);
    private MutableLiveData<String> errorMessage = new MutableLiveData<>();
    private MutableLiveData<Location> selectedLocation = new MutableLiveData<>();

    private WeatherRepository weatherRepository;
    private LocationRepository locationRepository;

    @Inject
    public WeatherViewModel(WeatherRepository weatherRepository, LocationRepository locationRepository) {
        this.weatherRepository = weatherRepository;
        this.locationRepository = locationRepository;

        // Инициализация с текущей локацией
        viewModelScope.launch(Dispatchers.IO, () -> {
            try {
                Location currentLocation = locationRepository.getCurrentLocation();
                selectedLocation.postValue(currentLocation);
                loadWeatherData(currentLocation);
            } catch (Exception e) {
                errorMessage.postValue("Failed to get location: " + e.getMessage());
            }
            return null;
        });
    }

    private void loadWeatherData(Location location) {
        isLoading.setValue(true);
        viewModelScope.launch(Dispatchers.IO, () -> {
            try {
                CurrentWeather weather = weatherRepository.getCurrentWeather(location);
                currentWeather.postValue(weather);

                List<DailyForecast> dailyForecast = weatherRepository.getForecast(location, 7);
                forecast.postValue(dailyForecast);

                List<WeatherAlert> alerts = weatherRepository.getWeatherAlerts(location);
                weatherAlerts.postValue(alerts);
            } catch (Exception e) {
                errorMessage.postValue("Failed to load weather data: " + e.getMessage());
            } finally {
                isLoading.postValue(false);
            }
            return null;
        });
    }

    public void searchLocation(String query) {
        isLoading.setValue(true);
        viewModelScope.launch(Dispatchers.IO, () -> {
            try {
                List<Location> locations = locationRepository.searchLocations(query);
                // Обработка результатов поиска
            } catch (Exception e) {
                errorMessage.postValue("Location search failed: " + e.getMessage());
            } finally {
                isLoading.postValue(false);
            }
            return null;
        });
    }

    // Getters for LiveData
    public LiveData<CurrentWeather> getCurrentWeather() {
        return currentWeather;
    }

    public LiveData<List<DailyForecast>> getForecast() {
        return forecast;
    }

    public LiveData<List<WeatherAlert>> getWeatherAlerts() {
        return weatherAlerts;
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public LiveData<Location> getSelectedLocation() {
        return selectedLocation;
    }

    // Private methods
    private void loadWeatherData(Location location) {
        isLoading.setValue(true);
        viewModelScope.launch(Dispatchers.getIO(), (CoroutineScope scope) -> {
            try {
                CurrentWeather weather = weatherRepository.getCurrentWeather(location);
                currentWeather.postValue(weather);

                List<DailyForecast> dailyForecast = weatherRepository.getForecast(location, 7);
                forecast.postValue(dailyForecast);

                List<WeatherAlert> alerts = weatherRepository.getWeatherAlerts(location);
                weatherAlerts.postValue(alerts);
            } catch (Exception e) {
                errorMessage.postValue("Failed to load weather data: " + e.getMessage());
            } finally {
                isLoading.postValue(false);
            }
            return Unit.INSTANCE;
        });
    }
}