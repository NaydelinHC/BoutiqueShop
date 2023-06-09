package com.example.inventario;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;


import java.util.HashMap;
import java.util.Map;


public class Login extends AppCompatActivity {

    private EditText txtEmail;
    private EditText txtPass;
    private Button BtnLogin;
    private TextView BtnForgotPassword;
    private TextView btnRegistrarse;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);

        txtEmail = findViewById(R.id.emailEditText);
        txtPass = findViewById(R.id.ContraseñaET);
        sharedPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        BtnForgotPassword = findViewById(R.id.BtnForgotPassword);
        BtnLogin = findViewById(R.id.BtnLogin);
        btnRegistrarse = findViewById(R.id.btnRegistrarse);

        BtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickBtnIniciarSesion(view);
            }
        });

        BtnLogin.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                vibrateButton(BtnLogin);
                return true;
            }
        });

        btnRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectToRegistro();
            }
        });

        BtnForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openForgotPassActivity();
            }
        });

        if (isLoggedIn()) {
            redirectToMainActivity();
        }
    }

    private void clickBtnIniciarSesion(View view) {
        final View finalView = view;
        String email = txtEmail.getText().toString().trim();
        String password = txtPass.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Snackbar.make(view, "Por favor completa todos los campos", Snackbar.LENGTH_LONG).show();
            return;
        }

        authenticateUser(email, password, finalView);
    }

    private void authenticateUser(final String email, final String password, final View view) {
        String url = "http://192.168.56.1/inventarioboutique/login.php";

        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.equals("Login successful")) {
                            Snackbar.make(view, "Login successful", Snackbar.LENGTH_LONG).show();
                            saveLoginStatus();
                            redirectToMainActivity();
                        } else {
                            Snackbar.make(view, "Invalid credentials", Snackbar.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Snackbar.make(view, "Error: " + error.toString(), Snackbar.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                params.put("password", password);
                return params;
            }
        };

        queue.add(stringRequest);
    }

    private void redirectToMainActivity() {
        Intent intent = new Intent(Login.this, MainActivity.class);
        startActivity(intent);
    }

    private void redirectToRegistro() {
        Intent intent = new Intent(Login.this, Registro.class);
        startActivity(intent);
    }

    private void openForgotPassActivity() {
        Intent intent = new Intent(Login.this, RecuContra.class);
        startActivity(intent);
    }

    private void saveLoginStatus() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isLoggedIn", true);
        editor.apply();
    }

    private boolean isLoggedIn() {
        return sharedPreferences.getBoolean("isLoggedIn", false);
    }

    private void vibrateButton(Button button) {
        final float originalY = button.getTranslationY();
        button.animate()
                .translationYBy(10f)
                .setDuration(50)
                .withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        button.animate()
                                .translationYBy(-20f)
                                .setDuration(50)
                                .withEndAction(new Runnable() {
                                    @Override
                                    public void run() {
                                        button.animate()
                                                .translationYBy(10f)
                                                .setDuration(50)
                                                .start();
                                    }
                                })
                                .start();
                    }
                })
                .start();
    }
}


