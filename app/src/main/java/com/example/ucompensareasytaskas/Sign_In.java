package com.example.ucompensareasytaskas;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ucompensareasytaskas.api.model.ApiResponse;
import com.example.ucompensareasytaskas.api.model.LoginRequest;
import com.example.ucompensareasytaskas.api.ApiService;
import com.example.ucompensareasytaskas.api.RetrofitClient;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Sign_In extends AppCompatActivity {

    private EditText loginNumInput, loginPassInput;
    private Button enterButton, showPasswordButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        loginNumInput = findViewById(R.id.loginNum_input);
        loginPassInput = findViewById(R.id.loginPass_input);
        enterButton = findViewById(R.id.enter_button);
        showPasswordButton = findViewById(R.id.btnShowPassword);

        // Lógica para mostrar/ocultar la contraseña
        showPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (loginPassInput.getInputType() == InputType.TYPE_TEXT_VARIATION_PASSWORD) {
                    loginPassInput.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    loginPassInput.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }

                // Mueve el cursor al final después de cambiar el tipo de input
                loginPassInput.setSelection(loginPassInput.getText().length());
            }
        });

        enterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser();
            }
        });
    }

    private void loginUser() {
        // Obtener los valores ingresados por el usuario
        String phoneNumber = loginNumInput.getText().toString().trim();
        String password = loginPassInput.getText().toString().trim();

        if (phoneNumber.isEmpty() || password.isEmpty()) {
            Toast.makeText(Sign_In.this, "Por favor ingresa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        // Crear el objeto LoginRequest
        LoginRequest loginRequest = new LoginRequest(phoneNumber, password);

        // Llamar al servicio de API
        ApiService apiService = RetrofitClient.getApiService();
        Call<ApiResponse> call = apiService.loginUser(loginRequest);

        // Realizar la llamada de la API
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Aquí manejamos la respuesta en caso de éxito
                    ApiResponse apiResponse = response.body();
                    String message = apiResponse.getMessage();
                    Long userId = apiResponse.getUserId(); // Usamos Long para userId

                    // Guardar el userId en SharedPreferences
                    SharedPreferences preferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putLong("user", userId); // Guardamos el userId como Long
                    editor.apply();

                    // Mostrar mensaje de éxito
                    Toast.makeText(Sign_In.this, message, Toast.LENGTH_SHORT).show();

                    // Ir a la siguiente pantalla (home)
                    Intent intent = new Intent(Sign_In.this, home.class);
                    startActivity(intent);
                    finish(); // Esto asegura que no puedas volver a la pantalla de login al presionar el botón de retroceso
                } else {
                    // Manejar error en la autenticación
                    try {
                        // Leer el cuerpo de la respuesta para mostrar el mensaje de error
                        String errorResponse = response.errorBody().string();
                        Toast.makeText(Sign_In.this, "Error: " + errorResponse, Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(Sign_In.this, "Error al procesar la respuesta", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                // Manejar error en la solicitud
                Toast.makeText(Sign_In.this, "Error de conexión: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
