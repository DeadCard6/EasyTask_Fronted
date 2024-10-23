package com.example.ucompensareasytaskas;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class Sign_In extends AppCompatActivity {

    private FirebaseAuth auth;
    private EditText loginNumInput;
    private Button enterButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        auth = FirebaseAuth.getInstance();

        loginNumInput = findViewById(R.id.loginNum_input);
        enterButton = findViewById(R.id.enter_button);

        enterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber = loginNumInput.getText().toString().trim();
                if (phoneNumber.isEmpty()) {
                    Toast.makeText(Sign_In.this, "Ingrese un número de celular", Toast.LENGTH_SHORT).show();
                } else {
                    sendVerificationCode(phoneNumber);
                }
            }
        });
    }

    private void sendVerificationCode(String phoneNumber) {
        PhoneAuthOptions options = PhoneAuthOptions.newBuilder(auth)
                .setPhoneNumber(phoneNumber)       // El número de teléfono a verificar
                .setTimeout(60L, TimeUnit.SECONDS) // Tiempo de espera
                .setActivity(this)                 // Actividad actual
                .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(PhoneAuthCredential credential) {
                        // Verificación completada automáticamente
                    }

                    @Override
                    public void onVerificationFailed(FirebaseException e) {
                        Log.e("Sign_In", "Error: " + e.getMessage());
                        Toast.makeText(Sign_In.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCodeSent(String verificationId, PhoneAuthProvider.ForceResendingToken token) {
                        // Guardar el ID de verificación y continuar a la pantalla OTP
                        Intent intent = new Intent(Sign_In.this, VerificationOTP.class);
                        intent.putExtra("verificationId", verificationId);
                        startActivity(intent);
                    }
                })
                .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }
}
