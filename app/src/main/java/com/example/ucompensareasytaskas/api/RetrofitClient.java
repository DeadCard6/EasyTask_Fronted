package com.example.ucompensareasytaskas.api;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static final String BASE_URL = "http://192.168.1.23:8080";
    private static Retrofit retrofit = null;

    // Singleton para obtener una instancia de Retrofit
    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())  // Conversor para JSON
                    .build();
        }
        return retrofit;
    }

    // Método para obtener la instancia de ApiService
    public static ApiService getApiService() {
        if (retrofit == null) {
            // Configuramos OkHttpClient con los timeout personalizados
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(30, TimeUnit.SECONDS)  // Timeout para conexión
                    .readTimeout(30, TimeUnit.SECONDS)     // Timeout para leer datos
                    .writeTimeout(30, TimeUnit.SECONDS)    // Timeout para escribir datos
                    .build();

            // Configuramos Retrofit con el cliente OkHttp personalizado
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(okHttpClient)  // Usamos el cliente con timeout personalizado
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(ApiService.class);
    }

}
