package com.debernardi.sokoban;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/* EXAMPLE CALL OF THIS ACTIVITY (used for testing)
public void onClickWin(View view){
	Bundle b = new Bundle();
	b.putInt("currentScore", 16);
	b.putInt("bestScore", 12);
	b.putInt("minimumScore", 14);
	b.putBoolean("newBest", true);

	Intent startWinLose = new Intent(this, WinLose.class);
	startWinLose.putExtras(b);
	startActivity(startWinLose);
}
*/

public class WinLose  extends AppCompatActivity {

	private int currentScore, bestScore, minimumScore;
	private boolean newBest;
	private String levelFileName;

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
		startActivity(startLevelSelect);
	}

	public void onClickRestartLevel(View view) {
		Intent startGame = new Intent(this, GameActivity.class);
		startGame.putExtra("levelFileName", levelFileName);
		startActivity(startGame);
	}

	@Override
	public void onBackPressed(){
		super.onBackPressed();
		startActivity(new Intent(this, LevelSelect.class));
		finish();
	}
}
