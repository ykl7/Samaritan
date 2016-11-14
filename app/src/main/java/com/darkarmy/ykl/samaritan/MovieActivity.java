package com.darkarmy.ykl.samaritan;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import java.util.Random;

public class MovieActivity extends AppCompatActivity {

    Button generateRecButton;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_movie);

        generateRecButton = (Button) findViewById(R.id.movieRec);

        String[] movieTitles = {"Goldfinger",
                "Breakfast at Tiffany's",
                "Harry Potter",
                "Sholay",
                "Gunda",
                "Doctor Strange",
                "X Men",
                "Theory of Everything",
                "Danish Girl",
                "War Horse",
                "The Artist",
                "Rush",
                "Eragon",
                "Up",
                "Roman Holiday"};

        Random r = new Random();
        int index = r.nextInt(16 - 0) + 0;

        String param = movieTitles[index];
        param = param.replace(" ", "+");

        url = "http://www.omdbapi.com/?t=" + param + "&y=&plot=short&r=json";

        generateRecButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                generateRecommendation();
            }
        });
    }

    public void generateRecommendation ()   {
        Intent intent = new Intent(this, Recommendation.class);
        intent.putExtra("url", url);
        startActivity(intent);
    }
}
