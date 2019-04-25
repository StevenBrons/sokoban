package com.debernardi.sokoban;

import android.content.Intent;
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
        SharedPreferences donutmode = getSharedPreferences("backgroundprefs", MODE_PRIVATE);
        boolean donutsunlocked = donutmode.getBoolean("donutsunlocked", false);
        SharedPreferences.Editor e = donutmode.edit();
        int n_steps = getSharedPreferences("statprefs", MODE_PRIVATE).getInt("n_steps", 0);
        if(n_steps>=1000)
            donutsunlocked = true;
        if(!donutsunlocked){
            TextView donutmode_view = (TextView) findViewById(R.id.donutMode);
            donutmode_view.setText("put " + (1000-n_steps) + " more steps!");
        } else {
            e.putBoolean("donutsunlocked", true);
            TextView donutmode_view = (TextView) findViewById(R.id.donutMode);
            if(donutmode.contains("donutsenabled")) {
                donutmode_view.setText("DONUT MODE: enabled");
            } else {
                donutmode_view.setText("DONUT MODE: disabled");
            }
        }
        e.commit();
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
        Intent home = new Intent(this, MainActivity.class);
        home.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        e = getSharedPreferences("backgroundprefs", MODE_PRIVATE).edit();
        e.clear();
        e.commit();
        startActivity(home);
    }

    public void onClickDonutMode(View view){
        SharedPreferences donutmode = getSharedPreferences("backgroundprefs", MODE_PRIVATE);
        boolean donutsunlocked = donutmode.getBoolean("donutsunlocked", false);
        SharedPreferences.Editor e = donutmode.edit();
        if(donutsunlocked){
            //toggle donut mode!
            if(donutmode.contains("donutsenabled")){
                e.remove("donutsenabled");
                ((TextView)view).setText("DONUT MODE: disabled");
            } else {
                e.putBoolean("donutsenabled", true);
                ((TextView)view).setText("DONUT MODE: enabled");
            }
            e.commit();
        }

    }


}
