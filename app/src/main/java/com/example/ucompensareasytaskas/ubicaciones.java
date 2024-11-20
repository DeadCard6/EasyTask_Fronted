package com.example.ucompensareasytaskas;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.ucompensareasytaskas.Groups.HomeGroups;
import com.example.ucompensareasytaskas.Groups.newNote;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Set;

public class ubicaciones extends AppCompatActivity implements OnMapReadyCallback {

    private static final int CODIGO_PERMISO_UBICACION = 100;
    private ImageButton groups_button, home_button, add_button, menu_button;
    private GoogleMap mMap;
    private FusedLocationProviderClient fusedLocationClient;
    private LocationCallback locationCallback;
    private boolean isPermisos = false;
    private SharedPreferences sharedPreferences;
    private static final String PREFS_NAME = "ubicaciones_prefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inflate layout without navigation drawer
        setContentView(R.layout.activity_ubicaciones);

        // Initialize buttons
        menu_button = findViewById(R.id.menu_button);
        groups_button = findViewById(R.id.groups_button);
        home_button = findViewById(R.id.home_button);
        add_button = findViewById(R.id.add_button);

        // Set button listeners
        setupButtonListeners();

        // SharedPreferences to save locations
        sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        // Request location permissions
        solicitarPermisosUbicacion();

        // Configure the map
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }

    private void setupButtonListeners() {
        menu_button.setOnClickListener(view -> {
            Intent i = new Intent(ubicaciones.this, menu.class);
            startActivity(i);
        });
        groups_button.setOnClickListener(view -> {
            Intent i = new Intent(ubicaciones.this, HomeGroups.class);
            startActivity(i);
        });

        home_button.setOnClickListener(view -> {
            Intent i = new Intent(ubicaciones.this, home.class);
            startActivity(i);
        });

        add_button.setOnClickListener(view -> {
            Intent i = new Intent(ubicaciones.this, newNote.class);
            startActivity(i);
        });
    }

    private void solicitarPermisosUbicacion() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            isPermisos = true;
            iniciarLocalizacion();
        } else if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
            mostrarDialogoExplicativo();
        } else {
            ActivityCompat.requestPermissions(this, new String[] {
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
            }, CODIGO_PERMISO_UBICACION);
        }
    }

    private void mostrarDialogoExplicativo() {
        new AlertDialog.Builder(this)
                .setTitle("Permisos necesarios")
                .setMessage("Esta aplicación requiere acceder a tu ubicación para mostrar tus coordenadas ¿Deseas permitir el acceso?")
                .setPositiveButton("Sí", (dialog, which) -> ActivityCompat.requestPermissions(
                        ubicaciones.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                        CODIGO_PERMISO_UBICACION
                ))
                .setNegativeButton("No", (dialog, which) -> {
                    dialog.dismiss();
                    mostrarMensajePermisosDenegados();
                })
                .create()
                .show();
    }

    private void mostrarMensajePermisosDenegados() {
        Toast.makeText(this, "La aplicación necesita permisos de ubicación para funcionar", Toast.LENGTH_LONG).show();
    }

    private void iniciarLocalizacion() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        LocationRequest locationRequest = LocationRequest.create()
                .setPriority(Priority.PRIORITY_HIGH_ACCURACY)
                .setInterval(30000);

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                if (locationResult.getLastLocation() != null) {
                    actualizarUbicacion(locationResult.getLastLocation().getLatitude(), locationResult.getLastLocation().getLongitude());
                }
            }
        };

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());
        }
    }

    private void actualizarUbicacion(double latitude, double longitude) {
        LatLng ubicacionActual = new LatLng(latitude, longitude);
        mMap.clear();
        mMap.addMarker(new MarkerOptions().position(ubicacionActual).title("Ubicación actual"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(ubicacionActual));
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        // Cargar ubicaciones guardadas desde SharedPreferences
        loadSavedLocations();

        // Agregar marcador al hacer clic en el mapa
        mMap.setOnMapClickListener(latLng -> {
            addMarker(latLng);
            showNoteDialog(latLng);  // Muestra el cuadro de diálogo para ingresar la nota
        });
    }

    // Método para agregar un marcador
    private void addMarker(LatLng latLng) {
        mMap.addMarker(new MarkerOptions().position(latLng).title("Ubicación Guardada"));
    }

    private void showNoteDialog(LatLng latLng) {
        EditText inputNote = new EditText(this);
        new AlertDialog.Builder(this)
                .setTitle("Agregar Nota")
                .setMessage("Ingresa una nota para esta ubicación:")
                .setView(inputNote)
                .setPositiveButton("Guardar", (dialog, which) -> saveLocationWithNote(latLng, inputNote.getText().toString()))
                .setNegativeButton("Cancelar", null)
                .show();
    }

    // Guardar ubicación y su nota en SharedPreferences
    private void saveLocationWithNote(LatLng latLng, String note) {
        long timestamp = System.currentTimeMillis();  // Obtener el tiempo actual en milisegundos
        String locationKey = latLng.latitude + "," + latLng.longitude;  // Representa la ubicación
        String value = note + ";" + timestamp;  // Guardar la nota junto con el timestamp
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(locationKey, value);
        editor.apply();
    }

    // Recuperar las notas de las ubicaciones guardadas
    private void loadSavedLocations() {
        Set<String> savedLocations = sharedPreferences.getAll().keySet();
        long currentTime = System.currentTimeMillis();

        for (String locationKey : savedLocations) {
            String value = sharedPreferences.getString(locationKey, "");
            if (value != null && !value.isEmpty()) {
                String[] parts = value.split(";");
                if (parts.length == 2) { // Verifica que haya dos partes separadas por ';'
                    try {
                        String note = parts[0]; // La nota
                        long timestamp = Long.parseLong(parts[1]); // El timestamp

                        // Eliminar notas más antiguas de 24 horas
                        if (currentTime - timestamp > 86400000) {
                            sharedPreferences.edit().remove(locationKey).apply();
                            continue;
                        }

                        // Extraer coordenadas de `locationKey`
                        String[] coords = locationKey.split(",");
                        if (coords.length == 2) { // Verificar formato correcto de las coordenadas
                            double lat = Double.parseDouble(coords[0]);
                            double lng = Double.parseDouble(coords[1]);
                            LatLng latLng = new LatLng(lat, lng);

                            // Agregar marcador al mapa
                            mMap.addMarker(new MarkerOptions().position(latLng).title("Ubicación Guardada - Nota: " + note));
                        } else {
                            Log.e("loadSavedLocations", "Formato inválido en coordenadas: " + locationKey);
                        }
                    } catch (NumberFormatException e) {
                        Log.e("loadSavedLocations", "Error al procesar datos de ubicación: " + e.getMessage());
                    }
                }
            }
        }
    }
}
