package com.debernardi.sokoban;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Settings extends AppCompatActivity {

    /**
    * Initializes the settings screen so that the buttons all contain the correct text
    * @author Robert Korpinkov
    * @param savedInstanceState Bundle to pass to AppCompatActivity
    * */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        TextView txtControl = findViewById(R.id.toggleInputMethod);
        if(getSharedPreferences("controlprefs", MODE_PRIVATE).contains("swipe_off")){
            txtControl.setText(R.string.control_button);
        } else {
            txtControl.setText(R.string.control_swipe);
        }
        TextView txtAudio = findViewById(R.id.audio);
        if(getSharedPreferences("audioprefs", MODE_PRIVATE).contains("muted")){
            txtAudio.setText(R.string.audio_muted);
        }
        else{
            txtAudio.setText(R.string.audio_enabled);
        }
        SharedPreferences donutMode = getSharedPreferences("backgroundprefs", MODE_PRIVATE);
        boolean donutsunlocked = donutMode.getBoolean("donutsunlocked", false);
        SharedPreferences.Editor e = donutMode.edit();
        int n_steps = getSharedPreferences("statprefs", MODE_PRIVATE).getInt("n_steps", 0);
        if(n_steps>=1000)
            donutsunlocked = true;
        if(!donutsunlocked){
            TextView donutModeView = findViewById(R.id.donutMode);
            donutModeView.setText(String.format(
                    getResources().getString(R.string.donut_mode_steps_left),1000-n_steps));
        } else {
            e.putBoolean("donutsunlocked", true);
            TextView donutModeView = findViewById(R.id.donutMode);
            if(donutMode.contains("donutsenabled")) {
                donutModeView.setText(R.string.donut_enabled);
            } else {
                donutModeView.setText(R.string.donut_disabled);
            }
        }
        e.apply();
    }
    /**
     * Toggles from swipe to buttons as input methods
     * @author Robert Koprinkov
     * @param v View that called this method
     * */
    public void onClickToggleControls(View v){
        SharedPreferences controlpref = getSharedPreferences("controlprefs", MODE_PRIVATE);
        SharedPreferences.Editor e = controlpref.edit();
        TextView text = findViewById(R.id.toggleInputMethod);
        if(controlpref.contains("swipe_off")){
            e.remove("swipe_off");
            e.remove("buttons");
            text.setText(R.string.control_swipe);
        } else {
            e.putInt("swipe_off", 1);
            e.putInt("buttons", 1);
            text.setText(R.string.control_button);
        }
        e.apply();
    }

    /**
     * clears all game progress
     * @author Robert Koprinkov
     * @param view View that called this method
     */
    public void onClickClearData(View view){
        SharedPreferences scoreprefs = getSharedPreferences("Highscores", MODE_PRIVATE);
        SharedPreferences.Editor e = scoreprefs.edit();
        e.clear();
        e.apply();
        e = getSharedPreferences("statprefs", MODE_PRIVATE).edit();
        e.clear();
        e.apply();
        Intent home = new Intent(this, MainActivity.class);
        home.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        e = getSharedPreferences("backgroundprefs", MODE_PRIVATE).edit();
        e.clear();
        e.apply();
        startActivity(home);
    }
    /**
     * Enables donutmode if the option has been unlocked.
     * @author Robert Koprinkov
     * @param view View that called this method
     * */
    public void onClickDonutMode(View view){
        SharedPreferences donutmode = getSharedPreferences("backgroundprefs", MODE_PRIVATE);
        boolean donutsunlocked = donutmode.getBoolean("donutsunlocked", false);
        SharedPreferences.Editor e = donutmode.edit();
        if(donutsunlocked){
            //toggle donut mode!
            if(donutmode.contains("donutsenabled")){
                e.remove("donutsenabled");
                ((TextView)view).setText(R.string.donut_disabled);
            } else {
                e.putBoolean("donutsenabled", true);
                ((TextView)view).setText(R.string.donut_enabled);
            }
            e.apply();
        }

    }


    /**
     * mutes sounds form the app
     * @author Robert Koprinkov
     * @param view View that called this method
     */
    public void onClickMute(View view){
        TextView v = findViewById(R.id.audio);
        SharedPreferences audioprefs = getSharedPreferences("audioprefs", MODE_PRIVATE);
        SharedPreferences.Editor e = audioprefs.edit();
        if(audioprefs.contains("muted")){
            e.remove("muted");
            v.setText(R.string.audio_enabled);
        } else {
            e.putBoolean("muted", true);
            v.setText(R.string.audio_muted);
        }
        e.apply();
    }
}
