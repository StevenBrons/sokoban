package com.debernardi.sokoban;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class LevelSelect extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_select);
    }

    public void onClickGame(View view){
        Intent startGame = new Intent(LevelSelect.this, Game.class);
        startActivity(startGame);
    }
}
