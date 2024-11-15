package com.example.proyecto1;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ListaActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private EdificacionAdapter adapter;
    private List<Edificacion> edificaciones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);

        // Inicializar datos
        edificaciones = cargarEdificaciones();

        // Configurar RecyclerView
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        adapter = new EdificacionAdapter(edificaciones);
        recyclerView.setAdapter(adapter);

        // Configurar el filtro
        EditText filterEditText = findViewById(R.id.filterEditText);
        filterEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.filtrar(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private List<Edificacion> cargarEdificaciones() {
        try {
            // Acceder a los archivos assets
            AssetManager assetManager = getAssets();
            InputStream inputStream = assetManager.open("edificaciones.json");

            // Convertir el InputStream en un String usando InputStreamReader
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

            // Usar Gson para parsear el JSON en una lista de objetos Edificacion
            Gson gson = new Gson();
            Type listType = new TypeToken<List<Edificacion>>() {}.getType();
            List<Edificacion> edificaciones = gson.fromJson(inputStreamReader, listType);

            // Imprimir la lista de edificaciones en la consola
            if (edificaciones != null) {
                for (Edificacion edificacion : edificaciones) {
                    Log.d("Edificacion", "Edificación: " + edificacion.toString());
                }
            }
            return edificaciones;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}