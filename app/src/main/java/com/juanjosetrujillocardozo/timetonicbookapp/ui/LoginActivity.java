package com.juanjosetrujillocardozo.timetonicbookapp.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.juanjosetrujillocardozo.timetonicbookapp.R;
import com.juanjosetrujillocardozo.timetonicbookapp.model.LoginRequest;
import com.juanjosetrujillocardozo.timetonicbookapp.model.LoginResponse;
import com.juanjosetrujillocardozo.timetonicbookapp.network.TimetonicApiService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextEmail, editTextPassword;
    private Button buttonLogin;
    private TimetonicApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);

        // Inicializa Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://timetonic.com/api/") // Ajusta esto según la URL real de la API
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(TimetonicApiService.class);

        TimetonicApiService apiService = retrofit.create(TimetonicApiService.class);




        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editTextEmail.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();

                // Crea el objeto de solicitud de inicio de sesión
                LoginRequest loginRequest = new LoginRequest(email, password);

                // Realiza la llamada al endpoint de inicio de sesión
                Call<LoginResponse> call = apiService.createAppKey(loginRequest);
                call.enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        if (response.isSuccessful()) {
                            LoginResponse loginResponse = response.body();
                            String sessionToken = loginResponse.getSessionToken();

                            // Almacena el token de sesión y procede a la página de inicio
                            saveSessionToken(sessionToken);
                            startActivity(new Intent(LoginActivity.this, LandingActivity.class));
                            finish();
                        } else {
                            Toast.makeText(LoginActivity.this, R.string.login_error, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        Toast.makeText(LoginActivity.this, R.string.network_error, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void saveSessionToken(String sessionToken) {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("sessionToken", sessionToken);
        editor.apply();
    }
}
