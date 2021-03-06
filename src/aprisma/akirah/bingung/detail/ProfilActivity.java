package aprisma.akirah.bingung.detail;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import aprisma.akirah.bingung.MainActivity;
import aprisma.akirah.bingung.R;
import aprisma.akirah.bingung.holder.User;

public class ProfilActivity extends Activity {

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.profil_activity);
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (!User.ISLOGIN){
			Intent intent = new Intent(getApplicationContext(),
					KlasifikasiActivity.class);
			startActivity(intent);
			return;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent intent = null;
		switch (item.getItemId()) {
		case android.R.id.home:
			backToSetting();
			return true;
		case R.id.action_settings:
			backToSetting();
			return true;
		case R.id.setlang:
			Toast.makeText(getApplicationContext(), "SET LANG",
					Toast.LENGTH_SHORT).show();
			return true;
		case R.id.logout:
			User.ISLOGIN = false;
			intent = new Intent(getApplicationContext(), MainActivity.class);
			startActivity(intent);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onBackPressed() {
		backToSetting();
	}
	
	private void backToSetting(){
		Intent intent = new Intent(getApplicationContext(), PengaturanActivity.class);
		startActivity(intent);
		overridePendingTransition(R.anim.slide_out, R.anim.slide_out);
	}
	
	public void fotoClicked(View view){
		Intent intent = new Intent(getApplicationContext(), FotoActivity.class);
		startActivity(intent);
		overridePendingTransition(R.anim.slide_in, R.anim.slide_in);
	}
	
	public void detailClicked(View view){
		Intent intent = new Intent(getApplicationContext(), DetailProfilActivity.class);
		startActivity(intent);
		overridePendingTransition(R.anim.slide_in, R.anim.slide_in);
	}
	
	public void akunClicked(View view){
		Intent intent = new Intent(getApplicationContext(), AkunActivity.class);
		startActivity(intent);
		overridePendingTransition(R.anim.slide_in, R.anim.slide_in);
	}

}
