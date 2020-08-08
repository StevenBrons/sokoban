package view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.content.res.ResourcesCompat;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.debernardi.sokoban.GameActivity;
import com.debernardi.sokoban.R;

import org.apache.commons.io.FilenameUtils;

import static android.content.Context.MODE_PRIVATE;

public class LevelItem implements LevelSelectItem {
	private Context context;
	private String levelFile;
	private String name;
	private String author;
	private int bestMoves;

	public LevelItem(Context context, String[] levelData){
		this.context = context;
		this.levelFile = levelData[0];
		this.name = levelData[1];
		this.author = levelData[2];
		this.bestMoves = Integer.parseInt(levelData[3]);
	}

	@Override
	public View getView(boolean even, int depth) {
		SharedPreferences prefHighscores = context.getSharedPreferences("Highscores", MODE_PRIVATE);
		int highscore = prefHighscores.getInt(FilenameUtils.removeExtension(levelFile),-1);

		RelativeLayout level = new RelativeLayout(context);
		if (even){
			level.setBackgroundColor(context.getResources().getColor(R.color.levelSelectBackgroundEven));
		} else{
			level.setBackgroundColor(context.getResources().getColor(R.color.levelSelectBackgroundOdd));
		}

//		ImageView previewView = new ImageView(context);
//		previewView.setId(View.generateViewId());
//		previewView.setAdjustViewBounds(true);
//		try{
//			Bitmap preview = GameView.getLevelBitmap(levelItem,false);
//			previewView.setImageBitmap(Bitmap.createScaledBitmap(preview,previewSize,
//					preview.getHeight()*previewSize/preview.getWidth(),false));
//		}
//		catch (Exception e){
//			previewView.setImageResource(R.color.colorPrimary);
//		}

		// Make level title text
		TextView levelNameView = new TextView(context);
		levelNameView.setText(name);
		levelNameView.setTextSize(20);
		levelNameView.setId(View.generateViewId());
		levelNameView.setTypeface(ResourcesCompat.getFont(context,R.font.dtm_mono));

		// Make level author text
		TextView authorNameView = new TextView(context);
		authorNameView.setText(author);
		authorNameView.setTextSize(16);
		authorNameView.setId(View.generateViewId());
		authorNameView.setTypeface(ResourcesCompat.getFont(context,R.font.dtm_mono));

		// Make best score text
		TextView bestTextView = new TextView(context);
		bestTextView.setText(String.format("%s/%s",highscore>=0?highscore:"-",
				bestMoves>=0?bestMoves:"-"));
		bestTextView.setTextSize(16);
		bestTextView.setPadding(10,3,10,3);
		bestTextView.setId(View.generateViewId());
		bestTextView.setTypeface(ResourcesCompat.getFont(context,R.font.dtm_mono));

		// Add elements to level container
//		level.addView(previewView);
		level.addView(levelNameView);
		level.addView(authorNameView);
		level.addView(bestTextView);

		// Add layout rules
//		RelativeLayout.LayoutParams previewViewLp = (RelativeLayout.LayoutParams) previewView.getLayoutParams();
//		previewViewLp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
//		previewViewLp.addRule(RelativeLayout.CENTER_VERTICAL);
//		previewViewLp.height = max(screenSize.x,screenSize.y)/numLevelsOnScreen;
//		previewViewLp.width = max(screenSize.x,screenSize.y)/numLevelsOnScreen;
		RelativeLayout.LayoutParams levelNameViewLp = (RelativeLayout.LayoutParams) levelNameView.getLayoutParams();
		levelNameViewLp.addRule(RelativeLayout.CENTER_HORIZONTAL);
		levelNameViewLp.addRule(RelativeLayout.ALIGN_PARENT_TOP);
		RelativeLayout.LayoutParams authorNameViewLp = (RelativeLayout.LayoutParams) authorNameView.getLayoutParams();
		authorNameViewLp.addRule(RelativeLayout.CENTER_HORIZONTAL);
		authorNameViewLp.addRule(RelativeLayout.BELOW,levelNameView.getId());
		RelativeLayout.LayoutParams bestTextViewLp = (RelativeLayout.LayoutParams) bestTextView.getLayoutParams();
		bestTextViewLp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		bestTextViewLp.addRule(RelativeLayout.CENTER_VERTICAL);

		// Add click functionality
		level.setClickable(true);
		final String levelFilenameCpy = "levels/"+levelFile;
		level.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent startGame = new Intent(context, GameActivity.class);
				startGame.putExtra("levelFileName",levelFilenameCpy);
				context.startActivity(startGame);
			}
		});

		return level;
	}
}
