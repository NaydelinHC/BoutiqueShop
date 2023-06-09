package com.example.inventario;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.os.Vibrator;
import android.os.Vibrator;
import android.content.Context;
import androidx.appcompat.app.AppCompatActivity;



import androidx.appcompat.app.AppCompatActivity;

public class RecuContra extends AppCompatActivity {

    private EditText emailEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recu_contra);

        emailEditText = findViewById(R.id.emailEditText);

    }

    public void recuperarContraseña(View view) {
        String email = emailEditText.getText().toString();

        if (email.isEmpty()) {
            Toast.makeText(this, "Ingresa tu correo electrónico", Toast.LENGTH_SHORT).show();
        } else {
            enviarCorreoElectronico(email);
        }
    }

    private void enviarCorreoElectronico(String email) {
        String subject = "Recuperación de contraseña";
        String message = "Haz clic en el enlace para cambiar tu contraseña: [ENLACE_AQUÍ]";

        // Aquí debes implementar el código para enviar el correo electrónico a la dirección 'email'
        // Puedes usar bibliotecas de correo electrónico como JavaMail o utilizar servicios de envío de correo electrónico de terceros

        // Ejemplo simulado de envío de correo electrónico
        String correoEmisor = "movilesequipou@gmail.com";
        String contraseñaEmisor = "movilesdos";
        String correoDestinatario = email;

        String uriText =
                "mailto:" + correoDestinatario +
                        "?subject=" + Uri.encode(subject) +
                        "&body=" + Uri.encode(message);

        Uri uri = Uri.parse(uriText);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(uri);
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{correoDestinatario});
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, message);

        try {
            startActivity(Intent.createChooser(intent, "Enviar correo electrónico"));
            Toast.makeText(this, "Correo electrónico enviado a " + email, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "Error al enviar el correo electrónico", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

}
