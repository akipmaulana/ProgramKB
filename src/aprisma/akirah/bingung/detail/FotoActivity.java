package aprisma.akirah.bingung.detail;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;
import aprisma.akirah.bingung.MainActivity;
import aprisma.akirah.bingung.R;

@SuppressLint("NewApi")
public class FotoActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.foto_profil_layout);

		getActionBar().setDisplayHomeAsUpEnabled(true);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		MenuItem itemSetting = menu.findItem(R.id.action_settings);
		itemSetting.setVisible(false);
		itemSetting.setEnabled(false);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent intent = null;
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			overridePendingTransition(R.anim.slide_out, R.anim.slide_out);
			return true;
		case R.id.setlang:
			Toast.makeText(getApplicationContext(), "SET LANG",
					Toast.LENGTH_SHORT).show();
			return true;
		case R.id.logout:
			intent = new Intent(getApplicationContext(), MainActivity.class);
			startActivity(intent);
			overridePendingTransition(R.anim.slide_out, R.anim.slide_out);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		overridePendingTransition(R.anim.slide_out, R.anim.slide_out);
	}

}
