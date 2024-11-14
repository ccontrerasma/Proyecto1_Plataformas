package com.example.proyecto1;

import static com.example.proyecto1.R.id.layoutCatedral;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentTransaction;

public class ListaActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Manejar el clic en la franja de la Catedral
        LinearLayout layoutCatedral = findViewById(R.id.layoutCatedral);
        layoutCatedral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, new CatedralFragment());
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        // Manejar el clic en la franja del Claustro
        LinearLayout layoutClaustro = findViewById(R.id.layoutClaustro);
        layoutClaustro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, new ClaustroFragment());
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
    }
}