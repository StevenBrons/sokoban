package com.debernardi.sokoban;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * @author Bram Pulles
 * This class shows the win screen when a player has won.
 * It shows different text messages depending on the scores.
 */
public class WinLose  extends AppCompatActivity {

	private int currentScore, bestScore, minimumScore;
	private boolean newBest;
	private String levelFileName;
	private MediaPlayer audioVictory;

	public Button restart, levelSelect;
	public TextView txtWin, txtCurrentScore, txtBestScore, txtMinimumScore;

	private void initializeVariables(){
		Bundle b = getIntent().getExtras();
		if(b == null)
			System.out.println("The bundle could not be created in WinLose.java.");

		currentScore = b.getInt("currentScore");
		bestScore = b.getInt("bestScore");
		minimumScore = b.getInt("minimumScore");
		newBest = b.getBoolean("newBest");
		levelFileName = b.getString("levelFileName");
	}

	private void initializeViews(){
		txtWin = findViewById(R.id.txtWin);
		txtCurrentScore = findViewById(R.id.currentScore);
		txtBestScore = findViewById(R.id.bestScore);
		txtMinimumScore = findViewById(R.id.minimumScore);

		restart = findViewById(R.id.button_restart);
		levelSelect = findViewById(R.id.button_levels);
	}

	private void setTxtWin(){
		if(currentScore == minimumScore)
			txtWin.setText(R.string.won_perfect);
		else if(newBest)
			txtWin.setText(R.string.won_newbest);
		else
			txtWin.setText(R.string.won_good);
	}

	private void setNumbers(){
		txtCurrentScore.setText(Integer.toString(currentScore));
		txtBestScore.setText(Integer.toString(bestScore));
		txtMinimumScore.setText((minimumScore > 0)?Integer.toString(minimumScore):"-");
	}

	@Override
	protected  void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_winlose);

		initializeVariables();
		initializeViews();
		setTxtWin();
		setNumbers();
	}

	public void onClickLevelSelect(View view){
		Intent startLevelSelect = new Intent(this, LevelSelect.class);
		startLevelSelect.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(startLevelSelect);
	}

	public void onClickRestartLevel(View view) {
		Intent startGame = new Intent(this, GameActivity.class);
		startGame.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startGame.putExtra("levelFileName", levelFileName);
		startActivity(startGame);
	}

	@Override
	public void onBackPressed(){
		Intent startLevelSelect = new Intent(this, LevelSelect.class);
		startLevelSelect.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(startLevelSelect);
	}

	@Override
	protected void onStart() {
		super.onStart();
		audioVictory = MediaPlayer.create(this,R.raw.solved);
		audioVictory.start();
	}
}
