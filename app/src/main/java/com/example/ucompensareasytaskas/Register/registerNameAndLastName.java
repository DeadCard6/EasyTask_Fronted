package com.example.ucompensareasytaskas.Register;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;


import com.example.ucompensareasytaskas.R;
import com.example.ucompensareasytaskas.home;

public class registerNameAndLastName extends AppCompatActivity {

    private EditText nameUserInput, lastNameUserInput;
    private Button continueButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_name_and_last_name);

        nameUserInput = findViewById(R.id.nameUser_input);
        lastNameUserInput = findViewById(R.id.lastNameUser_input);
        continueButton = findViewById(R.id.continue_button);

        continueButton.setOnClickListener(v -> {
            String firstName = nameUserInput.getText().toString();
            String lastName = lastNameUserInput.getText().toString();
            if (!firstName.isEmpty() && !lastName.isEmpty()) {
                // Send data to next activity
                Intent intent = new Intent(registerNameAndLastName.this, confirmPassword.class);
                intent.putExtra("first_name", firstName);
                intent.putExtra("last_name", lastName);
                startActivity(intent);
            } else {
                Toast.makeText(registerNameAndLastName.this, "Por favor ingrese su nombre y apellido.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
