package com.example.ucompensareasytaskas;

import android.content.Intent;
import android.os.Bundle;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Sign_In extends AppCompatActivity {

    private EditText loginNumInput, loginPassInput;
    private Button enterButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        loginNumInput = findViewById(R.id.loginNum_input);
        loginPassInput = findViewById(R.id.loginPass_input);
        enterButton = findViewById(R.id.enter_button);

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
                    // Aquí puedes manejar la respuesta del servidor en caso de éxito
                    Toast.makeText(Sign_In.this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show();

                    // Ir a la siguiente pantalla (home)
                    Intent intent = new Intent(Sign_In.this, home.class);
                    startActivity(intent);
                } else {
                    // Manejar error en la autenticación
                    Toast.makeText(Sign_In.this, "Error en el inicio de sesión", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                // Manejar fallo de conexión
                Log.e("API Error", t.getMessage());
                Toast.makeText(Sign_In.this, "Fallo en la conexión", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
