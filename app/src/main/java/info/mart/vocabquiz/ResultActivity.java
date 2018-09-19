package info.mart.vocabquiz;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ResultActivity extends AppCompatActivity {

    private List<QuizWrapper> quizList;
    private CustomAdapter adapter;
    SharedPreferences sharedpreferences;
	private InterstitialAd mInterstitialAd;
	private AdRequest adRequest;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_result);

		// Initialize the Mobile Ads SDK.
		MobileAds.initialize(this, "ca-app-pub-3940256099942544~3347511713");

		// Create the InterstitialAd and set the adUnitId.
		mInterstitialAd = new InterstitialAd(this);
		// Defined in res/values/strings.xml
		mInterstitialAd.setAdUnitId(getString(R.string.interstitial_full_screen));

		AdRequest adRequest = new AdRequest.Builder().build();
		mInterstitialAd.loadAd(adRequest);

		mInterstitialAd.setAdListener(new AdListener() {
			public void onAdLoaded() {
				showInterstitial();
			}

			@Override
			public void onAdClosed() {
				//Toast.makeText(getApplicationContext(), "Ad is closed!", Toast.LENGTH_SHORT).show();
				finish();
			}

			@Override
			public void onAdFailedToLoad(int errorCode) {
				//Toast.makeText(getApplicationContext(), "Ad failed to load! error code: " + errorCode, Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onAdLeftApplication() {
				//Toast.makeText(getApplicationContext(), "Ad left application!", Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onAdOpened() {
				//Toast.makeText(getApplicationContext(), "Ad is opened!", Toast.LENGTH_SHORT).show();
			}
		});


		sharedpreferences = getSharedPreferences("MyPrefs",
                Context.MODE_PRIVATE);
		QuizWrapper quizWrapper = new QuizWrapper();
		quizList = new ArrayList<>();
		RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

		Gson gson = new Gson();
		for(int i = 0; i < 10; i++) {
			String questions = sharedpreferences.getString(String.valueOf(i), "");
			quizWrapper = gson.fromJson(questions, QuizWrapper.class);
			quizList.add(quizWrapper);
		}


        adapter = new CustomAdapter(this,quizList);
		// set a LinearLayoutManager with default vertical orientation
		RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
		recyclerView.setLayoutManager(mLayoutManager);
		recyclerView.setItemAnimator(new DefaultItemAnimator());


		//  call the constructor of CustomAdapter to send the reference and data to Adapter
		//CustomAdapter customAdapter = new CustomAdapter(ResultActivity.this, quizList);
		recyclerView.setAdapter(adapter); // set the Adapter to RecyclerView

		//get rating bar object
		RatingBar bar=(RatingBar)findViewById(R.id.ratingBar1); 
		bar.setNumStars(5);
		bar.setStepSize(0.5f);
		//get text view
		TextView t=(TextView)findViewById(R.id.textResult);
		//get score
		Bundle b = getIntent().getExtras();
		int score= b.getInt("score");
		int rating = score;
		//display score
		if(score == 0){
			rating = 0;
		} else if(score == 1 || score == 2){
			rating = 1;
		}else if(score == 3 || score == 4){
			rating = 2;
		}else if(score == 5 || score == 6){
			rating = 3;
		}else if(score == 7 || score == 8){
			rating = 4;
		}else if(score == 9 || score == 10){
			rating = 5;
		}
		bar.setRating(rating);
		switch (score)
		{
			case 0:
		case 1:
		case 2:
		case 3:
		case 4: t.setText("Your Score :"+score+" Oopsie! Better Luck Next Time!");
		break;
		case 5:
		case 6:
		case 7:
		case 8:t.setText("Your Score :"+score+" Hmmmm.. Someone's been reading a lot of Vocabs");
		break;
		case 9:
		case 10:t.setText("Your Score :"+score+" Who are you? A Vocabs wizard???");
		break;
		}
	}

	private void showInterstitial() {
		/*if (mInterstitialAd.isLoaded()) {
			mInterstitialAd.show();
		}*/
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		if (mInterstitialAd.isLoaded()) {
			mInterstitialAd.show();
		}
		//mInterstitialAd.loadAd(adRequest);
	}
}
