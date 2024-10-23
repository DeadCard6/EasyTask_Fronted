package com.example.ucompensareasytaskas;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

public class VerificationOTP extends AppCompatActivity {

    private FirebaseAuth auth;
    private EditText codeInput;
    private Button verifyButton, cancelButton;
    private String verificationId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification_otp);

        auth = FirebaseAuth.getInstance();

        codeInput = findViewById(R.id.viewCode_inputSignIn);
        verifyButton = findViewById(R.id.verify_buttonOTP);
        cancelButton = findViewById(R.id.cancel_buttonOTP);

        // Obtener el ID de verificación enviado desde la actividad anterior
        verificationId = getIntent().getStringExtra("verificationId");

        verifyButton.setOnClickListener(v -> {
            String code = codeInput.getText().toString().trim();
            if (code.isEmpty()) {
                Toast.makeText(VerificationOTP.this, "Ingrese el código de verificación", Toast.LENGTH_SHORT).show();
            } else {
                verifyCode(code);
            }
        });

        cancelButton.setOnClickListener(v -> {
            Intent intent = new Intent(VerificationOTP.this, Sign_In.class);
            startActivity(intent);
        });
    }

    private void verifyCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        signInWithCredential(credential);
    }

    private void signInWithCredential(PhoneAuthCredential credential) {
        auth.signInWithCredential(credential)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Intent intent = new Intent(VerificationOTP.this, home.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(VerificationOTP.this, "Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
