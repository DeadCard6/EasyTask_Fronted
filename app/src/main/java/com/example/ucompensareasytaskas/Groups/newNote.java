package com.example.ucompensareasytaskas.Groups;

import android.Manifest;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import com.example.ucompensareasytaskas.R;
import com.example.ucompensareasytaskas.api.ApiService;
import com.example.ucompensareasytaskas.api.RetrofitClient;
import com.example.ucompensareasytaskas.api.model.Note;
import com.example.ucompensareasytaskas.api.model.User;
import com.example.ucompensareasytaskas.home;
import com.example.ucompensareasytaskas.menu;
import com.example.ucompensareasytaskas.ubicaciones;

import android.location.LocationListener;
import android.location.LocationManager;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class newNote extends AppCompatActivity {

    private ImageButton clockButton;
    private int selectedHour = 0;
    private ImageButton groups_button, home_button, add_button, menu_button;
    private int selectedMinute = 0;
    private String currentPhotoPath = null;
    private static final int REQUEST_LOCATION_PERMISSION = 3;
    private double latitude = 0.0;
    private double longitude = 0.0;
    private ApiService apiService = RetrofitClient.getApiService();
    private boolean locationFetched = false; // Add this line to declare and initialize the flag

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);

        menu_button = findViewById(R.id.menu_button);
        groups_button = findViewById(R.id.groups_button);
        home_button = findViewById(R.id.home_button);
        add_button = findViewById(R.id.add_button);
        clockButton = findViewById(R.id.clock_button);

        findViewById(R.id.map_button).setOnClickListener(v -> checkLocationPermission());

        clockButton.setOnClickListener(v -> openTimePickerDialog());

        findViewById(R.id.confirmar_button).setOnClickListener(v -> createNote());

        getLocation();
    }
    private void setupButtonListeners() {
        menu_button.setOnClickListener(view -> {
            Intent i = new Intent(newNote.this, menu.class);
            startActivity(i);
        });
        groups_button.setOnClickListener(view -> {
            Intent i = new Intent(newNote.this, HomeGroups.class);
            startActivity(i);
        });

        home_button.setOnClickListener(view -> {
            Intent i = new Intent(newNote.this, home.class);
            startActivity(i);
        });

        add_button.setOnClickListener(view -> {
            Intent i = new Intent(newNote.this, newNote.class);
            startActivity(i);
        });
    }

    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_PERMISSION);
        } else {
            getLocation();
        }
    }

    private void getLocation() {
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_PERMISSION);
            return;
        }

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0f, new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                latitude = location.getLatitude();
                longitude = location.getLongitude();

                // Ensure that the Toast is only shown once
                if (!locationFetched) {
                    Toast.makeText(newNote.this, "Lat: " + latitude + " Long: " + longitude, Toast.LENGTH_SHORT).show();
                    locationFetched = true; // Set the flag to true once location is fetched
                }
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {}

            @Override
            public void onProviderEnabled(String provider) {}

            @Override
            public void onProviderDisabled(String provider) {}
        });
    }

    private void openTimePickerDialog() {
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, (view, hourOfDay, minute) -> {
            selectedHour = hourOfDay;
            selectedMinute = minute;

            String time = String.format("%02d:%02d", selectedHour, selectedMinute);
            Toast.makeText(newNote.this, "Hora seleccionada: " + time, Toast.LENGTH_SHORT).show();
        }, selectedHour, selectedMinute, true);
        timePickerDialog.setButton(TimePickerDialog.BUTTON_POSITIVE, "Aceptar", (dialog, which) -> dialog.dismiss());
        timePickerDialog.setButton(TimePickerDialog.BUTTON_NEGATIVE, "Cancelar", (dialog, which) -> dialog.dismiss());
        timePickerDialog.show();
    }

    private void createNote() {
        String title = ((EditText) findViewById(R.id.titleInput)).getText().toString();
        String description = ((EditText) findViewById(R.id.descInput)).getText().toString();

        if (title.isEmpty() || description.isEmpty()) {
            Toast.makeText(this, "Por favor, ingrese todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        String date = new java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault()).format(new java.util.Date());
        String hour = String.format("%02d:%02d", selectedHour, selectedMinute);

        SharedPreferences prefs = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        long userId = prefs.getLong("user", -1);

        if (userId == -1L) {
            Toast.makeText(this, "Error al obtener el ID del usuario", Toast.LENGTH_SHORT).show();
            return;
        }

        // Convertir la latitud y longitud a String
        String latitudeStr = String.format("%.6f", latitude); // Usamos formato adecuado para coordenadas
        String longitudeStr = String.format("%.6f", longitude);

        // Crear la ubicación con las coordenadas como Strings
        com.example.ucompensareasytaskas.api.model.Location location = new com.example.ucompensareasytaskas.api.model.Location("Ubicación", latitudeStr, longitudeStr);

        // Crear la nueva nota
        Note newNote = new Note();
        newNote.setTitle(title);
        newNote.setDescription(description);
        newNote.setDate(date);
        newNote.setHour(hour);
        newNote.setImage(currentPhotoPath);
        newNote.setLocation(location);  // Usar tu Location personalizada con coordenadas como Strings
        newNote.setUser(new User(userId));

        showCoordinatesDialog(title, latitudeStr, longitudeStr);

        // Enviar la nota al servidor
        apiService.createNote(userId, newNote).enqueue(new Callback<Note>() {
            @Override
            public void onResponse(Call<Note> call, Response<Note> response) {
                if (response.isSuccessful()) {
                    Note createdNote = response.body();
                    // Handle success
                    Toast.makeText(newNote.this, "Note created successfully", Toast.LENGTH_SHORT).show();
                } else {
                    // Handle failure (e.g., bad request)
                    String errorMessage = response.message();
                    Toast.makeText(newNote.this, "Error: " + errorMessage, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Note> call, Throwable t) {
                Toast.makeText(newNote.this, "Request failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showCoordinatesDialog(String title, String latitude, String longitude) {
        String message = "Nombre: " + title + "\nCoordenadas: Latitud: " + latitude + ", Longitud: " + longitude;

        new AlertDialog.Builder(this)
                .setTitle("Coordenadas de la ubicación")
                .setMessage(message)
                .setPositiveButton("Aceptar", (dialog, which) -> dialog.dismiss())
                .show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);  // Call the superclass method

        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLocation();
            } else {
                Toast.makeText(this, "Permiso de ubicación denegado", Toast.LENGTH_SHORT).show();
            }
        }
    }
}

