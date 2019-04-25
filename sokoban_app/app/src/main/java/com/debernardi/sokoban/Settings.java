package com.debernardi.sokoban;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

public class Settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        TextView v = (TextView) findViewById(R.id.toggleInputMethod);
        if(getSharedPreferences("controlprefs", MODE_PRIVATE).contains("swipe_off")){
            v.setText("CONTROLS: buttons");
        } else {
            v.setText("CONTROLS: swipe");
        }
    }

    public void onClickToggleControls(View v){
        SharedPreferences controlpref = getSharedPreferences("controlprefs", MODE_PRIVATE);
        SharedPreferences.Editor e = controlpref.edit();
        TextView text = (TextView) findViewById(R.id.toggleInputMethod);
        if(controlpref.contains("swipe_off")){
            e.remove("swipe_off");
            e.remove("buttons");
            text.setText("CONTROLS: swipe");
        } else {
            e.putInt("swipe_off", 1);
            e.putInt("buttons", 1);
            text.setText("CONTROLS: buttons");
        }
        e.commit();
    }

    public void onClickClearData(View view){
        SharedPreferences scoreprefs = getSharedPreferences("Highscores", MODE_PRIVATE);
        SharedPreferences.Editor e = scoreprefs.edit();
        e.clear();
        e.commit();
        e = getSharedPreferences("statprefs", MODE_PRIVATE).edit();
        e.clear();
        e.commit();
    }


}
