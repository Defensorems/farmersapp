package com.agrohelper.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.lifecycle.ViewModelProvider;

import com.agrohelper.R;
import com.agrohelper.models.Plant;
import com.agrohelper.viewmodels.PlantViewModel;
import com.bumptech.glide.Glide;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddPlantActivity extends AppCompatActivity {
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_GALLERY_IMAGE = 2;
    
    private PlantViewModel plantViewModel;
    private EditText nameEditText;
    private Spinner typeSpinner;
    private EditText dateEditText;
    private EditText notesEditText;
    private ImageView plantImageView;
    
    private String currentPhotoPath;
    private Calendar calendar;
    private SimpleDateFormat dateFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_plant);
        
        // Set up the toolbar with back button
        setSupportActionBar(findViewById(R.id.toolbar));
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(R.string.add_plant);
        }
        
        // Initialize ViewModel
        plantViewModel = new ViewModelProvider(this).get(PlantViewModel.class);
        
        // Initialize views
        nameEditText = findViewById(R.id.edit_plant_name);
        typeSpinner = findViewById(R.id.spinner_plant_type);
        dateEditText = findViewById(R.id.edit_plant_date);
        notesEditText = findViewById(R.id.edit_plant_notes);
        plantImageView = findViewById(R.id.plant_image);
        Button saveButton = findViewById(R.id.button_save);
        Button takePictureButton = findViewById(R.id.button_take_picture);
        Button galleryButton = findViewById(R.id.button_gallery);
        
        // Set up date picker
        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        dateEditText.setText(dateFormat.format(calendar.getTime()));
        
        dateEditText.setOnClickListener(v -> showDatePicker());
        
        // Set up plant type spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.plant_types,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(adapter);
        
        // Set up image capture
        takePictureButton.setOnClickListener(v -> dispatchTakePictureIntent());
        
        // Set up gallery selection
        galleryButton.setOnClickListener(v -> {
            Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(galleryIntent, REQUEST_GALLERY_IMAGE);
        });
        
        // Set up save button
        saveButton.setOnClickListener(v -> savePlant());
    }

    private void showDatePicker() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, year, month, dayOfMonth) -> {
                    calendar.set(Calendar.YEAR, year);
                    calendar.set(Calendar.MONTH, month);
                    calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    dateEditText.setText(dateFormat.format(calendar.getTime()));
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                Toast.makeText(this, "Error creating image file", Toast.LENGTH_SHORT).show();
            }
            
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(
                        this,
                        "com.agrohelper.fileprovider",
                        photoFile
                );
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(null);
        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );
        
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_IMAGE_CAPTURE) {
                Glide.with(this)
                        .load(currentPhotoPath)
                        .centerCrop()
                        .into(plantImageView);
            } else if (requestCode == REQUEST_GALLERY_IMAGE && data != null) {
                Uri selectedImage = data.getData();
                currentPhotoPath = selectedImage.toString();
                Glide.with(this)
                        .load(selectedImage)
                        .centerCrop()
                        .into(plantImageView);
            }
        }
    }

    private void savePlant() {
        String name = nameEditText.getText().toString().trim();
        String type = typeSpinner.getSelectedItem().toString();
        String notes = notesEditText.getText().toString().trim();
        
        if (name.isEmpty()) {
            nameEditText.setError(getString(R.string.error_name_required));
            return;
        }
        
        Plant plant = new Plant(
                name,
                type,
                calendar.getTime(),
                notes,
                currentPhotoPath
        );
        
        plantViewModel.insertPlant(plant);
        Toast.makeText(this, R.string.plant_saved, Toast.LENGTH_SHORT).show();
        finish();
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
