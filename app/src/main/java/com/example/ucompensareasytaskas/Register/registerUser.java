package com.example.ucompensareasytaskas.Register;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.ucompensareasytaskas.R;
import com.example.ucompensareasytaskas.api.ApiService;
import com.example.ucompensareasytaskas.api.RetrofitClient;
import com.example.ucompensareasytaskas.api.model.ApiResponse;
import com.example.ucompensareasytaskas.api.model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class registerUser extends AppCompatActivity {

    private EditText nameInput, lastNameInput, passInput, phoneInput, emailInput;
    private Button continueButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register_user);

        nameInput = findViewById(R.id.nameUser_input);
        lastNameInput = findViewById(R.id.lastNameUser_input);
        passInput = findViewById(R.id.pass_input);
        phoneInput = findViewById(R.id.phoneNumb_input);
        emailInput = findViewById(R.id.email_input);
        continueButton = findViewById(R.id.continue_button);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        continueButton.setOnClickListener(v -> {
            String firstName = nameInput.getText().toString();
            String lastName = lastNameInput.getText().toString();
            String password = passInput.getText().toString();
            String phoneNumber = phoneInput.getText().toString();
            String email = emailInput.getText().toString();

            // Validación de los campos
            if (firstName.isEmpty() || lastName.isEmpty() || password.isEmpty() ||
                    phoneNumber.isEmpty() || email.isEmpty()) {
                Toast.makeText(registerUser.this, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
            } else {
                // Enviar datos al backend
                registerUserToApi(firstName, lastName, phoneNumber, email, password);
            }
        });
    }

    private void registerUserToApi(String firstName, String lastName, String phoneNumber, String email, String password) {
        User user = new User();
        user.setName(firstName);
        user.setSecondName(lastName);
        user.setPhoneNumber(phoneNumber);
        user.setEmail(email);
        user.setPassword(password);

        ApiService apiService = RetrofitClient.getApiService();
        apiService.registerUser(user).enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(registerUser.this, "Usuario registrado exitosamente.", Toast.LENGTH_SHORT).show();

                    // Redirigir al perfil de usuario para agregar imagen
                    Intent intent = new Intent(registerUser.this, profileUsername.class);
                    // Pasamos los datos necesarios para completar el perfil
                    intent.putExtra("first_name", firstName);
                    intent.putExtra("last_name", lastName);
                    intent.putExtra("phone_number", phoneNumber);
                    intent.putExtra("email", email);
                    intent.putExtra("password", password);
                    startActivity(intent);
                } else {
                    Toast.makeText(registerUser.this, "Error al registrar el usuario.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Toast.makeText(registerUser.this, "Error de conexión.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
