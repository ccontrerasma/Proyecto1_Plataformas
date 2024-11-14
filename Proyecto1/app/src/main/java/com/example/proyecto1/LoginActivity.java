package com.example.proyecto1;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.proyecto1.databinding.ActivityMainBinding;
import java.io.BufferedReader;


import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import java.io.FileReader;
import java.io.IOException;
public class LoginActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private ActivityResultLauncher<Intent> accountResultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        EditText editUsername = binding.editUsername;
        EditText editPassword = binding.editPassword;
        Button btnLogin = binding.btlnLogin;
        Button btnAddAccount = binding.btnAddAcount;

        // Inicializamos el ActivityResultLauncher para manejar los resultados de AccountActivity
        accountResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        String newUserData = result.getData().getStringExtra("newAccount");
                        Toast.makeText(getApplicationContext(), "Nueva cuenta creada", Toast.LENGTH_SHORT).show();
                    }
                });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = editUsername.getText().toString();
                String password = editPassword.getText().toString();

                if (validateLogin(username, password)) {
                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    intent.putExtra("username", username);
                    // Limpiamos el Activity Stack para evitar regresar a LoginActivity
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish(); // Cerramos LoginActivity después de iniciar HomeActivity
                } else {
                    Toast.makeText(getApplicationContext(), "Cuenta no encontrada", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "Error en la autentificación");
                }
            }
        });

        btnAddAccount.setOnClickListener(view1 -> {
            Intent intent = new Intent(getApplicationContext(), AccountActivity.class);
            accountResultLauncher.launch(intent); // Usamos ActivityResultLauncher para esperar resultados
        });
    }

    // Método que valida los datos de login con el archivo cuentas.txt
    private boolean validateLogin(String username, String password) {
        try (BufferedReader reader = new BufferedReader(new FileReader(getFilesDir() + "/cuentas.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] accountData = line.split(","); // El archivo debe tener el formato username,password
                if (accountData.length == 2 && accountData[0].equals(username) && accountData[1].equals(password)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}