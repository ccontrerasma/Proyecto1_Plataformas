package com.example.proyecto1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.example.proyecto1.databinding.ActivityHomeBinding;

public class HomeActivity extends AppCompatActivity {

    private ActivityHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Configuración de botón Buscar
        binding.btnBuscar.setOnClickListener(view -> {
            Intent intentBuscar = new Intent(HomeActivity.this, BuscarActivity.class);
            startActivity(intentBuscar);
        });

        // Configuración de botón Cerca de mí
       // binding.btnCercaDeMi.setOnClickListener(view -> {
        //    Intent intentLista = new Intent(HomeActivity.this, ListaActivity.class);
        //    startActivity(intentLista);
       // });
    }
}
