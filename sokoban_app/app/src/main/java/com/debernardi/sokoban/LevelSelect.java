package com.debernardi.sokoban;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class LevelSelect extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_select);
        LinearLayout layLevels = (LinearLayout) findViewById(R.id.layLevels);
        for (int i = 0;i<5;i++){
            LinearLayout level = new LinearLayout(this);
            level.setOrientation(LinearLayout.HORIZONTAL);

            ImageView preview = new ImageView(this);
            preview.setImageResource(R.color.colorPrimary);

            TextView levelName = new TextView(this);
            levelName.setText(String.format("Level: %d", i));
            levelName.setTextSize(18);

            TextView bestText = new TextView(this);
            bestText.setText(String.format("%d/%d",0,0));
            bestText.setTextSize(12);

            level.addView(preview);
            level.addView(levelName);
            level.addView(bestText);
            layLevels.addView(level);
        }
    }

    public void onClickGame(View view){
        Intent startGame = new Intent(this, Game.class);
        startActivity(startGame);
    }
}
