package com.debernardi.sokoban;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickLevelSelect(View view){
        Intent startLevelSelect = new Intent(MainActivity.this, LevelSelect.class);
        startActivity(startLevelSelect);
    }

    public void onClickStatistics(View view){
        Intent startStatistics = new Intent(MainActivity.this, Statistics.class);
        startActivity(startStatistics);
    }

    public void onClickSettings(View view){
        Intent startSettings = new Intent(MainActivity.this, Settings.class);
        startActivity(startSettings);
    }
}
