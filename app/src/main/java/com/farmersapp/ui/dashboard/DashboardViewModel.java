public class DashboardViewModel extends ViewModel {

    private MutableLiveData<FarmSummary> farmSummary = new MutableLiveData<>();
    private MutableLiveData<List<Task>> upcomingTasks = new MutableLiveData<>();
    private MutableLiveData<WeatherSummary> currentWeather = new MutableLiveData<>();
    private MutableLiveData<List<CropAlert>> cropAlerts = new MutableLiveData<>();

    private FarmRepository farmRepository;
    private TaskRepository taskRepository;
    private WeatherRepository weatherRepository;

    @Inject
    public DashboardViewModel(FarmRepository farmRepository,
                              TaskRepository taskRepository,
                              WeatherRepository weatherRepository) {
        this.farmRepository = farmRepository;
        this.taskRepository = taskRepository;
        this.weatherRepository = weatherRepository;

        loadDashboardData();
    }

    private void loadDashboardData() {
        // Load farm summary
        viewModelScope.launch(Dispatchers.IO) {
            try {
                FarmSummary summary = farmRepository.getFarmSummary();
                farmSummary.postValue(summary);

                // Load upcoming tasks
                List<Task> tasks = taskRepository.getUpcomingTasks(5); // Top 5 tasks
                upcomingTasks.postValue(tasks);

                // Load current weather
                WeatherSummary weather = weatherRepository.getCurrentWeather();
                currentWeather.postValue(weather);

                // Load crop alerts
                List<CropAlert> alerts = farmRepository.getCropAlerts();
                cropAlerts.postValue(alerts);
            } catch (Exception e) {
                // Handle errors
            }
        }
    }

    // Getters for LiveData
    public LiveData<FarmSummary> getFarmSummary() {
        return farmSummary;
    }

    public LiveData<List<Task>> getUpcomingTasks() {
        return upcomingTasks;
    }

    public LiveData<WeatherSummary> getCurrentWeather() {
        return currentWeather;
    }

    public LiveData<List<CropAlert>> getCropAlerts() {
        return cropAlerts;
    }
}