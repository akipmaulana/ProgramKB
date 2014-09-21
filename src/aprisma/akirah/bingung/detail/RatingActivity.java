package aprisma.akirah.bingung.detail;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import aprisma.akirah.bingung.R;
import aprisma.akirah.bingung.holder.User;

public class RatingActivity extends Dialog {

	private RatingBar rate_me;
	private float nilai;
	private Activity activity;

	public RatingActivity(Activity activity) {
		super(activity);
		this.activity = activity;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.rating_layout);
		((TextView) findViewById(R.id.text_rating)).setText("Rate By "+User.fullname);;
		rate_me = (RatingBar) findViewById(R.id.rate_me);
		rate_me.setOnTouchListener(new View.OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				float touchPositionX = event.getX();
				float width = rate_me.getWidth();
				float starsf = (touchPositionX / width) * 5.0f;
				nilai = starsf + 1;
				rate_me.setRating(nilai);
				return false;
			}
		});
		
		Button submit = (Button) findViewById(R.id.button_rate);
		submit.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(activity, "Sukses "+nilai, Toast.LENGTH_SHORT).show();
			}
		});
	}

}
