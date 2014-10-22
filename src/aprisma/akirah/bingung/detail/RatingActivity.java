package aprisma.akirah.bingung.detail;

import java.text.DecimalFormat;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import aprisma.akirah.bingung.R;
import aprisma.akirah.bingung.holder.Klasifikasi;
import aprisma.akirah.bingung.holder.Komentar;
import aprisma.akirah.bingung.holder.User;

public class RatingActivity extends Dialog {

	private RatingBar rate_me;
	private float nilai;
	private Activity activity;
	private String id_posting;
	private ProgressDialog pDialog;
	
	private DecimalFormat oneDecimal = new DecimalFormat("#,##0.0");

	public String formatBmi(double bmi) {
	  return oneDecimal.format(bmi);
	}

	public RatingActivity(Activity activity, String id_posting) {
		super(activity);
		this.activity = activity;
		this.id_posting = id_posting;
	}
	
	public RatingActivity(Activity activity, float nilai, String id_posting) {
		super(activity);
		this.activity = activity;
		this.nilai = nilai;
		this.id_posting = id_posting;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.rating_layout);
		((TextView) findViewById(R.id.text_rating)).setText("Rate By "+User.fullname);;
		rate_me = (RatingBar) findViewById(R.id.rate_me);
		
		if (nilai != 0.f) {
			rate_me.setRating(nilai);
		}
		
		rate_me.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
			
			@Override
			public void onRatingChanged(RatingBar ratingBar, float rating,
					boolean fromUser) {
				nilai = rating;
				rate_me.setRating(nilai);
			}
		});
		
		Button submit = (Button) findViewById(R.id.button_rate);
		submit.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				new SentRating().execute();
			}
		});
	}
	
	public void closeup(){
		dismiss();
		Intent intent = new Intent(
				activity,
				DetailActivity.class);
		intent.putExtra("rating", nilai);
		intent.putExtra(Klasifikasi.TAG_NAME, DetailActivity.namaku);
		activity.startActivity(intent);
	}
	
	private class SentRating extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pDialog = new ProgressDialog(activity);
			pDialog.setMessage("Sent Rating. . .");
			pDialog.setCancelable(true);
			pDialog.show();
		}
		
		@Override
		protected Void doInBackground(Void... params) {
			Komentar.sentRating(User.id_user, id_posting, nilai+"");
			return null;
		}
		
		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			// Dismiss the progress dialog
			if (pDialog.isShowing()) {
				pDialog.dismiss();
			}
			closeup();
		}
		
	}

}
