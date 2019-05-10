package com.debernardi.sokoban;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.io.IOException;


/**
 * This class shows the win screen when a player has won.
 * It shows different text messages depending on the scores.
 * @author Bram Pulles
 */
public class WinLose  extends AppCompatActivity {

	private int currentScore, bestScore, minimumScore;
	private boolean newBest;
	private String levelFileName;
	private MediaPlayer audioVictory;

	public Button restart, levelSelect;
	public TextView txtWin, txtCurrentScore, txtBestScore, txtMinimumScore;
	private String[] levelFiles;

	/**
	 * @author Bram Pulles
	 */
	private void initializeVariables(){
		Bundle b = getIntent().getExtras();
		if(b == null)
			System.out.println("The bundle could not be created in WinLose.java.");

		try{
			currentScore = b.getInt("currentScore");
			bestScore = b.getInt("bestScore");
			minimumScore = b.getInt("minimumScore");
			newBest = b.getBoolean("newBest");
			levelFileName = b.getString("levelFileName");
		} catch (Exception e){
			e.printStackTrace();
		}

	}

	/**
	 * @author Bram Pulles
	 */
	private void initializeViews(){
		txtWin = findViewById(R.id.txtWin);
		txtCurrentScore = findViewById(R.id.currentScore);
		txtBestScore = findViewById(R.id.bestScore);
		txtMinimumScore = findViewById(R.id.minimumScore);

		restart = findViewById(R.id.button_restart);
		levelSelect = findViewById(R.id.button_levels);
	}

	/**
	 * Set the win text according to the different scores.
	 * @author Bram Pulles
	 */
	private void setTxtWin(){
		if(currentScore == minimumScore)
			txtWin.setText(R.string.won_perfect);
		else if(newBest)
			txtWin.setText(R.string.won_newbest);
		else
			txtWin.setText(R.string.won_good);
	}

	/**
	 * Set the scores on the screen.
	 * @author Bram Pulles
	 */
	private void setNumbers(){
		txtCurrentScore.setText(String.format("%d",currentScore));
		txtBestScore.setText(String.format("%d",bestScore));
		txtMinimumScore.setText((minimumScore > 0)?Integer.toString(minimumScore):"-");
	}

	/**
	 * @author Bram Pulles
	 */
	@Override
	protected  void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_winlose);

		initializeVariables();
		initializeViews();
		setTxtWin();
		setNumbers();
		try {
			levelFiles = getAssets().list("levels");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	/**
	 * @author Bram Pulles
	 */
	public void onClickLevelSelect(View view){
		Intent startLevelSelect = new Intent(this, LevelSelect.class);
		startLevelSelect.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(startLevelSelect);
	}

	/**
	 * makes you go to the next level on click, if last level is selected you go the first level
	 * @author Thomas Berghuis
	 * @param view View that called this method
	 */
	public void onClicknextLevel(View view){
		int index = 0;
		for (int i = 0; i < levelFiles.length; i++)
			if (("levels/"+levelFiles[i]).equals(levelFileName))
				index = i;
		if (index == levelFiles.length-1)
			index = 0;
		else
			index++;
		final String levelFilenameCpy = "levels/"+levelFiles[index];
		Intent startGame = new Intent(WinLose.this, GameActivity.class);
		startGame.putExtra("levelFileName",levelFilenameCpy);
		startGame.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(startGame);

	}

	/**
	 * @author Bram Pulles
	 * @param view View that called this method
	 */
	public void onClickRestartLevel(View view) {
		Intent startGame = new Intent(this, GameActivity.class);
		startGame.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startGame.putExtra("levelFileName", levelFileName);
		startActivity(startGame);
	}

	/**
	 * @author Bram Pulles
	 */
	@Override
	public void onBackPressed(){
		Intent startLevelSelect = new Intent(this, LevelSelect.class);
		startLevelSelect.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(startLevelSelect);
	}

	@Override
	protected void onStart() {
		super.onStart();
		if(!getSharedPreferences("audioprefs", MODE_PRIVATE).contains("muted")) {
			audioVictory = MediaPlayer.create(this, R.raw.solved);
			audioVictory.start();
		}
	}
}
