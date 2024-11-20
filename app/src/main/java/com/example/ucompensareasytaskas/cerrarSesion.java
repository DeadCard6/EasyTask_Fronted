package com.example.ucompensareasytaskas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.ucompensareasytaskas.Groups.HomeGroups;
import com.example.ucompensareasytaskas.Groups.newNote;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

public class cerrarSesion extends AppCompatActivity {

    ImageButton groups_button;
    ImageButton home_button;
    ImageButton add_button;
    ImageButton cancelar_button;
    Button confirm_button;
    ImageButton menu_button;

    // Google Sign-In client
    private GoogleSignInClient googleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cerrar_sesion);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize views
        groups_button = findViewById(R.id.groups_button);
        home_button = findViewById(R.id.home_button);
        add_button = findViewById(R.id.add_button);
        cancelar_button = findViewById(R.id.cancelar_button);
        confirm_button = findViewById(R.id.confirm_button);
        menu_button = findViewById(R.id.menu_button);

        // Initialize Google Sign-In client
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(this, gso);

        // Menu button click listener
        menu_button.setOnClickListener(view -> {
            Intent i = new Intent(cerrarSesion.this, menu.class);
            startActivity(i);
        });

        // Groups button click listener
        groups_button.setOnClickListener(view -> {
            Intent i = new Intent(cerrarSesion.this, HomeGroups.class);
            startActivity(i);
        });

        // Home button click listener
        home_button.setOnClickListener(view -> {
            Intent i = new Intent(cerrarSesion.this, home.class);
            startActivity(i);
        });

        // Add button click listener
        add_button.setOnClickListener(view -> {
            Intent i = new Intent(cerrarSesion.this, newNote.class);
            startActivity(i);
        });

        // Cancel button click listener
        cancelar_button.setOnClickListener(view -> {
            Intent i = new Intent(cerrarSesion.this, menu.class);
            startActivity(i);
        });

        // Confirm button click listener (log out)
        confirm_button.setOnClickListener(view -> {
            // Sign out from Google
            googleSignInClient.signOut().addOnCompleteListener(this, task -> {
                // After signing out from Google, navigate to login screen
                Toast.makeText(cerrarSesion.this, "Sesión cerrada", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(cerrarSesion.this, loginHome.class);
                startActivity(i);
                finish();  // Finish this activity so it won't be in the back stack
            });
        });
    }
}
