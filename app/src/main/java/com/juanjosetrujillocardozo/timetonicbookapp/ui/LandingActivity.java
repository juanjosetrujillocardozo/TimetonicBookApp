package com.juanjosetrujillocardozo.timetonicbookapp.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.juanjosetrujillocardozo.timetonicbookapp.R;
import com.juanjosetrujillocardozo.timetonicbookapp.model.Book;
import com.juanjosetrujillocardozo.timetonicbookapp.network.TimetonicApiService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LandingActivity extends AppCompatActivity {

    private TimetonicApiService apiService;
    private RecyclerView recyclerView;
    private BookAdapter bookAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://timetonic.com/api/") // Reemplaza con la URL real de la API
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(TimetonicApiService.class);

        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String sessionToken = sharedPreferences.getString("sessionToken", "");

        Call<List<Book>> call = apiService.getBooks("Bearer " + sessionToken);
        call.enqueue(new Callback<List<Book>>() {
            @Override
            public void onResponse(Call<List<Book>> call, Response<List<Book>> response) {
                if (response.isSuccessful()) {
                    List<Book> books = response.body();
                    bookAdapter = new BookAdapter(books);
                    recyclerView.setAdapter(bookAdapter);
                } else {
                    Toast.makeText(LandingActivity.this, R.string.fetch_books_error, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Book>> call, Throwable t) {
                Toast.makeText(LandingActivity.this, R.string.network_error, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
