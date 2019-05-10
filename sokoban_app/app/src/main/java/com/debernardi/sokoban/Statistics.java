package com.debernardi.sokoban;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Statistics extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        int n_steps = this.getSharedPreferences("statprefs", MODE_PRIVATE).getInt("n_steps", 0);
        TextView steps = findViewById(R.id.n_steps);
        steps.setText(String.format(getResources().getString(R.string.statistics_distance),
                n_steps,((double)n_steps)*30.0));
    }
}
