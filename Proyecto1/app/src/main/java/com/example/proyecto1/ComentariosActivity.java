package com.example.proyecto1;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class ComentariosActivity extends AppCompatActivity {

    private EditText editTextComentario;
    private TextView textViewComentarios;
    private Button btnGuardarComentario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comentarios);

        // Vincula los elementos del layout
        editTextComentario = findViewById(R.id.editTextComentario);
        textViewComentarios = findViewById(R.id.textViewComentarios);
        btnGuardarComentario = findViewById(R.id.btnGuardarComentario);

        // Cargar los comentarios guardados cuando la actividad se cree
        cargarComentarios();

        // Configura el botón para guardar un nuevo comentario
        btnGuardarComentario.setOnClickListener(view -> {
            String comentario = editTextComentario.getText().toString();
            if (!comentario.isEmpty()) {
                guardarComentario(comentario);
                editTextComentario.setText(""); // Limpiar el campo de texto después de guardar
            } else {
                Toast.makeText(this, "Por favor escribe un comentario", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Método para guardar el comentario en un archivo de texto
    private void guardarComentario(String comentario) {
        try {
            File file = new File(getFilesDir(), "comentarios.txt");
            FileWriter fileWriter = new FileWriter(file, true);  // 'true' para añadir al archivo
            PrintWriter printWriter = new PrintWriter(fileWriter);
            printWriter.println(comentario);  // Escribir el comentario
            printWriter.close(); // Cerrar el flujo

            // Mostrar mensaje de éxito
            Toast.makeText(this, "Comentario guardado", Toast.LENGTH_SHORT).show();
            cargarComentarios();  // Actualizar la lista de comentarios mostrados
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error al guardar el comentario", Toast.LENGTH_SHORT).show();
        }
    }

    // Método para cargar los comentarios desde el archivo de texto
    private void cargarComentarios() {
        try {
            File file = new File(getFilesDir(), "comentarios.txt");
            if (file.exists()) {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                StringBuilder comentarios = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    comentarios.append(line).append("\n");
                }
                reader.close();

                // Mostrar los comentarios en el TextView
                textViewComentarios.setText(comentarios.toString());
            } else {
                textViewComentarios.setText("No hay comentarios guardados.");
            }
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error al cargar los comentarios", Toast.LENGTH_SHORT).show();
        }
    }
}
