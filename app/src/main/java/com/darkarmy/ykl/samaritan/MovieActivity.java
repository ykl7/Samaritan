package com.darkarmy.ykl.samaritan;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class MovieActivity extends AppCompatActivity {

    Button generateRecButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_movie);

        generateRecButton = (Button) findViewById(R.id.movieRec);

        generateRecButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                generateRecommendation();
            }
        });
    }

    public void generateRecommendation ()   {
        Intent intent = new Intent(this, Recommendation.class);
        startActivity(intent);
    }
}
