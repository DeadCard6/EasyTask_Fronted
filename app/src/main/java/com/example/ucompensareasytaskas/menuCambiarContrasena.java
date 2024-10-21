package com.example.ucompensareasytaskas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.ucompensareasytaskas.Groups.HomeGroups;
import com.example.ucompensareasytaskas.Groups.newNote;

public class menuCambiarContrasena extends AppCompatActivity {

    ImageButton groups_button;
    ImageButton home_button;
    ImageButton add_button;
    Button confirmar_button;
    ImageButton menu_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_menu_cambiar_contrasena);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        groups_button=(ImageButton)findViewById(R.id.groups_button);
        home_button=(ImageButton)findViewById(R.id.home_button);
        add_button=(ImageButton)findViewById(R.id.add_button);
        confirmar_button=(Button)findViewById(R.id.confirmar_button);
        menu_button=(ImageButton)findViewById(R.id.menu_button);

        menu_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(menuCambiarContrasena.this, menu.class);
                startActivity(i);
            }
        });
        groups_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(menuCambiarContrasena.this, HomeGroups.class);
                startActivity(i);
            }
        });
        home_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(menuCambiarContrasena.this, home.class);
                startActivity(i);
            }
        });
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(menuCambiarContrasena.this, newNote.class);
                startActivity(i);
            }
        });
        confirmar_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(menuCambiarContrasena.this, menu.class);
                startActivity(i);
            }
        });
    }
}