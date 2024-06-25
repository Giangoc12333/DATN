package com.example.RealFilm.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.RealFilm.R;
import com.example.RealFilm.adapter.ManagerMovieAdapter;
import com.example.RealFilm.listerner.MovieDeleteListener;
import com.example.RealFilm.model.ApiResponse;
import com.example.RealFilm.model.Movie;
import com.example.RealFilm.service.ApiService;
import com.example.RealFilm.service.MovieService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ManagerMovies extends AppCompatActivity implements MovieDeleteListener {

    // ...rest ui

    private List<Movie> movies = new ArrayList<>();
    private List<Movie> filteredMovies = new ArrayList<>();
    private RecyclerView recyclerView;
    private ImageButton btnBack;

    private EditText searchBar;
    private ImageView searchIcon;

    // ...rest different
    private ManagerMovieAdapter managerMovieAdapter;
    private LinearLayoutManager linearLayoutManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_movies);

        // ...
        recyclerView = findViewById(R.id.list_movies);
        btnBack = findViewById(R.id.btn_back);

        searchBar = findViewById(R.id.search_bar);
        searchIcon = findViewById(R.id.search_icon);


        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);


        // ...
        managerMovieAdapter = new ManagerMovieAdapter(movies, this);
        recyclerView.setAdapter(managerMovieAdapter);

        // ...rest api
        getMovies();
        setupSearchBar();
        // ...
        btnBackOnClick();
    }

    private void btnBackOnClick () {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }

        });
    }




    private void getMovies() {
        MovieService movieService = ApiService.createService(MovieService.class);
        Call<ApiResponse<List<Movie>>> call = movieService.getManagertMovies();
        call.enqueue(new Callback<ApiResponse<List<Movie>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<Movie>>> call, Response<ApiResponse<List<Movie>>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Movie> moviesRes = response.body().getData();
                    if (moviesRes != null) {
                        movies.clear();
                        movies.addAll(moviesRes);
                        filterMovies(searchBar.getText().toString()); // Filter with current search query
                        Log.d("MANAGER_MOVIES", "Movies loaded: " + movies.size());
                    } else {
                        Log.d("MANAGER_MOVIES", "Empty movie list");
                    }
                } else {
                    Log.e("MANAGER_MOVIES", "Failed to load movies");
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<List<Movie>>> call, Throwable t) {
                Log.e("MANAGER_MOVIES", "API call failed", t);
            }
        });
    }

    private void setupSearchBar() {
        searchIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterMovies(searchBar.getText().toString());
            }
        });

        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterMovies(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void filterMovies(String query) {
        List<Movie> filteredMovies = new ArrayList<>();
        if (!query.isEmpty()) {
            for (Movie movie : movies) {
                if (movie.getTitle().toLowerCase().contains(query.toLowerCase())) {
                    filteredMovies.add(movie);
                }
            }
        } else {
            filteredMovies.addAll(movies);
        }
        managerMovieAdapter.setMovies(filteredMovies);
        managerMovieAdapter.notifyDataSetChanged();
        Log.d("MANAGER_MOVIES", "Filtered movies: " + filteredMovies.size());
    }


//    private void getMovies() {
//        MovieService movieService = ApiService.createService(MovieService.class);
//        Call<ApiResponse<List<Movie>>> call = movieService.getManagertMovies();
//        call.enqueue(new Callback<ApiResponse<List<Movie>>>() {
//            @Override
//            public void onResponse(Call<ApiResponse<List<Movie>>> call, Response<ApiResponse<List<Movie>>> response) {
//                if (response.isSuccessful()) {
//                    List<Movie> moviesRes = response.body().getData();
//                    if (moviesRes != null && !moviesRes.isEmpty()) {
//                        movies = moviesRes;
//                        managerMovieAdapter.setMovies(movies);
//                        managerMovieAdapter.notifyDataSetChanged();
//                    } else {
//                        Log.d("MANAGER_MOVIES", "Empty movie list");
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ApiResponse<List<Movie>>> call, Throwable t) {
//                Log.e("MANAGER_MOVIES", "API call failed", t);
//            }
//        });
//    }

    @Override
    public void onDeleteMovie(Integer movieId) {
        MovieService movieService = ApiService.createService(MovieService.class);
        Call<ApiResponse> call = movieService.deleteMovie(movieId);
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful()) {
                    getMovies();

                } else {
                    Log.e("MANAGER_MOVIES", "Delete movie failed");
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Log.e("MANAGER_MOVIES", "API call failed", t);
            }
        });

    }
}