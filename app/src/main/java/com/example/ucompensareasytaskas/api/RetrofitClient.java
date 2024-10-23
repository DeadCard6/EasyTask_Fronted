package com.example.ucompensareasytaskas.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static final String BASE_URL = "http://192.168.1.23:8080/";
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
        return getClient().create(ApiService.class);
    }

}
