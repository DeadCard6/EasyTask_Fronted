package com.example.ucompensareasytaskas.Groups;

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

import com.example.ucompensareasytaskas.R;
import com.example.ucompensareasytaskas.home;
import com.example.ucompensareasytaskas.menu;

public class joinGroupId extends AppCompatActivity {

    ImageButton cancelar_button;
    ImageButton confirmar_button;
    ImageButton groups_button;
    ImageButton home_button;
    ImageButton add_button;
    ImageButton menu_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_join_group_id);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        cancelar_button=(ImageButton)findViewById(R.id.cancelar_button);
        confirmar_button=(ImageButton)findViewById(R.id.confirmar_button);
        groups_button=(ImageButton)findViewById(R.id.groups_button);
        home_button=(ImageButton)findViewById(R.id.home_button);
        add_button=(ImageButton)findViewById(R.id.add_button);
        menu_button=(ImageButton)findViewById(R.id.menu_button);

        menu_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(joinGroupId.this, menu.class);
                startActivity(i);
            }
        });
        cancelar_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(joinGroupId.this, HomeGroups.class);
                startActivity(i);
            }
        });
        confirmar_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(joinGroupId.this, HomeGroups.class);
                startActivity(i);
            }
        });

        groups_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(joinGroupId.this, HomeGroups.class);
                startActivity(i);
            }
        });
        home_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(joinGroupId.this, home.class);
                startActivity(i);
            }
        });
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(joinGroupId.this, newNote.class);
                startActivity(i);
            }
        });
    }
}