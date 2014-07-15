package aprisma.akirah.bingung;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import aprisma.akirah.bingung.detail.KlasifikasiActivity;
import aprisma.akirah.bingung.detail.PengaturanActivity;

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
				overridePendingTransition(R.anim.slide_in, R.anim.slide_in);
			}
		});
		
	}
	
	/*
	 * Button Action Login
	 */
	public void Login(View view){
		Intent intent = new Intent(this, KlasifikasiActivity.class);
		startActivity(intent);
		overridePendingTransition(R.anim.slide_in, R.anim.slide_in);
	}
	
	@Override
	public void onBackPressed() {
	} 
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
//		MenuItem item = menu.findItem(R.id.action_settings);
//		item.setVisible(false);
//		item.setEnabled(false);
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent intent = null;
		switch (item.getItemId()) {
		case R.id.action_settings:
			intent = new Intent(getApplicationContext(), PengaturanActivity.class);
			startActivity(intent);
			overridePendingTransition(R.anim.slide_in, R.anim.slide_in);
			break;
		case R.id.setlang:
			Toast.makeText(getApplicationContext(), "SET LANG", Toast.LENGTH_SHORT).show();
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
}
