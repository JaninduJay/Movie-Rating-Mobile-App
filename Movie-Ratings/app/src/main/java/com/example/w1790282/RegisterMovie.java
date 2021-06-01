package com.example.w1790282;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterMovie extends AppCompatActivity {
    Button saveButton;
    EditText titleEditText;
    EditText directorEditText;
    EditText actorsEditText;
    EditText reviewEditText;
    EditText yearEditText;
    EditText ratingEditText;
    TextView yearErrorTextView;
    TextView ratingErrorTextView;

    Boolean errorFlag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_movie);

        //Declaring fields
        yearEditText = findViewById(R.id.year_txt_edit_reg);
        ratingEditText = findViewById(R.id.rating_txt_edit_reg);
        titleEditText = findViewById(R.id.title_txt_edit_reg);
        directorEditText = findViewById(R.id.director_txt_edit_reg);
        actorsEditText = findViewById(R.id.actors_txt_edit_reg);
        reviewEditText = findViewById(R.id.review_txt_edit_reg);
        yearErrorTextView = findViewById(R.id.year_error_msg_reg);
        ratingErrorTextView = findViewById(R.id.rating_error_msg_reg);
        saveButton = findViewById(R.id.save_btn_reg);
        DatabaseHandle db = new DatabaseHandle(this);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Re-setting fields
                yearErrorTextView.setText("");
                ratingErrorTextView.setText("");
                errorFlag = true;

                if (titleEditText.getText().toString().trim().isEmpty() || yearEditText.getText().toString().trim().isEmpty() || directorEditText.getText().toString().trim().isEmpty() || actorsEditText.getText().toString().trim().isEmpty() || ratingEditText.getText().toString().trim().isEmpty() || reviewEditText.getText().toString().trim().isEmpty()) {
                    Toast.makeText(RegisterMovie.this,"Please fill all the fields !",Toast.LENGTH_SHORT).show();
                }else {
                    String title = titleEditText.getText().toString().trim();
                    String director = directorEditText.getText().toString().trim();
                    String actors = actorsEditText.getText().toString().trim();
                    String review = reviewEditText.getText().toString().trim();
                    int year = Integer.parseInt(yearEditText.getText().toString());
                    int rating = Integer.parseInt(ratingEditText.getText().toString());
                    int fav = 0;

                    if (year > 2021 || year < 1895) {
                        yearErrorTextView.setText("Year must be between 1895 and 2021");
                        errorFlag = false;
                    }

                    if (rating > 10 || rating < 1  ) {
                        ratingErrorTextView.setText("Rating must be between 1 and 10");
                        errorFlag = false;
                    }

                    if (errorFlag) {
                        //Adding data into database
                        try {
                            db.insertMovieDetails(title, year, director, actors, rating, review, fav);
                            Toast.makeText(RegisterMovie.this, "Movie Added", Toast.LENGTH_SHORT).show();
                        }catch (SQLiteConstraintException ec){
                            ec.printStackTrace();
                        }

                        titleEditText.setText("");
                        yearEditText.setText("");
                        directorEditText.setText("");
                        actorsEditText.setText("");
                        ratingEditText.setText("");
                        reviewEditText.setText("");

                    }else {
                        Toast.makeText(RegisterMovie.this,"Please correct the errors !",Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
    }



}