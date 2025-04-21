public class CropsViewModel extends ViewModel {

    private MutableLiveData<List<Crop>> crops = new MutableLiveData<>();
    private MutableLiveData<List<Field>> fields = new MutableLiveData<>();
    private MutableLiveData<CropStatistics> cropStatistics = new MutableLiveData<>();
    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);
    private MutableLiveData<String> errorMessage = new MutableLiveData<>();

    private CropRepository cropRepository;
    private FieldRepository fieldRepository;

    @Inject
    public CropsViewModel(CropRepository cropRepository, FieldRepository fieldRepository) {
        this.cropRepository = cropRepository;
        this.fieldRepository = fieldRepository;
        loadCrops();
        loadFields();
        loadStatistics();
    }

    public void addNewCrop(Crop crop) {
        isLoading.setValue(true);
        viewModelScope.launch(Dispatchers.IO) {
            try {
                cropRepository.addCrop(crop);
                loadCrops(); // Refresh the list
            } catch (Exception e) {
                errorMessage.postValue("Failed to add crop: " + e.getMessage());
            } finally {
                isLoading.postValue(false);
            }
        }
    }

    public void updateCrop(Crop crop) {
        isLoading.setValue(true);
        viewModelScope.launch(Dispatchers.IO) {
            try {
                cropRepository.updateCrop(crop);
                loadCrops(); // Refresh the list
            } catch (Exception e) {
                errorMessage.postValue("Failed to update crop: " + e.getMessage());
            } finally {
                isLoading.postValue(false);
            }
        }
    }

    public void deleteCrop(int cropId) {
        isLoading.setValue(true);
        viewModelScope.launch(Dispatchers.IO) {
            try {
                cropRepository.deleteCrop(cropId);
                loadCrops(); // Refresh the list
            } catch (Exception e) {
                errorMessage.postValue("Failed to delete crop: " + e.getMessage());
            } finally {
                isLoading.postValue(false);
            }
        }
    }

    // Getters for LiveData
    public LiveData<List<Crop>> getCrops() {
        return crops;
    }

    public LiveData<List<Field>> getFields() {
        return fields;
    }

    public LiveData<CropStatistics> getCropStatistics() {
        return cropStatistics;
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    // Private methods to load data
    private void loadCrops() {
        isLoading.setValue(true);
        viewModelScope.launch(Dispatchers.IO) {
            try {
                List<Crop> cropList = cropRepository.getCrops();
                crops.postValue(cropList);
            } catch (Exception e) {
                errorMessage.postValue("Failed to load crops: " + e.getMessage());
            } finally {
                isLoading.postValue(false);
            }
        }
    }

    private void loadFields() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                List<Field> fieldList = fieldRepository.getFields();
                fields.postValue(fieldList);
            } catch (Exception e) {
                errorMessage.postValue("Failed to load fields: " + e.getMessage());
            }
        }
    }

    private void loadStatistics() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                CropStatistics stats = cropRepository.getCropStatistics();
                cropStatistics.postValue(stats);
            } catch (Exception e) {
                errorMessage.postValue("Failed to load statistics: " + e.getMessage());
            }
        }
    }
}