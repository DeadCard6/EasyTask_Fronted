package com.example.ucompensareasytaskas.Register;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ucompensareasytaskas.R;
import com.example.ucompensareasytaskas.api.ApiService;
import com.example.ucompensareasytaskas.api.RetrofitClient;
import com.example.ucompensareasytaskas.api.model.ApiResponse;
import com.example.ucompensareasytaskas.api.model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class confirmPassword extends AppCompatActivity {

    private EditText passInput, verifyPassInput;
    private Button enterButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_password);

        passInput = findViewById(R.id.pass_input);
        verifyPassInput = findViewById(R.id.verifyPass_input);
        enterButton = findViewById(R.id.enter_button);

        enterButton.setOnClickListener(v -> {
            String password = passInput.getText().toString();
            String confirmPassword = verifyPassInput.getText().toString();

            if (password.equals(confirmPassword)) {
                // Retrieve previous data from Intent
                String firstName = getIntent().getStringExtra("first_name");
                String lastName = getIntent().getStringExtra("last_name");
                String phoneNumber = getIntent().getStringExtra("phone_number");

                // Send data to the backend
                registerUserToApi(firstName, lastName, phoneNumber, password);
            } else {
                Toast.makeText(confirmPassword.this, "Las contraseñas no coinciden.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void registerUserToApi(String firstName, String lastName, String phoneNumber, String password) {
        User user = new User();
        user.setName(firstName);
        user.setSecondName(lastName);
        user.setPhoneNumber(phoneNumber);
        user.setPassword(password);

        ApiService apiService = RetrofitClient.getApiService();
        apiService.registerUser(user).enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(confirmPassword.this, "Usuario registrado exitosamente.", Toast.LENGTH_SHORT).show();

                    // Redirigir a la siguiente pantalla (profileUsername) para agregar la imagen de perfil
                    Intent intent = new Intent(confirmPassword.this, profileUsername.class);
                    // Pasamos los datos al siguiente Activity
                    intent.putExtra("first_name", firstName);
                    intent.putExtra("last_name", lastName);
                    intent.putExtra("phone_number", phoneNumber);
                    intent.putExtra("password", password);  // Puedes enviar la contraseña si también la necesitas en la siguiente actividad
                    startActivity(intent);

                } else {
                    Toast.makeText(confirmPassword.this, "Error al registrar el usuario.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Toast.makeText(confirmPassword.this, "Error de conexión.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
