package com.example.w1790282;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class Rating extends AppCompatActivity {
    ArrayList<Movie> movies = new ArrayList<>();
    listViewAdapter dataAdapter;
    Button idmButton;
    ListView listView;
    String checkedMovie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);

        //Declaring fields
        idmButton = findViewById(R.id.find_movie_btn_rating);

        DatabaseHandle dataBase = new DatabaseHandle(this);

        Cursor cursor = dataBase.getMovieDetails();
        if(cursor.getCount()==0){
            Toast.makeText(Rating.this, "No Movies Found !", Toast.LENGTH_SHORT).show();
        }
        else {
            while(cursor.moveToNext()){
                String title = cursor.getString(0);
                int year = Integer.parseInt(cursor.getString(1));
                String director = cursor.getString(2);
                String actors = cursor.getString(3);
                int rating = Integer.parseInt(cursor.getString(4));
                String review = cursor.getString(5);
                boolean favAsIdmCheck = false;

                Movie movie = new Movie(title, year, director, actors, rating, review, favAsIdmCheck);
                movies.add(movie);
            }
        }

        //Custom created ArrayAdapter
        dataAdapter = new listViewAdapter(this, R.layout.list_view_radio_movies, movies);
        listView = (ListView) findViewById(R.id.list_view_rating);

        // Declaring adapter to ListView
        listView.setAdapter(dataAdapter);

        idmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view;
                TextView tv;
                RadioButton rb;

                for (int i =0; i < movies.size() ; i++){
                    view = listView.getChildAt(i);
                    rb = (RadioButton) view.findViewById(R.id.radio_rating);
                    tv = (TextView) view.findViewById(R.id.textView_rating);

                    if (rb.isChecked()){
                        checkedMovie = tv.getText().toString().trim();
                        new Thread(new MovieTitlesRunnable(checkedMovie)).start();
                    }
                }
            }
        });

    }


    private class listViewAdapter extends ArrayAdapter<Movie> {

        private ArrayList<Movie> moviesList;

        private listViewAdapter(Context context, int textViewResourceId, ArrayList<Movie> moviesList) {
            super(context, textViewResourceId, moviesList);
            this.moviesList = new ArrayList<Movie>();
            this.moviesList.addAll(moviesList);
        }

        private class ViewHolder {
            RadioButton idmCheck;
            TextView title;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            listViewAdapter.ViewHolder holder;

            LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = vi.inflate(R.layout.list_view_radio_movies, null);

            holder = new ViewHolder();
            holder.title = (TextView) convertView.findViewById(R.id.textView_rating);
            holder.idmCheck = (RadioButton) convertView.findViewById(R.id.radio_rating);
            convertView.setTag(holder);

            Movie movie = moviesList.get(position);
            holder.title.setText(movie.getTitle());
            holder.idmCheck.setTag(movie);


            holder.idmCheck.setOnClickListener( new View.OnClickListener() {
                public void onClick(View v) {
                    View view;
                    RadioButton rb;

                    for (int i =0; i < movies.size() ; i++){
                        view = listView.getChildAt(i);
                        rb = (RadioButton) view.findViewById(R.id.radio_rating);
                        rb.setChecked(false);
                    }
                    holder.idmCheck.setChecked(true);
                }
            });

            holder.title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String movieTitle = holder.title.getText().toString().trim();
                    getDetails(movieTitle);
                }
            });
            return convertView;
        }

    }

    public void getDetails(String name) {
        String movieTitle = name;

        Intent intent = new Intent(this, RatingTwo.class);
        intent.putExtra("title", movieTitle);
        startActivity(intent);
    }


    class MovieTitlesRunnable implements Runnable {
        String movieTitle;

        MovieTitlesRunnable(String name) {
            movieTitle = name;
        }

        @Override
        public void run() {
            ArrayList<String> movieIdArray = new ArrayList<>();
            String movieRating = null;
            StringBuilder jSonSB = new StringBuilder("");
            StringBuilder movieTitlesSB = new StringBuilder("");
            StringBuilder jSonSB_1 = new StringBuilder("");

            try {
                //Searching for name
                URL url = new URL("https://imdb-api.com/en/API/SearchTitle/k_cmt9a50a/" + movieTitle.trim());
                HttpURLConnection con = (HttpURLConnection) url.openConnection();

                BufferedReader bf = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String line;
                while ((line = bf.readLine()) != null) {
                    jSonSB.append(line);
                }

                JSONObject json = new JSONObject(jSonSB.toString());
                JSONArray jsonArray = json.getJSONArray("results");

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonTitle = jsonArray.getJSONObject(i);

                    //Saving title in movieTitleSB
                    String movieTitle = jsonTitle.getString("title");
                    movieTitlesSB.append(movieTitle + "\n");

                    //Saving IDs in arrayList
                    String movieId = jsonTitle.getString("id");
                    movieIdArray.add(movieId);
                }


                URL url_1 = new URL("https://imdb-api.com/en/API/UserRatings/k_cmt9a50a/" + movieIdArray.get(0).trim());
                HttpURLConnection con_1 = (HttpURLConnection) url_1.openConnection();

                BufferedReader bf_1 = new BufferedReader(new InputStreamReader(con_1.getInputStream()));
                String line_1;
                while ((line_1 = bf_1.readLine()) != null) {
                    jSonSB_1.append(line_1);
                }

                JSONObject json_1 = new JSONObject(jSonSB_1.toString());

                movieRating = json_1.getString("totalRating");

            } catch (Exception ex) {
                ex.printStackTrace();
            }

            String finalMovieRating = movieRating;
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(Rating.this);
                    builder1.setMessage("Movie Rating : " + finalMovieRating + "\n" + movieTitlesSB.toString());
                    builder1.setCancelable(true);
                    builder1.setTitle("----- Movies From IMDB -----");
                    builder1.show();
                }
            });
        }
    }




}