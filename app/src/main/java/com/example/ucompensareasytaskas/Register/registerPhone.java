package com.example.ucompensareasytaskas.Register;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.ucompensareasytaskas.R;
import com.example.ucompensareasytaskas.Sign_In;
import com.example.ucompensareasytaskas.loginHome;

public class registerPhone extends AppCompatActivity {

    Button confirm_button;
    Button cancel_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register_phone);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        confirm_button=(Button)findViewById(R.id.confirm_button);
        cancel_button=(Button)findViewById(R.id.cancel_button);


        confirm_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(registerPhone.this, verificationCode.class);
                startActivity(i);
            }
        });
        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(registerPhone.this, loginHome.class);
                startActivity(i);
            }
        });

    }
}