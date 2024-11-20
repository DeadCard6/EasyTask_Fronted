package com.example.ucompensareasytaskas.Register;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.ucompensareasytaskas.R;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.FirebaseException;

import java.util.concurrent.TimeUnit;

public class verificationCode extends AppCompatActivity {

    Button cancel_button;
    Button verify_button;
    EditText codeEditText; // EditText para el código de verificación
    FirebaseAuth auth; // Instancia de FirebaseAuth
    String verificationId; // ID de verificación

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_verification_code);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Inicialización de Firebase
        FirebaseApp.initializeApp(this); // Asegúrate de haber configurado Firebase correctamente en el proyecto

        cancel_button = findViewById(R.id.cancel_button);
        verify_button = findViewById(R.id.verify_button);
        codeEditText = findViewById(R.id.viewCode_input); // Suponiendo que tienes un EditText para el código
        auth = FirebaseAuth.getInstance();

        // Obtener el ID de verificación del intent
        verificationId = getIntent().getStringExtra("verificationId");

        // Establecer el flujo de verificación
        startPhoneVerification("+123456789");  // Sustituye con el número real

        verify_button.setOnClickListener(view -> {
            String code = codeEditText.getText().toString();
            if (!code.isEmpty() && verificationId != null) {
                PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
                auth.signInWithCredential(credential)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Intent i = new Intent(verificationCode.this, registerNameAndLastName.class);
                                startActivity(i);
                            } else {
                                Toast.makeText(verificationCode.this, "Código incorrecto", Toast.LENGTH_SHORT).show();
                            }
                        });
            } else {
                Toast.makeText(this, "Por favor, ingrese el código.", Toast.LENGTH_SHORT).show();
            }
        });

        cancel_button.setOnClickListener(view -> {
            Intent i = new Intent(verificationCode.this, registerPhone.class);
            startActivity(i);
        });
    }

    // Método para iniciar la verificación del teléfono
    private void startPhoneVerification(String phoneNumber) {
        PhoneAuthOptions options = PhoneAuthOptions.newBuilder(auth)
                .setPhoneNumber(phoneNumber) // Número de teléfono con código de país
                .setTimeout(60L, TimeUnit.SECONDS) // Tiempo límite para ingresar el código
                .setActivity(this) // Actividad actual
                .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(PhoneAuthCredential credential) {
                        // Verificación automática completada
                        auth.signInWithCredential(credential).addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Intent i = new Intent(verificationCode.this, registerNameAndLastName.class);
                                startActivity(i);
                            }
                        });
                    }

                    @Override
                    public void onVerificationFailed(FirebaseException e) {
                        // Error de verificación
                        Toast.makeText(verificationCode.this, "Error en la verificación: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onCodeSent(String verificationId, PhoneAuthProvider.ForceResendingToken token) {
                        // Código enviado al usuario
                        verificationId = verificationId; // Guarda el verificationId para usarlo más tarde
                    }
                })
                .build();

        // Inicia la verificación del número de teléfono
        PhoneAuthProvider.verifyPhoneNumber(options);
    }
}
