package view;

import android.content.Context;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.CardView;
import android.transition.ChangeBounds;
import android.transition.Fade;
import android.transition.TransitionManager;
import android.transition.TransitionSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.debernardi.sokoban.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class LevelCollectionItem implements LevelSelectItem {
	private Context context;
	private ArrayList<LevelSelectItem> elements = new ArrayList<>();
	private String name;
	private String author;

	private ViewGroup rootView;

	public LevelCollectionItem(Context context, String[] collectionData, ViewGroup rootView){
		this.context = context;
		String filename = collectionData[0];
		this.name = collectionData[1];
		this.author = collectionData[2];
		this.rootView = rootView;

		try{
			Scanner s = new Scanner(context.getAssets().open("levels/"+ filename));
			while (s.hasNextLine()){
				String[] levelData = s.nextLine().split(", ");
				if (!levelData[3].equals("0")){
					elements.add(new LevelItem(context, levelData));
				}
				else{
					elements.add(new LevelCollectionItem(context, levelData, rootView));
				}
			}
		} catch (IOException e){
			e.printStackTrace();
		}
	}

	@Override
	public View getView(boolean even, int depth) {
		final LinearLayout collectionBody = new LinearLayout(context);
		collectionBody.setOrientation(LinearLayout.VERTICAL);
		boolean evenItem = false;
		boolean hasCollectionItem = false;
		for (LevelSelectItem element: elements){
			collectionBody.addView(element.getView(evenItem,depth+1));
			hasCollectionItem |= (element instanceof LevelCollectionItem);
			evenItem = !evenItem;
		}

		if (!hasCollectionItem){
			collectionBody.setVisibility(GONE);
		}

		LinearLayout collectionItem = new LinearLayout(context);
		collectionItem.setOrientation(LinearLayout.VERTICAL);

		RelativeLayout collectionHeader = new RelativeLayout(context);
		if (!name.equals("")){
			if (even){
				collectionHeader.setBackgroundColor(context.getResources().getColor(R.color.colorPrimaryDark));
			} else{
				collectionHeader.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
			}

			// Make collection title text
			TextView levelNameView = new TextView(context);
			levelNameView.setText(name);
			levelNameView.setTextSize(20);
			levelNameView.setId(View.generateViewId());
			levelNameView.setTypeface(ResourcesCompat.getFont(context, R.font.dtm_mono));

			// Make collection author text
			TextView authorNameView = new TextView(context);
			authorNameView.setText(author);
			authorNameView.setTextSize(16);
			authorNameView.setId(View.generateViewId());
			authorNameView.setTypeface(ResourcesCompat.getFont(context,R.font.dtm_mono));

			// Add elements to level container
			collectionHeader.addView(levelNameView);
			collectionHeader.addView(authorNameView);

			// Add layout rules
			RelativeLayout.LayoutParams levelNameViewLp = (RelativeLayout.LayoutParams) levelNameView.getLayoutParams();
			levelNameViewLp.addRule(RelativeLayout.CENTER_HORIZONTAL);
			levelNameViewLp.addRule(RelativeLayout.ALIGN_PARENT_TOP);
			RelativeLayout.LayoutParams authorNameViewLp = (RelativeLayout.LayoutParams) authorNameView.getLayoutParams();
			authorNameViewLp.addRule(RelativeLayout.CENTER_HORIZONTAL);
			authorNameViewLp.addRule(RelativeLayout.BELOW,levelNameView.getId());

			// Add click functionality
			collectionHeader.setClickable(true);
			collectionHeader.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					TransitionSet transition = new TransitionSet();
					transition.addTransition(new ChangeBounds());
					transition.addTransition(new Fade());
					transition.setOrdering(TransitionSet.ORDERING_TOGETHER);
					transition.setInterpolator(new AccelerateDecelerateInterpolator());
					transition.setDuration(200);
					if (collectionBody.getVisibility() == VISIBLE){

					TransitionManager.beginDelayedTransition(rootView, transition);
					collectionBody.setVisibility(GONE);
				}
				else if (collectionBody.getVisibility() == GONE){
					TransitionManager.beginDelayedTransition(rootView, transition);
					collectionBody.setVisibility(VISIBLE);
				}
				}
			});
		}
		CardView headerCard = new CardView(context);
		headerCard.addView(collectionHeader);
		headerCard.setRadius(0.0f);

		collectionItem.addView(headerCard);
		collectionItem.addView(collectionBody);

		CardView collectionCard = new CardView(context);
		collectionCard.addView(collectionItem);
		collectionCard.setRadius(0.0f);

		switch (depth){
			case 0: collectionItem.setBackgroundColor(context.getResources().getColor(R.color.colorPrimaryLight)); break;
			default: collectionItem.setBackgroundColor(context.getResources().getColor(R.color.levelSelectBackgroundOdd)); break;
		}

		CardView.LayoutParams collectionCardLp = new CardView.LayoutParams(
				CardView.LayoutParams.MATCH_PARENT,
				CardView.LayoutParams.WRAP_CONTENT
		);
		collectionCardLp.setMargins(5,5,5,5);
		collectionCard.setLayoutParams(collectionCardLp);

		return collectionCard;
	}
}
