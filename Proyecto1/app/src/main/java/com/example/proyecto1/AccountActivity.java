package com.example.proyecto1;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;
import java.io.FileOutputStream;
import java.io.IOException;

public class AccountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        EditText editFirstname = findViewById(R.id.editFirstname);
        EditText editLastname = findViewById(R.id.editLastname);
        EditText editEmail = findViewById(R.id.editEmail);
        EditText editPhone = findViewById(R.id.editTextPhone);
        EditText editUsername = findViewById(R.id.editUsername2);
        EditText editPassword = findViewById(R.id.editPassword2);
        Button btnOk = findViewById(R.id.btnOk);
        Button btnCancel = findViewById(R.id.btnCancel);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Crear la nueva cuenta
                String newAccount = editUsername.getText().toString() + "," + editPassword.getText().toString() + "\n";

                // Guardar la cuenta en el archivo cuentas.txt
                try (FileOutputStream fos = openFileOutput("cuentas.txt", MODE_APPEND)) {
                    fos.write(newAccount.getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                // Devolver la cuenta a LoginActivity
                Intent resultIntent = new Intent();
                resultIntent.putExtra("newAccount", newAccount);
                setResult(RESULT_OK, resultIntent);
                finish(); // Cerramos AccountActivity
            }
        });

        btnCancel.setOnClickListener(v -> {
            // Cancelamos la creación de cuenta y volvemos a LoginActivity
            setResult(RESULT_CANCELED);
            finish();
        });
    }
}