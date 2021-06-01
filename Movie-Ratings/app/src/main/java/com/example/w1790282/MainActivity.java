package com.example.w1790282;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.EventListener;

public class MainActivity extends AppCompatActivity {
    public Button regMovieButton;
    public Button displayMoviesButton;
    public Button favouritesButton;
    public Button editMoviesButton;
    public Button searchButton;
    public Button ratingButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //On click of Register Movie button
        regMovieButton = findViewById(R.id.reg_movie_btn_main);
        regMovieButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { openActivityRegMovie(); }
        });

        //On click of Display Movies Button
        displayMoviesButton = findViewById(R.id.dis_movie_btn_main);
        displayMoviesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivityDisplayMovies();
            }
        });

        //On click of Favourites
        favouritesButton = findViewById(R.id.fav_btn_main);
        favouritesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivityFavouriteMovies();
            }
        });

        //On click of Edit Movies
        editMoviesButton = findViewById(R.id.edit_movie_btn_main);
        editMoviesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivityEditMovies();
            }
        });

        //On click of Search
        searchButton = findViewById(R.id.search_btn_main);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivitySearch();

            }
        });

        //On click of Ratings
        ratingButton = findViewById(R.id.rating_btn_main);
        ratingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivityRating();
            }
        });



    }


    public void openActivityRegMovie() {
        Intent intent = new Intent(this, RegisterMovie.class);
        startActivity(intent);
    }

    public void openActivityDisplayMovies() {
        Intent intent = new Intent(this, DisplayMovies.class);
        startActivity(intent);
    }

    public void openActivityFavouriteMovies() {
        Intent intent = new Intent(this, FavouriteMovies.class);
        startActivity(intent);
    }

    public void openActivityEditMovies() {
        Intent intent = new Intent(this, EditMovies.class);
        startActivity(intent);
    }

    public void openActivitySearch() {
        Intent intent = new Intent(this, Search.class);
        startActivity(intent);
    }

    public void openActivityRating() {
        Intent intent = new Intent(this, Rating.class);
        startActivity(intent);
    }
}