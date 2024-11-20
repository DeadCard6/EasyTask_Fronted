package com.example.ucompensareasytaskas.Groups;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.ucompensareasytaskas.R;
import com.example.ucompensareasytaskas.home;
import com.example.ucompensareasytaskas.menu;
import com.example.ucompensareasytaskas.ubicaciones;
import com.github.clans.fab.FloatingActionButton;

public class HomeGroups extends AppCompatActivity {

    ImageButton newGroup_button;
    ImageButton joinGroup_button;
    ImageButton groups_button;
    ImageButton home_button;
    ImageButton add_button;
    ImageButton menu_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_groups);

        newGroup_button=(ImageButton)findViewById(R.id.newGroup_button);
        joinGroup_button=(ImageButton)findViewById(R.id.joinGroup_button);
        groups_button=(ImageButton)findViewById(R.id.groups_button);
        home_button=(ImageButton)findViewById(R.id.home_button);
        add_button=(ImageButton)findViewById(R.id.add_button);
        menu_button=(ImageButton)findViewById(R.id.menu_button);

        FloatingActionButton fabUbi = findViewById(R.id.fab_ubi);


        // Configura el listener para el botón fabUbi
        fabUbi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Acción para el botón de ubicación
                Toast.makeText(HomeGroups.this, "Botón Ubicación presionado", Toast.LENGTH_SHORT).show();

                // Redirigir a la actividad ubicacion
                Intent intent = new Intent(HomeGroups.this, ubicaciones.class);
                startActivity(intent);
            }
        });

        menu_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HomeGroups.this, menu.class);
                startActivity(i);
            }
        });

        newGroup_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HomeGroups.this, newGroupName.class);
                startActivity(i);
            }
        });
        joinGroup_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HomeGroups.this, joinGroupId.class);
                startActivity(i);
            }
        });

        groups_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HomeGroups.this, HomeGroups.class);
                startActivity(i);
            }
        });
        home_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HomeGroups.this, home.class);
                startActivity(i);
            }
        });
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HomeGroups.this, newNote.class);
                startActivity(i);
            }
        });
    }
}