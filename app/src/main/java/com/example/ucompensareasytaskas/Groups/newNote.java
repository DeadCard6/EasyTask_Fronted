package com.example.ucompensareasytaskas.Groups;

import android.content.Intent;
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
import com.example.ucompensareasytaskas.home;
import com.example.ucompensareasytaskas.menu;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class newNote extends AppCompatActivity {

    private static final int REQUEST_IMAGE_GALLERY = 1;
    private static final int REQUEST_IMAGE_CAMERA = 2;

    ImageButton groups_button;
    ImageButton home_button;
    ImageButton add_button;
    ImageButton menu_button;
    ImageButton imageButton; // Botón de selección de imagen
    String currentPhotoPath; // Ruta de la imagen capturada

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_new_note);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        groups_button = findViewById(R.id.groups_button);
        home_button = findViewById(R.id.home_button);
        add_button = findViewById(R.id.add_button);
        menu_button = findViewById(R.id.menu_button);
        imageButton = findViewById(R.id.image_button);  // Botón para seleccionar imagen

        imageButton.setOnClickListener(v -> showImagePickerDialog());

        // Otros botones y eventos omitidos por brevedad
    }

    // Mostrar opciones para seleccionar imagen o tomar foto
    private void showImagePickerDialog() {
        String[] options = {"Seleccionar de galería", "Tomar una foto"};
        new androidx.appcompat.app.AlertDialog.Builder(this)
                .setTitle("Elige una opción")
                .setItems(options, (dialog, which) -> {
                    if (which == 0) {
                        // Seleccionar de la galería
                        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(galleryIntent, REQUEST_IMAGE_GALLERY);
                    } else {
                        // Tomar una foto con la cámara
                        dispatchTakePictureIntent();
                    }
                })
                .show();
    }

    private void dispatchTakePictureIntent() {
        // Verificar si el permiso de la cámara está concedido
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            // Solicitar permiso si no está concedido
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, REQUEST_IMAGE_CAMERA);
        } else {
            // Si el permiso ya está concedido, abrir la cámara
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                File photoFile = null;
                try {
                    photoFile = createImageFile();
                } catch (IOException ex) {
                    Toast.makeText(this, "Error al crear el archivo", Toast.LENGTH_SHORT).show();
                }

                if (photoFile != null) {
                    Uri photoURI = FileProvider.getUriForFile(this, "com.example.ucompensareasytaskas.provider", photoFile);
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAMERA);
                }
            }
        }
    }


    // Crear un archivo temporal para almacenar la imagen capturada
    private File createImageFile() throws IOException {
        // Crear un nombre de archivo único
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(null);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Guardar la ruta del archivo para usarla posteriormente
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_IMAGE_GALLERY && data != null) {
                // Imagen seleccionada de la galería
                Uri selectedImageUri = data.getData();
                imageButton.setImageURI(selectedImageUri);  // Mostrar imagen seleccionada en el botón
            } else if (requestCode == REQUEST_IMAGE_CAMERA) {
                // Imagen capturada con la cámara
                File file = new File(currentPhotoPath);
                if (file.exists()) {
                    Uri uri = Uri.fromFile(file);
                    imageButton.setImageURI(uri);  // Mostrar imagen capturada en el botón
                }
            }
        }
    }
}
