package com.example.w1790282;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class RatingTwo extends AppCompatActivity {
    ImageView MovieImageView;
    String picture_url;
    String movieTitle;
    TextView titleTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating_two);

        movieTitle = getIntent().getStringExtra("title");
        MovieImageView = findViewById(R.id.imgview);
        titleTextView = findViewById(R.id.title);
        titleTextView.setText(movieTitle);

        new Thread(new CocktailDetailsRunnable(movieTitle)).start();
    }

    class CocktailDetailsRunnable implements Runnable {
        String title;

        CocktailDetailsRunnable(String name) {
            title = name;
        }

        @Override
        public void run() {
            StringBuilder stb = new StringBuilder("");

            try {
                URL url = new URL("https://imdb-api.com/en/API/SearchMovie/k_cmt9a50a/" + title.trim());
                HttpURLConnection con = (HttpURLConnection) url.openConnection();

                BufferedReader bf = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String line;
                while ((line = bf.readLine()) != null) {
                    stb.append(line);
                }

                JSONObject json = new JSONObject(stb.toString());
                JSONArray jsonArray = json.getJSONArray("results");

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String movieName = jsonObject.getString("title");

                    if (movieName.toLowerCase().equals(title.toLowerCase())) {
                        picture_url = jsonObject.getString("image");
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            Bitmap moviePicture = getBitmap();

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    MovieImageView.setImageBitmap(moviePicture);

                }
            });
        }

        // Taking a bitmap image from the URL in JSON
        Bitmap getBitmap() {
            Bitmap bitmap = null;
            try {
                URL url = new URL(picture_url);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                BufferedInputStream stream = new BufferedInputStream(con.getInputStream());

                bitmap = BitmapFactory.decodeStream(stream);
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }

            return bitmap;
        }
    }
}