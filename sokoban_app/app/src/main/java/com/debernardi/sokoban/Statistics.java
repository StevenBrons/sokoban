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
        TextView steps = (TextView) findViewById(R.id.n_steps);
        steps.setText("You have set " + n_steps + " steps. That means you burned " + String.format("%.2f", ((double)n_steps)*30.0) + " calories! Keep it up!");
    }
}
