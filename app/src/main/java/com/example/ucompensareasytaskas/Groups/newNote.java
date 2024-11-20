package com.example.ucompensareasytaskas.Groups;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.ImageButton;
import android.widget.Toast;

import android.content.pm.PackageManager;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.Manifest;

import com.example.ucompensareasytaskas.R;
import com.example.ucompensareasytaskas.api.ApiService;
import com.example.ucompensareasytaskas.api.RetrofitClient;
import com.example.ucompensareasytaskas.api.model.Note;
import com.example.ucompensareasytaskas.api.model.User;
import com.example.ucompensareasytaskas.home;
import com.example.ucompensareasytaskas.menu;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class newNote extends AppCompatActivity {

    private ImageButton imageButton;
    private String currentPhotoPath;
    private final ApiService apiService = RetrofitClient.getApiService();

    private static final int REQUEST_IMAGE_GALLERY = 1;
    private static final int REQUEST_IMAGE_CAMERA = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);

        imageButton = findViewById(R.id.image_button);

        imageButton.setOnClickListener(v -> showImagePickerDialog());

        findViewById(R.id.confirmar_button).setOnClickListener(v -> createNote());
    }

    private void showImagePickerDialog() {
        String[] options = {"Seleccionar de galería", "Tomar una foto"};
        new androidx.appcompat.app.AlertDialog.Builder(this)
                .setTitle("Elige una opción")
                .setItems(options, (dialog, which) -> {
                    if (which == 0) {
                        selectImageFromGallery();
                    } else {
                        dispatchTakePictureIntent();
                    }
                })
                .show();
    }

    private void selectImageFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_IMAGE_GALLERY);
    }

    private void dispatchTakePictureIntent() {
        File photoFile;
        try {
            photoFile = createImageFile();
            Uri photoURI = FileProvider.getUriForFile(
                    this,
                    "com.example.ucompensareasytaskas.provider",
                    photoFile
            );
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
            startActivityForResult(intent, REQUEST_IMAGE_CAMERA);
        } catch (IOException e) {
            Toast.makeText(this, "Error al crear archivo de imagen", Toast.LENGTH_SHORT).show();
        }
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        File storageDir = getExternalFilesDir(null);
        File image = File.createTempFile(
                "JPEG_" + timeStamp + "_",
                ".jpg",
                storageDir
        );
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_IMAGE_GALLERY && data != null) {
                Uri selectedImageUri = data.getData();
                imageButton.setImageURI(selectedImageUri);
                currentPhotoPath = selectedImageUri.toString();
            } else if (requestCode == REQUEST_IMAGE_CAMERA) {
                File file = new File(currentPhotoPath);
                if (file.exists()) {
                    Uri uri = Uri.fromFile(file);
                    imageButton.setImageURI(uri);
                }
            }
        }
    }

    private void createNote() {
        // Recuperar el ID del usuario desde SharedPreferences
        SharedPreferences preferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String userId = preferences.getString("userId", null); // Si no existe, devuelve null

        if (userId != null) { // Verifica que el usuario esté autenticado
            // Crear un nuevo objeto Note
            Note newNote = new Note();
            newNote.setTitle("Mi Nota");
            newNote.setDescription("Descripción de la nota");
            newNote.setDate(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date()));
            newNote.setHour(new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date()));
            newNote.setImage(currentPhotoPath);

            // Convertir el userId a Long antes de pasarlo al constructor de User
            Long userIdLong = Long.parseLong(userId);

            // Asignar el usuario actual a la nota
            newNote.setUser(new User(userIdLong)); // Ahora pasamos Long en lugar de String

            // Si la nota no pertenece a un grupo, puedes omitir este paso
            // newNote.setGroup(new Group(1)); // Si es necesario, asigna el grupo

            // Enviar la solicitud para crear la nota
            apiService.createNote(newNote).enqueue(new Callback<Note>() {
                @Override
                public void onResponse(Call<Note> call, Response<Note> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(newNote.this, "Nota creada con éxito", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(newNote.this, "Error al crear la nota", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Note> call, Throwable t) {
                    Toast.makeText(newNote.this, "Error en la solicitud: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            // Si no se ha encontrado el ID del usuario, significa que no ha iniciado sesión
            Toast.makeText(newNote.this, "No se ha iniciado sesión. Por favor, inicia sesión primero.", Toast.LENGTH_SHORT).show();
        }
    }
}
