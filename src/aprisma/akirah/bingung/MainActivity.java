package aprisma.akirah.bingung;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import aprisma.akirah.bingung.slidingmenu.KlasifikasiActivity;

public class MainActivity extends Activity {

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//Hide Header action bar
		getActionBar().hide();
		
		TextView tvRegist = (TextView) findViewById(R.id.registrasi_link);
		tvRegist.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getApplicationContext(), RegistrasiActivity.class);
				startActivity(intent);
			}
		});
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.main, menu);
	    return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
	
	/*
	 * Button Action Login
	 */
	public void Login(View view){
		Toast.makeText(this, "Login", Toast.LENGTH_SHORT).show();
		Intent intent = new Intent(this, KlasifikasiActivity.class);
		startActivity(intent);
	}
	
}
