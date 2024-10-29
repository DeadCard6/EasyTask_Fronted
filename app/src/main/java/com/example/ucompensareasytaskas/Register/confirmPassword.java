package com.example.ucompensareasytaskas.Register;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.ucompensareasytaskas.R;
import com.example.ucompensareasytaskas.home;
import com.example.ucompensareasytaskas.menu;
import com.example.ucompensareasytaskas.menuCambiarContrasena;

public class confirmPassword extends AppCompatActivity {

    Button enter_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_confirm_password);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        enter_button=(Button)findViewById(R.id.enter_button);

        enter_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(confirmPassword.this, home.class);
                startActivity(i);
            }
        });

    }
}