package com.example.ucompensareasytaskas.Register;

import android.content.Intent;
import android.os.Bundle;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;


import com.example.ucompensareasytaskas.R;



public class registerPhone extends AppCompatActivity {

    private EditText phoneNumbInput;
    private Button confirmButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_phone);

        phoneNumbInput = findViewById(R.id.phoneNumb_input);
        confirmButton = findViewById(R.id.confirm_button);

        confirmButton.setOnClickListener(v -> {
            String phoneNumber = phoneNumbInput.getText().toString();
            if (!phoneNumber.isEmpty()) {
                // Save phone number to a shared preference or pass to next activity
                Intent intent = new Intent(registerPhone.this, registerUser.class);
                intent.putExtra("phone_number", phoneNumber);
                startActivity(intent);
            } else {
                Toast.makeText(registerPhone.this, "Por favor ingrese su número de celular.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
