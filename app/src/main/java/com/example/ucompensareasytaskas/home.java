package com.example.ucompensareasytaskas;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ucompensareasytaskas.Groups.HomeGroups;
import com.example.ucompensareasytaskas.Groups.NoteAdapter;
import com.example.ucompensareasytaskas.Groups.newNote;
import com.example.ucompensareasytaskas.api.ApiService;
import com.example.ucompensareasytaskas.api.RetrofitClient;
import com.example.ucompensareasytaskas.api.model.Note;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class home extends AppCompatActivity {

    private RecyclerView recyclerView;
    private NoteAdapter noteAdapter;
    private List<Note> notes;

    private ImageButton groups_button;
    private ImageButton home_button;
    private ImageButton add_button;
    private ImageButton menu_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Inicialización de RecyclerView
        recyclerView = findViewById(R.id.recyclerViewNotes);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Obtener el userId desde SharedPreferences
        Long userId = getUserId();

        // Verifica si el userId es válido
        if (userId != -1) { // Asegúrate de que el ID sea válido
            // Fetch notes for a specific user from API
            ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
            Call<List<Note>> call = apiService.getNotesByUser(userId);

            call.enqueue(new Callback<List<Note>>() {
                @Override
                public void onResponse(Call<List<Note>> call, Response<List<Note>> response) {
                    if (response.isSuccessful()) {
                        notes = response.body();
                        if (notes != null && !notes.isEmpty()) {
                            noteAdapter = new NoteAdapter(notes);
                            recyclerView.setAdapter(noteAdapter); // Establecer el adaptador
                        } else {
                            Toast.makeText(home.this, "No hay notas disponibles", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(home.this, "Error al obtener notas", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<List<Note>> call, Throwable t) {
                    Toast.makeText(home.this, "Fallo en la conexión", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(home.this, "No se pudo obtener el ID de usuario", Toast.LENGTH_SHORT).show();
        }

        // Configuración de botones de navegación
        groups_button = findViewById(R.id.groups_button);
        home_button = findViewById(R.id.home_button);
        add_button = findViewById(R.id.add_button);
        menu_button = findViewById(R.id.menu_button);

        menu_button.setOnClickListener(view -> {
            Intent i = new Intent(home.this, menu.class);
            startActivity(i);
        });

        groups_button.setOnClickListener(view -> {
            Intent i = new Intent(home.this, HomeGroups.class);
            startActivity(i);
        });

        home_button.setOnClickListener(view -> {
            Intent i = new Intent(home.this, home.class);
            startActivity(i);
        });

        add_button.setOnClickListener(view -> {
            Intent i = new Intent(home.this, newNote.class);
            startActivity(i);
        });
    }

    private Long getUserId() {
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE); // Asegúrate de que el nombre sea el mismo
        return sharedPreferences.getLong("user", -1); // Devuelve -1 si no está presente
    }
}
